package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.logs.LoggingTool;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;

public class RequirementPane extends FramedPane {

	private Requirement requirement;
	private Config config;

	private RequirementsListPane parentPane;

	private Label titleLabel;
	private Label idLabel;
	private Label statusLabel;
	private Label sourceLabel;
	private Label priorityLabel;
	private Label descriptionArea;
	private Label isCompleteLabel;
	private Label isFunctionalLabel;

	protected RequirementPane( Requirement requirement, RequirementsListPane parentPane, Config config,
			Stage primaryStage ) {
		LoggingTool.print( "Constructing new RequirementPane." );
		this.requirement = requirement;
		this.parentPane = parentPane;
		this.config = config;

		// Setup Mouse Pressed Event
		this.setOnMousePressed(
				e -> RequirementDetailsPane.showEditPane( this, this.parentPane, primaryStage, config ) );

		setup();
		update();
	}

	private void setup() {
		// Title
		this.titleLabel = new Label();
		this.titleLabel.layoutXProperty().setValue( this.config.buffer );
		this.titleLabel.layoutYProperty().setValue( this.config.buffer );
		this.getChildren().add( this.titleLabel );

		// Complete/Incomplete Label
		this.isCompleteLabel = new Label();
		this.isCompleteLabel.layoutXProperty()
				.bind( this.widthProperty().subtract( this.isCompleteLabel.widthProperty() )
						.subtract( this.config.buffer ) );
		this.isCompleteLabel.layoutYProperty().bind( this.titleLabel.layoutYProperty() );
		this.getChildren().add( this.isCompleteLabel );

		// ID Label
		this.idLabel = new Label();
		this.idLabel.layoutXProperty().bind( this.titleLabel.layoutXProperty() );
		this.idLabel.layoutYProperty().bind( this.titleLabel.layoutYProperty().add( this.titleLabel.heightProperty() )
				.add( this.config.buffer / 2 ) );
		this.getChildren().add( this.idLabel );

		// Status Label
		this.statusLabel = new Label();
		this.statusLabel.layoutXProperty()
				.bind( this.layoutXProperty().add( this.widthProperty() ).subtract( this.statusLabel.widthProperty() )
						.subtract( this.config.buffer ) );
		this.statusLabel.layoutYProperty().bind( this.idLabel.layoutYProperty() );
		this.getChildren().add( this.statusLabel );

		// Source Label
		this.sourceLabel = new Label();
		this.sourceLabel.layoutXProperty().bind( this.idLabel.layoutXProperty() );
		this.sourceLabel.layoutYProperty().bind( this.idLabel.layoutYProperty().add( this.idLabel.heightProperty() )
				.add( this.config.buffer / 2 ) );
		this.getChildren().add( this.sourceLabel );

		// Priority Label
		this.priorityLabel = new Label();
		this.priorityLabel.layoutXProperty().bind( this.widthProperty().subtract( this.priorityLabel.widthProperty() )
				.subtract( this.config.buffer ) );
		this.priorityLabel.layoutYProperty()
				.bind( this.statusLabel.layoutYProperty().add( this.statusLabel.heightProperty() )
						.add( this.config.buffer / 2 ) );
		this.getChildren().add( this.priorityLabel );

		// TODO Add isFunctional

		// Description Label
		Label descriptionLabel = new Label( "Description: " );
		descriptionLabel.layoutXProperty().bind( this.idLabel.layoutXProperty() );
		descriptionLabel.layoutYProperty()
				.bind( this.sourceLabel.layoutYProperty().add( this.sourceLabel.heightProperty() )
						.add( this.config.buffer / 2 ) );
		this.getChildren().add( descriptionLabel );

		// Description Area
		this.descriptionArea = new Label();
		this.descriptionArea.layoutXProperty().bind( this.idLabel.layoutXProperty() );
		this.descriptionArea.layoutYProperty()
				.bind( descriptionLabel.layoutYProperty().add( descriptionLabel.heightProperty() )
						.add( this.config.buffer / 4 ) );
		this.descriptionArea.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.descriptionArea.prefHeightProperty()
				.bind( this.heightProperty().subtract( this.descriptionArea.layoutYProperty() )
						.subtract( this.config.buffer ) );
		this.descriptionArea.setAlignment( Pos.TOP_LEFT );
		this.descriptionArea.setWrapText( true );
		this.descriptionArea.setOnMouseClicked( this.getOnMouseClicked() );
		this.getChildren().add( this.descriptionArea );
	}

	public void update() {
		// TODO Add isFunctional
		this.titleLabel.setText( "Title: " + this.requirement.getTitle() );
		this.isCompleteLabel.setText( ( this.requirement.isComplete() ? "Complete" : "Incomplete" ) );
		this.isCompleteLabel.setTextFill( ( this.requirement.isComplete() ? Color.GREEN : Color.RED ) );
		this.idLabel.setText( "Requirement ID: " + this.requirement.getItemNumber() );
		this.statusLabel.setText( "Status: " + this.requirement.getStatus() );
		this.sourceLabel.setText( "Source: " + this.requirement.getSource() );
		this.priorityLabel.setText( "Priority: " + this.requirement.getPriority() );
		this.descriptionArea.setText( this.requirement.getDescription() );
	}

	public Requirement getRequirement() {
		return this.requirement;
	}
}
