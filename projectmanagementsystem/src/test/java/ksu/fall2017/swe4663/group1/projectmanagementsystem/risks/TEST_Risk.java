package ksu.fall2017.swe4663.group1.projectmanagementsystem.risks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TEST_Risk {

	Risk risk;

	@Before public void setup() throws InvalidPercentageException, InvalidDollarAmountException {
		risk = new Risk( "This is a risk.", 118.3, 0.25 );
		// String description, double dollarAmount, double percentageLikelihood
	}

	@Test public void testAccessors() throws InvalidDollarAmountException, InvalidPercentageException {
		assertEquals( "This is a risk.", risk.getDescription() );
		assertEquals( 118.3, risk.getDollarAmount(), 0.001 );
		assertEquals( 0.25, risk.getPercentageLikelihood(), 0.001 );

		risk.setDescription( "This is a new description." );
		risk.setDollarAmount( 372.21 );
		risk.setPercentageLikelihood( 0.34 );

		assertEquals( "This is a new description.", risk.getDescription() );
		assertEquals( 372.21, risk.getDollarAmount(), 0.001 );
		assertEquals( 0.34, risk.getPercentageLikelihood(), 0.001 );
	}

	// Test percentage > 100% Constructor
	@Test( expected = InvalidPercentageException.class ) public void testInvalidPercentageConstructor1()
			throws InvalidPercentageException, InvalidDollarAmountException {
		risk = new Risk( "", 23, 1.2 );
	}

	// Test percentage < 0% Constructor
	@Test( expected = InvalidPercentageException.class ) public void testInvalidPercentageConstructor2()
			throws InvalidPercentageException, InvalidDollarAmountException {
		risk = new Risk( "", 23, -1.2 );
	}

	// Test percentage > 100% method
	@Test( expected = InvalidPercentageException.class ) public void testInvalidPercentageSetter1()
			throws InvalidPercentageException {
		risk.setPercentageLikelihood( 3.4 );
	}

	// Test percentage < 0% method
	@Test( expected = InvalidPercentageException.class ) public void testInvalidPercentageSetter2()
			throws InvalidPercentageException {
		risk.setPercentageLikelihood( -3.4 );
	}

	@Test( expected = InvalidDollarAmountException.class ) public void testInvalidDollarAmountConstructor()
			throws InvalidPercentageException, InvalidDollarAmountException {
		risk = new Risk( "", -23, .2 );
	}

	@Test( expected = InvalidDollarAmountException.class ) public void testInvalidDollarAmountSetter()
			throws InvalidDollarAmountException {
		risk.setDollarAmount( -3 );
	}
}
