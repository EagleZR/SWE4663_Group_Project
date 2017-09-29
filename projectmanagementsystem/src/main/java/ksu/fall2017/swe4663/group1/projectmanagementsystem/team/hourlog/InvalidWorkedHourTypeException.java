package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

/**
 * Used to represent when an Invalid {@link WorkedHourType} is used in the construction of a {@link
 * WorkedHours}.<p>Notably thrown when any {@link Person} attempts to construct a {@link WorkedHours} using {@link
 * WorkedHourType}{@code .ANY}, or when a non-Manager {@link Person} attempts to create a {@link WorkedHours} using
 * {@link WorkedHourType}.{@code PROJECT_MANAGEMENT}.</p>
 */
public class InvalidWorkedHourTypeException extends Exception {

	private static final long serialVersionUID = 2386932538357054109L;

	public InvalidWorkedHourTypeException( String message ) {
		super( message );
		LoggingTool.print( "Constructing new InvalidWorkedHourTypeException." );
	}
}
