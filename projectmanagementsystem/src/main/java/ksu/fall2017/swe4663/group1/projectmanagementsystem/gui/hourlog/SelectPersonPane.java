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
		scrollPane = new PersonButtonScrollPane();
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
		setup();
	}

	private void setup() {
		// Title label
		LoggingTool.print( "SelectPersonPane: Adding title label." );
		Label label = new Label( "Select a person: " );
		label.layoutXProperty().setValue( config.buffer );
		label.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( label );

		// ScrollPane
		LoggingTool.print( "SelectPersonPane: Adding PersonButtonScrollPane." );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
		scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( config.buffer * 3 ).subtract( label.heightProperty() ) );
		scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		scrollPane.layoutYProperty().bind( label.layoutYProperty().add( label.heightProperty() ).add( config.buffer ) );
		this.getChildren().add( scrollPane );
	}

	public void setSelectResponse( Runnable response ) {
		reportChange = response;
	}

	public Person getSelectedPerson() {
		return selectedPerson;
	}

	@Override public void addPerson( Person person ) {
		LoggingTool.print( "SelectPersonPane: Adding person: " + person.getName() + "." );
		if ( !scrollPane.containsPerson( person ) ) {
			PersonButton personButton = new PersonButton( person );
			personButton.setOnAction( e -> {
				for ( PersonButton button : scrollPane.getButtons() ) {
					button.setDefaultButton( false );
				}
				if ( selectedPerson != null && selectedPerson.equals( person ) ) {
					selectedPerson = null;
				} else {
					selectedPerson = person;
					personButton.setDefaultButton( true );
				}
				// LATER Send message to the EditDetailsPane
				reportChange.run();
			} );
			scrollPane.addButton( personButton );
		}
	}

	@Override public void removePerson( Person person ) {
		scrollPane.removePerson( person );
	}

	@Override public void updateTeamChange() {
		LoggingTool.print( "SelectPersonPane: Updating people." );
		// Check every member on team has button
		for ( Person person : project.getTeam().getMembers() ) {
			if ( !scrollPane.containsPerson( person ) ) {
				addPerson( person );
			}
		}

		// Check every button has member on team
		LinkedList<PersonButton> buttons = (LinkedList<PersonButton>)scrollPane.getButtons().clone();
		for ( PersonButton button : buttons ) {
			boolean isOnTeam = false;
			for ( Person person : project.getTeam().getMembers() ) {
				if ( button.getPerson().equals( person ) ) {
					isOnTeam = true;
				}
			}
			if ( !isOnTeam ) {
				LoggingTool.print( "SelectPersonPane: Found a Button for person" + button.getText()
						+ " who is no longer on the team." );
				scrollPane.removeButton( button );
			}
		}
	}

	@Override public void loadNewProject( Project project ) {
		this.project = project;
		scrollPane.clear();
		this.project.getTeam().addToDistro( this );
		updateTeamChange();
		reportChange.run();
	}
}
