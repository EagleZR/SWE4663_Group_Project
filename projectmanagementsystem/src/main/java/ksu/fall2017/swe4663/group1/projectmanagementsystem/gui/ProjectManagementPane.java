package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Config;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.general.GeneralPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.hourlog.HourLogPane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementsPane;

import java.io.File;
import java.io.IOException;

/**
 * Displays and modifies a {@link Project}. It is capable of creating new projects, saving the current project, and
 * loading a previous project.  <p>NOTE: This pane is meant to be set as the primary pane in the primary {@link Stage}.
 * Usage otherwise will result in awkward appearance.</p>
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class ProjectManagementPane extends BorderPane implements ProjectPane {

	private Stage primaryStage;
	private Project project;
	private Config config;
	private GeneralPane generalPane;
	private HourLogPane hourLogPane;
	private RequirementsPane requirementsPane;
	private Label statusLabel;

	/**
	 * Constructs a {@link ProjectManagementPane}, which consists of a {@link GeneralPane}, a {@link HourLogPane}, and a
	 * {@link RequirementsPane}, as well as the {@link MenuBar} at the top and the status {@link Label} at the bottom.
	 * <p>NOTE: This pane is meant to be set as the primary pane in the {@link Scene} of the primary {@link Stage}.</p>
	 *
	 * @param stage   The {@link Stage} in which this pane is displayed.
	 * @param config  The {@link Config} which dictates the functionality and appearance of this pane.
	 * @param project The {@link Project} which will be displayed and modified by this pane.
	 */
	public ProjectManagementPane( Stage stage, Config config, Project project ) {
		LoggingTool.print( "Constructing new ProjectManagementPane." );
		this.project = project;
		this.config = config;
		this.primaryStage = stage;
		this.statusLabel = new Label( "Status: " );

		// Initialize primary panes
		BorderPane contentPane = new BorderPane();
		this.setTop( getNewMenuBar() );
		this.setCenter( contentPane );
		this.setBottom( this.statusLabel );

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
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for RequirementPane in ProjectManagementPane." );
		Button requirements = new Button( "Requirements" );
		requirements.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		requirements.layoutXProperty()
				.bind( generalPaneButton.layoutXProperty().add( generalPaneButton.widthProperty() ) );
		requirements.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Hours Expended
		LoggingTool.print( "ProjectManagementPane: Creating Button tab for HourLogPane in ProjectManagementPane." );
		Button hoursLog = new Button( "Hour Log" );
		hoursLog.prefWidthProperty().bind( generalPaneButton.widthProperty() );
		hoursLog.layoutXProperty().bind( requirements.layoutXProperty().add( requirements.widthProperty() ) );
		hoursLog.layoutYProperty().bind( generalPaneButton.layoutYProperty() );

		// Actions
		generalPaneButton.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting GeneralPane as content in ProjectManagementPane." );
			contentPane.setCenter( this.generalPane );
			generalPaneButton.setDefaultButton( true );
			requirements.setDefaultButton( false );
			hoursLog.setDefaultButton( false );

		} );
		requirements.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting RequirementPane as content in ProjectManagementPane." );
			contentPane.setCenter( this.requirementsPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( true );
			hoursLog.setDefaultButton( false );
		} );
		hoursLog.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Setting HourLogPane as content in ProjectManagementPane." );
			contentPane.setCenter( this.hourLogPane );
			generalPaneButton.setDefaultButton( false );
			requirements.setDefaultButton( false );
			hoursLog.setDefaultButton( true );
		} );
		tabsPane.getChildren().addAll( generalPaneButton, requirements, hoursLog );

		////////////////////////////////////
		// Initialize content panes
		////////////////////////////////////
		LoggingTool.print( "ProjectManagementPane: Creating GeneralPane in ProjectManagementPane." );
		this.generalPane = new GeneralPane( project, stage, config );
		this.generalPane.prefWidthProperty().bind( contentPane.widthProperty() );
		this.generalPane.prefHeightProperty()
				.bind( contentPane.heightProperty().subtract( tabsPane.heightProperty() ) );
		LoggingTool.print( "ProjectManagementPane: Creating RequirementPane in ProjectManagementPane." );
		this.requirementsPane = new RequirementsPane( project, config, stage );
		this.requirementsPane.prefWidthProperty().bind( contentPane.widthProperty() );
		this.requirementsPane.prefHeightProperty()
				.bind( contentPane.heightProperty().subtract( tabsPane.heightProperty() ) );
		LoggingTool.print( "ProjectManagementPane: Creating HourLogPane in ProjectManagementPane." );
		this.hourLogPane = new HourLogPane( project, stage, config );
		this.hourLogPane.prefWidthProperty().bind( contentPane.widthProperty() );
		this.hourLogPane.prefHeightProperty()
				.bind( contentPane.heightProperty().subtract( tabsPane.heightProperty() ) );
		LoggingTool.print( "ProjectManagementPane: Setting GeneralPane as content in ProjectManagementPane." );
		contentPane.setCenter( this.generalPane );
		this.generalPane.prefWidthProperty().bind( contentPane.widthProperty() );
	}

	/**
	 * Builds and returns the {@link MenuBar} for this pane.
	 *
	 * @return The new {@link MenuBar} for thi pane.
	 */
	private MenuBar getNewMenuBar() {
		LoggingTool.print( "ProjectManagementPane: Creating MenuBar." );
		MenuBar menuBar = new MenuBar();

		// File
		Menu file = new Menu( "File" );
		MenuItem newProject = new MenuItem( "New" );
		newProject.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: New Project Created." );
			this.project = new Project();
			this.config.previousSave = new File( this.config.savesDirectory.getPath() + "\\newProject.save" );
			loadNewProject( this.project );
		} );
		MenuItem save = new MenuItem( "Save" );
		MenuItem saveAs = new MenuItem( "Save As" );
		save.setOnAction( e -> {
			LoggingTool.print( "ProjectManagementPane: Save button pressed." );
			if ( !this.config.previousSave.exists() ) {
				saveAs.fire();
			} else {
				LoggingTool.print( "ProjectManagementPane: Saving to " + this.config.previousSave.getAbsolutePath()
						+ "." );
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
			chooser.setInitialDirectory( this.config.savesDirectory );
			chooser.setSelectedExtensionFilter( new FileChooser.ExtensionFilter( "Project Save File", ".save" ) );
			chooser.setInitialFileName( this.config.previousSave.getName() );

			Stage fileChooserStage = new Stage();
			fileChooserStage.initModality( Modality.APPLICATION_MODAL );
			fileChooserStage.initOwner( this.primaryStage );
			fileChooserStage.initStyle( StageStyle.UTILITY );
			fileChooserStage.resizableProperty().setValue( false );

			File chosenFile = chooser.showSaveDialog( fileChooserStage );

			if ( chosenFile != null ) {
				if ( chosenFile.getName().length() < 5 || !chosenFile.getName()
						.substring( chosenFile.getName().length() - 6 ).equals( ".save" ) ) {
					chosenFile = new File( chosenFile.getPath() + ".save" );
				}
				this.config.previousSave = chosenFile;

				try {
					Project.save( this.project, this.config.previousSave );
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
			if ( !this.config.savesDirectory.exists() ) {
				LoggingTool.print( "ProkectManagementPane: The directory " + this.config.savesDirectory.getName()
						+ " does not exist." );
				if ( this.config.savesDirectory.mkdir() ) {
					LoggingTool.print( "ProkectManagementPane: The directory " + this.config.savesDirectory.getName()
							+ " was successfully created." );
				} else {
					LoggingTool.print( "ProkectManagementPane: The directory " + this.config.savesDirectory.getName()
							+ " could not be created." );
				}
			}
			chooser.setInitialDirectory( this.config.savesDirectory );
			chooser.setSelectedExtensionFilter( new FileChooser.ExtensionFilter( "Project Save File", ".save" ) );
			chooser.setInitialFileName( this.config.previousSave.getName() );

			Stage fileChooserStage = new Stage();
			fileChooserStage.initModality( Modality.APPLICATION_MODAL );
			fileChooserStage.initOwner( this.primaryStage );
			fileChooserStage.initStyle( StageStyle.UTILITY );
			fileChooserStage.resizableProperty().setValue( false );

			File chosenFile = chooser.showOpenDialog( fileChooserStage );

			if ( chosenFile != null ) {
				this.config.previousSave = chosenFile;

				try {
					// Load into new project to ensure successful load before converting class variable
					Project project = Project.load( this.config.previousSave );
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

			// Saves Directory

			// Hour Log Interval

			// Logging on/off

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

	/**
	 * Loads a new {@link Project} into this pane and its sub-panes.
	 *
	 * @param project The new {@link Project} to display.
	 */
	@Override public void loadNewProject( Project project ) {
		this.generalPane.loadNewProject( project );
		this.hourLogPane.loadNewProject( project );
		this.requirementsPane.loadNewProject( project );
		LoggingTool.print( "ProjectManagementPane: Loaded new project." );
	}
}
