package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.logs.LoggingTool;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class that saves, loads, and represents the configuration specifics for the {@link
 * ksu.fall2017.swe4663.group1.projectmanagementsystem}.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class Config implements Closeable {

	public File previousSave;
	public File savesDirectory = new File( "saves" );
	public String saveFileExtension = "save";
	public int windowWidth = 500;
	public int windowHeight = 600;
	public int buffer = 10;
	private File configFile = new File( "data/config.ini" );

	/**
	 * Constructs a new Config from the default location. By default, it is "data/config.ini" in the project folder. If
	 * the file is empty or missing values, the default values will be used instead.
	 *
	 * @throws FileNotFoundException Thrown if the file is not found. If this occurs, either specify the (empty or
	 *                               correct) file, or create an empty file at the default location.
	 */
	public Config() throws FileNotFoundException {
		LoggingTool.print( "Creating new Config." );
		this.loadConfig( this.configFile );
	}

	/**
	 * Constructs a new Config from the specified file. If the file is empty or missing values, the default values will
	 * be used instead.
	 *
	 * @param configFile The file from which the configuration will be loaded.
	 * @throws FileNotFoundException Thrown if the file is not found. If this occurs, either specify the (empty or
	 *                               correct) file, or create an empty file at the default location.
	 */
	public Config( File configFile ) throws FileNotFoundException {
		LoggingTool.print( "Creating new Config." );
		this.configFile = configFile;
		this.loadConfig( configFile );
	}

	/**
	 * Loads a configuration from a given file.
	 *
	 * @param configFile The file from which to load the configuration.
	 * @throws FileNotFoundException Thrown if the {@link File} is not found.
	 */
	private void loadConfig( File configFile ) throws FileNotFoundException {
		LoggingTool.print( "Config: Reading configuration from file: " + configFile.getAbsolutePath() + "." );
		if ( !configFile.exists() ) {
			LoggingTool.print( "Config: Config not found. Initializing new file." );
			if ( !configFile.getParentFile().exists() && !configFile.getParentFile().mkdirs() ) {
				LoggingTool.print( "Config: Unable to create parent directory." );
			}
			try {
				if ( configFile.createNewFile() ) {
					LoggingTool.print( "Config: Unable to create the config file." );
				}
			} catch ( IOException e ) {
				LoggingTool.print( "Config: There was an exception while attempting to create the config file." );
				LoggingTool.print( e.getMessage() );
			}
		}
		Scanner config = new Scanner( configFile );
		while ( config.hasNextLine() ) {
			this.parseSettingLine( config.nextLine() );
		}
	}

	/**
	 * Reads settings from an individual line in the config file.
	 *
	 * @param settingLine The line to be read.
	 */
	private void parseSettingLine( String settingLine ) {
		if ( settingLine.length() > 0 && settingLine.charAt( 0 ) != '#' ) {
			if ( settingLine.contains( "save_file_location" ) ) {
				this.previousSave = new File( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting previous save as: " + this.previousSave.getAbsolutePath() + "." );
			} else if ( settingLine.contains( "window_width" ) ) {
				this.windowWidth = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting window width as: " + this.windowWidth + "." );
			} else if ( settingLine.contains( "window_height" ) ) {
				this.windowHeight = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting window height as " + this.windowHeight + "." );
			} else if ( settingLine.contains( "buffer" ) ) {
				this.buffer = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting buffer as " + this.buffer + "." );
			} else if ( settingLine.contains( "save_folder_location" ) ) {
				this.savesDirectory = new File( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config Setting saves directory as " + this.savesDirectory.getPath() + "." );
			} else if ( settingLine.contains( "save_file_extension" ) ) {
				this.savesDirectory = new File( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting saves file extension as \"." + this.saveFileExtension + "\"." );
			}
		}
	}

	/**
	 * Writes the settings to the file.<p>This will overwrite any of the file's previous data.</p>
	 *
	 * @throws FileNotFoundException Thrown if the file is not found. This can only really happen if the file is deleted
	 *                               during runtime.... Don't do that.
	 */
	private void writeSettings() throws FileNotFoundException {
		// LATER Look into fixing this so it only changes necessary values instead of deleting everything that's there
		LoggingTool.print( "Config: Writing settings to file." );
		PrintStream out = new PrintStream( this.configFile );
		GregorianCalendar calendar = new GregorianCalendar();
		out.println(
				"# Current configuration saved on " + calendar.get( GregorianCalendar.DAY_OF_MONTH ) + " " + calendar
						.getDisplayName( GregorianCalendar.MONTH, Calendar.SHORT, Locale.getDefault() ) + " " + calendar
						.get( GregorianCalendar.YEAR ) + " at " + calendar.get( GregorianCalendar.HOUR_OF_DAY ) + ":"
						+ calendar.get( GregorianCalendar.MINUTE ) + ":" + calendar.get( GregorianCalendar.SECOND ) );
		out.println( "# NOTE: Be sure to include no extra spaces in the data." );

		//////////////////
		// Project Configuration
		//////////////////
		// Print Project Configuration title
		out.println( "\n########################\n" + "# Project Configuration\n" + "########################" );

		// Print save location
		out.println( "# Location of previous save location\n" + "save_file_location " + ( this.previousSave == null ?
				"" :
				this.previousSave.getPath() ) );

		// Print saves folder location
		out.println( "# Location of saves folder\n" + "save_folder_location " + this.savesDirectory.getPath() );

		// Print save file extension
		out.println( "# Save File Extension\n" + "save_file_extension " + this.saveFileExtension );

		//////////////////
		// Window Configuration
		//////////////////
		// Print Window Configuration title
		out.println( "\n########################\n" + "# Window Configuration\n" + "########################" );

		// Print out windowWidth
		out.println( "# Default window width. The minimum width is set to 450\n" + "window_width " + this.windowWidth );
		// Print out windowHeight
		out.println(
				"# Default window height. The minimum height is set to 500\n" + "window_height " + this.windowHeight );
		// Print out buffer
		out.println( "# Default buffer space\n" + "buffer " + this.buffer );
	}

	/**
	 * Saves the current settings to the config file.
	 *
	 * @throws FileNotFoundException Thrown if the file is not found. This can only really happen if the file is deleted
	 *                               during runtime.... Don't do that.
	 */
	@Override public void close() throws FileNotFoundException {
		LoggingTool.print( "Config: Begin closing." );
		this.writeSettings();
		LoggingTool.print( "Config: Finished writing settings. Will close." );
	}
}
