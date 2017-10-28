package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TEST_Requirement {

	@Test public void TEST_Accessors() {
		Requirement requirement = new Requirement( "Title", "Description", "Stakeholder", Priority.HIGH,
				Status.ACCEPTED_CURRENT, false, false );

		// Test getters
		assertEquals( "Description", requirement.getDescription() );
		assertEquals( "Stakeholder", requirement.getSource() );
		assertEquals( Priority.HIGH, requirement.getPriority() );
		assertEquals( Status.ACCEPTED_CURRENT, requirement.getStatus() );
		assertEquals( false, requirement.isComplete() );

		// Set new values
		requirement.setDescription( "New description" );
		requirement.setSource( "New source" );
		requirement.setPriority( Priority.LOW );
		requirement.setStatus( Status.REJECTED );
		requirement.setFulfilled( true );

		// Test setters
		assertEquals( "New description", requirement.getDescription() );
		assertEquals( "New source", requirement.getSource() );
		assertEquals( Priority.LOW, requirement.getPriority() );
		assertEquals( Status.REJECTED, requirement.getStatus() );
		assertEquals( true, requirement.isComplete() );
	}
}
