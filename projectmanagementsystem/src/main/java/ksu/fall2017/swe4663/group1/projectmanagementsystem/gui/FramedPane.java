package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * A pane which places a simple framed border just inside the outer edges of the pane, just for appearances. Otherwise
 * behaves no differently than a regular {@link Pane}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public abstract class FramedPane extends Pane {

	/**
	 * Constructs a framed pane with the simple frame.
	 */
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

	/**
	 * Constructs a framed pane with an additional label in the center of the pane.
	 *
	 * @param message The text that will go inside of the label.
	 */
	public FramedPane( String message ) {
		this();
		Label label = new Label( message );
		label.layoutXProperty().bind( this.widthProperty().divide( 2 ).subtract( label.widthProperty().divide( 2 ) ) );
		label.layoutYProperty()
				.bind( this.heightProperty().divide( 2 ).subtract( label.heightProperty().divide( 2 ) ) );
		this.getChildren().addAll( label );
	}
}
