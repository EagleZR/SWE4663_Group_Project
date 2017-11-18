package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidWorkedHourTypeException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

import java.time.LocalDate;

/**
 * Creates a {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours} using the selected
 * {@link Person} (via the {@code registerNewSelectedPerson(Person person) method} and the details specified in the
 * fields of this pane.
 */
public class WorkedHoursSubmissionPane extends FramedPane implements ProjectPane {

	private Config config;
	private Person selectedPerson;
	private Label personName;
	private Label personID;
	private Label personManager;
	private Label hourType;
	// https://stackoverflow.com/questions/27801119/populating-javafx-combobox-or-choicebox-from-enum
	private ComboBox<WorkedHourType> selectHourType;
	private Label duration;
	private TextField inputDuration;
	private Button submitButton;

	/**
	 * Creates a pane for interaction with the {@link Person#reportHours(double, WorkedHourType, LocalDate)} method. The
	 * {@link Person} is determined via the {@link #registerNewSelectedPerson(Person)} method.
	 *
	 * @param config The {@link Config} file that dictates the configuration of this pane.
	 */
	protected WorkedHoursSubmissionPane( Config config ) {
		LoggingTool.print( "Constructing new WorkedHoursSubmissionPane." );
		this.config = config;

		this.personName = new Label( "Person's name: " );
		this.personID = new Label( "Person's ID: " );
		this.personManager = new Label( "Person is a manager?: " );
		this.hourType = new Label( "Please select the type of worked hours: " );
		this.selectHourType = new ComboBox<>( FXCollections.observableArrayList( WorkedHourType.values() ) );
		this.duration = new Label( "Please select the duration of the worked hours:" );
		this.inputDuration = new TextField();
		this.submitButton = new Button( "Submit" );

		setup();
	}

	private void setup() {
		// Scroll Pane
		LoggingTool.print( "WorkedHoursSubmissionPane: Creating ScrollPane." );
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		scrollPane.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		scrollPane.layoutXProperty().setValue( 2 );
		scrollPane.layoutYProperty().setValue( 2 );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 4 ) );
		scrollPane.prefHeightProperty()
				.bind( this.submitButton.layoutYProperty().subtract( scrollPane.layoutYProperty() )
						.subtract( this.config.buffer / 2 ) );
		this.getChildren().add( scrollPane );

		// Content pane
		LoggingTool.print( "WorkedHoursSubmissionPane: Creating content Pane." );
		Pane form = new Pane();
		scrollPane.setContent( form );

		// Person Name Label
		this.personName.layoutXProperty().setValue( this.config.buffer );
		this.personName.layoutYProperty().setValue( this.config.buffer );
		this.personName.wrapTextProperty().setValue( true );
		form.getChildren().add( this.personName );

		// Person ID Label
		this.personID.layoutXProperty().bind( this.personName.layoutXProperty() );
		this.personID.layoutYProperty().bind( this.personName.layoutYProperty().add( this.personName.heightProperty() )
				.add( this.config.buffer ) );
		this.personID.wrapTextProperty().setValue( true );
		form.getChildren().add( this.personID );

		// Person Manager Label
		this.personManager.layoutXProperty().bind( this.personName.layoutXProperty() );
		this.personManager.layoutYProperty().bind( this.personID.layoutYProperty().add( this.personID.heightProperty() )
				.add( this.config.buffer ) );
		this.personManager.wrapTextProperty().setValue( true );
		form.getChildren().add( this.personManager );

		// Hour Type Label
		this.hourType.layoutXProperty().bind( this.personName.layoutXProperty() );
		this.hourType.layoutYProperty()
				.bind( this.personManager.layoutYProperty().add( this.personManager.heightProperty() )
						.add( this.config.buffer ) );
		this.hourType.wrapTextProperty().setValue( true );
		form.getChildren().add( this.hourType );

		// Select Hour Type ComboBox
		this.selectHourType.layoutXProperty().bind( this.hourType.layoutXProperty() );
		this.selectHourType.layoutYProperty()
				.bind( this.hourType.layoutYProperty().add( this.hourType.heightProperty() )
						.add( this.config.buffer ) );
		form.getChildren().add( this.selectHourType );

		// Duration Label
		this.duration.layoutXProperty().bind( this.selectHourType.layoutXProperty() );
		this.duration.layoutYProperty()
				.bind( this.selectHourType.layoutYProperty().add( this.selectHourType.heightProperty() )
						.add( this.config.buffer ) );
		this.duration.wrapTextProperty().setValue( true );
		form.getChildren().add( this.duration );

		// Input Duration TextField
		this.inputDuration.layoutXProperty().bind( this.duration.layoutXProperty() );
		this.inputDuration.layoutYProperty().bind( this.duration.layoutYProperty().add( this.duration.heightProperty() )
				.add( this.config.buffer ) );
		form.getChildren().add( this.inputDuration );

		// Submit Button
		this.submitButton.layoutXProperty().bind( scrollPane.layoutXProperty().add( this.config.buffer ) );
		this.submitButton.layoutYProperty().bind( this.heightProperty().subtract( this.submitButton.heightProperty() )
				.subtract( this.config.buffer / 2 ) );
		this.submitButton.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.submitButton.setOnAction( e -> registerHours() );
		this.getChildren().add( this.submitButton );

		update();
	}

	private void update() {
		// Person Name
		this.personName
				.setText( "Person's name: " + ( this.selectedPerson == null ? "" : this.selectedPerson.getName() ) );

		// Person ID
		this.personID.setText( "Person's ID: " + ( this.selectedPerson == null ? "" : this.selectedPerson.getID() ) );

		// Person Manager
		this.personManager.setText( "Person is a manager? " + ( this.selectedPerson == null ?
				"" :
				( this.selectedPerson.isManager() ? "Yes" : "No" ) ) );
	}

	void registerNewSelectedPerson( Person person ) {
		LoggingTool.print( "WorkedHoursSubmissionPane: Registering newly-selected Person: " + (
				this.selectedPerson == null ? "" : this.selectedPerson.getName() ) );
		this.selectedPerson = person;
		update();
	}

	void registerSubmitAction( Runnable runnable ) {
		this.submitButton.setOnAction( e -> {
			registerHours();
			runnable.run();
		} );
	}

	private void registerHours() {
		if ( this.selectedPerson == null ) {
			ErrorPopupSystem.displayMessage( "Please select a person." );
		} else {
			try {
				double hours = Double.parseDouble( this.inputDuration.getText() );
				if ( hours <= 0 ) {
					ErrorPopupSystem.displayMessage( "Please submit a number of hours greater than 0." );
				} else {
					this.selectedPerson.reportHours( hours, this.selectHourType.getValue(), LocalDate.now() );
				}
			} catch ( PersonNotOnTeamException e1 ) {
				ErrorPopupSystem.displayMessage( "There was an issue submitting the hours." );
			} catch ( InvalidWorkedHourTypeException e1 ) {
				ErrorPopupSystem
						.displayMessage( this.selectedPerson.getName() + " is unable to submit hours of that type." );
			} catch ( NumberFormatException e2 ) {
				ErrorPopupSystem.displayMessage(
						this.inputDuration.getText() + " is not a number. Please input a valid number." );
			}
		}
	}

	private void reset() {
		this.selectHourType.setValue( WorkedHourType.ANY );
		this.inputDuration.setText( "" );
		// LATER Send message to SelectPersonPane?
	}

	@Override public void loadNewProject( Project project ) {
		reset();
		LoggingTool.print( "WorkedHoursSubmissionPane: Loaded new project." );
	}
}
