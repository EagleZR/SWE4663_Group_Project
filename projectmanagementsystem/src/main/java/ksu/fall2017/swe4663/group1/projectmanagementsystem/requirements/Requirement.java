package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

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

	public Requirement( String title, String description, String source, Priority priority, Status status,
			boolean isFunctional, boolean isFulfilled ) {
		this.title = title;
		this.description = description;
		this.source = source;
		this.priority = priority;
		this.status = status;
		this.isFunctional = isFunctional;
		this.isFulfilled = isFulfilled;
	}

	public String getTitle() {
		return this.title;

	}

	public void setTitle( String title ) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource( String source ) {
		this.source = source;
	}

	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority( Priority priority ) {
		this.priority = priority;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus( Status status ) {
		this.status = status;
	}

	public boolean isFunctional() {
		return this.isFunctional;
	}

	public void setIsFunctional( boolean isFunctional ) {
		this.isFunctional = isFunctional;
	}

	public boolean isComplete() {
		return this.isFulfilled;
	}

	public void setFulfilled( boolean fulfilled ) {
		this.isFulfilled = fulfilled;
	}

	public short getItemNumber() {
		return this.itemNumber;
	}

	protected void setItemNumber( short itemNumber ) {
		this.itemNumber = itemNumber;
	}
}
