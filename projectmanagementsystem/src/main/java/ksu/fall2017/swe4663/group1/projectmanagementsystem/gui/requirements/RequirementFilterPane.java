package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.javafx.stages.PopupStage;
import eaglezr.support.errorsystem.ErrorPopupSystem;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

import java.util.function.Consumer;

public class RequirementFilterPane extends FramedPane
		implements Consumer<RequirementFilter.RequirementComparator>, ProjectPane {

	private RequirementFilter filter;
	private Runnable apply;
	private Stage stage;
	private Config config;
	private FilterButtonScrollPane pane;

	RequirementFilterPane( RequirementFilter filter, Runnable apply, Stage stage, Config config ) {
		this.filter = filter;
		this.apply = apply;
		this.stage = stage;
		this.config = config;
		setup();
	}

	private void setup() {
		// Close Button
		Button closeButton = new Button( "Close" );
		closeButton.layoutXProperty()
				.bind( this.widthProperty().subtract( closeButton.widthProperty() ).subtract( this.config.buffer ) );
		closeButton.layoutYProperty()
				.bind( this.heightProperty().subtract( closeButton.heightProperty() ).subtract( this.config.buffer ) );
		closeButton.setOnAction( e -> this.stage.close() );
		this.getChildren().add( closeButton );

		// Apply Button
		Button applyButton = new Button( "Apply" );
		applyButton.layoutXProperty().bind( closeButton.layoutXProperty().subtract( applyButton.widthProperty() )
				.subtract( this.config.buffer / 2 ) );
		applyButton.layoutYProperty().bind( closeButton.layoutYProperty() );
		applyButton.setOnAction( e -> this.apply.run() );
		this.getChildren().add( applyButton );

		// Ok Button
		Button okButton = new Button( "Ok" );
		okButton.layoutXProperty().bind( applyButton.layoutXProperty().subtract( okButton.widthProperty() )
				.subtract( this.config.buffer / 2 ) );
		okButton.layoutYProperty().bind( applyButton.layoutYProperty() );
		okButton.setOnAction( e -> {
			applyButton.fire();
			closeButton.fire();
		} );
		this.getChildren().add( okButton );

		// Add Filter Button
		Button addFilter = new Button( "Add Filter" );
		addFilter.layoutXProperty().setValue( this.config.buffer );
		addFilter.layoutYProperty().bind( okButton.layoutYProperty().subtract( addFilter.heightProperty() )
				.subtract( this.config.buffer ) );
		addFilter.setOnAction( e -> {
			FilterBuildPane pane = new FilterBuildPane( this.config );
			pane.setConsumer( this );
			Scene scene = new Scene( pane );
			PopupStage stage = new PopupStage( scene, this.stage );
			pane.setStage( stage );
			stage.setWidth( 300 );
			stage.setHeight( 300 );
			stage.setTitle( "Add Filter" );
			stage.show();
		} );
		this.getChildren().add( addFilter );

		// Remove Filter Button
		Button removeFilter = new Button( "Remove Filter" );
		removeFilter.layoutXProperty()
				.bind( addFilter.layoutXProperty().add( addFilter.widthProperty() ).add( this.config.buffer / 2 ) );
		removeFilter.layoutYProperty().bind( addFilter.layoutYProperty() );
		removeFilter.setOnAction( e -> this.pane.removeButton( this.pane.selectedButton ) );
		this.getChildren().add( removeFilter );

		// Clear Filters Button
		Button clearFilters = new Button( "Clear Filters" );
		clearFilters.layoutXProperty().bind( removeFilter.layoutXProperty().add( removeFilter.widthProperty() )
				.add( this.config.buffer / 2 ) );
		clearFilters.layoutYProperty().bind( removeFilter.layoutYProperty() );
		clearFilters.setOnAction( e -> this.pane.clear() );
		this.getChildren().add( clearFilters );

		// ScrollPane
		this.pane = new FilterButtonScrollPane( this.filter );
		this.pane.layoutXProperty().setValue( this.config.buffer );
		this.pane.layoutYProperty().setValue( this.config.buffer );
		this.pane.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.pane.prefHeightProperty().bind( addFilter.layoutYProperty().subtract( this.pane.layoutYProperty() )
				.subtract( this.config.buffer ) );
		this.getChildren().add( this.pane );
	}

	void update() {
		this.pane.update();
	}

	@Override public void accept( RequirementFilter.RequirementComparator comparator ) {
		try {
			this.pane.addComparator( comparator );
		} catch ( RequirementFilter.RedundantComparatorException e ) {
			ErrorPopupSystem.displayMessage( e.getMessage() );
		}
	}

	@Override public void loadNewProject( Project project ) {

	}
}
