package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.risks.Risk;

public class TEST_RiskPane extends Application {
	@Override public void start( Stage primaryStage ) throws Exception {
		Config config = new Config();
		RiskPane riskPane = new RiskPane( new Risk( "This is a risk that could happen", 431.00, 0.02 ),
				new RisksPane( new Project(), primaryStage, config ), primaryStage, config );
		Scene scene = new Scene( riskPane );
		primaryStage.setScene( scene );
		primaryStage.setWidth( 200 );
		primaryStage.setHeight( 150 );
		primaryStage.show();
	}
}
