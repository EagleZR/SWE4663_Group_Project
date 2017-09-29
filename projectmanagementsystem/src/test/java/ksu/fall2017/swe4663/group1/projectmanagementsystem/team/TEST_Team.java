package ksu.fall2017.swe4663.group1.projectmanagementsystem.team;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TEST_Team {

	Person dan;
	Person red;
	Person blue;
	Person adam;
	Person rex;
	Person mike;

	Team team;

	@Before public void setup() {
		dan = new Person( "Dan" );
		red = new Person( "Red" );
		blue = new Person( "Blue" );
		adam = new Person( "Adam" );
		rex = new Person( "Rex" );
		mike = new Person( "Mike" );

		team = new Team( dan, red, blue, adam, rex, mike );
	}

	@Test public void TET_addToTeam() {
		// Single person added with constructor
		Person dan = new Person( "Dan" );
		Team team = new Team( dan );
		assertTrue( team.getMembers().contains( dan ) );

		// Multiple people added with constructor
		Person red = new Person( "Red" );
		Person blue = new Person( "Blue" );
		team = new Team( red, blue );
		assertTrue( team.getMembers().contains( red ) );
		assertTrue( team.getMembers().contains( blue ) );

		// Single person added
		Person adam = new Person( "Adam" );
		team.addToTeam( adam );
		assertTrue( team.getMembers().contains( adam ) );

		// Multiple people added
		Person rex = new Person( "Rex" );
		Person mike = new Person( "Mike" );
		team.addToTeam( rex, mike );
		assertTrue( team.getMembers().contains( rex ) );
		assertTrue( team.getMembers().contains( mike ) );
	}

	@Test public void TEST_getManager() throws PersonNotOnTeamException {
		team.promote( adam );

		assertFalse( team.getManagers().contains( dan ) );
		assertFalse( team.getManagers().contains( red ) );
		assertFalse( team.getManagers().contains( blue ) );
		assertEquals( team.getManager(), adam );
		assertFalse( team.getManagers().contains( rex ) );
		assertFalse( team.getManagers().contains( mike ) );
	}

	@Test public void TEST_getManagers() {
		Person dan = new Person( "Dan" );
		Person red = new Person( "Red" );
		Person blue = new Person( "Blue" );
		Person adam = new Person( "Adam" );
		Person rex = new Person( "Rex" );
		Person mike = new Person( "Mike" );

		Team team = new Team( dan, red, blue, adam, rex, mike );

		team.promote( adam );
		team.promote( blue );

		assertFalse( team.getManagers().contains( dan ) );
		assertFalse( team.getManagers().contains( red ) );
		assertTrue( team.getManagers().contains( blue ) );
		assertTrue( team.getManagers().contains( adam ) );
		assertFalse( team.getManagers().contains( rex ) );
		assertFalse( team.getManagers().contains( mike ) );
	}

	@Test public void TEST_promote() {
		team.promote( blue );
		team.promote( adam );
		team.promote( dan );

		assertTrue( team.getManagers().contains( dan ) );
		assertFalse( team.getManagers().contains( red ) );
		assertTrue( team.getManagers().contains( blue ) );
		assertTrue( team.getManagers().contains( adam ) );
		assertFalse( team.getManagers().contains( rex ) );
		assertFalse( team.getManagers().contains( mike ) );
	}

	@Test public void TEST_demote() {
		team.promote( blue );
		team.promote( adam );
		team.demote( blue );

		assertFalse( team.getManagers().contains( dan ) );
		assertFalse( team.getManagers().contains( red ) );
		assertFalse( team.getManagers().contains( blue ) );
		assertTrue( team.getManagers().contains( adam ) );
		assertFalse( team.getManagers().contains( rex ) );
		assertFalse( team.getManagers().contains( mike ) );
	}

//	@Test public void TEST_save_load() throws IOException, ClassNotFoundException {
//		Team.save( team, "Test Save.save" );
//		Team team2 = Team.load( "Test Save.save" );
//
//		//		assertEquals( team, team2 );
//		assertTrue( team.equals( team2 ) );
//	}
}
