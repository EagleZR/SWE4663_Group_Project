package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import eaglezr.support.logs.LoggingTool;

public class PersonNotOnTeamException extends Exception {

	private static final long serialVersionUID = 4076833977752940124L;

	public PersonNotOnTeamException( String message ) {
		super( message );
		LoggingTool.print( "Constructing new PersonNotOnTeamException." );
	}
}
