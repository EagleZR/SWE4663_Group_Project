package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;

public class TEST_AboutPane extends Application {

	@Override public void start( Stage primaryStage ) throws Exception {
		AboutPane pane = new AboutPane( new Config() );
		Scene scene = new Scene( pane, 500, 400 );
		primaryStage.setScene( scene );
		primaryStage.show();
	}
}
