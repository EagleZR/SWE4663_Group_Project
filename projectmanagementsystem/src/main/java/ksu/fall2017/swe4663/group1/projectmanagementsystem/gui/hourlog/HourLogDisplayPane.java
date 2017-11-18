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

/**
 * Displays the current {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.ProjectHourLog} for the
 * current {@link Project} using {@link HourTypeDisplayPane}s for each {@link WorkedHourType}.
 */
class HourLogDisplayPane extends FramedPane implements ProjectPane {

	private Project project;
	private Label total;
	private HourTypeDisplayPane requirements;
	private HourTypeDisplayPane design;
	private HourTypeDisplayPane coding;
	private HourTypeDisplayPane testing;
	private HourTypeDisplayPane management;
	private ScrollPane scrollPane;

	/**
	 * Constructs a new {@link HourLogDisplayPane} from the given {@link Project}.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 */
	protected HourLogDisplayPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new HourLogDisplayPane." );
		this.project = project;
		LoggingTool.print( "HourLogDisplayPane: Creating new Label." );
		this.total = new Label();
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		this.requirements = new HourTypeDisplayPane( WorkedHourType.REQUIREMENTS_ANALYSIS, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		this.design = new HourTypeDisplayPane( WorkedHourType.DESIGNING, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		this.coding = new HourTypeDisplayPane( WorkedHourType.CODING, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		this.testing = new HourTypeDisplayPane( WorkedHourType.TESTING, project, config );
		LoggingTool.print( "HourLogDisplayPane: Creating new HourTypeDisplayPane." );
		this.management = new HourTypeDisplayPane( WorkedHourType.PROJECT_MANAGEMENT, project, config );

		this.scrollPane = new ScrollPane();

		setup( config );
		update();
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private void setup( Config config ) {
		//		Rectangle rectangle = new Rectangle();
		//		rectangle.layoutXProperty().setValue( 0 );
		//		rectangle.layoutYProperty().setValue( 0 );
		//		rectangle.widthProperty().bind( this.widthProperty() );
		//		rectangle.heightProperty().bind( this.heightProperty() );
		//		rectangle.setFill( Color.RED );
		//		this.getChildren().add( rectangle );

		// Scroll Pane
		this.getChildren().add( this.scrollPane );
		this.scrollPane.setFitToWidth( true );
		this.scrollPane.layoutXProperty().setValue( 2 );
		this.scrollPane.layoutYProperty().setValue( 2 );
		this.scrollPane.prefWidthProperty().bind( this.widthProperty().subtract( 4 ) );
		this.scrollPane.prefHeightProperty().bind( this.heightProperty().subtract( 4 ) );

		Pane contentPane = new Pane();
		this.scrollPane.setContent( contentPane );

		//		Rectangle rectangle1 = new Rectangle();
		//		rectangle1.layoutXProperty().setValue( 0 );
		//		rectangle1.layoutYProperty().setValue( 0 );
		//		rectangle1.widthProperty().bind( contentPane.widthProperty() );
		//		rectangle1.heightProperty().bind( contentPane.heightProperty() );
		//		rectangle1.setFill( Color.BLUE );
		//		contentPane.getChildren().add( rectangle1 );

		// Total
		this.total.layoutXProperty().setValue( config.buffer );
		this.total.layoutYProperty().setValue( config.buffer );
		contentPane.getChildren().add( this.total );

		// Requirements
		this.requirements.layoutXProperty().setValue( 0 );
		this.requirements.layoutYProperty()
				.bind( this.total.layoutYProperty().add( this.total.heightProperty().add( config.buffer ) ) );
		this.requirements.prefWidthProperty().bind( contentPane.widthProperty() );
		contentPane.getChildren().add( this.requirements );

		// Design
		this.design.layoutXProperty().bind( this.requirements.layoutXProperty() );
		this.design.layoutYProperty()
				.bind( this.requirements.layoutYProperty().add( this.requirements.heightProperty() ) );
		this.design.prefWidthProperty().bind( contentPane.widthProperty() );
		contentPane.getChildren().add( this.design );

		// Coding
		this.coding.layoutXProperty().bind( this.design.layoutXProperty() );
		this.coding.layoutYProperty().bind( this.design.layoutYProperty().add( this.design.heightProperty() ) );
		this.coding.prefWidthProperty().bind( contentPane.widthProperty() );
		contentPane.getChildren().add( this.coding );

		// Testing
		this.testing.layoutXProperty().bind( this.coding.layoutXProperty() );
		this.testing.layoutYProperty().bind( this.coding.layoutYProperty().add( this.coding.heightProperty() ) );
		this.testing.prefWidthProperty().bind( contentPane.widthProperty() );
		contentPane.getChildren().add( this.testing );

		// Management
		this.management.layoutXProperty().bind( this.testing.layoutXProperty() );
		this.management.layoutYProperty().bind( this.testing.layoutYProperty().add( this.testing.heightProperty() ) );
		this.management.prefWidthProperty().bind( contentPane.widthProperty() );
		contentPane.getChildren().add( this.management );

		contentPane.prefHeightProperty().bind( this.total.heightProperty().add( this.requirements.heightProperty() )
				.add( this.design.heightProperty() ).add( this.coding.heightProperty() )
				.add( this.testing.heightProperty() ).add( this.management.heightProperty() ).add( config.buffer ) );
	}

	/**
	 * This updates each field to display their current value.
	 */
	void update() {
		this.total.setText( "Total: " + this.project.getTeam().getHours( WorkedHourType.ANY ) );
		this.requirements.update();
		this.design.update();
		this.coding.update();
		this.testing.update();
		this.management.update();
	}

	@Override public void loadNewProject( Project project ) {
		this.project = project;
		this.total.setText( "Total: " + project.getTeam().getHours( WorkedHourType.ANY ) );
		this.requirements.loadNewProject( project );
		this.design.loadNewProject( project );
		this.coding.loadNewProject( project );
		this.testing.loadNewProject( project );
		this.management.loadNewProject( project );
		LoggingTool.print( "HourLogDisplayPane: Loaded new project." );
	}
}
