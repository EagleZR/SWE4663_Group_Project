package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.RequirementsList;

import java.util.ArrayList;

/**
 * A pane that lists the {@link RequirementPane}s that are associated with all of the {@link Requirement}s of this
 * {@link Project}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RequirementsListPane extends ScrollPane implements ProjectPane {

	private Pane contentPane;
	private Config config;
	private Stage primaryStage;
	private RequirementsList requirements;
	private ArrayList<RequirementPane> requirementPanes;
	private ArrayList<RequirementPane> visiblePanes;
	private RequirementFilter filter;

	/**
	 * Constructs a new pane to display and edit the {@link Requirement}s for the given {@link Project}.
	 *
	 * @param project      The project whose {@link Requirement}s will be displayed and edited.
	 * @param primaryStage The stage over which pop-ups will be displayed.
	 * @param config       This defines some of the physical properties and behavior of this pane.
	 */
	protected RequirementsListPane( Project project, Stage primaryStage, Config config ) {
		this.config = config;
		this.primaryStage = primaryStage;
		this.filter = new RequirementFilter();
		setup();
		loadNewProject( project );
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 */
	private void setup() {
		this.contentPane = new Pane();
		this.setContent( this.contentPane );
		this.setHbarPolicy( ScrollBarPolicy.NEVER );
		this.setVbarPolicy( ScrollBarPolicy.ALWAYS );
		this.setFitToWidth( true );
		this.requirementPanes = new ArrayList<>();
		this.visiblePanes = new ArrayList<>();
	}

	/**
	 * This updates each field to display their current value.
	 */
	protected void update() {
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

	/**
	 * Adds a new {@link RequirementPane} for the given {@link Requirement}.
	 *
	 * @param requirement The requirement to add to this pane.
	 */
	private void addPane( Requirement requirement ) {
		RequirementPane pane = new RequirementPane( requirement, this, this.primaryStage, this.config );
		pane.prefWidthProperty().bind( this.contentPane.widthProperty() );
		pane.prefHeightProperty().setValue( 125 );
		if ( !this.requirements.contains( requirement ) ) {
			LoggingTool.print( "RequirementsList: Adding Requirement: " + requirement.getItemNumber() + "." );
			this.requirements.add( requirement );
		}
		this.requirementPanes.add( pane );
		if ( passedFilter( pane ) ) {
			setVisible( pane );
		}
	}

	/**
	 * Removes the pane for the associated {@link Requirement} from the project.
	 *
	 * @param requirement The requirement to remove from being displayed.
	 */
	private void removePane( Requirement requirement ) {
		removePane( findPane( requirement ) );
	}

	/**
	 * Removes the pane from the project.
	 *
	 * @param pane The pane whose {@link Requirement} is to be removed.
	 */
	protected void removePane( RequirementPane pane ) {
		LoggingTool.print( "RequirementsList: Removing Requirement: " + pane.getRequirement().getItemNumber() + "." );
		this.requirementPanes.remove( pane );
		this.requirements.remove( pane.getRequirement() );
		setInvisible( pane );
	}

	/**
	 * Finds the pane associated with the given {@link Requirement}.
	 *
	 * @param requirement The requirement whose pane is to be retrieved.
	 * @return The pane which is associated with the given {@link Requirement} if it exists, {@code null} if it does not
	 * exist.
	 */
	private RequirementPane findPane( Requirement requirement ) {
		for ( RequirementPane pane : this.requirementPanes ) {
			if ( pane.getRequirement().equals( requirement ) ) {
				return pane;
			}
		}
		return null;
	}

	/**
	 * Sets the given {@link RequirementPane} as visible.
	 *
	 * @param pane The pane to be made visible.
	 */
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

	/**
	 * Sets the given {@link RequirementPane} as invisible.
	 *
	 * @param pane The pane to be made invisible.
	 */
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

	/**
	 * Checks if the {@link Requirement} associated with the given {@link RequirementPane} passes the {@link
	 * RequirementFilter}.
	 *
	 * @param pane The pane whose {@link Requirement} will be checked against the {@link RequirementFilter}.
	 * @return {@code true} if the {@link Requirement} passes the filter, {@code false} if not.
	 */
	private boolean passedFilter( RequirementPane pane ) {
		return this.filter.passesFilter( pane.getRequirement() );
	}

	/**
	 * Retrieves all {@link Requirement}s associated with this pane.
	 *
	 * @return A {@link RequirementsList} of all {@link Requirement}s associated with the {@link Project} this pane is
	 * displaying.
	 */
	public RequirementsList getRequirements() {
		return this.requirements;
	}

	/**
	 * Retrieves the {@link RequirementFilter} which is limiting what is displayed.
	 *
	 * @return The filter for the {@link RequirementPane}s of this pane.
	 */
	protected RequirementFilter getFilter() {
		return this.filter;
	}

	@Override public void loadNewProject( Project project ) {
		this.requirements = project.getRequirements();
		update();
		LoggingTool.print( "RequirementsListPane: Loaded new project." );
	}
}
