
package com.ihsinformatics.is3web.model;

// Generated Jul 21, 2014 9:51:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Participant generated by hbm2java
 */
public class Participant implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3647257385338324187L;
	private Integer	participantId;
	private String	contactNo;
	private String	participantName;
	private Date	dateRegistered;
	private Boolean	isBlacklisted;
	private String	uuid;

	public Participant ()
	{
	}

	public Participant (Integer participantId, String contactNo)
	{
		this.participantId = participantId;
		this.contactNo = contactNo;
	}

	public Participant (Integer participantId, String contactNo, String participantName, Date dateRegistered, Boolean isBlacklisted, String uuid)
	{
		this.participantId = participantId;
		this.contactNo = contactNo;
		this.participantName = participantName;
		this.dateRegistered = dateRegistered;
		this.isBlacklisted = isBlacklisted;
		this.uuid = uuid;
	}

	public Integer getParticipantId ()
	{
		return this.participantId;
	}

	public void setParticipantId (Integer participantId)
	{
		this.participantId = participantId;
	}

	public String getContactNo ()
	{
		return this.contactNo;
	}

	public void setContactNo (String contactNo)
	{
		this.contactNo = contactNo;
	}

	public String getParticipantName ()
	{
		return this.participantName;
	}

	public void setParticipantName (String participantName)
	{
		this.participantName = participantName;
	}

	public Date getDateRegistered ()
	{
		return this.dateRegistered;
	}

	public void setDateRegistered (Date dateRegistered)
	{
		this.dateRegistered = dateRegistered;
	}

	public Boolean getIsBlacklisted ()
	{
		return this.isBlacklisted;
	}

	public void setIsBlacklisted (Boolean isBlacklisted)
	{
		this.isBlacklisted = isBlacklisted;
	}

	public String getUuid ()
	{
		return this.uuid;
	}

	public void setUuid (String uuid)
	{
		this.uuid = uuid;
	}

}
