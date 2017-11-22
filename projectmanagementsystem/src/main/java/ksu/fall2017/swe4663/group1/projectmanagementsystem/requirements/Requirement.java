package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import eaglezr.support.logs.LoggingTool;

import java.io.Serializable;

/**
 * A class to represent the requirements associated with a development project.
 *
 * @author Mark Zeagler
 * @version 1.0
 */
public class Requirement implements Serializable {
	private static final long serialVersionUID = -2391731060123178351L;

	private String title;
	private String description;
	private String source;
	private Priority priority;
	private Status status;
	private boolean isFulfilled;
	private boolean isFunctional;
	private short itemNumber;

	/**
	 * Constructs a requirement with the given attributes.
	 *
	 * @param title        The title of the Requirement. This could also be called a "short description", meant to be no
	 *                     more than 20 characters in length.
	 * @param description  A more verbose description of the requirement. This is meant to be a full-detailed summary
	 *                     and description of the requirement, and what all needs to be done to fulfill this
	 *                     requirement.
	 * @param source       The source that is requiring this requirement. This could be a stakeholder, or perhaps
	 *                     industry standard, or the development company's business practices. A simple indicator is all
	 *                     that is necessary (i.e. David Reynolds, Aerotech Enterprises, or OSHAct SEC. 8.a.1).
	 * @param priority     There is a simple priority enumeration used to scale requirements by.
	 * @param status       There are 3 acceptance statuses that a requirement may be assigned.
	 * @param isFunctional If this is a functional requirement, use {@code true}. If it is a non-functional requirement,
	 *                     use {@code false}.
	 * @param isFulfilled  Indicates whether or not this requirement's conditions have been met. Usually, when creating
	 *                     a new requirement, this will be {@code false}.
	 */
	public Requirement( String title, String description, String source, Priority priority, Status status,
			boolean isFunctional, boolean isFulfilled ) {
		LoggingTool.print( "Constructing new Requirement." );
		this.title = title;
		this.description = description;
		this.source = source;
		this.priority = priority;
		this.status = status;
		this.isFunctional = isFunctional;
		this.isFulfilled = isFulfilled;
	}

	/**
	 * Retrieves the title of this requirement.
	 *
	 * @return The title of this requirement.
	 */
	public String getTitle() {
		return this.title;

	}

	/**
	 * Sets a new title for this requirement.
	 *
	 * @param title The new title to be assigned to this requirement.
	 */
	public void setTitle( String title ) {
		this.title = title;
	}

	/**
	 * Retrieves the description of this requirement.
	 *
	 * @return The description of this requirement.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets a new description for this requirement.
	 *
	 * @param description The new description to be assigned to this requirement.
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * Retrieves the source of this requirement.
	 *
	 * @return The source of this requirement.
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * Sets a new source for this requirement.
	 *
	 * @param source The new source to be assigned to this requirement.
	 */
	public void setSource( String source ) {
		this.source = source;
	}

	/**
	 * Retrieves the priority of this requirement.
	 *
	 * @return The priority of this requirement.
	 */
	public Priority getPriority() {
		return this.priority;
	}

	/**
	 * Sets a new priority for this requirement.
	 *
	 * @param priority The new priority to be assigned to this requirement.
	 */
	public void setPriority( Priority priority ) {
		this.priority = priority;
	}

	/**
	 * Retrieves the acceptance status of this requirement.
	 *
	 * @return The acceptance status of this requirement.
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * Sets a new acceptance status for this requirement.
	 *
	 * @param status The new acceptance status  to be assigned to this requirement.
	 */
	public void setStatus( Status status ) {
		this.status = status;
	}

	/**
	 * Retrieves the functional flag for this requirement.
	 *
	 * @return Returns {@code true} if this is a functional requirement and {@code false} if it is a non-functional
	 * requirement.
	 */
	public boolean isFunctional() {
		return this.isFunctional;
	}

	/**
	 * Sets the functional flag for this requirement.
	 *
	 * @param isFunctional The flag should be {@code true} to indicate this is a functional requirement and {@code
	 *                     false} to indicate that it is a non-functional requirement.
	 */
	public void setIsFunctional( boolean isFunctional ) {
		this.isFunctional = isFunctional;
	}

	/**
	 * Retrieves the completion flag for this requirement.
	 *
	 * @return Returns {@code true} if all conditions of this requirement have been fulfilled and {@code false} if not.
	 */
	public boolean isComplete() {
		return this.isFulfilled;
	}

	/**
	 * Sets the completion flag for this requirement.
	 *
	 * @param fulfilled The flag should be {@code true} if all conditions of this requirement have been met and {@code
	 *                  false} if not.
	 */
	public void setFulfilled( boolean fulfilled ) {
		this.isFulfilled = fulfilled;
	}

	/**
	 * Retrieves the item number of this requirement.
	 *
	 * @return The item number of this requirement.
	 */
	public short getItemNumber() {
		return this.itemNumber;
	}

	/**
	 * Sets a new item number for this requirement.
	 *
	 * @param itemNumber The new item number to be assigned to this requirement.
	 */
	protected void setItemNumber( short itemNumber ) {
		this.itemNumber = itemNumber;
	}
}
