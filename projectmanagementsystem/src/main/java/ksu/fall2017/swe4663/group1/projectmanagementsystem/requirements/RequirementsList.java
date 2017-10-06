package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import java.util.Collection;
import java.util.LinkedList;

public class RequirementsList extends LinkedList<Requirement> {

	private static final long serialVersionUID = -4756933007834651578L;

	@Override public boolean add( Requirement requirement ) {
		setItemNumber( requirement );
		return super.add( requirement );
	}

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
	 * @param priority
	 * @return
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

	public LinkedList<Requirement> getByStatus( Status status ) {
		LinkedList<Requirement> statusList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( requirement.getStatus() == status ) {
				statusList.add( requirement );
			}
		}
		return statusList;
	}

	public LinkedList<Requirement> getUnFulfilled() {
		LinkedList<Requirement> unFulfilledList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( !requirement.isFulfilled() ) {
				unFulfilledList.add( requirement );
			}
		}
		return unFulfilledList;
	}

	public LinkedList<Requirement> getFulfilled() {
		LinkedList<Requirement> fulfilledList = new LinkedList<>();
		for ( Requirement requirement : this ) {
			if ( requirement.isFulfilled() ) {
				fulfilledList.add( requirement );
			}
		}
		return fulfilledList;
	}
}
