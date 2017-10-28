package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.ProjectManagementPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

	private File configFile;
	private Config config;

	public static void main( String[] args ) {
		launch( args );
	}

	@Override public void start( Stage primaryStage ) throws Exception {
		////////////////////////////////////
		// Setup
		////////////////////////////////////
		File logFile = LoggingTool.generateLogPrinterFile( "projectmanager" );
		LoggingTool.getLogger().setDefault( LoggingTool.generateLogPrinter( logFile ) );
		//		System.setOut( new PrintStream( new FileOutputStream( logFile ) ) );
		//		System.setErr( new PrintStream( new FileOutputStream( logFile ) ) );
		LoggingTool.print( "Main: Initializing Project Manager." );
		loadSettings();
		Project project;
		try {
			project = initializeProject();
			LoggingTool.print( "Main: Previous save file found and loaded." );
		} catch ( Exception e ) {
			LoggingTool.print( "Main: Previous save not found. Creating new project." );
			project = new Project();
		}
		LoggingTool.print( "Main: Initializing ErrorPopupSystem." );
		ErrorPopupSystem.setDefaultStage( primaryStage );

		////////////////////////////////////
		// Display Scene
		////////////////////////////////////
		LoggingTool.print( "Main: Creating ProjectManagementPane." );
		ProjectManagementPane pane = new ProjectManagementPane( primaryStage, this.config, project );
		primaryStage.setTitle( "Project Management System" );
		primaryStage.setScene( new Scene( pane, this.config.windowWidth, this.config.windowHeight ) );
		primaryStage.setMinWidth( 450 );
		primaryStage.setMinHeight( 500 );
		primaryStage.getIcons().add( new Image( "icon/icon.png" ) );
		LoggingTool.print( "Main: Displaying Project Management System window." );
		primaryStage.setWidth( 500 );
		primaryStage.setHeight( 600 );
		primaryStage.show();
	}

	/**
	 * Load settings from the config file
	 */
	private void loadSettings() throws IOException {
		LoggingTool.print( "Main: Loading settings." );
		try {
			LoggingTool.print( "Main: Configuration file found. Reading configuration." );
			this.configFile = new File( "data/config.ini" );
			this.config = new Config( this.configFile );
		} catch ( FileNotFoundException e ) {
			LoggingTool.print( "Main: Configuration file not found. Initializing new configuration." );
			initializeNewConfigFile();
		}
	}

	/**
	 * Initializes a new config file with default settings.
	 *
	 * @throws IOException
	 */
	private void initializeNewConfigFile() throws IOException {
		File newConfigFile = new File( "data/config.ini" );
		LoggingTool.print( "Main: Creating new configuration file at " + newConfigFile.getAbsolutePath() + "." );
		newConfigFile.getParentFile().mkdirs();
		newConfigFile.createNewFile();
		LoggingTool.print( "Main: Setting default configuration." );
		this.config = new Config( newConfigFile );
	}

	/**
	 * Creates a new {@link Team} if possible.
	 *
	 * @return The loaded {@link Project}.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Project initializeProject() throws IOException, ClassNotFoundException {
		LoggingTool.print( "Main: Loading previous save." );
		return Project.load( this.config.previousSave );
	}

	/**
	 * Saves the config and closes the logger
	 */
	@Override public void stop() {
		try {
			this.config.close();
		} catch ( IOException e ) {
			LoggingTool.print( "Config file could not be closed." );
		}
		LoggingTool.print( "Program is exiting." );
		LoggingTool.getLogger().close();
		Platform.exit();
	}
}
