package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHours;

/**
 * Exception meant to be thrown when a {@link Person} is not on a team and attempts to use a team-specific function,
 * when a {@link Team} is looking for a Manager and cannot find one, or when {@link WorkedHours} are sent to a {@link
 * Team} to be reported, and the working {@link Person} is not on the team.
 */
public class PersonNotOnTeamException extends Exception {

	private static final long serialVersionUID = 4076833977752940124L;

	public PersonNotOnTeamException( String message ) {
		super( message );
		LoggingTool.print( "Constructing new PersonNotOnTeamException." );
	}
}
