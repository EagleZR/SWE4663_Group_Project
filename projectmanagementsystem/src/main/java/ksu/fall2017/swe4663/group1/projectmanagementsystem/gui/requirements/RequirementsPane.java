package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

/**
 * A pane which displays and edits all {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement}s
 * associated with a given {@link Project}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RequirementsPane extends FramedPane implements ProjectPane {

	private RequirementsListPane requirementsListPane;
	private Stage filterStage;
	private RequirementFilterPane filterPane;

	/**
	 * Constructs a new pane for the given {@link Project}.
	 *
	 * @param project The project currently being viewed/edited.
	 * @param stage   The stage over which pop-ups will be displayed.
	 * @param config  This defines some of the physical properties and behavior of this pane.
	 */
	public RequirementsPane( Project project, Stage stage, Config config ) {
		this.requirementsListPane = new RequirementsListPane( project, stage, config );
		setup( stage, config );
	}

	/**
	 * This sets up the pane and places each of the components in their correct positions.
	 *
	 * @param stage  The stage over which pop-ups will be displayed.
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	private void setup( Stage stage, Config config ) {
		// Add new requirement button
		Button addRequirementButton = new Button( "Add Requirement" );
		addRequirementButton.layoutXProperty()
				.bind( this.widthProperty().subtract( addRequirementButton.widthProperty() )
						.subtract( config.buffer ) );
		addRequirementButton.layoutYProperty()
				.bind( this.heightProperty().subtract( addRequirementButton.heightProperty() )
						.subtract( config.buffer / 2 ) );
		addRequirementButton.setOnAction( e -> {
			RequirementDetailsPane.showAddPane( this.requirementsListPane, stage, config );
		} );
		this.getChildren().add( addRequirementButton );

		// Filter button
		Button filterButton = new Button( "Filter" );
		filterButton.layoutXProperty().bind( this.requirementsListPane.layoutXProperty() );
		filterButton.layoutYProperty()
				.bind( this.heightProperty().subtract( filterButton.heightProperty() ).subtract( config.buffer / 2 ) );
		filterButton.setOnAction( e -> {
			if ( this.filterStage == null ) {
				this.filterStage = new Stage();
				this.filterPane = new RequirementFilterPane( this.requirementsListPane.getFilter(),
						() -> this.requirementsListPane.update(), this.filterStage, config );
				Scene scene = new Scene( this.filterPane );
				this.filterStage.setScene( scene );
				this.filterStage.setWidth( 500 );
				this.filterStage.setHeight( 400 );
				this.filterStage.setTitle( "Filter Requirements" );
				this.filterStage.getIcons().addAll( stage.getIcons() );
			}
			this.filterStage.show();
		} );
		this.getChildren().add( filterButton );

		// RequirementsListPane
		this.requirementsListPane.layoutXProperty().setValue( config.buffer );
		this.requirementsListPane.layoutYProperty().setValue( config.buffer / 2 );
		this.requirementsListPane.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 2 ) );
		this.requirementsListPane.prefHeightProperty().bind( filterButton.layoutYProperty().subtract( config.buffer ) );
		this.getChildren().add( this.requirementsListPane );

		// Reset filter button
		Button resetFilterButton = new Button( "Reset Filter" );
		resetFilterButton.layoutXProperty()
				.bind( filterButton.layoutXProperty().add( filterButton.widthProperty() ).add( config.buffer ) );
		resetFilterButton.layoutYProperty().bind( filterButton.layoutYProperty() );
		resetFilterButton.setOnAction( e -> {
			this.requirementsListPane.getFilter().resetFilter();
			this.requirementsListPane.update();
			this.filterPane.update();
		} );
		this.getChildren().add( resetFilterButton );
	}

	@Override public void loadNewProject( Project project ) {
		this.requirementsListPane.loadNewProject( project );
	}
}
