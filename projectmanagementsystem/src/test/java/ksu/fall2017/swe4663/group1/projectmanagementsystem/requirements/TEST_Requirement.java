package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TEST_Requirement {

	@Test public void TEST_Accessors() {
		Requirement requirement = new Requirement( "Title", "Description", "Stakeholder", Priority.HIGH,
				Status.ACCEPTED_CURRENT, false, false );

		// Test getters
		assertEquals( "Title", requirement.getTitle() );
		assertEquals( "Description", requirement.getDescription() );
		assertEquals( "Stakeholder", requirement.getSource() );
		assertEquals( Priority.HIGH, requirement.getPriority() );
		assertEquals( Status.ACCEPTED_CURRENT, requirement.getStatus() );
		assertEquals( false, requirement.isFunctional() );
		assertEquals( false, requirement.isComplete() );

		// Set new values
		requirement.setTitle( "New title" );
		requirement.setDescription( "New description" );
		requirement.setSource( "New source" );
		requirement.setPriority( Priority.LOW );
		requirement.setStatus( Status.REJECTED );
		requirement.setIsFunctional( true );
		requirement.setFulfilled( true );
		requirement.setItemNumber( (short) 213 );

		// Test setters
		assertEquals( "New title", requirement.getTitle() );
		assertEquals( "New description", requirement.getDescription() );
		assertEquals( "New source", requirement.getSource() );
		assertEquals( Priority.LOW, requirement.getPriority() );
		assertEquals( Status.REJECTED, requirement.getStatus() );
		assertEquals( true, requirement.isFunctional() );
		assertEquals( true, requirement.isComplete() );
		assertEquals( (short) 213, requirement.getItemNumber() );
	}
}
