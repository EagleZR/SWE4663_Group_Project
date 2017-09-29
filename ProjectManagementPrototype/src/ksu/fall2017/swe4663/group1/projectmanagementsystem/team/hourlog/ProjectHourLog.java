package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

public class ProjectHourLog implements Serializable {

	private static final long serialVersionUID = -7863698898951761080L;
	private LinkedList<WorkedHours> workedHours;
	private LinkedList<Person> contributors;

	public ProjectHourLog( WorkedHours... workedHours ) {
		LoggingTool.print( "Constructing new ProjectHourLog." );
		this.workedHours = new LinkedList<>( Arrays.asList( workedHours ) );
		this.contributors = new LinkedList<>();
	}

	public double getHours( WorkedHourType workedHourType ) {
		if ( workedHourType.hasChanged() ) {
			int count = 0;
			for ( WorkedHours workedHours : this.workedHours ) {
				if ( workedHours.getType() == workedHourType || workedHourType == WorkedHourType.ANY ) {
					count += workedHours.getDuration();
				}
			}
			workedHourType.setTypeHourTotal( count );
			workedHourType.setHasChanged( false );
		}
		return workedHourType.getTypeHourTotal();
	}

	public double getHours( Person person ) {
		int count = 0;
		for ( WorkedHours workedHours : this.workedHours ) {
			if ( workedHours.getPerson().equals( person ) ) {
				count += workedHours.getDuration();
			}
		}
		return count;
	}

	public void registerHours( WorkedHours newWorkedHours ) {
		LoggingTool.print( "ProjectHourLog: Registering new Hours: " + newWorkedHours.toString() + "." );
		this.workedHours.add( newWorkedHours );
		Person contributor = newWorkedHours.getPerson();
		if ( !contributors.contains( contributor ) ) {
			contributors.add( contributor );
		}
		WorkedHourType.ANY.setHasChanged( true );
		newWorkedHours.getType().setHasChanged( true );
	}

	@Override public boolean equals( Object other ) {
		ProjectHourLog otherHourLog;
		if ( other.getClass().equals( ProjectHourLog.class ) ) {
			otherHourLog = (ProjectHourLog) other;
		} else {
			return false;
		}
		if ( this.workedHours.size() != otherHourLog.workedHours.size() ) {
			return false;
		}
		for ( int i = 0; i < this.workedHours.size(); i++ ) {
			if ( !this.workedHours.get( i ).equals( otherHourLog.workedHours.get( i ) ) ) {
				return false;
			}
		}
		return true;
	}
}
