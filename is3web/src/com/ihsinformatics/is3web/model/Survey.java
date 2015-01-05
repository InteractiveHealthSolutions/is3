
package com.ihsinformatics.is3web.model;

// Generated Jul 21, 2014 9:51:39 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Survey generated by hbm2java
 */
public class Survey implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1533322532049758137L;
	private Integer	surveyId;
	private String	surveyName;
	private String	surveyer;
	private Date	dateStart;
	private Date	dateExpire;
	private Boolean	isExpired;
	private String	initText;
	private String	uuid;

	public Survey ()
	{
	}

	public Survey (Integer surveyId, String surveyName, String initText)
	{
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.initText = initText;
	}

	public Survey (Integer surveyId, String surveyName, String surveyer, Date dateStart, Date dateExpire, Boolean isExpired, String initText, String uuid)
	{
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.surveyer = surveyer;
		this.dateStart = dateStart;
		this.dateExpire = dateExpire;
		this.isExpired = isExpired;
		this.initText = initText;
		this.uuid = uuid;
	}

	public Integer getSurveyId ()
	{
		return this.surveyId;
	}

	public void setSurveyId (Integer surveyId)
	{
		this.surveyId = surveyId;
	}

	public String getSurveyName ()
	{
		return this.surveyName;
	}

	public void setSurveyName (String surveyName)
	{
		this.surveyName = surveyName;
	}

	public String getSurveyer ()
	{
		return this.surveyer;
	}

	public void setSurveyer (String surveyer)
	{
		this.surveyer = surveyer;
	}

	public Date getDateStart ()
	{
		return this.dateStart;
	}

	public void setDateStart (Date dateStart)
	{
		this.dateStart = dateStart;
	}

	public Date getDateExpire ()
	{
		return this.dateExpire;
	}

	public void setDateExpire (Date dateExpire)
	{
		this.dateExpire = dateExpire;
	}

	public Boolean getIsExpired ()
	{
		return this.isExpired;
	}

	public void setIsExpired (Boolean isExpired)
	{
		this.isExpired = isExpired;
	}

	public String getInitText ()
	{
		return this.initText;
	}

	public void setInitText (String initText)
	{
		this.initText = initText;
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