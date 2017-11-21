package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.logs.LoggingTool;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

import java.util.LinkedList;

/**
 * This pane displays and edits the managers of the given {@link Team} within the {@link Project}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class ManagerPane extends FramedPane implements TeamPresenter, ProjectPane {

	private Project project;
	private Person manager;
	private Label label;
	private PersonButtonScrollPane scrollPane;
	private Stage stage;
	private Config config;

	/**
	 * Constructs a new {@link ManagerPane} from the given parameters.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param stage   The stage over which pop-ups will be displayed.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 * @throws PersonNotOnTeamException Thrown if there is some issue where the current {@link Team} does not have a
	 *                                  manager. In most cases (i.e. new teams), this can be disregarded.
	 */
	protected ManagerPane( Project project, Stage stage, Config config ) throws PersonNotOnTeamException {
		LoggingTool.print( "Creating new ManagerPane." );
		this.project = project;
		this.project.getTeam().addToDistro( this );
		this.stage = stage;
		this.config = config;
		try {
			LoggingTool.print( "Setting " + project.getTeam().getManager() + " as the manager in the ManagerPane." );
			this.manager = project.getTeam().getManager();
			setup();
		} catch ( PersonNotOnTeamException e ) {
			LoggingTool.print( "No manager was found. Setting an empty person as the manager in the ManagerPane." );
			this.manager = new Person( "" );
			this.manager.promote();
			setup();
		}
		for ( Person person : project.getTeam().getMembers() ) {
			addPerson( person );
		}
		update();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 */
	private void setup() {
		// Draw Label
		LoggingTool.print( "ManagerPane: Creating title label in ManagerPane." );
		this.label = new Label( "" );
		this.label.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.label.layoutXProperty().setValue( this.config.buffer );
		this.label.layoutYProperty().setValue( this.config.buffer );
		this.getChildren().add( this.label );

		// Draw ScrollPane of Person Buttons
		LoggingTool.print( "ManagerPane: Creating PersonButtonScrollPane in ManagerPane." );
		this.scrollPane = new PersonButtonScrollPane();
		this.scrollPane.layoutXProperty().bind( this.label.layoutXProperty() );
		this.scrollPane.layoutYProperty()
				.bind( this.label.layoutYProperty().add( this.config.buffer ).add( this.label.heightProperty() ) );
		this.scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( this.config.buffer * 3 )
				.subtract( this.label.heightProperty() ) );
		this.getChildren().add( this.scrollPane );
	}

	/**
	 * This updates each field to display their current value.
	 */
	private void update() {
		LoggingTool.print( "ManagerPane: Updating ManagerPane." );
		try {
			this.manager = this.project.getTeam().getManager();
		} catch ( PersonNotOnTeamException e ) {
			LoggingTool.print( "ManagerPane: Unable to update manager." );
			this.manager = null;
			// LATER Use status label instead?
		}
		for ( PersonButton button : this.scrollPane.getButtons() ) {
			button.setDefaultButton( button.getPerson().isManager() );
		}
		this.label.setText( "Project Manager: " + ( this.manager == null ? "" : this.manager.getName() ) );
	}

	@Override public void addPerson( Person person ) {
		LoggingTool.print( "ManagerPane: Adding person: " + person.getName() + "." );
		PersonButton personButton = new PersonButton( person );
		personButton.setOnAction( e -> {
			Pane pane = new Pane();
			Scene scene = new Scene( pane, 280, 70 );
			PopupStage popupStage = new PopupStage( scene, this.stage );
			popupStage.setTitle( "Set as manager?" );

			Label label = new Label( "Set " + person.getName() + " as the manager?" );
			label.layoutXProperty()
					.bind( pane.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
			label.layoutYProperty().setValue( this.config.buffer );
			pane.getChildren().add( label );

			// Edit Button
			Button yes = new Button( "Yes" );
			yes.layoutXProperty().bind( pane.widthProperty().divide( 4 ).subtract( yes.widthProperty().divide( 2 ) ) );
			yes.layoutYProperty()
					.bind( label.layoutYProperty().add( this.config.buffer ).add( label.heightProperty() ) );
			yes.setOnAction( a -> {
				if ( this.manager != null ) {
					this.project.getTeam().demote( this.manager );
				}
				this.project.getTeam().promote( person );
				this.manager = person;
				popupStage.close();
			} );

			// Delete Button
			Button no = new Button( "No" );
			no.layoutXProperty()
					.bind( pane.widthProperty().divide( 4 ).multiply( 3 ).subtract( no.widthProperty().divide( 2 ) ) );
			no.layoutYProperty()
					.bind( label.layoutYProperty().add( this.config.buffer ).add( label.heightProperty() ) );
			no.setOnAction( a -> popupStage.close() );

			pane.getChildren().add( yes );
			pane.getChildren().add( no );

			popupStage.show();
		} );
		this.scrollPane.addButton( personButton );
	}

	@Override public void removePerson( Person person ) {
		LoggingTool.print( "ManagerPane: Removing person: " + person.getName() + "." );
		this.scrollPane.removePerson( person );
	}

	@Override public void updateTeamChange() {
		LoggingTool.print( "ManagerPane: Updating people." );
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
				LoggingTool.print( "PersonButtonScrollPane: Found a Button for person" + button.getText()
						+ " who is no longer on the team." );
				this.scrollPane.removeButton( button );
			}
		}

		update();
	}

	@Override public void loadNewProject( Project project ) {
		LoggingTool.print( "ManagerPane: Loading new project." );
		this.project = project;
		this.scrollPane.clear();
		updateTeamChange();
		this.project.getTeam().addToDistro( this );
		update();
	}
}
