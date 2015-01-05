package com.ihsinformatics.is3web.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SQLQuery;

import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Participant;
import com.ihsinformatics.is3web.model.Question;
import com.ihsinformatics.is3web.model.Survey;
import com.ihsinformatics.is3web.model.Transaction;
import com.ihsinformatics.is3web.server.util.HibernateUtil;

public class JspServlet extends HttpServlet {
	
	//private ServerServiceAsync serverService=GWT.create(ServerService.class);
	//PrintStream out=new Prin;
PrintWriter out;
	@Override
	protected void doGet(HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
	
		if(req.getParameter("action").equalsIgnoreCase("survey"))
		{
			if(req.getParameter("searchtype").equalsIgnoreCase("complete")){
				
				int id=Integer.parseInt(req.getParameter("searchtext"));
				//req.setAttribute("results", getCompleteTransactionTable(findCompleteTransactionsBySurveyId(id)));
				//req.getRequestDispatcher("NewFile.jsp").forward(req, resp);
			out=resp.getWriter();
			
			out.print(getCompleteTransactionTable(findCompleteTransactionsBySurveyId(id)));
				out.flush();
				out.close();

				
			}else if(req.getParameter("searchtype").equalsIgnoreCase("incomplete")){
				
				int id=Integer.parseInt(req.getParameter("searchtext"));
				//req.setAttribute("results", getCompleteTransactionTable(findCompleteTransactionsBySurveyId(id)));
				//req.getRequestDispatcher("NewFile.jsp").forward(req, resp);
			out=resp.getWriter();
			
			out.print(getCompleteTransactionTable(findIncompleteTransactionBySurveyId(id)));
				out.flush();
				out.close();
			}
			else if(req.getParameter("searchtype").equalsIgnoreCase("pending")){
				
				
			}
			
			int id=Integer.parseInt(req.getParameter("searchtext"));
			//req.setAttribute("results", getCompleteTransactionTable(findCompleteTransactionsBySurveyId(id)));
			//req.getRequestDispatcher("NewFile.jsp").forward(req, resp);
		out=resp.getWriter();
		
		out.print(getCompleteTransactionTable(findCompleteTransactionsBySurveyId(id)));
			out.flush();
			out.close();
			//resp.
		}else if(req.getParameter("action").equalsIgnoreCase("participant")){
			System.out.println("participant");
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	public String getCompleteTransactionTable(List<CollectionPairs> list)
	{    StringBuilder toReturn=new StringBuilder();

	toReturn.append("<Table  id='dataT' class='display' cellspacing='0' width='100%' onload='initDataTable();'><thead> <tr> <th>participant id</th><th> question</th><th> date</th></tr></thead>");
	toReturn.append(" <thead><tr><th>Contact No</th><th>Question</th><th>Date Answered</th></tr> </thead>"+
 "<tfoot><tr><th>Contact No</th><th>Question</th><th>Date Answered</th></tr></tfoot>");
                
            
        
	toReturn.append("<tBody>");
	for(CollectionPairs p: list)
	{
		toReturn.append("<tr>");
		toReturn.append("<td>"+p.getParticipant().getContactNo()+"</td>");
		toReturn.append("<td>"+p.getQuestion().getQuestionText()+"</td>");
		toReturn.append("<td>"+p.getTransaction().getDateAnswered()+"</td>");
		toReturn.append("</tr>");
	}
	toReturn.append("</tbody>");
//	toReturn.append("<tfoot></tfoot>");
	
	toReturn.append("</table>");
	//toReturn.append("<Script >initDataTable(); </Script>");
	return toReturn.toString();
	}
	
	
	public List<CollectionPairs> findCompleteTransactionsBySurveyId(int surveyId)
	{
		List<CollectionPairs> list=new ArrayList<CollectionPairs>();
		String query="select {transaction.*} ,{survey.*},{participant.*},{question.*}  from transaction  inner join survey    using (survey_id) inner join participant  using(participant_id) "+
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
	}//end of method
	
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
	
	
 
}
