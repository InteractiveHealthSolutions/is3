package com.ihsinformatics.is3web.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.model.Answer;
import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Participant;
import com.ihsinformatics.is3web.model.Question;
import com.ihsinformatics.is3web.model.QuestionId;
import com.ihsinformatics.is3web.model.Survey;
import com.ihsinformatics.is3web.model.Transaction;
import com.ihsinformatics.is3web.server.util.HibernateUtil;
import com.ihsinformatics.is3web.server.util.ReportUtil;
import com.ihsinformatics.is3web.shared.IRZ;
import com.ihsinformatics.is3web.shared.Parameter;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServerServiceImpl extends RemoteServiceServlet implements ServerService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String reportPath="";
    public ServerServiceImpl()
    {
	String currentDirectory = System.getProperty("user.dir");
	System.out.println("Current directory:" + currentDirectory);
	reportPath=currentDirectory+"\\reports\\";
	IRZ.setResourcesPath( reportPath);

    }
    
    // for user authentication 
	@Override
	public boolean login(String user, String pass) {
		boolean result =false;
		if(user.equalsIgnoreCase("admin"))
		{
			if(pass.equalsIgnoreCase("admin"))
			{
				
				result=true;
			}
			
		}
		return result;
	}
	public String getReportDirectory()
	{
		//
		
				
		return reportPath;

		
	}
	
	public HashMap<String, Double>  getIncompleteSurveuysPercentage()
	{ HashMap<String , Double> map=new HashMap<String , Double>();
		
	int total=0;
	for(Survey s:findSurveys())
	{
	List<CollectionPairs> list=findIncompleteTransactionBySurveyId(s.getSurveyId());
	
	total=total+list.size();
	
	}	
	for(Survey s: findSurveys())
	{
		List<CollectionPairs> list=findIncompleteTransactionBySurveyId(s.getSurveyId());
		
	 double d=(list.size()*100)/total;
		map.put(s.getSurveyName(), d);
	}
	
	return map;
	}//end of method
	
	public HashMap<String, Double>  getCompleteSurveuysPercentage()
	{ HashMap<String , Double> map=new HashMap<String , Double>();
		
	int total=0;
	for(Survey s:findSurveys())
	{
	List<CollectionPairs> list=findCompleteTransactionsBySurveyId(s.getSurveyId());
	
	total=total+list.size();
	
	}	
	for(Survey s: findSurveys())
	{
		List<CollectionPairs> list=findCompleteTransactionsBySurveyId(s.getSurveyId());
		
	 double d=(list.size()*100)/total;
		map.put(s.getSurveyName(), d);
	}
	
	return map;
	}//end of method
	
	
	
	/* Delete methods */
	public boolean saveSurvey (Survey survey)
	{
		if (survey.getDateExpire ().before (survey.getDateStart ()))
		{	return false;}
		return HibernateUtil.util.save (survey);
	}

	public boolean saveParticipant (Participant participant)
	{
		return HibernateUtil.util.save (participant);
	}

	public boolean saveQuestion (Question question)
	{
		Object id = HibernateUtil.util.selectObject ("select ifnull(max(question_id), 1) from question where survey_id = " + question.getId ().getSurveyId ());
		QuestionId questionId = new QuestionId (question.getId ().getSurveyId (), Integer.parseInt (id.toString ()));
		question.setId (questionId);
		return HibernateUtil.util.save (question);
	}
	
	

	public boolean saveAnswer (Answer answer)
	{
		return HibernateUtil.util.save (answer);
	}

	public boolean saveTransaction (Transaction transaction)
	{
		return HibernateUtil.util.save (transaction);
	}

	/* Update methods */
	public boolean updateSurvey (Survey survey)
	{
		survey.setIsExpired (survey.getDateExpire ().after (new Date ()));
		return HibernateUtil.util.update (survey);
	}

	public boolean updateParticipant (Participant participant)
	{
		return HibernateUtil.util.update (participant);
	}

	public boolean updateQuestion (Question question)
	{
		return HibernateUtil.util.update (question);
	}

	public boolean updateAnswer (Answer answer)
	{
		return HibernateUtil.util.update (answer);
	}

	public boolean updateTransaction (Transaction transaction)
	{
		if (transaction.getDateAnswered () == null)
			transaction.setDateAnswered (new Date ());
		if (transaction.getAnswer () == null)
			transaction.setAnswer ("");
		return HibernateUtil.util.update (transaction);
	}

	/* Delete methods */
	public boolean deleteSurvey (Survey survey)
	{
		return HibernateUtil.util.delete (survey);
	}

	public boolean deleteParticipant (Participant participant)
	{
		return HibernateUtil.util.delete (participant);
	}

	public boolean deleteQuestion (Question question)
	{
		return HibernateUtil.util.delete (question);
	}

	public boolean deleteAnswer (Answer answer)
	{
		return HibernateUtil.util.delete (answer);
	}

	public boolean deleteTransaction (Transaction transaction)
	{
		return HibernateUtil.util.delete (transaction);
	}

	/* Find methods */
	public Survey findSurveyById (int surveyId)
	{
		Survey survey = (Survey) HibernateUtil.util.findObject ("from Survey where surveyId=" + surveyId);
		return survey;
	}

	public Survey findSurveyByName (String surveyName)
	{
		Survey survey = (Survey) HibernateUtil.util.findObject ("from Survey where surveyName='" + surveyName + "'");
		return survey;
	}

	public Participant findParticipantById (int participantId)
	{
		Participant participant = (Participant) HibernateUtil.util.findObject ("from Participant where participantId=" + participantId);
	
		return participant;
	}

	public Participant findParticipantByContactNo (String contactNo)
	{
		Participant participant = (Participant) HibernateUtil.util.findObject ("from Participant where contact_no='" + contactNo+"'" );
		return participant;
	}
	
	public List<Participant> findParticipantBySurvey(String surveyName)
	{
		Survey survey=findSurveyByName(surveyName);
		System.out.println(survey.getSurveyName());
		String  query="Select  distinct participant.participant_id  , participant.participant_name  ,participant.contact_no ,participant.date_registered ,participant.is_blacklisted , participant.uuid  from participant inner join transaction on  transaction.participant_id=participant.participant_id where transaction.survey_id="+survey.getSurveyId()+" ;";
		Object[][] ob=HibernateUtil.util.selectData(query); 
		//Participant[] participantList=(Participant[])
		List<Participant> list=new ArrayList<Participant>();
		for(int i=0;i<ob.length;i++)
		{
			//System.out.println(((Participant)ob[i]).getParticipantName());
			Participant participant=new Participant();
			participant.setParticipantId((Integer)ob[i][0]);
			participant.setParticipantName((String)ob[i][1]);
			participant.setContactNo((String) ob[i][2]);
			participant.setDateRegistered((Date)ob[i][3]);
			participant.setIsBlacklisted((Boolean)ob[i][4]);
			participant.setUuid((String)ob[i][5]);
			list.add(participant);
			
		}
		
		return list;
	}

	public Question findQuestionById (QuestionId questionId)
	{
		Question question = (Question) HibernateUtil.util.findObject ("from Question where id.surveyId=" + questionId.getSurveyId () + " and id.questionId=" + questionId.getQuestionId ());
		return question;
	}

	public Question[] findQuestionsBySurvey (Survey survey)
	{
		Object[] objs = HibernateUtil.util.findObjects ("from Question where survey_id=" + survey.getSurveyId ());
		Question[] questions = new Question[objs.length];
		for (int i = 0; i < objs.length; i++)
		{
			questions[i] = (Question) objs[i];
		}
		return questions;
	}
	
	public Question findLastQuestionBySurvey (Survey suvey)
	{
		Object id=HibernateUtil.util.selectObject("select question_id from answer where survey_id="+suvey.getSurveyId()+" and ends_survey=1");
		Object[] objs=HibernateUtil.util.findObjects("from Question where question_id="+id);
		
		//System.out.println(objs[0]. instanceof Question);
		Question question=(Question)objs[objs.length-1];
		return question;
	}

	public Answer[] findAnswersBySurvey (Survey survey)
	{
		Object[] objs = HibernateUtil.util.findObjects ("from Answer where answerId.id.surveyId=" + survey.getSurveyId ());
		Answer[] answers = new Answer[objs.length];
		for (int i = 0; i < objs.length; i++)
		{
			answers[i] = (Answer) objs[i];
		}
		return answers;
	}

	public Answer[] findAnswersByQuestion (Question question)
	{
		Object[] objs = HibernateUtil.util.findObjects ("from Answer where id.surveyId=" + question.getId ().getSurveyId () + " and id.questionId="
				+ question.getId ().getQuestionId ());
		Answer[] answers = new Answer[objs.length];
		for (int i = 0; i < objs.length; i++)
		{
			answers[i] = (Answer) objs[i];
		}
		return answers;
	}

	public Answer findAnswerByQuestionIdAndAnswer (QuestionId questionId, String answerText)
	{
		Answer answer = (Answer) HibernateUtil.util.findObject ("from Answer where id.surveyId=" + questionId.getSurveyId () + " and id.questionId=" + questionId.getQuestionId () + " and answer='"
				+ answerText + "'");
		return answer;
	}

	public Transaction findTransactionById (int transactionId)
	{
		Transaction transaction = (Transaction) HibernateUtil.util.findObject ("from Transaction where transactionId=" + transactionId);
		return transaction;
	}

	public Transaction[] findTransactionsByParticipant (Participant participant)
	{
		Object[] objs = HibernateUtil.util.findObjects ("from Transaction where participantId=" + participant.getParticipantId ());
		Transaction[] transactions = new Transaction[objs.length];
		for (int i = 0; i < objs.length; i++)
			transactions[i] = (Transaction) objs[i];
		return transactions;
	}//end of method
	
	public Transaction[] findCompleteTransactionsByParticipantId(int participantId)
	{
		
		
	
		return null;
	}
	
	public List<CollectionPairs> findTransactionByParticipantContact(String contact)
	{
	Participant participant=	findParticipantByContactNo(contact);
		if(participant==null) {return null;}
		return findTransactionByParticipant(participant.getParticipantId());
	}//end of method
	
	public List<CollectionPairs>  findTransactionByParticipantName(String name)
	{
		Participant participant =(Participant)HibernateUtil.util.findObject("from Participant where participant_name='"+name+"'");
		if(participant==null) {return null;}
		return findTransactionByParticipant(participant.getParticipantId());
	}//end of method
	
	public List<CollectionPairs> findTransactionByParticipant(int participantId)
	{
	List<CollectionPairs> list=new ArrayList<CollectionPairs>();
		String query="select {transaction.*} ,{survey.*},{participant.*},{question.*} from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
					"inner join question   using (question_id)   where participant_id="+participantId+"  order by survey.survey_id";
		SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
		q.addEntity("transaction",Transaction.class);
		q.addEntity("survey",Survey.class);
		q.addEntity("participant",Participant.class);
	
		q.addEntity("question",Question.class);
		List l=q.list();
		//Object[][] o=q.list().to
		for(int i=0;i<l.size();i++)
		{
			CollectionPairs c=new CollectionPairs();
			c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
			//System.out.println( instanceof Transaction);
			
			c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
			c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
		
			c.setQuestion((Question)((Object[])(l.get(i)))[3] );
			list.add(c);
		}// end of for
	
	return list;
	}//end of method

	public Transaction findLastTransactionByParticipant (Participant participant)
	{
		Transaction transaction = (Transaction) HibernateUtil.util.findObject ("from Transaction where participantId=" + participant.getParticipantId () + " and dateCreated = (select max(dateCreated) from Transaction where participantId=" + participant.getParticipantId () + ")");
		return transaction;
	}
	public List<CollectionPairs> findCompleteTransactionsBySurveyName(String surveyName)
	{
		Survey survey=findSurveyByName(surveyName);
		return findCompleteTransactionsBySurveyId(survey.getSurveyId());
	}//end of the method
	
	public List<CollectionPairs> findPendingTransactionsBySurveyName(String surveyName)
	{
		Survey survey=findSurveyByName(surveyName);
		return findPendingTransactionBySurveyId(survey.getSurveyId());
	}//end of the method
	
	
	
	public List<CollectionPairs> findCompleteTransactionsBySurveyId(int surveyId)
	{
		List<CollectionPairs> list=new ArrayList<CollectionPairs>();
		String query="select distinct {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
				"inner join question  on question.question_id=transaction.question_id    and question.survey_id="+surveyId+  
				" inner join answer on answer.survey_id="+surveyId+" and answer.question_id=transaction.question_id"+
				"  where  transaction.survey_id="+surveyId+" and transaction.date_answered is not null " +
				"	 and  transaction.answer is not null   and answer.ends_survey=1 " +
				" order by participant.participant_id ";
				
		
		//String hql="from Transaction t inner join Survey s inner join Question q inner join Answer a where t.survey_id="+surveyId;
				
//				"select {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "
//	+"inner join question   using (question_id)   where transaction.survey_id="+surveyId+" and date_answered is not null "
//	
//	+"	order by participant.participant_id ";
		//+"	having count(question_id)=(select count(question_id) from question where survey_id="+surveyId+");";
		
//		Query query1 = HibernateUtil.util.getSession().createQuery(hql);
//		
//		List l=query1.list();
//		for (Iterator it = query1.iterate(); it.hasNext();) {
//			Object[] row = (Object[]) it.next();
//			System.out.print(row[0]);
//			System.out.print("\t\t" + row[1]);
//			System.out.print("\t\t" + row[2]);
//			System.out.print("\t" + row[3]);
//			System.out.print("\t" + row[4]);
//			System.out.print("\t" + row[5]);
//			System.out.println();
//			}
		
		SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
		
		q.addEntity("transaction",Transaction.class);
		q.addEntity("survey",Survey.class);
		q.addEntity("participant",Participant.class);
	
		q.addEntity("question",Question.class);
	//	q.addEntity("answer",Question.class);
		List l=q.list();
		
		//Object[][] o=q.list().to
		for(int i=0;i<l.size();i++)
		{
			CollectionPairs c=new CollectionPairs();
			c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
			//System.out.println( instanceof Transaction);
			
			c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
			c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
		
			c.setQuestion((Question)((Object[])(l.get(i)))[3] );
			//c.setAnswer((Answer)((Object[])(l.get(i)))[4]);
			list.add(c);
		}// end of for
		
		
	
	
	return list;
	}	//end of method

	//select *  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) 
