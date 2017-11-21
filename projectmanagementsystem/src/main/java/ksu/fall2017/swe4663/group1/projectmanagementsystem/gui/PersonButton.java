package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

/**
 * A {@link Button} that is built for a specific {@link Person} and displays that Person's name in the Button's label.
 * <p>NOTE: There is no action specified for when the {@link PersonButton} is pushed, and should be specified when
 * creating it.</p>
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class PersonButton extends Button {

	private Person person;

	/**
	 * Constructs a new button for the given {@link Person}.
	 *
	 * @param person The person to be displayed and contained by this button.
	 */
	public PersonButton( Person person ) {
		super( person.getName() );
		LoggingTool.print( "Constructing new PersonButton for Person named " + person.getName() + "." );
		this.person = person;
	}

	/**
	 * Retrieves the {@link Person} associated with this button.
	 *
	 * @return The {@link Person} associated with this button.
	 */
	public Person getPerson() {
		return this.person;
	}

	/**
	 * Changes the name of the {@link Person} associated with this button.
	 *
	 * @param name The new name to be given to the {@link Person}.
	 */
	public void changeName( String name ) {
		LoggingTool.print( "PersonButton: Changing the person's name in PersonButton from " + this.person.getName()
				+ " to " + name + "." );
		this.person.changeName( name );
		super.setText( name );
	}
}
