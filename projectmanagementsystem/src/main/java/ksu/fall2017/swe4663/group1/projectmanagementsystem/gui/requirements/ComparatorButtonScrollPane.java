package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.errorsystem.ErrorPopupSystem;
import eaglezr.support.logs.LoggingTool;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.Project;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.ProjectPane;

import java.util.ArrayList;

/**
 * A pane that holds a bunch of {@link ComparatorButton}s, providing a scroll bar when necessary. It provides access to
 * the {@link ComparatorButtonScrollPane#selectedButton} to other methods in the package.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class ComparatorButtonScrollPane extends ScrollPane implements ProjectPane {

	ComparatorButton selectedButton;
	private RequirementFilter filter;
	private Pane filterButtonPane;
	private ArrayList<ComparatorButton> buttons;

	/**
	 * This provides a {@link ScrollPane} of comparators that are accessible and editable through {@link
	 * ComparatorButton}s.
	 *
	 * @param filter The filter which this pane will display and edit.
	 */
	protected ComparatorButtonScrollPane( RequirementFilter filter ) {
		super();
		LoggingTool.print( "Constructing new ComparatorButtonScrollPane." );
		this.filter = filter;
		this.filterButtonPane = new Pane();
		super.setContent( this.filterButtonPane );
		this.buttons = new ArrayList<>();
	}

	/**
	 * Adds a given comparator to the {@link RequirementFilter}, creating a button for it in the process.
	 *
	 * @param comparator The new comparator to be added.
	 * @throws RequirementFilter.RedundantComparatorException Thrown if there are multiple comparators of the same type
	 *                                                        in the filter.
	 */
	protected void addComparator( RequirementFilter.RequirementComparator comparator )
			throws RequirementFilter.RedundantComparatorException {
		if ( findButton( comparator ) == null ) {
			LoggingTool.print( "ComparatorButtonScrollPane: Adding comparator: \"" + comparator.toString() + "\"." );
			this.filter.addComparator( comparator );
			ComparatorButton button = new ComparatorButton( comparator );
			button.setOnAction( e -> {
				for ( ComparatorButton button1 : this.buttons ) {
					button1.setDefaultButton( false );
				}
				button.setDefaultButton( true );
				this.selectedButton = button;
			} );
			if ( this.buttons.size() == 0 ) {
				button.layoutXProperty().setValue( 0 );
				button.layoutYProperty().setValue( 0 );
			} else {
				ComparatorButton previous = this.buttons.get( this.buttons.size() - 1 );
				button.layoutXProperty().bind( previous.layoutXProperty() );
				button.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
			}
			this.buttons.add( button );
			this.filterButtonPane.getChildren().add( button );
		}
	}

	/**
	 * Removes the comparator from the {@link RequirementFilter}.
	 *
	 * @param comparator The comparator to be removed.
	 */
	protected void removeComparator( RequirementFilter.RequirementComparator comparator ) {
		removeButton( findButton( comparator ) );
	}

	/**
	 * Removes the comparator from the {@link RequirementFilter}.
	 *
	 * @param button The button whose comparator is to be removed.
	 */
	protected void removeButton( ComparatorButton button ) {
		if ( this.buttons.contains( button ) ) {
			LoggingTool.print( "ComparatorButtonScrollPane: Removing comparator: \"" + button.comparator.toString()
					+ "\"." );
			if ( this.buttons.indexOf( button ) == this.buttons.size() - 1 ) {
				this.buttons.remove( button );
				this.filterButtonPane.getChildren().remove( button );
			} else if ( this.buttons.indexOf( button ) == 0 ) {
				ComparatorButton next = this.buttons.get( this.buttons.indexOf( button ) + 1 );
				next.layoutXProperty().unbind();
				next.layoutXProperty().setValue( 0 );
				next.layoutYProperty().unbind();
				next.layoutYProperty().setValue( 0 );
				this.buttons.remove( button );
				this.filterButtonPane.getChildren().remove( button );
			} else {
				int index = this.buttons.indexOf( button );
				ComparatorButton previous = this.buttons.get( index - 1 );
				ComparatorButton next = this.buttons.get( index + 1 );
				next.layoutXProperty().unbind();
				next.layoutYProperty().unbind();
				next.layoutXProperty().bind( previous.layoutXProperty() );
				next.layoutYProperty().bind( previous.layoutYProperty().add( previous.heightProperty() ) );
				this.buttons.remove( button );
				this.filterButtonPane.getChildren().remove( button );
			}
			this.filter.removeComparator( button.comparator );
		}
	}

	/**
	 * Finds and returns the {@link ComparatorButton} corresponding to the given {@link
	 * ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementFilter.RequirementComparator}.
	 *
	 * @param comparator The comparator whose {@link ComparatorButton} will be searched for.
	 * @return The appropriate {@link ComparatorButton} if it exists, or {@code null} if it does not.
	 */
	protected ComparatorButton findButton( RequirementFilter.RequirementComparator comparator ) {
		for ( ComparatorButton button : this.buttons ) {
			if ( button.comparator.equals( comparator ) ) {
				return button;
			}
		}
		return null;
	}

	/**
	 * This updates each field to display their current value.
	 */
	protected void update() {
		// Make sure all Requirements have a RequirementPane and add as needed
		for ( RequirementFilter.RequirementComparator comparator : this.filter.getComparators() ) {
			boolean hasButton = false;
			for ( ComparatorButton button : this.buttons ) {
				if ( button.getComparator().equals( comparator ) ) {
					hasButton = true;
					break;
				}
			}
			if ( !hasButton ) {
				try {
					addComparator( comparator );
				} catch ( RequirementFilter.RedundantComparatorException e ) {
					ErrorPopupSystem.displayMessage( "There was an error while updating the filter pane." );
				}
			}
		}

		// Remove any RequirementPanes that do not correspond to a Requirement currently in the list
		for ( ComparatorButton button : this.buttons ) {
			boolean isInFilter = false;
			for ( RequirementFilter.RequirementComparator comparator : this.filter.getComparators() ) {
				if ( button.getComparator().equals( comparator ) ) {
					isInFilter = true;
				}
			}
			if ( !isInFilter ) {
				removeButton( button );
			}
		}
	}

	/**
	 * Clears all of the values and resets to the default state.
	 */
	protected void clear() {
		LoggingTool.print( "ComparatorButtonScrollPane: Clearing." );
		this.filterButtonPane.getChildren().clear();
		this.buttons.clear();
		this.filter.resetFilter();
	}

	@Override public void loadNewProject( Project project ) {
		clear();
	}

	/**
	 * An extension of {@link Button} specifically made for a {@link ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements.RequirementFilter.RequirementComparator}.
	 */
	private class ComparatorButton extends Button {

		RequirementFilter.RequirementComparator comparator;

		private ComparatorButton( RequirementFilter.RequirementComparator comparator ) {
			super( comparator.toString() );
			LoggingTool.print( "Constructing new ComparatorButton." );
			this.comparator = comparator;
		}

		RequirementFilter.RequirementComparator getComparator() {
			return this.comparator;
		}
	}
}
