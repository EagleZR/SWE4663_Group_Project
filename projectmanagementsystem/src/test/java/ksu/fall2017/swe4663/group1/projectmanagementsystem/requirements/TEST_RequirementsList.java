package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TEST_RequirementsList {

	private RequirementsList requirements;

	private Requirement req0 = new Requirement( "Req 0", "", Priority.LOW, Status.ACCEPTED_CURRENT, false );
	private Requirement req1 = new Requirement( "Req 1", "", Priority.HIGH, Status.ACCEPTED_CURRENT, false );
	private Requirement req2 = new Requirement( "Req 2", "", Priority.LOW, Status.ACCEPTED_POST_RELEASE, false );
	private Requirement req3 = new Requirement( "Req 3", "", Priority.CRITICAL, Status.ACCEPTED_POST_RELEASE, false );
	private Requirement req4 = new Requirement( "Req 4", "", Priority.MEDIUM, Status.ACCEPTED_POST_RELEASE, false );
	private Requirement req5 = new Requirement( "Req 5", "", Priority.HIGH, Status.ACCEPTED_POST_RELEASE, true );
	private Requirement req6 = new Requirement( "Req 6", "", Priority.CRITICAL, Status.ACCEPTED_POST_RELEASE, true );
	private Requirement req7 = new Requirement( "Req 7", "", Priority.CRITICAL, Status.ACCEPTED_CURRENT, true );
	private Requirement req8 = new Requirement( "Req 8", "", Priority.LOW, Status.ACCEPTED_CURRENT, true );
	private Requirement req9 = new Requirement( "Req 9", "", Priority.LOW, Status.REJECTED, true );
	private Requirement req10 = new Requirement( "Req 10", "", Priority.MEDIUM, Status.REJECTED, false );
	private Requirement req11 = new Requirement( "Req 11", "", Priority.MEDIUM, Status.REJECTED, true );
	private Requirement req12 = new Requirement( "Req 12", "", Priority.MEDIUM, Status.REJECTED, false );
	private Requirement req13 = new Requirement( "Req 13", "", Priority.MEDIUM, Status.REJECTED, true );

	@Before public void setup() {
		requirements = new RequirementsList();
		requirements.add( req0 );
		requirements.add( req1 );
		requirements.add( req2 );
		requirements.add( req3 );
		requirements.add( req4 );
		requirements.add( req5 );
		requirements.add( req6 );
		requirements.add( req7 );
		requirements.add( req8 );
		requirements.add( req9 );
		requirements.add( req10 );
		requirements.add( req11 );
		requirements.add( req12 );
		requirements.add( req13 );
	}

	@Test public void TEST_getPriority() {
		assertEquals( 4, requirements.getByPriority( Priority.LOW ).size() );
		assertEquals( 5, requirements.getByPriority( Priority.MEDIUM ).size() );
		assertEquals( 2, requirements.getByPriority( Priority.HIGH ).size() );
		assertEquals( 3, requirements.getByPriority( Priority.CRITICAL ).size() );
	}

	@Test public void TEST_getPriorityUp() {
		assertEquals( 14, requirements.getByPriorityUp( Priority.LOW ).size() );
		assertEquals( 10, requirements.getByPriorityUp( Priority.MEDIUM ).size() );
		assertEquals( 5, requirements.getByPriorityUp( Priority.HIGH ).size() );
		assertEquals( 3, requirements.getByPriorityUp( Priority.CRITICAL ).size() );
	}

	@Test public void TEST_getStatus() {
		assertEquals( 4, requirements.getByStatus(Status.ACCEPTED_CURRENT).size() );
		assertEquals( 5, requirements.getByStatus(Status.ACCEPTED_POST_RELEASE).size() );
		assertEquals( 5, requirements.getByStatus(Status.REJECTED).size() );
	}

	@Test public void TEST_getFulfilled() {
		assertEquals( 7, requirements.getUnFulfilled().size() );
		assertEquals( 7, requirements.getFulfilled().size() );
	}
}
