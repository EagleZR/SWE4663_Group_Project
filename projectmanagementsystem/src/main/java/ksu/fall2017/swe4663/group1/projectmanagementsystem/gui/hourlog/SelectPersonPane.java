package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButton;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButtonScrollPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.TeamPresenter;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.util.LinkedList;

public class SelectPersonPane extends FramedPane implements TeamPresenter, ProjectPane {

	private Config config;
	private Project project;
	private Person selectedPerson;
	private PersonButtonScrollPane scrollPane;
	private Runnable reportChange;

	public SelectPersonPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new SelectPersonPane." );
		this.config = config;
		this.project = project;
		this.project.getTeam().addToDistro( this );
		this.scrollPane = new PersonButtonScrollPane();
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
		setup();
	}

	private void setup() {
		// Title label
		LoggingTool.print( "SelectPersonPane: Adding title label." );
		Label label = new Label( "Select a person: " );
		label.layoutXProperty().setValue( this.config.buffer );
		label.layoutYProperty().setValue( this.config.buffer );
		this.getChildren().add( label );

		// ScrollPane
		LoggingTool.print( "SelectPersonPane: Adding PersonButtonScrollPane." );
		this.scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( this.config.buffer * 3 ).subtract( label.heightProperty() ) );
		this.scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		this.scrollPane.layoutYProperty().bind( label.layoutYProperty().add( label.heightProperty() ).add( this.config.buffer ) );
		this.getChildren().add( this.scrollPane );
	}

	public void setSelectResponse( Runnable response ) {
		this.reportChange = response;
	}

	public Person getSelectedPerson() {
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
		LinkedList<PersonButton> buttons = (LinkedList<PersonButton>) this.scrollPane.getButtons().clone();
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
	}
}
