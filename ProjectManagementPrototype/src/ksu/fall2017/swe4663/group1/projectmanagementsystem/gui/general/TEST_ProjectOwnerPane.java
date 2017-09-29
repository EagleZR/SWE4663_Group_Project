package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.ProjectOwnerPane;

public class TEST_ProjectOwnerPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		ProjectOwnerPane pane = new ProjectOwnerPane( new Project(), primaryStage, new Config() );
		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
