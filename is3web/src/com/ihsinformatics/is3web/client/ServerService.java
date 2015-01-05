package com.ihsinformatics.is3web.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ihsinformatics.is3web.model.Answer;
import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Participant;
import com.ihsinformatics.is3web.model.Question;
import com.ihsinformatics.is3web.model.QuestionId;
import com.ihsinformatics.is3web.model.Survey;
import com.ihsinformatics.is3web.model.Transaction;
import com.ihsinformatics.is3web.shared.Parameter;

@RemoteServiceRelativePath("smssurvey")
public interface ServerService extends RemoteService {

	//save methods
	public boolean saveSurvey (Survey survey);
	public boolean saveParticipant (Participant participant);
	public boolean saveQuestion (Question question);
	public boolean saveAnswer (Answer answer);
	public boolean saveTransaction (Transaction transaction);
	
	//update methods
	
	public boolean updateSurvey (Survey survey);
	public boolean updateParticipant (Participant participant);
	public boolean updateQuestion (Question question);
	public boolean updateAnswer (Answer answer);
	public boolean updateTransaction (Transaction transaction);
	//delete methods
	public boolean deleteSurvey (Survey survey);
	public boolean deleteParticipant (Participant participant);
	public boolean deleteQuestion (Question question);
	public boolean deleteAnswer (Answer answer);
	public boolean deleteTransaction (Transaction transaction);
	//find methods
	public List<Survey> findSurveys();
	public Survey findSurveyById (int surveyId);
	public Survey findSurveyByName (String surveyName);
	public Participant findParticipantById (int participantId);
	public Participant findParticipantByContactNo (String contactNo);
	public Question findQuestionById (QuestionId questionId);
	public Question[] findQuestionsBySurvey (Survey survey);
	public Question findLastQuestionBySurvey (Survey suvey);
	public Answer[] findAnswersBySurvey (Survey survey);
	public Answer[] findAnswersByQuestion (Question question);
	public Answer findAnswerByQuestionIdAndAnswer (QuestionId questionId, String answerText);
	public Transaction findTransactionById (int transactionId);
	public Transaction[] findTransactionsByParticipant (Participant participant);
	public Transaction findLastTransactionByParticipant (Participant participant);
	public Transaction[] findTransactionsByQuestion (QuestionId questionId);
	public Transaction[] findTransactionsByParticipantAndQuestion (Participant participant, QuestionId questionId);	
	
	List<Participant> findParticipantBySurvey(String surveyName);
	
	List<CollectionPairs> findTransactionByParticipant(int participantId);
	 List<CollectionPairs>  findTransactionByParticipantName(String name);
	 List<CollectionPairs> findTransactionByParticipantContact(String contact);
	 
	 List<CollectionPairs> findCompleteTransactionsBySurveyId(int surveyId);
	 List<CollectionPairs> findPendingTransactionBySurveyId(int surveyId);
	 List<CollectionPairs> findTransactionBySurveyId(int surveyId);
	 
	 List<CollectionPairs> findPendingTransactionsBySurveyName(String surveyName);
	 List<CollectionPairs> findCompleteTransactionsBySurveyName(String surveyName);
	 List<CollectionPairs> findIncompleteTransactionBySurveyId(int surveyId);
	 String getReportDirectory();
	 	
	 String generateReport(String fileName, Parameter[] params, boolean export);
	 String generateReportWithSubreport(String fileName,String[] subReports , Parameter[] params, boolean export);

	 
	 HashMap<String, Double>  getCompleteSurveuysPercentage();
	 HashMap<String , Double>  getIncompleteSurveuysPercentage();
	HashMap<String, Integer> getCompleteSurveuysNumber();
	HashMap<String, Integer> getIncompleteSurveuysNumber();
	HashMap<String, Integer> getSkippedSurveuysNumber();
	HashMap<String, Double> getSkippedSurveuysPercentage();
	HashMap<String, Integer> getIncompleteTimespan(int timespan);
	HashMap<String , Integer> getSkippedTimespan();
	boolean login(String user, String pass);
}
