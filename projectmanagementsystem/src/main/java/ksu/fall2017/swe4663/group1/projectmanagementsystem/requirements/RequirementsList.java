package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A derivative of the {@link LinkedList} class, specially made for {@link Requirement}s.
 */
public class RequirementsList extends LinkedList<Requirement> {

	private static final long serialVersionUID = -4756933007834651578L;

	/**
	 * Adds the {@link Requirement} to the list and initializes its item number.
	 *
	 * @param requirement The {@link Requirement} to be added to the list.
	 * @return {@code true} if the {@link Requirement} was successfully added.
	 */
	@Override public boolean add( Requirement requirement ) {
		setItemNumber( requirement );
		return super.add( requirement );
	}

	/**
	 * Adds all of the given {@link Requirement}s to the list.
	 *
	 * @param c The collection of {@link Requirement}s to be added.
	 * @return {@code true} if the {@link Requirement}s were successfully added.
	 */
	@Override public boolean addAll( Collection<? extends Requirement> c ) {
		for ( Requirement requirement : c ) {
			setItemNumber( requirement );
		}
		return super.addAll( c );
	}

	/**
	 * Sets the itemNumber for the requirement. This ensures that each {@link Requirement} has a unique item number,
	 * preferably that isn't too long.
	 *
	 * @param requirement The requirement whose itemNumber will be set.
	 */
	private void setItemNumber( Requirement requirement ) {
		short tempNumber = (short) this.size(); // A project with 1,000s of Requirements? Short should be good enough...
		while ( getByItemNumber( tempNumber ) != null ) {
			tempNumber++;
			if ( tempNumber == Short.MAX_VALUE ) {
				throw new IndexOutOfBoundsException(
						"Somehow, you've filled up the list of requirements, and the index is out of range. You should probably decrease your project's scope." );
			}
		}
		requirement.setItemNumber( tempNumber );
	}

	/**
	 * Retrieves the {@link Requirement} using the given itemNumber.
	 *
	 * @param itemNumber The itemNumber of the {@link Requirement} to be returned.
	 * @return The {@link Requirement} with the specified itemNumber.
	 */
	public Requirement getByItemNumber( short itemNumber ) {
		for ( Requirement requirement : this ) {
			if ( requirement.getItemNumber() == itemNumber ) {
				return requirement;
			}
		}
		return null;
	}

	/**
	 * Returns a {@link LinkedList}<{@link Requirement}> of all {@link Requirement}s at the specified priority level.
	 *
	 * @param priority The priority level of the {@link Requirement}s to be returned.
	 * @return A {@link LinkedList}<{@link Requirement}> consisting of all {@link Requirement}s at the specified {@link
	 * Priority} level.
	 */
	public LinkedList<Requirement> getByPriority( Priority priority ) {
		LinkedList<Requirement> priorityList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( requirement.getPriority() == priority ) {
				priorityList.add( requirement );
			}
		}
		return priorityList;
	}

	/**
	 * Retrieves a {@link LinkedList}<{@link Requirement}> of the Requirements with the given {@link Priority}.
	 *
	 * @param priority The priority of the {@link Requirement} to be retrieved.
	 * @return A new {@link LinkedList}<{@code Requirement}> of the {@link Requirement}s that mee the given {@link
	 * Priority}.
	 */
	public LinkedList<Requirement> getByPriorityUp( Priority priority ) {
		LinkedList<Requirement> priorityList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( requirement.getPriority().getWeight() >= priority.getWeight() ) {
				priorityList.add( requirement );
			}
		}
		return priorityList;
	}

	/**
	 * Returns a {@link LinkedList}<{@code Requirement}> of all requirements with the given {@link Status}.
	 *
	 * @param status The status of the {@link Requirement}s to be retrieved.
	 * @return A new {@link LinkedList}<{@code Requirement}> of the {@link Requirement}s that meet the given {@link
	 * Status}.
	 */
	public LinkedList<Requirement> getByStatus( Status status ) {
		LinkedList<Requirement> statusList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( requirement.getStatus() == status ) {
				statusList.add( requirement );
			}
		}
		return statusList;
	}

	/**
	 * Retrieves all unfulfilled {@link Requirement}s.
	 *
	 * @return A new {@link LinkedList}<{@code Requirement}> of all {@link Requirement}s in this list flagged as
	 * unfulfilled.
	 */
	public LinkedList<Requirement> getUnFulfilled() {
		LinkedList<Requirement> unFulfilledList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( !requirement.isComplete() ) {
				unFulfilledList.add( requirement );
			}
		}
		return unFulfilledList;
	}

	/**
	 * Retrieves all fulfilled {@link Requirement}s.
	 *
	 * @return A new {@link LinkedList}<{@code Requirement}> of all {@link Requirement}s in this list flagged as
	 * fulfilled.
	 */
	public LinkedList<Requirement> getFulfilled() {
		LinkedList<Requirement> fulfilledList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( requirement.isComplete() ) {
				fulfilledList.add( requirement );
			}
		}
		return fulfilledList;
	}
}
