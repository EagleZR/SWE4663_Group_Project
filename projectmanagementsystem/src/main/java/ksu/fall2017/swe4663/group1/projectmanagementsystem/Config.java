package ksu.fall2017.swe4663.group1.projectmanagementsystem;

import eaglezr.support.logs.LoggingTool;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class meant save, load, and represent the configuration specifics for the {@link ksu.fall2017.swe4663.group1.projectmanagementsystem}.
 */
public class Config implements Closeable {

	private static File configFile = new File( "data/config.ini" );
	public File previousSave;
	public File savesDirectory = new File( "saves" );
	public int windowWidth = 500;
	public int windowHeight = 600;
	public int buffer = 10;

	public Config() throws FileNotFoundException {
		LoggingTool.print( "Creating new Config." );
		LoggingTool.print( "Config: Reading configuration from file: " + configFile.getAbsolutePath() + "." );
		Scanner config = new Scanner( configFile );
		while ( config.hasNextLine() ) {
			parseSettingLine( config.nextLine() );
		}
	}

	public Config( File configFile ) throws FileNotFoundException {
		LoggingTool.print( "Creating new Config." );
		Config.configFile = configFile;
		LoggingTool.print( "Config: Reading configuration from file: " + configFile.getAbsolutePath() + "." );
		Scanner config = new Scanner( configFile );
		while ( config.hasNextLine() ) {
			parseSettingLine( config.nextLine() );
		}
	}

	/**
	 * Reads settings from an individual line in the config file
	 *
	 * @param settingLine
	 */
	private void parseSettingLine( String settingLine ) {
		if ( settingLine.length() > 0 ) {
			if ( settingLine.charAt( 0 ) == '#' ) {
				// Do nothing
			} else if ( settingLine.contains( "save_file_location" ) ) {
				previousSave = new File( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting previous save as: " + previousSave.getAbsolutePath() + "." );
			} else if ( settingLine.contains( "window_width" ) ) {
				windowWidth = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting window width as: " + windowWidth + "." );
			} else if ( settingLine.contains( "window_height" ) ) {
				windowHeight = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting window height as " + windowHeight + "." );
			} else if ( settingLine.contains( "buffer" ) ) {
				buffer = Integer.parseInt( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
				LoggingTool.print( "Config: Setting buffer as " + buffer + "." );
			} else if ( settingLine.contains( "save_folder_location" ) ) {
				savesDirectory = new File( settingLine.substring( settingLine.indexOf( " " ) + 1 ) );
			}
		}
	}

	private void writeSettings() throws FileNotFoundException {
		LoggingTool.print( "Config: Writing settings to file." );
		PrintStream out = new PrintStream( configFile );
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
		out.println( "# Location of previous save location\n" + "save_file_location " + ( previousSave == null ?
				"" :
				previousSave.getPath() ) );

		// Print saves folder location
		out.println( "# Location of saves folder\n" + "save_folder_location " + savesDirectory.getPath() );

		//////////////////
		// Window Configuration
		//////////////////
		// Print Window Configuration title
		out.println( "\n########################\n" + "# Window Configuration\n" + "########################" );

		// Print out windowWidth
		out.println( "# Default window width. The minimum width is set to 450\n" + "window_width " + windowWidth );
		// Print out windowHeight
		out.println( "# Default window height. The minimum height is set to 500\n" + "window_height " + windowHeight );
		// Print out buffer
		out.println( "# Default buffer space\n" + "buffer " + buffer );
	}

	@Override public void close() throws IOException {
		LoggingTool.print( "Config: Begin closing." );
		writeSettings();
	}
}
