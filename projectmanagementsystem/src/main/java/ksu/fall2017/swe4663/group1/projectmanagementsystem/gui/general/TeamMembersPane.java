package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.logs.LoggingTool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.TeamPresenter;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButton;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.PersonButtonScrollPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

import java.util.LinkedList;

/**
 * A pane to display the {@link Person}s who are team members of the {@link Project}'s {@link Team}.
 */
class TeamMembersPane extends FramedPane implements TeamPresenter, ProjectPane {

	private Stage primaryStage;
	private Project project;
	private PersonButtonScrollPane scrollPane;
	private Config config;
	private Button addButton;

	/**
	 * Constructs a new {@link TeamMembersPane} from the given {@link Project}.
	 *
	 * @param primaryStage The stage over which pop-ups will be displayed.
	 * @param project      The project currently being viewed/edited.
	 * @param config       This defines some of the physical properties and behavior of this pane.
	 */
	protected TeamMembersPane( Stage primaryStage, Project project, Config config ) {
		LoggingTool.print( "Constructing a new TeamMembersPane." );
		this.primaryStage = primaryStage;
		this.project = project;
		this.config = config;
		this.project.getTeam().addToDistro( this );
		setup();
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 */
	private void setup() {
		// Label
		LoggingTool.print( "TeamMembersPane: Creating title label in TeamMembersPane." );
		Label label = new Label( "Team Members (Click to edit): " );
		label.layoutXProperty().setValue( this.config.buffer );
		label.layoutYProperty().setValue( this.config.buffer );
		this.getChildren().add( label );

		// Add Button
		LoggingTool.print( "TeamMembersPane: Creating the \"Add Member\" Button in the TeamMembersPane." );
		this.addButton = new Button( "+ Add Member +" );
		this.addButton.layoutXProperty().bind( label.layoutXProperty() );
		this.addButton.layoutYProperty().bind( this.heightProperty().subtract( this.addButton.heightProperty() )
				.subtract( this.config.buffer ) );
		this.addButton.setOnAction( new AddHandler( this.project ) );
		this.getChildren().add( this.addButton );

		// ScrollPane
		LoggingTool.print( "TeamMembersPane: Creating PersonButtonScrollPane in TeamMembersPane." );
		this.scrollPane = new PersonButtonScrollPane();
		this.scrollPane.layoutXProperty().bind( label.layoutXProperty() );
		this.scrollPane.layoutYProperty()
				.bind( label.layoutYProperty().add( this.config.buffer ).add( label.heightProperty() ) );
		this.scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.scrollPane.prefHeightProperty()
				.bind( this.heightProperty().subtract( this.config.buffer * 4 ).subtract( label.heightProperty() )
						.subtract( this.addButton.heightProperty() ) );
		this.getChildren().add( this.scrollPane );
	}

	@Override public void addPerson( Person person ) {
		LoggingTool.print( "TeamMembersPane: Adding " + person.getName() + " to the TeamMembersPane." );
		PersonButton newMemberButton = new PersonButton( person );
		this.scrollPane.addButton( newMemberButton );
		newMemberButton.setOnAction( i -> editMember( newMemberButton ) );
	}

	/**
	 * Edits the {@link Person} stored in the given {@link PersonButton}.
	 *
	 * @param personButton The {@link PersonButton} which holds the {@link Person} to be edited.
	 */
	private void editMember( PersonButton personButton ) {
		LoggingTool
				.print( "TeamMembersPane: Editing " + personButton.getPerson().getName() + " in the TeamMembersPane." );
		Pane pane = new Pane();
		Scene scene = new Scene( pane, 280, 70 );
		PopupStage popupStage = new PopupStage( scene, this.primaryStage );
		popupStage.setTitle( "Edit: " + personButton.getPerson().getName() );

		// Label
		Label label = new Label( "Member's Name: " );
		label.layoutXProperty().setValue( this.config.buffer );
		label.layoutYProperty().setValue( this.config.buffer );
		pane.getChildren().add( label );

		// Text Field
		TextField textField = new TextField();
		textField.setText( personButton.getText() );
		textField.layoutXProperty()
				.bind( label.layoutXProperty().add( label.widthProperty() ).add( this.config.buffer / 2 ) );
		textField.layoutYProperty().bind( label.layoutYProperty() );
		pane.getChildren().add( textField );

		// Edit Button
		Button edit = new Button( "Edit" );
		edit.layoutXProperty().bind( pane.widthProperty().divide( 4 ).subtract( edit.widthProperty().divide( 2 ) ) );
		edit.layoutYProperty().bind( label.layoutYProperty().add( this.config.buffer ).add( label.heightProperty() ) );
		edit.setOnAction( e -> {
			if ( !textField.getText().equals( "" ) && !textField.getText().equals( edit.getText() ) ) {
				LoggingTool
						.print( "TeamMembersPane: Changing the name of " + personButton.getPerson().getName() + " to "
								+ textField.getText() + "in the TeamMembersPane." );
				personButton.changeName( textField.getText() );
			}
			popupStage.close();
		} );

		// Delete Button
		Button delete = new Button( "Delete" );
		delete.layoutXProperty()
				.bind( pane.widthProperty().divide( 4 ).multiply( 3 ).subtract( delete.widthProperty().divide( 2 ) ) );
		delete.layoutYProperty()
				.bind( label.layoutYProperty().add( this.config.buffer ).add( label.heightProperty() ) );
		delete.setOnAction( e -> {
			LoggingTool.print( "TeamMembersPane: Deleting " + personButton.getPerson().getName()
					+ " from the TeamMembersPane." );
			removePerson( personButton.getPerson() );
			this.scrollPane.removeButton( personButton );
			popupStage.close();
		} );

		pane.prefWidthProperty()
				.bind( textField.layoutXProperty().add( textField.widthProperty() ).add( this.config.buffer ) );
		pane.getChildren().add( edit );
		pane.getChildren().add( delete );

		popupStage.show();
	}

	@Override public void removePerson( Person person ) {
		this.project.getTeam().removeFromTeam( person );
	}

	@Override public void updateTeamChange() {
		LoggingTool.print( "TeamMembersPane: Updating people." );
		// Check every member on team has button
		for ( Person person : this.project.getTeam().getMembers() ) {
			if ( !this.scrollPane.containsPerson( person ) ) {
				addPerson( person );
			}
		}

		// Check every button has member on team
		LinkedList<PersonButton> buttons = this.scrollPane.getButtons();
		for ( PersonButton button : buttons ) {
			boolean isOnTeam = false;
			for ( Person person : this.project.getTeam().getMembers() ) {
				if ( button.getPerson().equals( person ) ) {
					isOnTeam = true;
				}
			}
			if ( !isOnTeam ) {
				LoggingTool.print( "TeamMembersPane: Found a Button for person" + button.getText()
						+ " who is no longer on the team." );
				this.scrollPane.removeButton( button );
			}
		}
	}

	@Override public void loadNewProject( Project project ) {
		LoggingTool.print( "TeamMembersPane: Loading new project." );
		this.project = project;
		this.scrollPane.clear();
		updateTeamChange();
		this.addButton.setOnAction( new AddHandler( project ) );
		this.project.getTeam().addToDistro( this );
	}

	/**
	 * A class too handle the actions necessary for adding a {@link Person} to the {@link Team}.
	 */
	private class AddHandler implements EventHandler<ActionEvent> {

		private Project project;

		/**
		 * Constructs the {@link AddHandler} using the given {@link Project}.
		 *
		 * @param project The project currently being viewed/edited.
		 */
		private AddHandler( Project project ) {
			this.project = project;
		}

		@Override public void handle( ActionEvent event ) {
			Pane pane = new Pane();
			Scene scene = new Scene( pane, 280, 70 );
			PopupStage popupStage = new PopupStage( scene, TeamMembersPane.this.primaryStage );
			popupStage.setTitle( "Add new member" );

			// Label
			Label label = new Label( "Member's Name: " );
			label.layoutXProperty().setValue( TeamMembersPane.this.config.buffer );
			label.layoutYProperty().setValue( TeamMembersPane.this.config.buffer );
			pane.getChildren().add( label );

			// Text Field
			TextField textField = new TextField();
			textField.layoutXProperty().bind( label.layoutXProperty().add( label.widthProperty() )
					.add( TeamMembersPane.this.config.buffer / 2 ) );
			textField.layoutYProperty().bind( label.layoutYProperty() );
			pane.getChildren().add( textField );

			// Button
			Button button = new Button( "Add" );
			button.layoutXProperty()
					.bind( pane.widthProperty().divide( 2 ).subtract( button.widthProperty().divide( 2 ) ) );
			button.layoutYProperty().bind( label.layoutYProperty().add( TeamMembersPane.this.config.buffer )
					.add( label.heightProperty() ) );
			button.setOnAction( e -> {
				if ( !textField.getText().equals( "" ) ) {
					// LATER Check how to send message to Manager Pane
					LoggingTool.print( "TeamMembersPane: Creating a new Person named " + textField.getText()
							+ " in the TeamMembersPane." );
					Person newMember = new Person( textField.getText() );
					this.project.getTeam().addToTeam( newMember );

					popupStage.close();
				}
			} );

			pane.prefWidthProperty().bind( textField.layoutXProperty().add( textField.widthProperty() )
					.add( TeamMembersPane.this.config.buffer ) );
			pane.getChildren().add( button );

			popupStage.show();
		}
	}
}
