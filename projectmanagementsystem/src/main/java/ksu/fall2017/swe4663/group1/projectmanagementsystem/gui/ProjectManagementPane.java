package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.GeneralPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog.HoursPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementsPane;

import java.io.File;
import java.io.IOException;

public class ProjectManagementPane extends BorderPane implements ProjectPane {

	private Stage primaryStage;
	private Project project;
	private Config config;
	private GeneralPane generalPane;
	private HoursPane hoursPane;
	private RequirementsPane requirementsPane;

	public ProjectManagementPane( Stage primaryStage, Config config, Project project ) {
		LoggingTool.print( "Constructing new ProjectManagementPane." );
		this.project = project;
		this.config = config;
		this.primaryStage = primaryStage;

		// Initialize primary panes
		BorderPane contentPane = new BorderPane();
		this.setCenter( contentPane );

		this.setTop( getMenuBar() );

		////////////////////////////////////
		// Initialize content panes
		////////////////////////////////////
		LoggingTool.print( "ProjectManagementPane: Creating GeneralPane in ProjectManagementPane." );
		generalPane = new GeneralPane( primaryStage, project, config );
		LoggingTool.print( "ProjectManagementPane: Creating RequirementsPane in ProjectManagementPane." );
		requirementsPane = new RequirementsPane( config );
		LoggingTool.print( "ProjectManagementPane: Creating HoursPane in ProjectManagementPane." );
		hoursPane = new HoursPane( project, config );
		LoggingTool.print( "ProjectManagementPane: Setting GeneralPane as content in ProjectManagementPane." );
		contentPane.setCenter( generalPane );
		generalPane.prefWidthProperty().bind( contentPane.widthProperty() );

		////////////////////////////////////
		// Initialize tabs
		////////////////////////////////////
		LoggingTool.print( "ProjectManagementPane: Creating tabs in ProjectManagementPane." );
		Pane tabsPane = new Pane();
		contentPane.setTop( tabsPane );

		// General Pane
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for GeneralPane in ProjectManagementPane." );
		Button generalPaneButton = new Button( "General" );
		generalPaneButton.setDefaultButton( true );
		generalPaneButton.prefWidthProperty().bind( tabsPane.widthProperty().divide( 3 ) );
		generalPaneButton.layoutXProperty().setValue( 0 );
		generalPaneButton.layoutYProperty().setValue( 0 );

		// Requirements
		LoggingTool
				.print( "ProjectManagementPane: Creating Button tab for RequirementsPane in ProjectManagementPane." );
		Button requirements = new Button( "Requirements" );
		requirements.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		requirements.layoutXProperty()
				.bind( generalPaneButton.layoutXProperty().add( generalPaneButton.widthProperty() ) );
		requirements.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Hours Expended
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for HoursPane in ProjectManagementPane." );
		Button hoursLog = new Button( "Hour Log" );
		hoursLog.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		hoursLog.layoutXProperty().bind( requirements.layoutXProperty().add( requirements.widthProperty() ) );
		hoursLog.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Actions
		generalPaneButton.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting GeneralPane as content in ProjectManagementPane." );
			contentPane.setCenter( generalPane );
			generalPaneButton.setDefaultButton( true );
			requirements.setDefaultButton( false );
			hoursLog.setDefaultButton( false );

		} );
		requirements.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting RequirementsPane as content in ProjectManagementPane." );
			contentPane.setCenter( requirementsPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( true );
			hoursLog.setDefaultButton( false );
		} );
		hoursLog.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting HoursPane as content in ProjectManagementPane." );
			contentPane.setCenter( hoursPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( false );
			hoursLog.setDefaultButton( true );
		} );
		tabsPane.getChildren().addAll( generalPaneButton, requirements, hoursLog );
	}

	private MenuBar getMenuBar() {
		LoggingTool.print( "ProjectManagementPane: Creating MenuBar." );
		MenuBar menuBar = new MenuBar();

		// File
		Menu file = new Menu( "File" );
		MenuItem newProject = new MenuItem( "New" );
		newProject.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: New Project Created." );
			this.project = new Project();
			this.config.previousSave = new File( config.savesDirectory.getPath() + "\\newProject.save" );
			loadNewProject( this.project );
		} );
		MenuItem save = new MenuItem( "Save" );
		MenuItem saveAs = new MenuItem( "Save As" );
		save.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Save button pressed." );
			if ( !this.config.previousSave.exists() ) {
				saveAs.fire();
			} else {
				LoggingTool.print( "ProjectManagementPane: Saving to " + config.previousSave.getAbsolutePath() + "." );
				try {
					Project.save( this.project, this.config.previousSave );
				} catch ( IOException e1 ) {
					ErrorPopupSystem.displayMessage( "The file could not be saved." );
				}
			}
		} );
		saveAs.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Save As button pressed." );
			FileChooser chooser = new FileChooser();
			chooser.setTitle( "Please select where to save the file." );
			chooser.setInitialDirectory( config.savesDirectory );
			chooser.setSelectedExtensionFilter( new FileChooser.ExtensionFilter( "Project Save File", ".save" ) );
			chooser.setInitialFileName( config.previousSave.getName() );

			Stage fileChooserStage = new Stage();
			fileChooserStage.initModality( Modality.APPLICATION_MODAL );
			fileChooserStage.initOwner( primaryStage );
			fileChooserStage.initStyle( StageStyle.UTILITY );
			fileChooserStage.resizableProperty().setValue( false );

			File chosenFile = chooser.showSaveDialog( fileChooserStage );

			if ( chosenFile != null ) {
				if ( chosenFile.getName().length() < 5 || !chosenFile.getName()
						.substring( chosenFile.getName().length() - 6 ).equals( ".save" ) ) {
					chosenFile = new File( chosenFile.getPath() + ".save" );
				}
				config.previousSave = chosenFile;

				try {
					Project.save( project, config.previousSave );
				} catch ( IOException e1 ) {
					ErrorPopupSystem.displayMessage( "Unable to save the file." );
					LoggingTool.print( "ProjectManagementPane: Unable to save the file." );
					e1.printStackTrace();
				}
			}

			fileChooserStage.close();
		} );
		MenuItem load = new MenuItem( "Load" );
		load.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Load button pressed." );
			FileChooser chooser = new FileChooser();
			chooser.setTitle( "Please select where to save the file." );
			chooser.setInitialDirectory( config.savesDirectory );
			chooser.setSelectedExtensionFilter( new FileChooser.ExtensionFilter( "Project Save File", ".save" ) );
			chooser.setInitialFileName( config.previousSave.getName() );

			Stage fileChooserStage = new Stage();
			fileChooserStage.initModality( Modality.APPLICATION_MODAL );
			fileChooserStage.initOwner( primaryStage );
			fileChooserStage.initStyle( StageStyle.UTILITY );
			fileChooserStage.resizableProperty().setValue( false );

			File chosenFile = chooser.showOpenDialog( fileChooserStage );

			if ( chosenFile != null ) {
				config.previousSave = chosenFile;

				try {
					Project project = Project.load( config.previousSave );
					this.project = project;
					loadNewProject( this.project );
				} catch ( IOException e1 ) {
					ErrorPopupSystem.displayMessage( "Unable to load the file." );
					LoggingTool.print( "ProjectManagementPane: Unable to load the file." );
					e1.printStackTrace();
				} catch ( ClassNotFoundException e1 ) {
					ErrorPopupSystem.displayMessage( "That file does not exist" );
					LoggingTool.print( "ProjectManagementPane: The specified file does not exist." );
					e1.printStackTrace();
				}
			}

			fileChooserStage.close();
		} );
		MenuItem exit = new MenuItem( "Exit" );
		exit.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Exit button pressed." );
			Platform.exit();
		} );
		file.getItems().addAll( newProject, save, saveAs, load, exit );

		// Options
		Menu options = new Menu( "Options" );
		MenuItem settings = new MenuItem( "Settings" );
		settings.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Settings button pressed." );
			// TODO Display settings stage
		} );
		options.getItems().addAll( settings );

		// Help
		Menu help = new Menu( "Help" );
		MenuItem helpItem = new MenuItem( "Help" );
		helpItem.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Help button pressed." );
			// TODO Display a help stage
		} );
		MenuItem about = new MenuItem( "About" );
		about.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: About button pressed." );
			// TODO Display the about stage
		} );
		help.getItems().addAll( helpItem, about );

		menuBar.getMenus().addAll( file, options, help );

		return menuBar;
	}

	@Override public void loadNewProject( Project project ) {
		generalPane.loadNewProject( project );
		hoursPane.loadNewProject( project );
		requirementsPane.loadNewProject( project );
	}
}
