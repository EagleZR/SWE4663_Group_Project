package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import eaglezr.support.logs.LoggingTool;

public class InvalidWorkedHourTypeException extends Exception {

	private static final long serialVersionUID = 2386932538357054109L;

	public InvalidWorkedHourTypeException( String message ) {
		super( message );
		LoggingTool.print( "Constructing new InvalidWorkedHourTypeException." );
	}
}
