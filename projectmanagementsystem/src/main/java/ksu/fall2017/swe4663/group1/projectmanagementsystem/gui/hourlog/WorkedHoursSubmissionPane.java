package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidSubmissionException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidWorkedHourTypeException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.SubmissionInterval;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

import java.time.LocalDate;

/**
 * Creates a {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours} using the selected
 * {@link Person} (via the {@code registerNewSelectedPerson(Person person) method} and the details specified in the
 * fields of this pane.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class WorkedHoursSubmissionPane extends FramedPane implements ProjectPane {

	private Project project;
	private Stage stage;
	private Config config;
	private Person selectedPerson;
	private Label personName;
	private Label personID;
	private Label personManager;
	private DatePicker picker;
	// https://stackoverflow.com/questions/27801119/populating-javafx-combobox-or-choicebox-from-enum
	private ComboBox<WorkedHourType> selectHourType;
	private TextField inputDuration;
	private Button submitButton;

	/**
	 * Creates a pane for interaction with the {@link Person#reportHours(double, WorkedHourType, LocalDate)} method. The
	 * {@link Person} is determined via the {@link #registerNewSelectedPerson(Person)} method.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param stage   The stage over which pop-ups will be displayed.
	 * @param config  The {@link Config} file that dictates the configuration of this pane.
	 */
	protected WorkedHoursSubmissionPane( Project project, Stage stage, Config config ) {
		LoggingTool.print( "Constructing new WorkedHoursSubmissionPane." );
		this.project = project;
		this.stage = stage;
		this.config = config;
		this.personName = new Label( "Person's name: " );
		this.personID = new Label( "Person's ID: " );
		this.personManager = new Label( "Person is a manager?: " );
		this.selectHourType = new ComboBox<>( FXCollections.observableArrayList( WorkedHourType.values() ) );
		this.inputDuration = new TextField();
		this.submitButton = new Button( "Submit" );

		setup();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 */
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

		// Reporting Period Start Date
		Label reportingPeriod = new Label( "Reporting Period Start Date:" );
		reportingPeriod.layoutXProperty().bind( this.personManager.layoutXProperty() );
		reportingPeriod.layoutYProperty()
				.bind( this.personManager.layoutYProperty().add( this.personManager.heightProperty() )
						.add( this.config.buffer ) );
		reportingPeriod.wrapTextProperty().setValue( true );
		form.getChildren().add( reportingPeriod );

		// Date Chooser
		this.picker = new DatePicker();
		this.picker.layoutXProperty().bind( reportingPeriod.layoutXProperty() );
		this.picker.layoutYProperty().bind( reportingPeriod.layoutYProperty().add( reportingPeriod.heightProperty() )
				.add( this.config.buffer / 2 ) );
		form.getChildren().add( this.picker );

		// Hour Type Label
		Label hourType = new Label( "Please select the type of worked hours: " );
		hourType.layoutXProperty().bind( this.picker.layoutXProperty() );
		hourType.layoutYProperty()
				.bind( this.picker.layoutYProperty().add( this.picker.heightProperty() ).add( this.config.buffer ) );
		hourType.wrapTextProperty().setValue( true );
		form.getChildren().add( hourType );

		// Select Hour Type ComboBox
		this.selectHourType.layoutXProperty().bind( hourType.layoutXProperty() );
		this.selectHourType.layoutYProperty()
				.bind( hourType.layoutYProperty().add( hourType.heightProperty() ).add( this.config.buffer ) );
		form.getChildren().add( this.selectHourType );

		// Duration Label
		Label duration = new Label( "Please select the duration of the worked hours:" );
		duration.layoutXProperty().bind( this.selectHourType.layoutXProperty() );
		duration.layoutYProperty()
				.bind( this.selectHourType.layoutYProperty().add( this.selectHourType.heightProperty() )
						.add( this.config.buffer ) );
		duration.wrapTextProperty().setValue( true );
		form.getChildren().add( duration );

		// Input Duration TextField
		this.inputDuration.layoutXProperty().bind( duration.layoutXProperty() );
		this.inputDuration.layoutYProperty()
				.bind( duration.layoutYProperty().add( duration.heightProperty() ).add( this.config.buffer ) );
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

	/**
	 * This updates each field to display their current value.
	 */
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

	/**
	 * Registers new {@link Person} for the construction of a new {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours}.
	 *
	 * @param person The {@link Person} who is submitting the new {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours}
	 *               to the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.ProjectHourLog}.
	 */
	protected void registerNewSelectedPerson( Person person ) {
		LoggingTool.print( "WorkedHoursSubmissionPane: Registering newly-selected Person: " + (
				this.selectedPerson == null ? "" : this.selectedPerson.getName() ) );
		this.selectedPerson = person;
		update();
	}

	/**
	 * Registers a new {@link Runnable} action to execute during submission.
	 *
	 * @param runnable The {@link Runnable} set of actions to execute.
	 */
	protected void registerSubmitAction( Runnable runnable ) {
		this.submitButton.setOnAction( e -> {
			LoggingTool.print( "WorkedHoursSubmissionPane: Attempting to submit new hours." );
			if ( verifySubmissionInterval() ) {
				registerHours();
				runnable.run();
				reset();
			}
		} );
	}

	/**
	 * Verifies that the {@link SubmissionInterval} is set, and prompts the user to set it if not.
	 */
	private boolean verifySubmissionInterval() {
		if ( this.project.getTeam().getProjectHourLog().getSubmissionInterval() == null ) {
			LoggingTool
					.print( "WorkedHoursSubmissionPane: Submission was attempted before the submission interval was set. The user will be prompted to set the interval using a pop-up window." );
			Pane selectionPane = new Pane();

			// Message label
			Label label = new Label(
					"In order to submit, a submission interval must be selected. Please do so below." );
			label.layoutXProperty().setValue( this.config.buffer );
			label.layoutYProperty().setValue( this.config.buffer );
			selectionPane.getChildren().add( label );

			// ComboBox
			ComboBox<SubmissionInterval> comboBox = new ComboBox<>(
					FXCollections.observableArrayList( SubmissionInterval.values() ) );
			comboBox.layoutXProperty().bind( label.layoutXProperty() );
			comboBox.layoutYProperty()
					.bind( label.layoutYProperty().add( label.heightProperty() ).add( this.config.buffer ) );
			selectionPane.getChildren().add( comboBox );

			// OK Button
			Button okButton = new Button( "Ok" );
			okButton.layoutXProperty()
					.bind( selectionPane.widthProperty().divide( 4 ).subtract( okButton.widthProperty().divide( 2 ) ) );
			okButton.layoutYProperty()
					.bind( comboBox.layoutYProperty().add( comboBox.heightProperty() ).add( this.config.buffer ) );
			selectionPane.getChildren().add( okButton );

			// Cancel Button
			Button cancelButton = new Button( "Cancel" );
			cancelButton.layoutXProperty().bind( selectionPane.widthProperty().divide( 4 ).multiply( 3 )
					.subtract( okButton.widthProperty().divide( 2 ) ) );
			cancelButton.layoutYProperty().bind( okButton.layoutYProperty() );
			selectionPane.getChildren().add( cancelButton );

			selectionPane.prefHeightProperty().bind( cancelButton.layoutYProperty().add( cancelButton.heightProperty() )
					.add( this.config.buffer ) );
			Scene scene = new Scene( selectionPane, 450, 100 );
			PopupStage popupStage = new PopupStage( scene, this.stage );
			popupStage.setTitle( "Select Submission Interval" );
			okButton.setOnAction( e -> {
				SubmissionInterval selectedInterval = comboBox.getValue();
				if ( selectedInterval != null ) {
					this.project.getTeam().getProjectHourLog().setSubmissionInterval( selectedInterval );
					popupStage.close();
					LoggingTool.print( "WorkedHoursSubmissionPane: Submission interval set. Resubmitting." );
					this.submitButton.fire(); // LATER Potential for stack overflow with lambdas?
				} else {
					ErrorPopupSystem.displayMessage( "Please select a submission interval (or choose 'Cancel')." );
				}
			} );
			cancelButton.setOnAction( e -> {
				LoggingTool
						.print( "WorkedHoursSubmissionPane: The prompting pop-up was closed without setting a value." );
				popupStage.close();
			} );

			// Display
			popupStage.show();
			return false;
		}
		return true;
	}

	/**
	 * Registers new hours through the selected {@link Person}.
	 */
	private void registerHours() {
		if ( this.selectedPerson == null ) {
			ErrorPopupSystem.displayMessage( "Please select a person." );
		} else if ( this.picker.getValue() == null ) {
			ErrorPopupSystem.displayMessage( "Please select a start date." );
		} else if ( this.selectHourType.getValue() == null ) {
			ErrorPopupSystem.displayMessage( "Please select a type for the worked hours." );
		} else {
			try {
				double hours = Double.parseDouble( this.inputDuration.getText() );
				if ( hours <= 0 ) {
					ErrorPopupSystem.displayMessage( "Please submit a number of hours greater than 0." );
				} else {
					this.selectedPerson.reportHours( hours, this.selectHourType.getValue(), this.picker.getValue() );
				}
			} catch ( PersonNotOnTeamException e1 ) {
				ErrorPopupSystem.displayMessage( "There was an issue submitting the hours." );
			} catch ( InvalidWorkedHourTypeException e1 ) {
				ErrorPopupSystem
						.displayMessage( this.selectedPerson.getName() + " is unable to submit hours of that type." );
			} catch ( NumberFormatException e2 ) {
				ErrorPopupSystem.displayMessage(
						this.inputDuration.getText() + " is not a number. Please input a valid number." );
			} catch ( InvalidSubmissionException e ) {
				ErrorPopupSystem.displayMessage(
						"The submitted hours conflicted with hours that were already submitted based on the selected submission interval. The new hours were rejected." );
			}
		}
	}

	/**
	 * Resets the fields to their original values.
	 */
	private void reset() {
		LoggingTool.print( "WorkedHoursSubmissionPane: Resetting values." );
		this.selectHourType.setValue( WorkedHourType.ANY );
		this.inputDuration.setText( "" );
		this.picker.setValue( null );
	}

	@Override public void loadNewProject( Project project ) {
		reset();
		LoggingTool.print( "WorkedHoursSubmissionPane: Loaded new project." );
	}
}
