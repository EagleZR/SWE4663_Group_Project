package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Priority;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Status;

public class TEST_RequirementPane extends Application {

	@Override public void start( Stage primaryStage ) throws Exception {
		Requirement requirement = new Requirement( "This is a title", "This is a requirement", "LastName, FirstName",
				Priority.CRITICAL, Status.ACCEPTED_CURRENT, false, false );
		Config config = new Config();
		RequirementPane pane = new RequirementPane( requirement,
				new RequirementsListPane( new Project(), config, primaryStage ), config, primaryStage );

		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 400 );
		primaryStage.setHeight( 150 );
		primaryStage.show();
	}

}
