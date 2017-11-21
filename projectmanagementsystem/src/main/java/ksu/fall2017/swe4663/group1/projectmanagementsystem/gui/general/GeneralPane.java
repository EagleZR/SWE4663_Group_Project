package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.risks.RisksPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;

/**
 * A pane that displays a {@link DescriptionPane}, a {@link ManagerPane}, a {@link TeamMembersPane}, and a {@link
 * RisksPane} for a given {@link Project}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class GeneralPane extends Pane implements ProjectPane {

	private DescriptionPane descriptionPane;
	private TeamMembersPane teamMembersPane;
	private ManagerPane managerPane;
	private RisksPane risksPane;

	/**
	 * Creates a new {@link GeneralPane} using the given parameters.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param stage   The stage over which pop-ups will be displayed.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 */
	public GeneralPane( Project project, Stage stage, Config config ) {
		LoggingTool.print( "Constructing new GeneralPane." );

		// Description Pane
		LoggingTool.print( "GeneralPane: Creating DescriptionPane in GeneralPane." );
		this.descriptionPane = new DescriptionPane( project, config );
		this.descriptionPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		this.descriptionPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.descriptionPane.layoutXProperty().setValue( 0 );
		this.descriptionPane.layoutYProperty().setValue( 0 );
		this.getChildren().add( this.descriptionPane );

		// Team Members Pane
		LoggingTool.print( "GeneralPane: Creating TeamMembersPane in GeneralPane." );
		this.teamMembersPane = new TeamMembersPane( project, stage, config );
		this.teamMembersPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		this.teamMembersPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.teamMembersPane.layoutXProperty().bind( this.descriptionPane.layoutXProperty() );
		this.teamMembersPane.layoutYProperty()
				.bind( this.descriptionPane.layoutYProperty().add( this.descriptionPane.heightProperty() ) );
		this.getChildren().add( this.teamMembersPane );

		// Project Owner Pane
		LoggingTool.print( "GeneralPane: Creating ManagerPane in GeneralPane." );
		this.managerPane = null;
		try {
			this.managerPane = new ManagerPane( project, stage, config );
		} catch ( PersonNotOnTeamException e ) {
			e.printStackTrace();
		}
		this.managerPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		this.managerPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.managerPane.layoutXProperty()
				.bind( this.descriptionPane.layoutXProperty().add( this.descriptionPane.widthProperty() ) );
		this.managerPane.layoutYProperty().bind( this.descriptionPane.layoutYProperty() );
		this.getChildren().add( this.managerPane );

		// Risks Pane
		LoggingTool.print( "GeneralPane: Creating RisksPane in GeneralPane." );
		this.risksPane = new RisksPane( project, stage, config );
		this.risksPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		this.risksPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		this.risksPane.layoutXProperty()
				.bind( this.descriptionPane.layoutXProperty().add( this.descriptionPane.widthProperty() ) );
		this.risksPane.layoutYProperty()
				.bind( this.descriptionPane.layoutYProperty().add( this.descriptionPane.heightProperty() ) );
		this.getChildren().addAll( this.risksPane );
	}

	public void loadNewProject( Project project ) {
		LoggingTool.print( "GeneralPane: Loading new project." );
		this.descriptionPane.loadNewProject( project );
		this.teamMembersPane.loadNewProject( project );
		this.managerPane.loadNewProject( project );
		this.risksPane.loadNewProject( project );
	}
}
