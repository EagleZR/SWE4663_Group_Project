package ksu.fall2017.swe4663.group1.projectmanagementsystem.requirements;

import java.io.Serializable;

public class Requirement implements Serializable {
	private static final long serialVersionUID = -2391731060123178351L;

	private String description;
	private String source;
	private Priority priority;
	private Status status;
	private boolean isFulfilled;
	private short itemNumber;

	public Requirement( String description, String source, Priority priority, Status status, boolean isFulfilled ) {
		this.description = description;
		this.source = source;
		this.priority = priority;
		this.status = status;
		this.isFulfilled = isFulfilled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource( String source ) {
		this.source = source;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority( Priority priority ) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus( Status status ) {
		this.status = status;
	}

	public boolean isFulfilled() {
		return isFulfilled;
	}

	public void setFulfilled( boolean fulfilled ) {
		isFulfilled = fulfilled;
	}

	public short getItemNumber() {
		return itemNumber;
	}

	protected void setItemNumber( short itemNumber ) {
		this.itemNumber = itemNumber;
	}
}
