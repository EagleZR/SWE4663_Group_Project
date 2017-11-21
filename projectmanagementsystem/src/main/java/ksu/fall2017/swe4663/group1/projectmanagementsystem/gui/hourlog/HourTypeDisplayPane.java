package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;

/**
 * Displays the sum of {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours} that have
 * been submitted of a {@link WorkedHourType}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
class HourTypeDisplayPane extends FramedPane implements ProjectPane {

	private Team team;
	private WorkedHourType hourType;
	private ProgressBar progressBar;
	private Label label;

	/**
	 * Constructs a new {@link HourTypeDisplayPane} from the given {@link Project} and {@link WorkedHourType}.
	 *
	 * @param hourType The {@link WorkedHourType} that this pane will display.
	 * @param project  The project currently being viewed/edited.
	 * @param config   This defines some of the physical properties and behavior of this pane.
	 */
	protected HourTypeDisplayPane( WorkedHourType hourType, Project project, Config config ) {
		LoggingTool.print( "Constructing new HourTypeDisplayPane for " + hourType.toString() + "." );
		this.team = project.getTeam();
		this.hourType = hourType;

		this.label = new Label( hourType.toString() );
		this.label.layoutXProperty().setValue( config.buffer );
		this.label.layoutYProperty().setValue( config.buffer );
		this.getChildren().add( this.label );

		this.progressBar = new ProgressBar();
		this.progressBar.layoutXProperty().bind( this.label.layoutXProperty() );
		this.progressBar.layoutYProperty()
				.bind( this.label.layoutYProperty().add( this.label.heightProperty() ).add( config.buffer ) );
		this.progressBar.prefWidthProperty().bind( this.widthProperty().subtract( 20 ) );
		//		progressBar.prefHeightProperty()
		//				.bind( this.heightProperty().subtract( config.buffer * 3 ).subtract( label.heightProperty() ) );
		this.progressBar.prefHeightProperty().setValue( 20 );
		this.getChildren().add( this.progressBar );
		this.prefHeightProperty()
				.bind( this.label.heightProperty().add( this.progressBar.heightProperty() ).add( config.buffer * 3 ) );

		update();
	}

	/**
	 * This updates each field to display their current value.
	 */
	protected void update() {
		double totalWorked = this.team.getHours( WorkedHourType.ANY );
		double typeWorked = this.team.getHours( this.hourType );
		this.label.setText( this.hourType.toString() + ": " + typeWorked + " / " + totalWorked );
		this.progressBar.setProgress( ( totalWorked == 0 || typeWorked == 0 ? 0 : typeWorked / totalWorked ) );
	}

	@Override public void loadNewProject( Project project ) {
		this.team = project.getTeam();
		update();
		LoggingTool.print( "HourTypeDisplayPane: Loaded new project." );
	}
}
