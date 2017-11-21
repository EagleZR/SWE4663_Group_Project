package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import eaglezr.support.logs.LoggingTool;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Priority;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Status;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This provides a filter for displaying only {@link Requirement}s that meet a certain set of criteria. If there are no
 * criteria, everything is permitted. If there are criteria, only {@link Requirement}s which pass each criterium are
 * permitted.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class RequirementFilter {

	private LinkedList<RequirementComparator> comparators;

	/**
	 * Constructs an empty filter.
	 */
	public RequirementFilter() {
		LoggingTool.print( "Constructing new RequirementFilter." );
		this.comparators = new LinkedList<>();
	}

	/**
	 * Constructs a filter from the given comparators.
	 *
	 * @param comparators The comparators which will define the behavior of the filter.
	 */
	public RequirementFilter( RequirementComparator... comparators ) {
		this();
		this.comparators.addAll( Arrays.asList( comparators ) );
	}

	/**
	 * Adds the given comparator to the filter.
	 *
	 * @param comparator The comparator to be added to the filter.
	 * @throws RedundantComparatorException Thrown if the comparator added conflicts with a comparator already in the
	 *                                      filter.
	 */
	protected void addComparator( RequirementComparator comparator ) throws RedundantComparatorException {
		for ( RequirementComparator comparator1 : this.comparators ) {
			if ( comparator1.getClass()
					.equals( comparator.getClass() ) ) { // LATER What about multiple 'contains' filters?
				throw new RedundantComparatorException(
						"A comparator of type " + comparator.getClass().getSimpleName() + " has already been added." );
			}
		}
		this.comparators.add( comparator );
	}

	/**
	 * Removes the given comparator from the filter.
	 *
	 * @param comparator The comparator to be removed.
	 */
	protected void removeComparator( RequirementComparator comparator ) {
		this.comparators.remove( comparator );
	}

	/**
	 * Retrieves all of the comparators defining this filter.
	 *
	 * @return The comparators which define this filter.
	 */
	protected LinkedList<RequirementComparator> getComparators() {
		return this.comparators;
	}

	/**
	 * Removes all comparators from this filter.
	 */
	protected void resetFilter() {
		this.comparators.clear();
	}

	/**
	 * Checks if a given {@link Requirement} passes the filter.
	 *
	 * @param requirement The requirement to be checked against the filter.
	 * @return Returns {@code true} if the {@link Requirement} passes the filter, {@code false} if it does not.
	 */
	protected boolean passesFilter( Requirement requirement ) {
		for ( RequirementComparator comparator : this.comparators ) {
			if ( !comparator.passesComparator( requirement ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Enumerates the two ways by which a string can be matched.
	 */
	protected enum StringMatch {
		/**
		 * The corresponding string in the Requirement must contain the comparator's string to pass the filter.
		 */
		CONTAINS( "contains" ), /**
		 * The corresponding string in the Requirement must match exactly the comparator's string to pass the filter.
		 */
		MATCHES( "matches" );

		private String text;

		StringMatch( String text ) {
			this.text = text;
		}

		@Override public String toString() {
			return this.text;
		}
	}

	/**
	 * Enumerates the ways by which two {@link Priority}s can be compared.
	 */
	protected enum PriorityMatch {
		LOWER_THAN( "is lower than" ), LOWER_THAN_EQUALS( "is lower than or equal to" ), EQUALS(
				"is equal to" ), GREATER_THAN_EQUALS( "is greater than or equal to" ), GREATER_THAN(
				"is greater than" );
		String text;

		PriorityMatch( String text ) {
			this.text = text;
		}

		@Override public String toString() {
			return this.text;
		}
	}

	/**
	 * Enumerates the different comparators that can be used for the filter.
	 */
	protected enum ComparatorType {
		TITLE( "Title" ), DESCRIPTION( "Description" ), SOURCE( "Source" ), PRIORITY( "Priority" ), STATUS(
				"Status" ), FULFILLMENT( "Fulfillment" ), FUNCTIONALITY( "Functionality" ), ID( "ID" );

		String text;

		ComparatorType( String text ) {
			this.text = text;
		}

		@Override public String toString() {
			return this.text;
		}
	}

	/**
	 * Provides an abstract base class that each of the comparators will extend from.
	 */
	protected static abstract class RequirementComparator {

		/**
		 * Checks if the given {@link Requirement} passes this comparator.
		 *
		 * @param requirement The requirement to be checked against this comparator.
		 * @return Returns {@code true} if the {@link Requirement} passes this comparator, {@code false} if it does not.
		 */
		protected abstract boolean passesComparator( Requirement requirement );

		@Override public abstract String toString();
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#title}.
	 */
	protected static class TitleComparator extends RequirementComparator {

		String comparisonValue;
		StringMatch matchType;

		protected TitleComparator( String comparisonValue, StringMatch matchType ) {
			this.comparisonValue = comparisonValue.trim();
			this.matchType = matchType;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			if ( this.matchType == StringMatch.CONTAINS ) {
				return requirement.getTitle().contains( this.comparisonValue );
			} else {
				return requirement.getTitle().trim().equals( this.comparisonValue );
			}
		}

		@Override public String toString() {
			return "Requirement's title " + this.matchType + " \"" + this.comparisonValue + "\"";
		}
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#description}.
	 */
	protected static class DescriptionComparator extends RequirementComparator {

		String comparisonValue;
		StringMatch matchType;

		protected DescriptionComparator( String comparisonValue, StringMatch matchType ) {
			this.comparisonValue = comparisonValue.trim();
			this.matchType = matchType;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			if ( this.matchType == StringMatch.CONTAINS ) {
				return requirement.getDescription().contains( this.comparisonValue );
			} else {
				return requirement.getDescription().trim().equals( this.comparisonValue );
			}
		}

		@Override public String toString() {
			return "Requirement's description " + this.matchType + " \"" + this.comparisonValue + "\"";
		}
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#source}.
	 */
	protected static class SourceComparator extends RequirementComparator {

		String comparisonValue;
		StringMatch matchType;

		protected SourceComparator( String comparisonValue, StringMatch matchType ) {
			this.comparisonValue = comparisonValue.trim();
			this.matchType = matchType;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			if ( this.matchType == StringMatch.CONTAINS ) {
				return requirement.getSource().contains( this.comparisonValue );
			} else {
				return requirement.getSource().trim().equals( this.comparisonValue );
			}
		}

		@Override public String toString() {
			return "Requirement's source " + this.matchType + " \"" + this.comparisonValue + "\"";
		}
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#priority}.
	 */
	protected static class PriorityComparator extends RequirementComparator {

		Priority priority;
		PriorityMatch priorityMatch;

		protected PriorityComparator( Priority priority, PriorityMatch priorityMatch ) {
			this.priority = priority;
			this.priorityMatch = priorityMatch;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			if ( this.priorityMatch == PriorityMatch.LOWER_THAN ) {
				return requirement.getPriority().getWeight() < this.priority.getWeight();
			} else if ( this.priorityMatch == PriorityMatch.LOWER_THAN_EQUALS ) {
				return requirement.getPriority().getWeight() <= this.priority.getWeight();
			} else if ( this.priorityMatch == PriorityMatch.EQUALS ) {
				return requirement.getPriority().getWeight() == this.priority.getWeight();
			} else if ( this.priorityMatch == PriorityMatch.GREATER_THAN_EQUALS ) {
				return requirement.getPriority().getWeight() > this.priority.getWeight();
			} else {
				return requirement.getPriority().getWeight() >= this.priority.getWeight();
			}
		}

		@Override public String toString() {
			return "Requirement's priority " + this.priorityMatch + " \"" + this.priority + "\"";
		}

	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#status}.
	 */
	protected static class StatusComparator extends RequirementComparator {

		Status status;

		protected StatusComparator( Status status ) {
			this.status = status;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			return this.status == requirement.getStatus();
		}

		@Override public String toString() {
			return "Requirement's status is \"" + this.status + "\"";
		}
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#isFulfilled} value.
	 */
	protected static class FulfillmentComparator extends RequirementComparator {

		boolean isFulfilled;

		protected FulfillmentComparator( boolean isFulfilled ) {
			this.isFulfilled = isFulfilled;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			return this.isFulfilled == requirement.isComplete();
		}

		@Override public String toString() {
			return "Requirement is " + ( this.isFulfilled ? "" : "not " ) + "fulfilled";
		}
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#isFunctional} value.
	 */
	protected static class FunctionalityComparator extends RequirementComparator {

		boolean isFunctional;

		protected FunctionalityComparator( boolean isFunctional ) {
			this.isFunctional = isFunctional;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			return this.isFunctional == requirement.isFunctional();
		}

		@Override public String toString() {
			return "Requirement is a " + ( this.isFunctional ? "" : "non-" ) + "functional requirement";
		}
	}

	/**
	 * A comparator that places a qualification on the {@link Requirement#itemNumber}.
	 */
	protected static class IDComparator extends RequirementComparator {

		short ID;

		protected IDComparator( short targetID ) {
			this.ID = targetID;
		}

		@Override protected boolean passesComparator( Requirement requirement ) {
			return this.ID == requirement.getItemNumber();
		}

		@Override public String toString() {
			return "Requirement's ID number is \"" + this.ID + "\"";
		}
	}

	/**
	 * An exception meant to be thrown if a comparator that is added to a filter conflicts with a comparator that is
	 * already in the filter.
	 */
	protected class RedundantComparatorException extends Exception {
		private RedundantComparatorException( String message ) {
			super( message );
		}
	}
}
