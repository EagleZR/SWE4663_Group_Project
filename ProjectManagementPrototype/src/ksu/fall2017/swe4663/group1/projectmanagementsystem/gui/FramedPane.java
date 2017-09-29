package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public abstract class FramedPane extends Pane {

	public FramedPane() {
		LoggingTool.print( "Constructing new FramedPane." );
		// Draw Frame
		Line topLine = new Line( 1, 1, this.getWidth(), 1 );
		Line rightLine = new Line( this.getWidth(), 1, this.getWidth(), this.getHeight() );
		Line leftLine = new Line( 1, 1, 1, this.getHeight() );
		Line bottomLine = new Line( 1, this.getHeight(), this.getWidth(), this.getHeight() );

		topLine.endXProperty().bind( this.widthProperty().subtract( 1 ) );

		rightLine.startXProperty().bind( this.widthProperty().subtract( 1 ) );
		rightLine.endXProperty().bind( rightLine.startXProperty() );
		rightLine.endYProperty().bind( this.heightProperty().subtract( 1 ) );

		leftLine.endYProperty().bind( this.heightProperty().subtract( 1 ) );

		bottomLine.startYProperty().bind( this.heightProperty().subtract( 1 ) );
		bottomLine.endXProperty().bind( this.widthProperty().subtract( 1 ) );
		bottomLine.endYProperty().bind( bottomLine.startYProperty() );

		this.getChildren().addAll( topLine, rightLine, leftLine, bottomLine );
	}

	public FramedPane( String message ) {
		this();
		Label label = new Label( message );
		label.layoutXProperty().bind( this.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
		label.layoutYProperty()
				.bind( this.heightProperty().divide( 2 ).subtract( label.heightProperty().divide( 2 ) ) );
		this.getChildren().addAll( label );
	}
}
