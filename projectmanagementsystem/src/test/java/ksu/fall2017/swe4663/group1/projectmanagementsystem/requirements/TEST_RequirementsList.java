package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TEST_RequirementsList {

	private RequirementsList requirements;

	private Requirement req0 = new Requirement( "Title 1", "Req 0", "", Priority.LOW, Status.ACCEPTED_CURRENT, false,
			false );
	private Requirement req1 = new Requirement( "Title 1", "Req 1", "", Priority.HIGH, Status.ACCEPTED_CURRENT, false,
			false );
	private Requirement req2 = new Requirement( "Title 1", "Req 2", "", Priority.LOW, Status.ACCEPTED_POST_RELEASE,
			false, false );
	private Requirement req3 = new Requirement( "Title 1", "Req 3", "", Priority.CRITICAL, Status.ACCEPTED_POST_RELEASE,
			false, false );
	private Requirement req4 = new Requirement( "Title 1", "Req 4", "", Priority.MEDIUM, Status.ACCEPTED_POST_RELEASE,
			false, false );
	private Requirement req5 = new Requirement( "Title 1", "Req 5", "", Priority.HIGH, Status.ACCEPTED_POST_RELEASE,
			true, true );
	private Requirement req6 = new Requirement( "Title 1", "Req 6", "", Priority.CRITICAL, Status.ACCEPTED_POST_RELEASE,
			true, true );
	private Requirement req7 = new Requirement( "Title 1", "Req 7", "", Priority.CRITICAL, Status.ACCEPTED_CURRENT,
			true, true );
	private Requirement req8 = new Requirement( "Title 1", "Req 8", "", Priority.LOW, Status.ACCEPTED_CURRENT, true,
			true );
	private Requirement req9 = new Requirement( "Title 1", "Req 9", "", Priority.LOW, Status.REJECTED, true, true );
	private Requirement req10 = new Requirement( "Title 1", "Req 10", "", Priority.MEDIUM, Status.REJECTED, false,
			false );
	private Requirement req11 = new Requirement( "Title 1", "Req 11", "", Priority.MEDIUM, Status.REJECTED, true,
			true );
	private Requirement req12 = new Requirement( "Title 1", "Req 12", "", Priority.MEDIUM, Status.REJECTED, false,
			false );
	private Requirement req13 = new Requirement( "Title 1", "Req 13", "", Priority.MEDIUM, Status.REJECTED, true,
			true );

	@Before public void setup() {
		requirements = new RequirementsList();
		RequirementsList firstList = new RequirementsList();
		firstList.add( req0 );
		firstList.add( req1 );
		firstList.add( req2 );
		firstList.add( req3 );
		requirements.addAll( firstList );
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
		requirements.remove( req0 );
		requirements.add( req0 );
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
		assertEquals( 4, requirements.getByStatus( Status.ACCEPTED_CURRENT ).size() );
		assertEquals( 5, requirements.getByStatus( Status.ACCEPTED_POST_RELEASE ).size() );
		assertEquals( 5, requirements.getByStatus( Status.REJECTED ).size() );
	}

	@Test public void TEST_getFulfilled() {
		assertEquals( 7, requirements.getUnFulfilled().size() );
		assertEquals( 7, requirements.getFulfilled().size() );
	}

	@Test( expected = IndexOutOfBoundsException.class ) public void TEST_exceed_Max_Short() {
		for ( int i = 0; i < ( (int) Short.MAX_VALUE ) + 1; i++ ) {
			requirements.add( new Requirement( "", "", "", Priority.LOW, Status.ACCEPTED_CURRENT, false, false ) );
		}
	}
}
