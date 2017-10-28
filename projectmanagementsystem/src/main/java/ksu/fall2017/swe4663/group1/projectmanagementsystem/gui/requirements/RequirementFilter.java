package ksu.fall2017.swe4663.group1.projectmanagementsystem.gui.requirements;

import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Priority;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Requirement;
import ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements.Status;

import java.util.Arrays;
import java.util.LinkedList;

public class RequirementFilter {

	private LinkedList<RequirementComparator> comparators;

	public RequirementFilter() {
		this.comparators = new LinkedList<>();
	}

	public RequirementFilter( RequirementComparator... comparators ) {
		this();
		this.comparators.addAll( Arrays.asList( comparators ) );
	}

	public void addComparator( RequirementComparator comparator ) throws RedundantComparatorException {
		for ( RequirementComparator comparator1 : this.comparators ) {
			if ( comparator1.getClass()
					.equals( comparator.getClass() ) ) { // LATER What about multiple 'contains' filters?
				throw new RedundantComparatorException(
						"A comparator of type " + comparator.getClass().getSimpleName() + " has already been added." );
			}
		}
		this.comparators.add( comparator );
	}

	public void removeComparator( RequirementComparator comparator ) {
		this.comparators.remove( comparator );
	}

	public LinkedList<RequirementComparator> getComparators() {
		return this.comparators;
	}

	public void resetFilter() {
		this.comparators.clear();
	}

	public boolean passesFilter( Requirement requirement ) {
		for ( RequirementComparator comparator : this.comparators ) {
			if ( !comparator.passesFilter( requirement ) ) {
				return false;
			}
		}
		return true;
	}

	public enum StringMatch {
		CONTAINS( "contains" ), MATCHES( "matches" );

		private String text;

		StringMatch( String text ) {
			this.text = text;
		}

		@Override public String toString() {
			return this.text;
		}
	}

	public enum PriorityMatch {
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

	public enum ComparatorType {
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

	public static abstract class RequirementComparator {

		abstract boolean passesFilter( Requirement requirement );

		public abstract String toString();
	}

	public static class TitleComparator extends RequirementComparator {

		String comparisonValue;
		StringMatch matchType;

		public TitleComparator( String comparisonValue, StringMatch matchType ) {
			this.comparisonValue = comparisonValue.trim();
			this.matchType = matchType;
		}

		@Override boolean passesFilter( Requirement requirement ) {
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

	public static class DescriptionComparator extends RequirementComparator {

		String comparisonValue;
		StringMatch matchType;

		public DescriptionComparator( String comparisonValue, StringMatch matchType ) {
			this.comparisonValue = comparisonValue.trim();
			this.matchType = matchType;
		}

		@Override boolean passesFilter( Requirement requirement ) {
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

	public static class SourceComparator extends RequirementComparator {

		String comparisonValue;
		StringMatch matchType;

		public SourceComparator( String comparisonValue, StringMatch matchType ) {
			this.comparisonValue = comparisonValue.trim();
			this.matchType = matchType;
		}

		@Override boolean passesFilter( Requirement requirement ) {
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

	public static class PriorityComparator extends RequirementComparator {

		Priority priority;
		PriorityMatch priorityMatch;

		public PriorityComparator( Priority priority, PriorityMatch priorityMatch ) {
			this.priority = priority;
			this.priorityMatch = priorityMatch;
		}

		@Override boolean passesFilter( Requirement requirement ) {
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

	public static class StatusComparator extends RequirementComparator {

		Status status;

		public StatusComparator( Status status ) {
			this.status = status;
		}

		@Override boolean passesFilter( Requirement requirement ) {
			return this.status == requirement.getStatus();
		}

		@Override public String toString() {
			return "Requirement's status is \"" + this.status + "\"";
		}
	}

	public static class FulfillmentComparator extends RequirementComparator {

		boolean isFulfilled;

		public FulfillmentComparator( boolean isFulfilled ) {
			this.isFulfilled = isFulfilled;
		}

		@Override boolean passesFilter( Requirement requirement ) {
			return this.isFulfilled == requirement.isComplete();
		}

		@Override public String toString() {
			return "Requirement is " + ( this.isFulfilled ? "" : "not " ) + "fulfilled";
		}
	}

	public static class FunctionalityComparator extends RequirementComparator {

		boolean isFunctional;

		public FunctionalityComparator( boolean isFunctional ) {
			this.isFunctional = isFunctional;
		}

		@Override boolean passesFilter( Requirement requirement ) {
			return this.isFunctional == requirement.isFunctional();
		}

		@Override public String toString() {
			return "Requirement is a " + ( this.isFunctional ? "" : "non-" ) + "functional requirement";
		}
	}

	public static class IDComparator extends RequirementComparator {

		short ID;

		public IDComparator( short targetID ) {
			this.ID = targetID;
		}

		@Override boolean passesFilter( Requirement requirement ) {
			return this.ID == requirement.getItemNumber();
		}

		@Override public String toString() {
			return "Requirement's ID number is \"" + this.ID + "\"";
		}
	}

	public class RedundantComparatorException extends Exception {
		public RedundantComparatorException( String message ) {
			super( message );
		}
	}
}
