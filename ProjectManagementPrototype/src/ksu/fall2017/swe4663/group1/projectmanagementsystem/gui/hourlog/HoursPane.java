package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;

public class HoursPane extends Pane implements ProjectPane {

	private SelectPersonPane selectPersonPane;
	private WorkedHoursSubmissionPane detailsPane;
	private HourLogDisplayPane hourLogDisplayPane;

	public HoursPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new HoursPane." );
		// Select Person Pane
		LoggingTool.print( "HoursPane: Creating new SelectPersonPane." );
		selectPersonPane = new SelectPersonPane( project, config );
		selectPersonPane.layoutXProperty().setValue( 0 );
		selectPersonPane.layoutYProperty().setValue( 0 );
		selectPersonPane.prefWidthProperty().bind( this.widthProperty().divide( 3 ) );
		selectPersonPane.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( selectPersonPane );

		// Fill In Details Pane
		LoggingTool.print( "HoursPane: Creating new WorkedHoursSubmissionPane." );
		detailsPane = new WorkedHoursSubmissionPane( project, config );
		detailsPane.layoutXProperty()
				.bind( selectPersonPane.layoutXProperty().add( selectPersonPane.widthProperty() ) );
		detailsPane.layoutYProperty().bind( selectPersonPane.layoutYProperty() );
		detailsPane.prefWidthProperty().bind( this.widthProperty().divide( 3 ).multiply( 2 ) );
		detailsPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		detailsPane.maxHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.getChildren().add( detailsPane );

		// Display Hour Log Pane
		LoggingTool.print( "HoursPane: Creating new HourLogDisplayPane." );
		hourLogDisplayPane = new HourLogDisplayPane( project, config );
		hourLogDisplayPane.layoutXProperty().bind( detailsPane.layoutXProperty() );
		hourLogDisplayPane.layoutYProperty().bind( detailsPane.layoutYProperty().add( detailsPane.heightProperty() ) );
		hourLogDisplayPane.prefWidthProperty().bind( detailsPane.widthProperty() );
		hourLogDisplayPane.prefHeightProperty().bind( this.heightProperty().subtract( detailsPane.heightProperty() ) );
		this.getChildren().add( hourLogDisplayPane );

		selectPersonPane.setSelectResponse( () -> {
			LoggingTool
					.print( "HoursPane: Relaying newly-selected person from SelectPersonPane to WorkedHoursSubmissionPane." );
			detailsPane.registerNewSelectedPerson( selectPersonPane.getSelectedPerson() );
		} );

		detailsPane.registerSubmitAction( () -> {
			LoggingTool
					.print( "HoursPane: Relaying submitted hours from WorkedHoursSubmissionPane to HourLogDisplayPane." );
			hourLogDisplayPane.update();
		} );
	}

	public void loadNewProject( Project project ) {
		selectPersonPane.loadNewProject( project );
		detailsPane.loadNewProject( project );
		hourLogDisplayPane.loadNewProject( project );
	}

}
