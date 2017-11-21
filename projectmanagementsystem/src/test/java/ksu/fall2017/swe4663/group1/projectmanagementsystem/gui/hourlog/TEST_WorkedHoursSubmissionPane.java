package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.SubmissionInterval;

public class TEST_WorkedHoursSubmissionPane extends Application {

	@Override public void start( Stage primaryStage ) throws Exception {
		Project project = new Project();
		WorkedHoursSubmissionPane pane = new WorkedHoursSubmissionPane( project, primaryStage, new Config() );
		Person person = new Person( "Bob" );
		project.getTeam().addToTeam( person );
		// Un-comment either one to test how that works
		// Leave them both commented to test un-set state
		project.getTeam().getProjectHourLog().setSubmissionInterval( SubmissionInterval.WEEKLY );
		// project.getTeam().getProjectHourLog().setSubmissionInterval( SubmissionInterval.DAILY );
		pane.registerNewSelectedPerson( person );
		pane.registerSubmitAction( () -> {
			System.out.println( "Submission trigger" );
		} );

		Scene scene = new Scene( pane, 400, 400 );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
