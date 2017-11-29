package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;

/**
 * A pane to display information about the program.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class AboutPane extends Pane {

	/**
	 * Constructs a pane using the given configuration. <p> <p><b>NOTE: The pane already sizes itself, and is meant to
	 * not be resizeable. Resizing it may result in a warped display.</b></p>
	 *
	 * @param config This defines some of the physical properties and behavior of this pane.
	 */
	public AboutPane( Config config ) {
		LoggingTool.print( "Constructing new AboutPane." );
		this.prefWidthProperty().setValue( 500 );
		this.prefHeightProperty().setValue( 400 );

		Color textColor = Color.WHITE;

		// Background
		Rectangle rectangle = new Rectangle( 500, 400, Color.BLACK );
		rectangle.layoutXProperty().setValue( 0 );
		rectangle.layoutYProperty().setValue( 0 );
		rectangle.widthProperty().bind( this.widthProperty().add( config.buffer ) );
		rectangle.heightProperty().bind( this.heightProperty().add( config.buffer ) );
		this.getChildren().add( rectangle );

		// Image
		ImageView image = new ImageView( new Image( "icon/icon.png" ) );
		image.layoutXProperty().setValue( config.buffer * 2 );
		image.layoutYProperty().setValue( config.buffer * 2 );
		this.getChildren().add( image );

		// Program Name
		Label nameA = new Label( "Project Management" );
		nameA.layoutXProperty()
				.bind( this.widthProperty().subtract( nameA.widthProperty() ).subtract( config.buffer * 2 ) );
		nameA.layoutYProperty().bind( image.layoutYProperty() );
		nameA.setFont( new Font( 36 ) );
		nameA.setTextFill( textColor );
		this.getChildren().add( nameA );

		Label nameB = new Label( "System" );
		nameB.layoutXProperty().bind( nameA.layoutXProperty().add( nameA.widthProperty().divide( 2 ) )
				.subtract( nameB.widthProperty().divide( 2 ) ) );
		nameB.layoutYProperty().bind( nameA.layoutYProperty().add( nameA.heightProperty() ) );
		nameB.setFont( new Font( 36 ) );
		nameB.setTextFill( textColor );
		this.getChildren().add( nameB );

		// Lauren
		Label lauren = new Label( "Lauren Robbins: Business Analyst" );
		lauren.layoutXProperty().bind( image.layoutXProperty() );
		lauren.layoutYProperty().bind( this.heightProperty().divide( 2 ) );
		lauren.setTextFill( textColor );
		this.getChildren().add( lauren );

		// Mark
		Label mark = new Label( "Mark Zeagler: Lead Designer/Programmer" );
		mark.layoutXProperty().bind( lauren.layoutXProperty() );
		mark.layoutYProperty().bind( lauren.layoutYProperty().add( lauren.heightProperty() ) );
		mark.setTextFill( textColor );
		this.getChildren().add( mark );

		// Nygel
		Label nygel = new Label( "Nygel Jones: Quality Assurance" );
		nygel.layoutXProperty().bind( mark.layoutXProperty() );
		nygel.layoutYProperty().bind( mark.layoutYProperty().add( mark.heightProperty() ) );
		nygel.setTextFill( textColor );
		this.getChildren().add( nygel );

		// Sharmell
		Label sharmell = new Label( "Sharmell Smith: Project Lead" );
		sharmell.layoutXProperty().bind( nygel.layoutXProperty() );
		sharmell.layoutYProperty().bind( nygel.layoutYProperty().add( nygel.heightProperty() ) );
		sharmell.setTextFill( textColor );
		this.getChildren().add( sharmell );

		// Bilash
		Label bilash = new Label( "Bilash Paul: Mascot" );
		bilash.layoutXProperty().bind( sharmell.layoutXProperty() );
		bilash.layoutYProperty().bind( sharmell.layoutYProperty().add( sharmell.heightProperty() ) );
		bilash.setTextFill( textColor );
		this.getChildren().add( bilash );

		// Underline
		Line line = new Line();
		line.startXProperty().bind( lauren.layoutXProperty() );
		line.endXProperty().bind( this.widthProperty().divide( 2 ) );
		line.startYProperty().bind( this.heightProperty().divide( 2 ) );
		line.endYProperty().bind( line.startYProperty() );
		line.setStroke( textColor );
		line.setStrokeWidth( 2 );
		this.getChildren().add( line );

		// Project Team
		Label team = new Label( "Project Team: " );
		team.layoutXProperty().bind( image.layoutXProperty() );
		team.layoutYProperty().bind( line.startYProperty().subtract( team.heightProperty() ) );
		team.setTextFill( textColor );
		team.setFont( new Font( 14 ) );
		this.getChildren().add( team );

		Label description = new Label(
				"This project was developed for the Software Project Management class (SWE 4663) of Kennesaw State University for the Fall 2017 semester. Our professor was Dr. Hassan Pournaghshband." );
		description.layoutXProperty().bind( bilash.layoutXProperty() );
		description.layoutYProperty()
				.bind( this.heightProperty().subtract( description.heightProperty() ).subtract( config.buffer ) );
		description.prefWidthProperty().bind( this.widthProperty().subtract( config.buffer * 4 ) );
		description.setWrapText( true );
		description.setTextFill( Color.GRAY );
		this.getChildren().add( description );
	}
}
