package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;

/**
 * Displays the {@link HourLogDisplayPane}, the {@link SelectPersonPane}, and the {@link WorkedHoursSubmissionPane}.
 */
public class HourLogPane extends Pane implements ProjectPane {

	private SelectPersonPane selectPersonPane;
	private WorkedHoursSubmissionPane submissionPane;
	private HourLogDisplayPane hourLogDisplayPane;

	/**
	 * Constructs a new {@link HourLogPane} using the given {@link Project}.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 */
	public HourLogPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new HourLogPane." );
		// Select Person Pane
		LoggingTool.print( "HourLogPane: Creating new SelectPersonPane." );
		this.selectPersonPane = new SelectPersonPane( project, config );
		this.selectPersonPane.layoutXProperty().setValue( 0 );
		this.selectPersonPane.layoutYProperty().setValue( 0 );
		this.selectPersonPane.prefWidthProperty().bind( this.widthProperty().divide( 3 ) );
		this.selectPersonPane.prefHeightProperty().bind( this.heightProperty() );
		this.getChildren().add( this.selectPersonPane );

		// Fill In Details Pane
		LoggingTool.print( "HourLogPane: Creating new WorkedHoursSubmissionPane." );
		this.submissionPane = new WorkedHoursSubmissionPane( config );
		this.submissionPane.layoutXProperty()
				.bind( this.selectPersonPane.layoutXProperty().add( this.selectPersonPane.widthProperty() ) );
		this.submissionPane.layoutYProperty().bind( this.selectPersonPane.layoutYProperty() );
		this.submissionPane.prefWidthProperty().bind( this.widthProperty().divide( 3 ).multiply( 2 ) );
		this.submissionPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.getChildren().add( this.submissionPane );

		// Display Hour Log Pane
		LoggingTool.print( "HourLogPane: Creating new HourLogDisplayPane." );
		this.hourLogDisplayPane = new HourLogDisplayPane( project, config );
		this.hourLogDisplayPane.layoutXProperty().bind( this.submissionPane.layoutXProperty() );
		this.hourLogDisplayPane.layoutYProperty()
				.bind( this.submissionPane.layoutYProperty().add( this.submissionPane.heightProperty() ) );
		this.hourLogDisplayPane.prefWidthProperty().bind( this.submissionPane.widthProperty() );
		this.hourLogDisplayPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.getChildren().add( this.hourLogDisplayPane );

		this.selectPersonPane.setSelectResponse( () -> {
			LoggingTool
					.print( "HourLogPane: Relaying newly-selected person from SelectPersonPane to WorkedHoursSubmissionPane." );
			this.submissionPane.registerNewSelectedPerson( this.selectPersonPane.getSelectedPerson() );
		} );

		this.submissionPane.registerSubmitAction( () -> {
			LoggingTool
					.print( "HourLogPane: Relaying submitted hours from WorkedHoursSubmissionPane to HourLogDisplayPane." );
			this.hourLogDisplayPane.update();
		} );
	}

	@Override public void loadNewProject( Project project ) {
		this.selectPersonPane.loadNewProject( project );
		this.submissionPane.loadNewProject( project );
		this.hourLogDisplayPane.loadNewProject( project );
		LoggingTool.print( "HourLogPane: Loaded new project." );
	}

}
