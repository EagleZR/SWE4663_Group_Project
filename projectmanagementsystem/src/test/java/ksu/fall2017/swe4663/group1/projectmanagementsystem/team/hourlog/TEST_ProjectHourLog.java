package ksu.fall2017.swe4663.group1.projectmanagementsystem.team.hourlog;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.PersonNotOnTeamException;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Team;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertNotSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TEST_ProjectHourLog {

	private ProjectHourLog projectHourLog;

	private Person p1;
	private Person p2;
	private Person p3;
	private Person p4;
	private Person p5;
	private Person p6;
	private Person p7;

	@Before public void setup()
			throws InvalidWorkedHourTypeException, PersonNotOnTeamException, InvalidSubmissionException {
		this.p1 = new Person( "Bob" );
		this.p2 = new Person( "Red" );
		this.p3 = new Person( "Jim" );
		this.p4 = new Person( "Ate" );
		this.p5 = new Person( "Eight" );
		this.p6 = new Person( "Ayt" );
		this.p7 = new Person( "Mr. Manager" );

		Team team = new Team( this.p1, this.p2, this.p3, this.p4, this.p5, this.p6, this.p7 );
		team.promote( this.p7 );

		this.projectHourLog = team.getProjectHourLog();

		this.p1.reportHours( 5, WorkedHourType.DESIGNING, LocalDate.now() );                // Designing: 5, 		p1: 5
		this.p2.reportHours( 8, WorkedHourType.DESIGNING, LocalDate.now() );                // Designing: 13, 		p2: 8
		this.p3.reportHours( 3, WorkedHourType.TESTING, LocalDate.now() );                  // Testing: 3,			p3: 3
		this.p4.reportHours( 2, WorkedHourType.DESIGNING, LocalDate.now() );                // Designing: 15, 		p4: 2
		this.p5.reportHours( 8, WorkedHourType.REQUIREMENTS_ANALYSIS, LocalDate.now() );    // Requirements: 8,	 	p5: 8
		this.p6.reportHours( 5, WorkedHourType.TESTING, LocalDate.now() );                  // Testing: 8, 			p6: 8
		this.p7.reportHours( 6, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 6, 			p7: 6
		this.p1.reportHours( 1, WorkedHourType.DESIGNING, LocalDate.now() );                // Designing: 16, 		p1: 6
		this.p2.reportHours( 8, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 14, 			p2: 16
		this.p3.reportHours( 8, WorkedHourType.REQUIREMENTS_ANALYSIS, LocalDate.now() );    // Requirements: 16, 	p3: 11
		this.p4.reportHours( 9, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 23,			p4: 11
		this.p5.reportHours( 10, WorkedHourType.DESIGNING, LocalDate.now() );               // Designing: 26, 		p5: 18
		this.p6.reportHours( 5, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 28, 			p6: 13
		this.p7.reportHours( 4, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 32, 			p7: 10
		this.p1.reportHours( 3, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 35, 			p1: 9
		this.p2.reportHours( 7, WorkedHourType.DESIGNING, LocalDate.now() );                // Designing: 33,		p2: 23
		this.p3.reportHours( 3, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 38, 			p3: 14
		this.p4.reportHours( 6, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 44,			p4: 17
		this.p5.reportHours( 3, WorkedHourType.TESTING, LocalDate.now() );                  // Testing: 11,			p5: 21
		this.p6.reportHours( 7, WorkedHourType.CODING, LocalDate.now() );                   // Coding: 51,			p6: 20
		this.p7.reportHours( 8, WorkedHourType.PROJECT_MANAGEMENT, LocalDate.now() );       // Management: 8			p7: 18

		// Requirements: 16, Designing: 33, Coding: 42, Testing: 11, ProjectManagement: 8
		// p1: 10, p2: 23, p3: 14, p4: 17, p5: 21, p6: 20, p7: 18
	}

	@Test public void TEST_getEffortOfType() {
		assertEquals( 119.0, this.projectHourLog.getHours( WorkedHourType.ANY ), .001 );
		assertEquals( 16.0, this.projectHourLog.getHours( WorkedHourType.REQUIREMENTS_ANALYSIS ), .001 );
		assertEquals( 33.0, this.projectHourLog.getHours( WorkedHourType.DESIGNING ), .001 );
		assertEquals( 51.0, this.projectHourLog.getHours( WorkedHourType.CODING ), .001 );
		assertEquals( 11.0, this.projectHourLog.getHours( WorkedHourType.TESTING ), .001 );
		assertEquals( 8.0, this.projectHourLog.getHours( WorkedHourType.PROJECT_MANAGEMENT ), .001 );
	}

	@Test public void TEST_getEffortOfPerson() {
		assertEquals( 9.0, this.projectHourLog.getHours( this.p1 ), .001 );
		assertEquals( 23.0, this.projectHourLog.getHours( this.p2 ), .001 );
		assertEquals( 14.0, this.projectHourLog.getHours( this.p3 ), .001 );
		assertEquals( 17.0, this.projectHourLog.getHours( this.p4 ), .001 );
		assertEquals( 21.0, this.projectHourLog.getHours( this.p5 ), .001 );
		assertEquals( 17.0, this.projectHourLog.getHours( this.p6 ), .001 );
		assertEquals( 18.0, this.projectHourLog.getHours( this.p7 ), .001 );
	}

	@Test public void TEST_equals() {
		assertEquals( this.projectHourLog, this.projectHourLog );

		assertNotSame( new Team().getProjectHourLog(), new Team().getProjectHourLog() );
	}

	@Test public void TEST_submissionInterval() {
		assertEquals( null, this.projectHourLog.getSubmissionInterval() );
		assertNotEquals( SubmissionInterval.DAILY, this.projectHourLog.getSubmissionInterval() );
		assertNotEquals( SubmissionInterval.WEEKLY, this.projectHourLog.getSubmissionInterval() );

		this.projectHourLog.setSubmissionInterval( SubmissionInterval.DAILY );
		assertEquals( SubmissionInterval.DAILY, this.projectHourLog.getSubmissionInterval() );
		assertNotEquals( SubmissionInterval.WEEKLY, this.projectHourLog.getSubmissionInterval() );
		assertNotEquals( null, this.projectHourLog.getSubmissionInterval() );

		this.projectHourLog.setSubmissionInterval( SubmissionInterval.WEEKLY );
		assertEquals( SubmissionInterval.WEEKLY, this.projectHourLog.getSubmissionInterval() );
		assertNotEquals( SubmissionInterval.DAILY, this.projectHourLog.getSubmissionInterval() );
		assertNotEquals( null, this.projectHourLog.getSubmissionInterval() );
	}

}
