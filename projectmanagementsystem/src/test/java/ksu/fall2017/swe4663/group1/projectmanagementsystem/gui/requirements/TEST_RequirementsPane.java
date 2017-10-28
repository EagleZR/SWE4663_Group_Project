package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;

public class TEST_RequirementsPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		Project project = new Project();
		RequirementsPane pane = new RequirementsPane( project, new Config(), primaryStage );

		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 400 );
		primaryStage.setHeight( 400 );
		primaryStage.show();
	}
}
