
package com.ihsinformatics.is3web.model;

// Generated Jul 21, 2014 9:51:39 AM by Hibernate Tools 3.4.0.CR1

/**
 * AnswerId generated by hbm2java
 */
public class AnswerId implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8133166748156421867L;
	private int	surveyId;
	private int	questionId;
	private int	answerId;

	public AnswerId ()
	{
	}

	public AnswerId (int surveyId, int questionId, int answerId)
	{
		this.surveyId = surveyId;
		this.questionId = questionId;
		this.answerId = answerId;
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

	public int getAnswerId ()
	{
		return this.answerId;
	}

	public void setAnswerId (int answerId)
	{
		this.answerId = answerId;
	}

	public boolean equals (Object other)
	{
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AnswerId))
			return false;
		AnswerId castOther = (AnswerId) other;

		return (this.getSurveyId () == castOther.getSurveyId ()) && (this.getQuestionId () == castOther.getQuestionId ()) && (this.getAnswerId () == castOther.getAnswerId ());
	}

	public int hashCode ()
	{
		int result = 17;

		result = 37 * result + this.getSurveyId ();
		result = 37 * result + this.getQuestionId ();
		result = 37 * result + this.getAnswerId ();
		return result;
	}

}
