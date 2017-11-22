package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Priority;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Status;

public class TEST_RequirementsListPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		Project project = new Project();
		project.getRequirements().add( new Requirement( "This is a title",
				"This is a requirement with a really long description that I want to run off the end of the pane and",
				"LastName, FirstName", Priority.CRITICAL, Status.ACCEPTED_CURRENT, false, false ) );
		project.getRequirements()
				.add( new Requirement( "This is a second title", "This is a requirement 2", "LastName, FirstName",
						Priority.LOW, Status.REJECTED, false, false ) );
		RequirementsListPane pane = new RequirementsListPane( project, primaryStage, new Config() );

		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 400 );
		primaryStage.setHeight( 300 );
		primaryStage.show();
	}
}
