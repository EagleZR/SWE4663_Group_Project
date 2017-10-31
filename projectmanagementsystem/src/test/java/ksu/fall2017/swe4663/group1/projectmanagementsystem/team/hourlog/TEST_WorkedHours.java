package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TEST_WorkedHours {

	@Test public void TEST_Accessors() throws InvalidWorkedHourTypeException {
		WorkedHours workedHours = new WorkedHours( new Person( "Bob" ), 5, WorkedHourType.CODING, LocalDate.now() );
		assertTrue( workedHours.getPerson().getName().equals( "Bob" ) );
		assertTrue( workedHours.getDuration() == 5 );
		assertTrue( workedHours.getType() == WorkedHourType.CODING );
	}

	@Test( expected = InvalidWorkedHourTypeException.class ) public void TEST_EffortManagerException()
			throws InvalidWorkedHourTypeException {
		new WorkedHours( new Person( "Bob" ), 5, WorkedHourType.PROJECT_MANAGEMENT, LocalDate.now() );
		fail();
	}

	@Test( expected = InvalidWorkedHourTypeException.class ) public void TEST_AnyException()
			throws InvalidWorkedHourTypeException {
		new WorkedHours( new Person( "Bob" ), 5, WorkedHourType.ANY, LocalDate.now() );
		fail();
	}

	@Test public void TEST_hoursConflict() throws InvalidWorkedHourTypeException {
		Person bob = new Person( "Bob" );
		// Test DAILY with same day
		assertTrue( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ), SubmissionInterval.DAILY ) );

		// Test WEEKLY with same day
		assertTrue( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ), SubmissionInterval.WEEKLY ) );

		// Test WEEKLY with +4 days
		assertTrue( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now().plusDays( 4 ) ),
				SubmissionInterval.WEEKLY ) );

		// Test WEEKLY with -4 days
		assertTrue( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now().minusDays( 4 ) ),
				SubmissionInterval.WEEKLY ) );

		// Test DAILY with +1 days
		assertFalse( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now().plusDays( 1 ) ),
				SubmissionInterval.DAILY ) );

		// Test DAILY with -1 days
		assertFalse( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now().minusDays( 1 ) ),
				SubmissionInterval.DAILY ) );

		// Test WEEKLY with +7 days
		assertFalse( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now().plusDays( 7 ) ),
				SubmissionInterval.WEEKLY ) );

		// Test WEEKLY with -7 days
		assertFalse( WorkedHours.hoursConflict( new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now() ),
				new WorkedHours( bob, 15, WorkedHourType.CODING, LocalDate.now().minusDays( 7 ) ),
				SubmissionInterval.WEEKLY ) );
	}
}
