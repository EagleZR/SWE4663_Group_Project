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

public class WorkedHoursSubmissionPane extends FramedPane implements ProjectPane {

	private Config config;
	private Person selectedPerson;
	private Project project;
	private Label personName;
	private Label personID;
	private Label personManager;
	private Label hourType;
	// https://stackoverflow.com/questions/27801119/populating-javafx-combobox-or-choicebox-from-enum
	private ComboBox<WorkedHourType> selectHourType;
	private Label duration;
	private TextField inputDuration;
	private Button submitButton;

	public WorkedHoursSubmissionPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new WorkedHoursSubmissionPane." );
		this.project = project;
		this.config = config;

		personName = new Label( "Person's name: " );
		personID = new Label( "Person's ID: " );
		personManager = new Label( "Person is a manager?: " );
		hourType = new Label( "Please select the type of worked hours: " );
		selectHourType = new ComboBox<>( FXCollections.observableArrayList( WorkedHourType.values() ) );
		//		selectHourType.getItems().remove( WorkedHourType.ANY );
		//		selectHourType.getItems().add( "" );
		duration = new Label( "Please select the duration of the worked hours:" );
		inputDuration = new TextField();
		submitButton = new Button( "Submit" );

		setup();
	}

	private void setup() {
		// Scroll Pane
		LoggingTool.print( "WorkedHoursSubmissionPane: Creating ScrollPane." );
		ScrollPane scrollPane = new ScrollPane();
		this.getChildren().add( scrollPane );
		scrollPane.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		scrollPane.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		scrollPane.layoutXProperty().setValue( 2 );
		scrollPane.layoutYProperty().setValue( 2 );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 4 ) );
		scrollPane.prefHeightProperty()
				.bind( this.heightProperty().subtract( config.buffer * 2 ).subtract( submitButton.heightProperty() )
						.subtract( 4 ) );
		scrollPane.setFitToWidth( true );

		// Content pane
		LoggingTool.print( "WorkedHoursSubmissionPane: Creating content Pane." );
		Pane form = new Pane();
		scrollPane.setContent( form );

		// Person Name Label
		personName.layoutXProperty().setValue( config.buffer );
		personName.layoutYProperty().setValue( config.buffer );
		personName.wrapTextProperty().setValue( true );
		form.getChildren().add( personName );

		// Person ID Label
		personID.layoutXProperty().bind( personName.layoutXProperty() );
		personID.layoutYProperty()
				.bind( personName.layoutYProperty().add( personName.heightProperty() ).add( config.buffer ) );
		personID.wrapTextProperty().setValue( true );
		form.getChildren().add( personID );

		// Person Manager Label
		personManager.layoutXProperty().bind( personName.layoutXProperty() );
		personManager.layoutYProperty()
				.bind( personID.layoutYProperty().add( personID.heightProperty() ).add( config.buffer ) );
		personManager.wrapTextProperty().setValue( true );
		form.getChildren().add( personManager );

		// Hour Type Label
		hourType.layoutXProperty().bind( personName.layoutXProperty() );
		hourType.layoutYProperty()
				.bind( personManager.layoutYProperty().add( personManager.heightProperty() ).add( config.buffer ) );
		hourType.wrapTextProperty().setValue( true );
		form.getChildren().add( hourType );

		// Select Hour Type ComboBox
		selectHourType.layoutXProperty().bind( hourType.layoutXProperty() );
		selectHourType.layoutYProperty()
				.bind( hourType.layoutYProperty().add( hourType.heightProperty() ).add( config.buffer ) );
		form.getChildren().add( selectHourType );

		// Duration Label
		duration.layoutXProperty().bind( selectHourType.layoutXProperty() );
		duration.layoutYProperty()
				.bind( selectHourType.layoutYProperty().add( selectHourType.heightProperty() ).add( config.buffer ) );
		duration.wrapTextProperty().setValue( true );
		form.getChildren().add( duration );

		// Input Duration TextField
		inputDuration.layoutXProperty().bind( duration.layoutXProperty() );
		inputDuration.layoutYProperty()
				.bind( duration.layoutYProperty().add( duration.heightProperty() ).add( config.buffer ) );
		form.getChildren().add( inputDuration );

		// Submit Button
		submitButton.layoutXProperty().bind( scrollPane.layoutXProperty().add( config.buffer ) );
		submitButton.layoutYProperty()
				.bind( scrollPane.layoutYProperty().add( scrollPane.heightProperty() ).add( config.buffer ) );
		submitButton.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
		submitButton.setOnAction( e -> {
			registerHours();
		} );
		this.getChildren().add( submitButton );

		update();
	}

	private void update() {
		// Person Name
		personName.setText( "Person's name: " + ( selectedPerson == null ? "" : selectedPerson.getName() ) );

		// Person ID
		personID.setText( "Person's ID: " + ( selectedPerson == null ? "" : selectedPerson.getID() ) );

		// Person Manager
		personManager.setText( "Person is a manager? " + ( selectedPerson == null ?
				"" :
				( selectedPerson.isManager() ? "Yes" : "No" ) ) );
	}

	public void registerNewSelectedPerson( Person person ) {
		LoggingTool.print( "WorkedHoursSubmissionPane: Registering newly-selected Person: " + ( selectedPerson == null ?
				"" :
				selectedPerson.getName() ) );
		this.selectedPerson = person;
		update();
	}

	protected void registerSubmitAction( Runnable runnable ) {
		submitButton.setOnAction( e -> {
			registerHours();
			runnable.run();
		} );
	}

	private void registerHours() {
		if ( selectedPerson == null ) {
			ErrorPopupSystem.displayMessage( "Please select a person." );
		} else {
			try {
				double hours = Double.parseDouble( inputDuration.getText() );
				if ( hours <= 0 ) {
					ErrorPopupSystem.displayMessage( "Please submit a number of hours greater than 0." );
				} else {
					selectedPerson.reportHours( hours, selectHourType.getValue() );
				}
			} catch ( PersonNotOnTeamException e1 ) {
				ErrorPopupSystem.displayMessage( "There was an issue submitting the hours." );
			} catch ( InvalidWorkedHourTypeException e1 ) {
				ErrorPopupSystem
						.displayMessage( selectedPerson.getName() + " is unable to submit hours of that type." );
			} catch ( NumberFormatException e2 ) {
				ErrorPopupSystem
						.displayMessage( inputDuration.getText() + " is not a number. Please input a valid number." );
			}
		}
	}

	private void reset() {
		selectHourType.setValue( WorkedHourType.ANY );
		inputDuration.setText( "" );
		// LATER Send message to SelectPersonPane?
	}

	@Override public void loadNewProject( Project project ) {
		reset();
	}
}