//	inner join question   using (question_id)   where transaction.survey_id=1 and date_answered is not null 
	//		group by participant.participant_id
		//	having count(question_id)=(select count(question_id) from question where survey_id=1)

	
	public List<CollectionPairs> findIncompleteTransactionBySurveyId(int surveyId)
	{
		List<CollectionPairs> list=new ArrayList<CollectionPairs>();
		String query="select {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "
	+"inner join question   using (question_id)   where transaction.survey_id="+surveyId+"  "
		+"	group by participant.participant_id "
		+"	having count(question_id)<(select count(question_id) from question where survey_id="+surveyId+");";
		
		
		SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
		q.addEntity("transaction",Transaction.class);
		q.addEntity("survey",Survey.class);
		q.addEntity("participant",Participant.class);
	
		q.addEntity("question",Question.class);
		List l=q.list();
		
		//Object[][] o=q.list().to
		for(int i=0;i<l.size();i++)
		{
			CollectionPairs c=new CollectionPairs();
			c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
			//System.out.println( instanceof Transaction);
			
			c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
			c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
		
			c.setQuestion((Question)((Object[])(l.get(i)))[3] );
			list.add(c);
		}// end of for
	
	return list;
		
	}//end of method
	
	public List<CollectionPairs> findPendingTransactionBySurveyId(int surveyId)
	{

		List<CollectionPairs> list=new ArrayList<CollectionPairs>();
		String query="select {transaction.*} ,{survey.*},{participant.*},{question.*} from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
					"inner join question   using (question_id)   where survey.survey_id="+surveyId+"  and transaction.date_answered is null order by participant.participant_id";
		SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
		q.addEntity("transaction",Transaction.class);
		q.addEntity("survey",Survey.class);
		q.addEntity("participant",Participant.class);
	
		q.addEntity("question",Question.class);
		List l=q.list();
		
		//Object[][] o=q.list().to
		for(int i=0;i<l.size();i++)
		{
			CollectionPairs c=new CollectionPairs();
			c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
			//System.out.println( instanceof Transaction);
			
			c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
			c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
		
			c.setQuestion((Question)((Object[])(l.get(i)))[3] );
			list.add(c);
		}// end of for
	
	return list;
	}//end of method
	
	public List<CollectionPairs> findTransactionBySurveyId(int surveyId)
	{
		List<CollectionPairs> list=new ArrayList<CollectionPairs>();
		String query="select {transaction.*} ,{survey.*},{participant.*},{question.*} from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
					"inner join question   using (question_id)   where survey.survey_id="+surveyId+"  order by participant.participant_id";
		SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
		q.addEntity("transaction",Transaction.class);
		q.addEntity("survey",Survey.class);
		q.addEntity("participant",Participant.class);
	
		q.addEntity("question",Question.class);
		List l=q.list();
		//Object[][] o=q.list().to
		for(int i=0;i<l.size();i++)
		{
			CollectionPairs c=new CollectionPairs();
			c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
			//System.out.println( instanceof Transaction);
			
			c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
			c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
		
			c.setQuestion((Question)((Object[])(l.get(i)))[3] );
			list.add(c);
		}// end of for
	
	return list;
		
	}//end of method

	public Transaction[] findTransactionsByQuestion (QuestionId questionId)
	{
		Object[] objs = HibernateUtil.util.findObjects ("from Transaction where surveyId=" + questionId.getSurveyId () + " and questionId=" + questionId.getQuestionId ());
		Transaction[] transactions = new Transaction[objs.length];
		for (int i = 0; i < objs.length; i++)
			transactions[i] = (Transaction) objs[i];
		return transactions;
	}//end of method

	public Transaction[] findTransactionsByParticipantAndQuestion (Participant participant, QuestionId questionId)
	{
		Object[] objs = HibernateUtil.util.findObjects ("from Transaction where participantId = " + participant.getParticipantId () + " and surveyId=" + questionId.getSurveyId () + " and questionId="
				+ questionId.getQuestionId ());
		Transaction[] transactions = new Transaction[objs.length];
		for (int i = 0; i < objs.length; i++)
			transactions[i] = (Transaction) objs[i];
		return transactions;
	}//end of method

	@Override
	public List<Survey> findSurveys() {
		Object[] objs=HibernateUtil.util.findObjects("FROM Survey");
		//Survey[] surveys=new Survey[objs.length];
		List<Survey> list=new ArrayList<Survey>();
		for(int i=0;i<objs.length;i++)
		{
			list.add((Survey) objs[i]);
		}
		return list;
	}//end of method

	  public String generateCSVfromQuery(String database, String query)
	    {
		return ReportUtil.generateCSVfromQuery(database, query, ',');
	    }//end of method

	    /**
	     * Generate report on server side and return the path it was created to
	     * 
	     * @param Path
	     *            of report as String Report parameters as Parameter[] Report to
	     *            be exported in csv format as Boolean
	     * @return String
	     */
	    public String generateReport(String fileName, Parameter[] params, boolean export)
	    {
		return ReportUtil.generateReport(fileName, params, export);
	    }
		@Override
		public String generateReportWithSubreport(String fileName,
				String[] subReports, Parameter[] params, boolean export) {
			// TODO Auto-generated method stub
			return ReportUtil.generateReportWithSubreport(fileName, subReports, params, export);
		}//end of method
		
		
		@Override
		public HashMap<String, Integer> getCompleteSurveuysNumber() {
			HashMap<String , Integer> map=new HashMap<String , Integer>();
			
			int total=0;
			for(Survey s:findSurveys())
			{
			List<CollectionPairs> list=findCompleteTransactionsBySurveyId(s.getSurveyId());
			
			total=total+list.size();
			
			}	
			for(Survey s: findSurveys())
			{
				List<CollectionPairs> list=findCompleteTransactionsBySurveyId(s.getSurveyId());
				
		//	 double d=(list.size()*100)/total;
				map.put(s.getSurveyName(), list.size());
			}
			
			return map;
		}//end of method
		
		
		@Override
		public HashMap<String, Integer> getIncompleteSurveuysNumber() {
			 HashMap<String , Integer> map=new HashMap<String ,Integer>();
				
				int total=0;
				for(Survey s:findSurveys())
				{
				List<CollectionPairs> list=findIncompleteTransactionBySurveyId(s.getSurveyId());
				
				total=total+list.size();
				
				}	
				for(Survey s: findSurveys())
				{
					List<CollectionPairs> list=findIncompleteTransactionBySurveyId(s.getSurveyId());
					
			//	 double d=(list.size()*100)/total;
					map.put(s.getSurveyName(), list.size());
				}
				
				return map;
		}//end of method
		@Override
		public HashMap<String, Integer> getSkippedSurveuysNumber() {
	 HashMap<String , Integer> map=new HashMap<String ,Integer>();
				
		
				for(Survey s: findSurveys())
				{
					List<CollectionPairs> list=skippedSurveys(s.getSurveyId());
					
			//	 double d=(list.size()*100)/total;
					map.put(s.getSurveyName(), list.size());
				}
				
				return map;
			
		}
		
		private List<CollectionPairs> skippedSurveys(int surveyId)
		{
			List<CollectionPairs> list=new ArrayList<CollectionPairs>();
			String query="select  {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
					"inner join question  on question.question_id=transaction.question_id    and question.survey_id="+surveyId+  
					" inner join answer on answer.survey_id="+surveyId+" and answer.question_id=transaction.question_id"+
					"  where  transaction.survey_id="+surveyId+" and transaction.answer='X' or transaction.answer='x' " +
					"	  " +
					" order by participant.participant_id ";
					
			

			
			SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
			
			q.addEntity("transaction",Transaction.class);
			q.addEntity("survey",Survey.class);
			q.addEntity("participant",Participant.class);
		
			q.addEntity("question",Question.class);
		
			List l=q.list();
			
	
			for(int i=0;i<l.size();i++)
			{
				CollectionPairs c=new CollectionPairs();
				c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
				//System.out.println( instanceof Transaction);
				
				c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
				c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
			
				c.setQuestion((Question)((Object[])(l.get(i)))[3] );
				//c.setAnswer((Answer)((Object[])(l.get(i)))[4]);
				list.add(c);
			}// end of for
			
			
			return list;
		}//end of method
		
		
		private List<CollectionPairs> skippedSurveysTimespan(int surveyId)
		{
			List<CollectionPairs> list=new ArrayList<CollectionPairs>();
			String query="select  {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
					"inner join question  on question.question_id=transaction.question_id    and question.survey_id="+surveyId+  
					" inner join answer on answer.survey_id="+surveyId+" and answer.question_id=transaction.question_id "+
					"  where  transaction.survey_id="+surveyId+"  and  transaction.answer='X' or transaction.answer='x'  " +
					"	  " +
					" group by participant.participant_id "+
					"having count(transaction.question_id)<(select count(question_id) from question where survey_id="+surveyId+");";
					
			

			
			SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
			
			q.addEntity("transaction",Transaction.class);
			q.addEntity("survey",Survey.class);
			q.addEntity("participant",Participant.class);
		
			q.addEntity("question",Question.class);
		
			List l=q.list();
			
	
			for(int i=0;i<l.size();i++)
			{
				CollectionPairs c=new CollectionPairs();
				c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
				//System.out.println( instanceof Transaction);
				
				c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
				c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
			
				c.setQuestion((Question)((Object[])(l.get(i)))[3] );
				//c.setAnswer((Answer)((Object[])(l.get(i)))[4]);
				list.add(c);
			}// end of for
			
			
			return list;
		}//end of method
		
		
		
		
		
		@Override
		public HashMap<String, Double> getSkippedSurveuysPercentage() {
			HashMap<String , Double> map=new HashMap<String , Double>();
			
			int total=0;
			for(Survey s:findSurveys())
			{
			List<CollectionPairs> list=skippedSurveys(s.getSurveyId());
			
			total=total+list.size();
			
			}	
			for(Survey s: findSurveys())
			{
				List<CollectionPairs> list=skippedSurveys(s.getSurveyId());
				
				if(total==0)
				{
					
					map.put(s.getSurveyName(), 0.0);
				}
				else{
			 double d=(list.size()*100)/total;
			 System.out.println(d);
				map.put(s.getSurveyName(), d);
				}
				}
			
			return map;	
		}//end of method

		
		private List<CollectionPairs> findIncompleteTimespan(int surveyId,int timespan)
		{
			List<CollectionPairs> list=new ArrayList<CollectionPairs>();
			String query="select {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "
		+"inner join question   using (question_id)   where transaction.survey_id="+surveyId+"  and transaction.date_answered is null and TIMESTAMPDIFF(hour,transaction.date_created, now()) >"+timespan
			+"	group by participant.participant_id "
			+"	having count(question_id)<(select count(question_id) from question where survey_id="+surveyId+");";
			
			
			SQLQuery q = HibernateUtil.util.getSession().createSQLQuery(query);
			q.addEntity("transaction",Transaction.class);
			q.addEntity("survey",Survey.class);
			q.addEntity("participant",Participant.class);
		
			q.addEntity("question",Question.class);
			List l=q.list();
			
			//Object[][] o=q.list().to
			for(int i=0;i<l.size();i++)
			{
				CollectionPairs c=new CollectionPairs();
				c.setTransaction((Transaction)((Object[])(l.get(i)))[0]);
				//System.out.println( instanceof Transaction);
				
				c.setSurvey((Survey)((Object[])(l.get(i)))[1]);
				c.setParticipant((Participant)((Object[])(l.get(i)))[2]);
			
				c.setQuestion((Question)((Object[])(l.get(i)))[3] );
				list.add(c);
			}// end of for
		
		return list;
			
		}//end of method
		
		private long countQuestionBySurvey(int surveyId)
		{long result=0;
		//if((HibernateUtil.util.findObject("select count(questionId) from question where surveyId="+surveyId))!=null){
			result=(long) HibernateUtil.util.findObject("SELECT COUNT(q.id.questionId) FROM Question q WHERE q.id.surveyId="+surveyId);
		//}
			return result;
		}//end of method
		
		public HashMap<String , Integer> getSkippedTimespan()
		{ HashMap<String,Integer> map =new HashMap<String,Integer>();
		
		List<Survey> list=findSurveys();
		
		for(int i=0;i<list.size()-1;i++){
			 long total=0;
			 long iResult=countQuestionBySurvey(list.get(i).getSurveyId());
			 int  iPending=skippedSurveysTimespan(list.get(i).getSurveyId()).size();
			for(int j=1;j<list.size();j++)
			{
			
			
			long jResult=countQuestionBySurvey(list.get(j).getSurveyId());
			
			
			int  jPending=skippedSurveysTimespan(list.get(j).getSurveyId()).size();
			
			System.out.println ("IResult : "+iPending);	;
			System.out.println ("JResult : "+jPending);	;
			if(iResult==jResult)
				{
					if(i==0 || j==1)
					{
					total=total+iPending+jPending;
					}
					else
					{
						total=total+jResult;
					}
				}
			
				
				
			}
			if(total==0)
			{
				total=iPending;
			}
			map.put(iResult+"", (int)total);
		}
	
		
			
		return map;	
		}
		
		
		
		public HashMap<String, Integer> getIncompleteTimespan(int timespan)
		{ HashMap<String,Integer> map =new HashMap<String,Integer>();
			List<Survey> list=findSurveys();
		
			for(int i=0;i<list.size()-1;i++){
				long total=0;
				long iResult=countQuestionBySurvey(list.get(i).getSurveyId());
				int  iPending=findIncompleteTimespan(list.get(i).getSurveyId(),timespan).size();
				for(int j=1;j<list.size();j++)
				{ 
				
			
				long jResult=countQuestionBySurvey(list.get(j).getSurveyId());
				
				//int  iPending=findIncompleteTimespan(list.get(i).getSurveyId(),timespan).size();
				int  jPending=findIncompleteTimespan(list.get(j).getSurveyId(),timespan).size();
				
				System.out.println ("JResult : "+jResult);	;
				if(iResult==jResult)
					{
						if(i==0 || j==1)
						{
						total=total+iPending+jPending;
						}
						else
						{
							total=total+jResult;
						}
					}
					
					//map.put(iResult+"", (int)total);
				}
				if(total==0)
				{
					total=iPending;
				}
				map.put(iResult+"", (int)total);
			}
		
		return map;	
		}
		
		
}
