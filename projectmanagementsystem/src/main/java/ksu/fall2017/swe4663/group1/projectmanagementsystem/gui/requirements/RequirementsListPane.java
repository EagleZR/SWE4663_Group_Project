package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.RequirementsList;

import java.util.ArrayList;

public class RequirementsListPane extends ScrollPane implements ProjectPane {

	private Pane contentPane;
	private Config config;
	private Stage primaryStage;
	private RequirementsList requirements;
	private ArrayList<RequirementPane> requirementPanes;
	private ArrayList<RequirementPane> visiblePanes;
	private RequirementFilter filter;

	RequirementsListPane( Project project, Config config, Stage primaryStage ) {
		this.config = config;
		this.primaryStage = primaryStage;
		this.filter = new RequirementFilter();
		setup();
		loadNewProject( project );
	}

	private void setup() {
		this.contentPane = new Pane();
		this.setContent( this.contentPane );
		this.setHbarPolicy( ScrollBarPolicy.NEVER );
		this.setVbarPolicy( ScrollBarPolicy.ALWAYS );
		this.setFitToWidth( true );
		this.requirementPanes = new ArrayList<>();
		this.visiblePanes = new ArrayList<>();
	}

	public void update() {
		// Make sure all Requirements have a RequirementPane and add as needed
		for ( Requirement requirement : this.requirements ) {
			boolean hasPane = false;
			for ( RequirementPane pane : this.requirementPanes ) {
				if ( pane.getRequirement().equals( requirement ) ) {
					hasPane = true;
					break;
				}
			}
			if ( !hasPane ) {
				addPane( requirement );
			}
		}

		// Remove any RequirementPanes that do not correspond to a Requirement currently in the list
		for ( RequirementPane pane : this.requirementPanes ) {
			boolean isInRequirements = false;
			for ( Requirement requirement : this.requirements ) {
				if ( pane.getRequirement().equals( requirement ) ) {
					isInRequirements = true;
				}
			}
			if ( !isInRequirements ) {
				removePane( pane );
			} else {
				if ( passedFilter( pane ) ) {
					setVisible( pane );
				} else {
					setInvisible( pane );
				}
			}
		}
	}

	private void addPane( Requirement requirement ) {
		RequirementPane pane = new RequirementPane( requirement, this, this.config, this.primaryStage );
		pane.prefWidthProperty().bind( this.contentPane.widthProperty() );
		pane.prefHeightProperty().setValue( 125 );
		if ( !this.requirements.contains( requirement ) ) {
			this.requirements.add( requirement );
		}
		this.requirementPanes.add( pane );
		if ( passedFilter( pane ) ) {
			setVisible( pane );
		}
	}

	private void removePane( Requirement requirement ) {
		removePane( findPane( requirement ) );
	}

	public void removePane( RequirementPane pane ) {
		this.requirementPanes.remove( pane );
		this.requirements.remove( pane.getRequirement() );
		setInvisible( pane );
	}

	private RequirementPane findPane( Requirement requirement ) {
		for ( RequirementPane pane : this.requirementPanes ) {
			if ( pane.getRequirement().equals( requirement ) ) {
				return pane;
			}
		}
		return null;
	}

	private void setVisible( RequirementPane pane ) {
		if ( !this.visiblePanes.contains( pane ) ) {
			this.contentPane.getChildren().add( pane );
			if ( this.visiblePanes.size() == 0 ) {
				pane.layoutXProperty().setValue( 0 );
				pane.layoutYProperty().setValue( 0 );
			} else {
				RequirementPane previous = this.visiblePanes.get( this.visiblePanes.size() - 1 );
				pane.layoutXProperty().bind( previous.layoutXProperty() );
				pane.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
			}
			this.visiblePanes.add( pane );
		}
	}

	private void setInvisible( RequirementPane pane ) {
		if ( this.visiblePanes.contains( pane ) ) {
			if ( this.visiblePanes.indexOf( pane ) == this.visiblePanes.size() - 1 ) {
				this.contentPane.getChildren().remove( pane );
			} else if ( this.visiblePanes.indexOf( pane ) == 0 ) {
				RequirementPane next = this.visiblePanes.get( 1 );
				next.layoutXProperty().unbind();
				next.layoutYProperty().unbind();
				next.layoutXProperty().setValue( 0 );
				next.layoutYProperty().setValue( 0 );
				this.contentPane.getChildren().remove( pane );
			} else {
				int index = this.visiblePanes.indexOf( pane );
				RequirementPane previous = this.visiblePanes.get( index - 1 );
				RequirementPane next = this.visiblePanes.get( index + 1 );
				next.layoutXProperty().unbind();
				next.layoutYProperty().unbind();
				next.layoutXProperty().bind( previous.layoutXProperty() );
				next.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
				this.contentPane.getChildren().remove( pane );
			}
			this.visiblePanes.remove( pane );
		}
	}

	private boolean passedFilter( RequirementPane pane ) {
		return this.filter.passesFilter( pane.getRequirement() );
	}

	public RequirementsList getRequirements() {
		return this.requirements;
	}

	public RequirementFilter getFilter() {
		return this.filter;
	}

	@Override public void loadNewProject( Project project ) {
		this.requirements = project.getRequirements();
		update();
	}
}
