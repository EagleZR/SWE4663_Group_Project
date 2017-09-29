package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.ProjectManagementPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.DescriptionPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.ProjectOwnerPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.RisksPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.TeamMembersPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;

public class GeneralPane extends Pane implements ProjectPane {

	private Config config;
	DescriptionPane descriptionPane;
	TeamMembersPane teamMembersPane;
	ProjectOwnerPane projectOwnerPane;
	RisksPane risksPane;

	public GeneralPane( Stage primaryStage, Project project, Config config ) {
		LoggingTool.print( "Constructing new GeneralPane." );
		// Initialize system
		this.config = config;

		// Description Pane
		LoggingTool.print( "GeneralPane: Creating DescriptionPane in GeneralPane." );
		descriptionPane = new DescriptionPane( project, config );
		descriptionPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		descriptionPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		descriptionPane.layoutXProperty().setValue( 0 );
		descriptionPane.layoutYProperty().setValue( 0 );
		this.getChildren().add( descriptionPane );

		// Team Members Pane
		LoggingTool.print( "GeneralPane: Creating TeamMembersPane in GeneralPane." );
		teamMembersPane = new TeamMembersPane( primaryStage, project, config );
		teamMembersPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		teamMembersPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		teamMembersPane.layoutXProperty().bind( descriptionPane.layoutXProperty() );
		teamMembersPane.layoutYProperty()
				.bind( descriptionPane.layoutYProperty().add( descriptionPane.heightProperty() ) );
		this.getChildren().add( teamMembersPane );

		// Project Owner Pane
		LoggingTool.print( "GeneralPane: Creating ProjectOwnerPane in GeneralPane." );
		projectOwnerPane = null;
		try {
			projectOwnerPane = new ProjectOwnerPane( project, primaryStage, config );
		} catch ( PersonNotOnTeamException e ) {
			e.printStackTrace();
		}
		projectOwnerPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		projectOwnerPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		projectOwnerPane.layoutXProperty()
				.bind( descriptionPane.layoutXProperty().add( descriptionPane.widthProperty() ) );
		projectOwnerPane.layoutYProperty().bind( descriptionPane.layoutYProperty() );
		this.getChildren().add( projectOwnerPane );

		// Risks Pane
		LoggingTool.print( "GeneralPane: Creating RisksPane in GeneralPane." );
		risksPane = new RisksPane( config );
		risksPane.prefWidthProperty().bind( this.widthProperty().divide( 2 ) );
		risksPane.prefHeightProperty().bind( this.heightProperty().divide( 2 ) );
		risksPane.layoutXProperty().bind( descriptionPane.layoutXProperty().add( descriptionPane.widthProperty() ) );
		risksPane.layoutYProperty().bind( descriptionPane.layoutYProperty().add( descriptionPane.heightProperty() ) );
		this.getChildren().addAll( risksPane );
	}

	public void loadNewProject( Project project ) {
		LoggingTool.print( "GeneralPane: Loading new project." );
		descriptionPane.loadNewProject( project );
		teamMembersPane.loadNewProject( project );
		projectOwnerPane.loadNewProject( project );
		risksPane.loadNewProject( project );
	}
}
