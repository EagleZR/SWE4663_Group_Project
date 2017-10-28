package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.FramedPane;

public class DescriptionPane extends FramedPane implements ProjectPane {

	private Project project;
	private TextArea textArea;
	private Label statusLabel;
	private Config config;

	public DescriptionPane( Project project, Config config ) {
		LoggingTool.print( "Constructing new DescriptionPane." );
		this.project = project;
		this.config = config;
		setup();
	}

	private void setup() {
		// Label
		LoggingTool.print( "DescriptionPane: Creating new title label in DescriptionPane." );
		Label label = new Label( "Project Description: " );
		label.layoutXProperty().setValue( this.config.buffer );
		label.layoutYProperty().setValue( this.config.buffer );
		this.getChildren().add( label );

		// Text Area
		LoggingTool.print( "DescriptionPane: Creating new TextArea in DescriptionPane." );
		this.textArea = new TextArea( this.project.getDescription() );
		this.textArea.prefWidthProperty().bind( this.widthProperty().subtract( this.config.buffer * 2 ) );
		this.textArea.layoutXProperty().bind( label.layoutXProperty() );
		this.textArea.layoutYProperty().bind( label.layoutYProperty().add( label.heightProperty() ).add( this.config.buffer ) );
		this.textArea.wrapTextProperty().setValue( true );
		this.textArea.setOnKeyTyped( e -> {
			showStatusChanged();
		} );
		this.getChildren().add( this.textArea );

		// Update Button
		LoggingTool.print( "DescriptionPane: Creating update Button in DescriptionPane." );
		Button update = new Button( "Update" );
		update.setOnAction( e -> {
			LoggingTool.print( "DescriptionPane: Updating the description in DescriptionPane." );
			this.project.setDescription( this.textArea.getText() );
			showStatusCurrent();
		} );
		update.layoutXProperty()
				.bind( this.textArea.layoutXProperty().add( this.textArea.widthProperty() ).subtract( update.widthProperty() ) );
		update.layoutYProperty()
				.bind( this.heightProperty().subtract( this.config.buffer ).subtract( update.heightProperty() ) );
		this.getChildren().add( update );

		// Reset Button
		LoggingTool.print( "DescriptionPane: Creating reset button in DescriptionPane." );
		Button reset = new Button( "Reset" );
		reset.setOnAction( e -> {
			LoggingTool.print( "DescriptionPane: Resetting description in DescriptionPane." );
			this.textArea.setText( this.project.getDescription() );
			showStatusCurrent();
		} );
		reset.layoutXProperty().bind( update.layoutXProperty().subtract( reset.widthProperty().add( this.config.buffer ) ) );
		reset.layoutYProperty().bind( update.layoutYProperty() );
		this.getChildren().add( reset );

		this.textArea.prefHeightProperty()
				.bind( update.layoutYProperty().subtract( this.textArea.layoutYProperty().add( this.config.buffer ) ) );

		// Status Label
		LoggingTool.print( "DescriptionPane: Creating status label in DescriptionPane." );
		this.statusLabel = new Label( "Status: Current" );
		this.statusLabel.layoutXProperty().bind( this.textArea.layoutXProperty() );
		this.statusLabel.layoutYProperty().bind( update.layoutYProperty() );
		this.statusLabel.setTextFill( Color.GREEN );

		this.getChildren().add( this.statusLabel );
	}

	private void showStatusChanged() {
		LoggingTool.print( "DescriptionPane: Updating status label in DescriptionPane to show \"Status: Changed\"." );
		this.statusLabel.setText( "Status: Changed" );
		this.statusLabel.setTextFill( Color.RED );
	}

	private void showStatusCurrent() {
		LoggingTool.print( "DescriptionPane: Updating status label in DescriptionPane to show \"Status: Current\"." );
		this.statusLabel.setText( "Status: Current" );
		this.statusLabel.setTextFill( Color.GREEN );
	}

	public void loadNewProject( Project project ) {
		LoggingTool.print( "DescriptionPane: Loading new project." );
		this.project = project;
		this.textArea.setText( project.getDescription() );
		showStatusCurrent();
	}

}
