package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.ProjectHourLog;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

class HourTypeDisplayPane extends FramedPane implements ProjectPane {

	private ProjectHourLog hourLog;
	private WorkedHourType hourType;
	private ProgressBar progressBar;
	private Label label;

	HourTypeDisplayPane( WorkedHourType hourType, Project project, Config config ) {
		LoggingTool.print( "Constructing new HourTypeDisplayPane for " + hourType.toString() + "." );
		this.hourLog = project.getTeam().getProjectHourLog();
		this.hourType = hourType;

		label = new Label( hourType.toString() );
		label.layoutXProperty().setValue( config.buffer );
		label.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( label );

		progressBar = new ProgressBar();
		progressBar.layoutXProperty().bind( label.layoutXProperty() );
		progressBar.layoutYProperty()
				.bind( label.layoutYProperty().add( label.heightProperty() ).add( config.buffer ) );
		progressBar.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
//		progressBar.prefHeightProperty()
//				.bind( this.heightProperty().subtract( config.buffer * 3 ).subtract( label.heightProperty() ) );
		progressBar.prefHeightProperty().setValue( 20 );
		this.getChildren().add( progressBar );
		this.minHeightProperty().bind( label.heightProperty().add( progressBar.heightProperty() ).add( 30 ) );

		update();
	}

	protected void update() {
		double totalWorked = hourLog.getHours( WorkedHourType.ANY );
		double typeWorked = hourLog.getHours( this.hourType );
		label.setText( hourType.toString() + ": " + typeWorked + " / " + totalWorked );
		progressBar.setProgress( ( totalWorked == 0 || typeWorked == 0 ? 0 : typeWorked / totalWorked ) );
	}

	@Override public void loadNewProject( Project project ) {
		this.hourLog = project.getTeam().getProjectHourLog();
		update();
	}
}
