package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidSubmissionException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.InvalidWorkedHourTypeException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog.WorkedHourType;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TEST_Person {
	@Test public void getName() throws Exception {
		Person programmer = new Person( "Bob" );
		assertEquals( "Bob", programmer.getName() );
		programmer.changeName( "Richard" );
		assertEquals( "Richard", programmer.getName() );
		assertNotEquals( null, programmer.getID() );
	}

	@Test public void addToTeam() throws Exception {
		Person programmer = new Person( "Bob" );
		Team team = new Team( programmer );
		programmer.changeName( "Mike" );
		assertEquals( team, programmer.team );
	}

	@Test public void reportEffort() throws Exception {
		Person programmer = new Person( "Bob" );
		Team team = new Team( programmer );
		programmer.reportHours( 5.0, WorkedHourType.CODING, LocalDate.now() );

		assertEquals( 5.0, team.getProjectHourLog().getHours( programmer ), .001 );

		programmer.reportHours( 8, WorkedHourType.DESIGNING, LocalDate.now() );

		assertEquals( 5.0, team.getProjectHourLog().getHours( WorkedHourType.CODING ), .001 );
		assertEquals( 8.0, team.getProjectHourLog().getHours( WorkedHourType.DESIGNING ), .001 );
		assertEquals( 13.0, team.getProjectHourLog().getHours( programmer ), .001 );

		Person programmer1 = new Person( "Dexter" );
		team.addToTeam( programmer1 );
		programmer1.reportHours( 2, WorkedHourType.CODING, LocalDate.now() );

		assertEquals( 7.0, team.getProjectHourLog().getHours( WorkedHourType.CODING ), .001 );
		assertEquals( 8.0, team.getProjectHourLog().getHours( WorkedHourType.DESIGNING ), .001 );
		assertEquals( 13.0, team.getProjectHourLog().getHours( programmer ), .001 );
		assertEquals( 2.0, team.getProjectHourLog().getHours( programmer1 ), .001 );
	}

	@Test public void equals() throws Exception {
		Person person1 = new Person( "Bob" );
		Person person2 = new Person( "Bob" );

		assertEquals( person1, person1 );
		assertEquals( person2, person2 );

		assertNotEquals( person1, person2 );
	}

	@Test public void promote() throws Exception {
		Person person = new Person( "Bob" );
		person.promote();
		assertTrue( person.isManager() );

	}

	@Test public void demote() throws Exception {
		Person person = new Person( "Bob" );
		person.promote();
		person.demote();
		assertFalse( person.isManager() );
	}

	@Test( expected = PersonNotOnTeamException.class ) public void TEST_submitWhileNotOnTeam()
			throws InvalidWorkedHourTypeException, PersonNotOnTeamException, InvalidSubmissionException {
		Person bob = new Person( "Bob" );
		bob.reportHours( 13, WorkedHourType.CODING, LocalDate.now() );
	}
}
