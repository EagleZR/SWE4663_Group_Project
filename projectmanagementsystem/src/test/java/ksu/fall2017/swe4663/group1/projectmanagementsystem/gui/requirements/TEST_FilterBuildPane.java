package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;

import java.util.function.Consumer;

public class TEST_FilterBuildPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		FilterBuildPane pane = new FilterBuildPane( new Config() );
		pane.setStage( primaryStage );

		pane.setConsumer( new TestClass() );
		Scene scene = new Scene( pane );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 400 );
		primaryStage.setHeight( 400 );
		primaryStage.show();
	}

	class TestClass implements Consumer<RequirementFilter.RequirementComparator> {

		@Override public void accept( RequirementFilter.RequirementComparator comparator ) {
			System.out.println( comparator.toString() );
		}
	}
}
