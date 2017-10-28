package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class RequirementsPane extends FramedPane implements ProjectPane {

	private Config config;
	private Stage primaryStage;
	private RequirementsListPane requirementsListPane;
	private Stage filterStage;
	private RequirementFilterPane filterPane;

	public RequirementsPane( Project project, Config config, Stage primaryStage ) {
		this.config = config;
		this.primaryStage = primaryStage;
		this.requirementsListPane = new RequirementsListPane( project, config, primaryStage );
		setup();
	}

	private void setup() {
		// Add new requirement button
		Button addRequirementButton = new Button( "Add Requirement" );
		addRequirementButton.layoutXProperty()
				.bind( this.widthProperty().subtract( addRequirementButton.widthProperty() )
						.subtract( this.config.buffer ) );
		addRequirementButton.layoutYProperty()
				.bind( this.heightProperty().subtract( addRequirementButton.heightProperty() )
						.subtract( this.config.buffer / 2 ) );
		addRequirementButton.setOnAction( e -> {
			RequirementDetailsPane.showAddPane( this.requirementsListPane, this.primaryStage, this.config );
		} );
		this.getChildren().add( addRequirementButton );

		// Filter button
		Button filterButton = new Button( "Filter" );
		filterButton.layoutXProperty().bind( this.requirementsListPane.layoutXProperty() );
		filterButton.layoutYProperty().bind( this.heightProperty().subtract( filterButton.heightProperty() )
				.subtract( this.config.buffer / 2 ) );
		filterButton.setOnAction( e -> {
			if ( this.filterStage == null ) {
				this.filterStage = new Stage();
				this.filterPane = new RequirementFilterPane( this.requirementsListPane.getFilter(),
						() -> this.requirementsListPane.update(), this.filterStage, this.config );
				Scene scene = new Scene( this.filterPane );
				this.filterStage.setScene( scene );
				this.filterStage.setWidth( 500 );
				this.filterStage.setHeight( 400 );
				this.filterStage.setTitle( "Filter Requirements" );
				this.filterStage.getIcons().addAll( this.primaryStage.getIcons() );
			}
			this.filterStage.show();
		} );
		this.getChildren().add( filterButton );

		// RequirementsListPane
		this.requirementsListPane.layoutXProperty().setValue( this.config.buffer );
		this.requirementsListPane.layoutYProperty().setValue( this.config.buffer / 2 );
		this.requirementsListPane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.requirementsListPane.prefHeightProperty()
				.bind( filterButton.layoutYProperty().subtract( this.config.buffer ) );
		this.getChildren().add( this.requirementsListPane );

		// Reset filter button
		Button resetFilterButton = new Button( "Reset Filter" );
		resetFilterButton.layoutXProperty()
				.bind( filterButton.layoutXProperty().add( filterButton.widthProperty() ).add( this.config.buffer ) );
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
