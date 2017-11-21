package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.TeamPresenter;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButton;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButtonScrollPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.util.LinkedList;

/**
 * Displays all of the {@link Person} members of the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team}
 * from the given {@link Project}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class SelectPersonPane extends FramedPane implements TeamPresenter, ProjectPane {

	private Project project;
	private Person selectedPerson;
	private PersonButtonScrollPane scrollPane;
	private Runnable reportChange;

	/**
	 * Constructs a new {@link SelectPersonPane} from a given {@link Project}.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 */
	protected SelectPersonPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new SelectPersonPane." );
		this.project = project;
		this.project.getTeam().addToDistro( this );
		this.scrollPane = new PersonButtonScrollPane();
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
		setup( config );
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private void setup( Config config ) {
		// Title label
		LoggingTool.print( "SelectPersonPane: Adding title label." );
		Label label = new Label( "Select a person: " );
		label.layoutXProperty().setValue( config.buffer );
		label.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( label );

		// ScrollPane
		LoggingTool.print( "SelectPersonPane: Adding PersonButtonScrollPane." );
		this.scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
		this.scrollPane.prefHeightProperty()
				.bind( this.heightProperty().subtract( config.buffer * 3 ).subtract( label.heightProperty() ) );
		this.scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		this.scrollPane.layoutYProperty()
				.bind( label.layoutYProperty().add( label.heightProperty() ).add( config.buffer ) );
		this.getChildren().add( this.scrollPane );
	}

	/**
	 * Sets the action to be performed each time a new {@link PersonButton} is selected.
	 *
	 * @param response The {@link Runnable} response to be executed each time a new {@link PersonButton} is selected.
	 */
	protected void setSelectResponse( Runnable response ) {
		LoggingTool.print( "SelectPersonPane: Setting new action for execution upon PersonButton selection." );
		this.reportChange = response;
	}

	/**
	 * Retrieves the currently-selected {@link PersonButton}.
	 *
	 * @return The currently-selected {@link PersonButton}. If no one is selected, it will return {@link null}.
	 */
	protected Person getSelectedPerson() {
		return this.selectedPerson;
	}

	@Override public void addPerson( Person person ) {
		LoggingTool.print( "SelectPersonPane: Adding person: " + person.getName() + "." );
		if ( !this.scrollPane.containsPerson( person ) ) {
			PersonButton personButton = new PersonButton( person );
			personButton.setOnAction( e -> {
				for ( PersonButton button : this.scrollPane.getButtons() ) {
					button.setDefaultButton( false );
				}
				if ( this.selectedPerson != null && this.selectedPerson.equals( person ) ) {
					this.selectedPerson = null;
				} else {
					this.selectedPerson = person;
					personButton.setDefaultButton( true );
				}
				// LATER Send message to the EditDetailsPane
				this.reportChange.run();
			} );
			this.scrollPane.addButton( personButton );
		}
	}

	@Override public void removePerson( Person person ) {
		this.scrollPane.removePerson( person );
	}

	@Override public void updateTeamChange() {
		LoggingTool.print( "SelectPersonPane: Updating people." );
		// Check every member on team has button
		for ( Person person : this.project.getTeam().getMembers() ) {
			if ( !this.scrollPane.containsPerson( person ) ) {
				addPerson( person );
			}
		}

		// Check every button has member on team
		LinkedList<PersonButton> buttons = this.scrollPane.getButtons(); // LATER Test if it still works...
		for ( PersonButton button : buttons ) {
			boolean isOnTeam = false;
			for ( Person person : this.project.getTeam().getMembers() ) {
				if ( button.getPerson().equals( person ) ) {
					isOnTeam = true;
				}
			}
			if ( !isOnTeam ) {
				LoggingTool.print( "SelectPersonPane: Found a Button for person" + button.getText()
						+ " who is no longer on the team." );
				this.scrollPane.removeButton( button );
			}
		}
	}

	@Override public void loadNewProject( Project project ) {
		this.project = project;
		this.scrollPane.clear();
		this.project.getTeam().addToDistro( this );
		updateTeamChange();
		this.reportChange.run();
		LoggingTool.print( "SelectPersonPane: Loaded new project." );
	}
}
