package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidSubmissionException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidWorkedHourTypeException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

import java.time.LocalDate;

public class TEST_HourTypeDisplayPane extends Application {

	@Override public void start( Stage primaryStage ) throws Exception {
		Pane pane = new Pane();
		Project project = new Project();
		project.getTeam().addToTeam( new Person( "Bill" ) );

		// Display Pane
		HourTypeDisplayPane displayPane = new HourTypeDisplayPane( WorkedHourType.CODING, project, new Config() );
		displayPane.layoutXProperty().setValue( 10 );
		displayPane.layoutYProperty().setValue( 10 );
		displayPane.prefWidthProperty().bind( pane.widthProperty().subtract( 20 ) );
		pane.getChildren().add( displayPane );

		// ComboBox
		ComboBox selectHourType = new ComboBox<>( FXCollections.observableArrayList( WorkedHourType.values() ) );
		selectHourType.getItems().remove( WorkedHourType.ANY );
		selectHourType.getItems().remove( WorkedHourType.PROJECT_MANAGEMENT );
		selectHourType.layoutXProperty().bind( displayPane.layoutXProperty() );
		selectHourType.layoutYProperty()
				.bind( pane.heightProperty().subtract( selectHourType.heightProperty() ).subtract( 10 ) );
		pane.getChildren().add( selectHourType );

		// TextField
		TextField duration = new TextField();
		duration.layoutXProperty()
				.bind( selectHourType.layoutXProperty().add( selectHourType.widthProperty() ).add( 10 ) );
		duration.layoutYProperty().bind( selectHourType.layoutYProperty() );
		pane.getChildren().add( duration );

		// Button
		Button button = new Button( "Add hours" );
		button.layoutXProperty().bind( duration.layoutXProperty().add( duration.widthProperty() ).add( 10 ) );
		button.layoutYProperty().bind( duration.layoutYProperty() );
		button.setOnAction( e -> {
			try {
				project.getTeam().getMembers().get( 0 ).reportHours( Integer.parseInt( duration.getText() ),
						(WorkedHourType) selectHourType.getValue(), LocalDate.now() );
			} catch ( PersonNotOnTeamException e1 ) {
				e1.printStackTrace();
			} catch ( InvalidWorkedHourTypeException e1 ) {
				e1.printStackTrace();
			} catch ( InvalidSubmissionException e1 ) {
				e1.printStackTrace();
			}
			displayPane.update();
		} );
		pane.getChildren().add( button );

		displayPane.prefHeightProperty()
				.bind( pane.heightProperty().subtract( selectHourType.heightProperty() ).subtract( 30 ) );

		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 500 );
		primaryStage.setHeight( 200 );
		primaryStage.show();
	}
}
