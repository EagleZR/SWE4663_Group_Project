package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_WorkedHours {

	@Test public void TEST_Accessors() throws InvalidWorkedHourTypeException {
		WorkedHours workedHours = new WorkedHours( new Person( "Bob" ), 5, WorkedHourType.CODING );
		assertTrue( workedHours.getPerson().getName().equals( "Bob" ) );
		assertTrue( workedHours.getDuration() == 5 );
		assertTrue( workedHours.getType() == WorkedHourType.CODING );
	}

	@Test (expected = InvalidWorkedHourTypeException.class) public void TEST_EffortManagerException()
			throws InvalidWorkedHourTypeException {
		WorkedHours workedHours = new WorkedHours( new Person( "Bob" ), 5, WorkedHourType.PROJECT_MANAGEMENT );
	}

	@Test (expected = InvalidWorkedHourTypeException.class) public void TEST_AnyException()
			throws InvalidWorkedHourTypeException {
		WorkedHours workedHours = new WorkedHours( new Person( "Bob" ), 5, WorkedHourType.ANY );
	}
}
