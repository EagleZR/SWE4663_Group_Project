package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;

public class TEST_TeamMembersPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		TeamMembersPane teamMembersPane = new TeamMembersPane( primaryStage, new Project(), new Config() );
		Scene scene = new Scene( teamMembersPane );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
