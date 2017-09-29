package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.DescriptionPane;

public class TEST_DescriptionPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		DescriptionPane descriptionPane = new DescriptionPane( new Project(), new Config() );

		Scene scene = new Scene( descriptionPane );
		primaryStage.setScene( scene );
		primaryStage.show();

	}
}
