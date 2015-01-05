
package com.ihsinformatics.is3web.model;

// Generated Jul 21, 2014 9:51:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Transaction generated by hbm2java
 */
public class Transaction implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -348052445808978602L;
	private Integer	transactionId;
	private int	participantId;
	private int		surveyId;
	private int		questionId;
	private String	answer;
	private Date	dateCreated;
	private Date	dateAnswered;

	public Transaction ()
	{
	}

	public Transaction (int participantId, int surveyId, int questionId)
	{
		this.participantId = participantId;
		this.surveyId = surveyId;
		this.questionId = questionId;
	}

	public Transaction (int participantId, int surveyId, int questionId, String answer, Date dateCreated, Date dateAnswered)
	{
		this.participantId = participantId;
		this.surveyId = surveyId;
		this.questionId = questionId;
		this.answer = answer;
		this.dateCreated = dateCreated;
		this.setDateAnswered (dateAnswered);
	}

	public Integer getTransactionId ()
	{
		return this.transactionId;
	}

	public void setTransactionId (Integer transactionId)
	{
		this.transactionId = transactionId;
	}

	public int getParticipantId ()
	{
		return this.participantId;
	}

	public void setParticipantId (int participantId)
	{
		this.participantId = participantId;
	}

	public int getSurveyId ()
	{
		return this.surveyId;
	}

	public void setSurveyId (int surveyId)
	{
		this.surveyId = surveyId;
	}

	public int getQuestionId ()
	{
		return this.questionId;
	}

	public void setQuestionId (int questionId)
	{
		this.questionId = questionId;
	}

	public String getAnswer ()
	{
		return this.answer;
	}

	public void setAnswer (String answer)
	{
		this.answer = answer;
	}

	public Date getDateCreated ()
	{
		return this.dateCreated;
	}

	public void setDateCreated (Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public Date getDateAnswered ()
	{
		return dateAnswered;
	}

	public void setDateAnswered (Date dateAnswered)
	{
		this.dateAnswered = dateAnswered;
	}

}
