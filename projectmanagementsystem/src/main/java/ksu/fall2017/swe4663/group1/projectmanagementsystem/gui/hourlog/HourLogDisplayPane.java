package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

class HourLogDisplayPane extends FramedPane implements ProjectPane {

	private Project project;
	//	private HourTypeDisplayPane total;
	private Label total;
	private HourTypeDisplayPane requirements;
	private HourTypeDisplayPane design;
	private HourTypeDisplayPane coding;
	private HourTypeDisplayPane testing;
	private HourTypeDisplayPane management;
	private ScrollPane scrollPane;

	HourLogDisplayPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new HourLogDisplayPane." );
		this.project = project;
		LoggingTool.print( "HourLogDisplayPane: Creating new Label." );
		total = new Label();
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		requirements = new HourTypeDisplayPane( WorkedHourType.REQUIREMENTS_ANALYSIS, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		design = new HourTypeDisplayPane( WorkedHourType.DESIGNING, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		coding = new HourTypeDisplayPane( WorkedHourType.CODING, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		testing = new HourTypeDisplayPane( WorkedHourType.TESTING, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		management = new HourTypeDisplayPane( WorkedHourType.PROJECT_MANAGEMENT, project, config );

		scrollPane = new ScrollPane();

		setup( config );
		update();
	}

	private void setup( Config config ) {

		this.getChildren().addAll( scrollPane );
		scrollPane.setFitToWidth( true );
		scrollPane.layoutXProperty().setValue( 2 );
		scrollPane.layoutYProperty().setValue( 2 );
		scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 4 ) );
		scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( 4 ) );

		Pane pane = new Pane();
		scrollPane.setContent( pane );

		// Total
		total.layoutXProperty().setValue( config.buffer );
		total.layoutYProperty().setValue( config.buffer );
		pane.getChildren().add( total );

		// Requirements
		requirements.layoutXProperty().setValue( 0 );
		requirements.layoutYProperty()
				.bind( total.layoutYProperty().add( total.heightProperty().add( config.buffer ) ) );
		requirements.prefWidthProperty().bind( pane.widthProperty() );
		pane.getChildren().add( requirements );

		// Design
		design.layoutXProperty().bind( requirements.layoutXProperty() );
		design.layoutYProperty().bind( requirements.layoutYProperty().add( requirements.heightProperty() ) );
		design.prefWidthProperty().bind( pane.widthProperty() );
		pane.getChildren().add( design );

		// Coding
		coding.layoutXProperty().bind( design.layoutXProperty() );
		coding.layoutYProperty().bind( design.layoutYProperty().add( design.heightProperty() ) );
		coding.prefWidthProperty().bind( pane.widthProperty() );
		pane.getChildren().add( coding );

		// Testing
		testing.layoutXProperty().bind( coding.layoutXProperty() );
		testing.layoutYProperty().bind( coding.layoutYProperty().add( coding.heightProperty() ) );
		testing.prefWidthProperty().bind( pane.widthProperty() );
		pane.getChildren().addAll( testing );

		// Management
		management.layoutXProperty().bind( testing.layoutXProperty() );
		management.layoutYProperty().bind( testing.layoutYProperty().add( testing.heightProperty() ) );
		management.prefWidthProperty().bind( pane.widthProperty() );
		pane.getChildren().add( management );
	}

	void update() {
		total.setText( "Total: " + project.getTeam().getHours( WorkedHourType.ANY ) );
		requirements.update();
		design.update();
		coding.update();
		testing.update();
		management.update();
	}

	@Override public void loadNewProject( Project project ) {
		this.project = project;
		//		total.loadNewProject( project );
		requirements.loadNewProject( project );
		design.loadNewProject( project );
		coding.loadNewProject( project );
		testing.loadNewProject( project );
		management.loadNewProject( project );
		// Each sub-pane already updates. No need to do it again
	}
}
