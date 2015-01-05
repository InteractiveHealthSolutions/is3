
package com.ihsinformatics.is3web.model;

// Generated Jul 21, 2014 9:51:39 AM by Hibernate Tools 3.4.0.CR1

/**
 * Answer generated by hbm2java
 */
public class Answer implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -66856810167047015L;
	private AnswerId	id;
	private String		answer;
	private String		reply;
	private Integer		nextQuestionId;
	private Boolean		resetsSurvey;
	private Boolean		endsSurvey;
	private String		uuid;

	public Answer ()
	{
	}

	public Answer (AnswerId id)
	{
		this.id = id;
	}

	public Answer (AnswerId id, String answer, String reply, Integer nextQuestionId, Boolean resetsSurvey, Boolean endsSurvey, String uuid)
	{
		this.id = id;
		this.answer = answer;
		this.setReply (reply);
		this.nextQuestionId = nextQuestionId;
		this.resetsSurvey = resetsSurvey;
		this.endsSurvey = endsSurvey;
		this.uuid = uuid;
	}

	public AnswerId getId ()
	{
		return this.id;
	}

	public void setId (AnswerId id)
	{
		this.id = id;
	}

	public String getAnswer ()
	{
		return this.answer;
	}

	public void setAnswer (String answer)
	{
		this.answer = answer;
	}

	public String getReply ()
	{
		return reply;
	}

	public void setReply (String reply)
	{
		this.reply = reply;
	}

	public Integer getNextQuestionId ()
	{
		return this.nextQuestionId;
	}

	public void setNextQuestionId (Integer nextQuestionId)
	{
		this.nextQuestionId = nextQuestionId;
	}

	public Boolean getResetsSurvey ()
	{
		return this.resetsSurvey;
	}

	public void setResetsSurvey (Boolean resetsSurvey)
	{
		this.resetsSurvey = resetsSurvey;
	}

	public Boolean getEndsSurvey ()
	{
		return this.endsSurvey;
	}

	public void setEndsSurvey (Boolean endsSurvey)
	{
		this.endsSurvey = endsSurvey;
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
