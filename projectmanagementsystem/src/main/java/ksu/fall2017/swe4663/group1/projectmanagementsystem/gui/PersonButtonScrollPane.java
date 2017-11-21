package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui;

import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.team.Person;

import java.util.LinkedList;

/**
 * A pane to hold a set of {@link PersonButton}s, ensuring that each {@link Person} has one button, and only one.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class PersonButtonScrollPane extends ScrollPane {

	private Pane buttonPane;
	private LinkedList<PersonButton> buttons;

	/**
	 * Constructs an empty pane.
	 */
	public PersonButtonScrollPane() {
		LoggingTool.print( "Constructing a new PersonButtonScrollPane." );
		this.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );
		this.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
		this.setFitToWidth( true );
		this.buttonPane = new Pane();
		this.setContent( this.buttonPane );
		this.buttons = new LinkedList<>();
	}

	/**
	 * Adds the given {@link PersonButton} to the pane.
	 *
	 * @param button The button to be added.
	 */
	public void addButton( PersonButton button ) {
		LoggingTool.print( "PersonButtonScrollPane: Adding Button with text: " + button.getText() );
		button.prefWidthProperty().bind( this.buttonPane.widthProperty() );
		button.layoutXProperty().setValue( 0 );
		if ( this.buttons.size() == 0 ) {
			button.layoutYProperty().setValue( 0 );
		} else {
			Button previous = this.buttons.get( this.buttons.size() - 1 );
			button.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
		}
		this.buttons.add( button );
		this.buttonPane.getChildren().add( button );
	}

	/**
	 * Removes the given {@link PersonButton} from the pane.
	 *
	 * @param button The button to be removed.
	 */
	public void removeButton( PersonButton button ) {
		LoggingTool.print( "PersonButtonScrollPane: Removing Button with text: " + ( button == null ?
				"<null>" :
				button.getText() ) );
		int index = this.buttons.indexOf( button );
		if ( this.buttons.size() == 1 ) {

		} else if ( index == 0 ) {
			Button next = this.buttons.get( 1 );
			next.layoutYProperty().unbind();
			next.layoutYProperty().setValue( 0 );
		} else if ( index != this.buttons.size() - 1 && index > 0 ) {
			Button next = this.buttons.get( index + 1 );
			Button prev = this.buttons.get( index - 1 );
			next.layoutYProperty().bind( prev.layoutYProperty().add( prev.heightProperty() ) );
		}

		this.buttonPane.getChildren().remove( button );
		this.buttons.remove( button );
	}

	/**
	 * Retrieves the {@link PersonButton}s contained by this pane.
	 *
	 * @return The buttons contained by this pane.
	 */
	public LinkedList<PersonButton> getButtons() {
		return this.buttons;
	}

	public void addPerson( Person person ) {
		PersonButton button = new PersonButton( person );
		addButton( button );
	}

	/**
	 * Removes the {@link PersonButton} associated with the given {@link Person} form the pane.
	 *
	 * @param person The person whose {@link PersonButton} will be removed.
	 */
	public void removePerson( Person person ) {
		if ( containsPerson( person ) ) {
			removeButton( getPersonButton( person ) );
		}
	}

	/**
	 * Checks if any of the {@link PersonButton}s in this pane are associated with the given {@link Person}.
	 *
	 * @param person The person to search for in this pane.
	 * @return Returns {@code true} if the person is displayed by this pane, {@code} false if it is not.
	 */
	public boolean containsPerson( Person person ) {
		for ( PersonButton button : this.buttons ) {
			if ( button.getPerson().equals( person ) ) {
				// Updates the name if it's changed.
				if ( !button.getText().equals( person.getName() ) ) {
					LoggingTool
							.print( "PersonButtonScrollPane: Updating the name displayed in the button for: " + person
									.getName() + "." );
					button.setText( person.getName() );
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves the {@link PersonButton} associated with the given {@link Person}.
	 *
	 * @param person The person whose {@link PersonButton} is to be retrieved.
	 * @return The {@link PersonButton} associated with the given {@link Person} if it exists, {@code null} if it does
	 * not.
	 */
	public PersonButton getPersonButton( Person person ) {
		for ( PersonButton button : this.buttons ) {
			if ( button.getPerson().equals( person ) ) {
				return button;
			}
		}
		return null;
	}

	/**
	 * Removes all {@link PersonButton}s.
	 */
	public void clear() {
		LoggingTool.print( "PersonButtonScrollPane: Clearing." );
		this.buttonPane.getChildren().clear();
		this.buttons.clear();
	}
}
