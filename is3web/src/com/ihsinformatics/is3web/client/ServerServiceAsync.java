package com.ihsinformatics.is3web.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ihsinformatics.is3web.model.Answer;
import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Participant;
import com.ihsinformatics.is3web.model.Question;
import com.ihsinformatics.is3web.model.QuestionId;
import com.ihsinformatics.is3web.model.Survey;
import com.ihsinformatics.is3web.model.Transaction;
import com.ihsinformatics.is3web.shared.Parameter;

public interface ServerServiceAsync {

	void saveParticipant(Participant participant,
			AsyncCallback<Boolean> callback);

	void deleteAnswer(Answer answer, AsyncCallback<Boolean> callback);

	void deleteParticipant(Participant participant,
			AsyncCallback<Boolean> callback);

	void deleteQuestion(Question question, AsyncCallback<Boolean> callback);

	void deleteSurvey(Survey survey, AsyncCallback<Boolean> callback);

	void deleteTransaction(Transaction transaction,
			AsyncCallback<Boolean> callback);

	void findAnswerByQuestionIdAndAnswer(QuestionId questionId,
			String answerText, AsyncCallback<Answer> callback);

	void findAnswersByQuestion(Question question,
			AsyncCallback<Answer[]> callback);

	void findAnswersBySurvey(Survey survey, AsyncCallback<Answer[]> callback);

	void findLastQuestionBySurvey(Survey suvey, AsyncCallback<Question> callback);

	void findLastTransactionByParticipant(Participant participant,
			AsyncCallback<Transaction> callback);

	void findParticipantByContactNo(String contactNo,
			AsyncCallback<Participant> callback);

	void findParticipantById(int participantId,
			AsyncCallback<Participant> callback);

	void findQuestionById(QuestionId questionId,
			AsyncCallback<Question> callback);

	void findQuestionsBySurvey(Survey survey, AsyncCallback<Question[]> callback);

	void findSurveyById(int surveyId, AsyncCallback<Survey> callback);

	void findSurveyByName(String surveyName, AsyncCallback<Survey> callback);

	void findTransactionById(int transactionId,
			AsyncCallback<Transaction> callback);

	void findTransactionsByParticipant(Participant participant,
			AsyncCallback<Transaction[]> callback);

	void findTransactionsByParticipantAndQuestion(Participant participant,
			QuestionId questionId, AsyncCallback<Transaction[]> callback);

	void findTransactionsByQuestion(QuestionId questionId,
			AsyncCallback<Transaction[]> callback);

	void saveAnswer(Answer answer, AsyncCallback<Boolean> callback);

	void saveQuestion(Question question, AsyncCallback<Boolean> callback);

	void saveSurvey(Survey survey, AsyncCallback<Boolean> callback);

	void saveTransaction(Transaction transaction,
			AsyncCallback<Boolean> callback);

	void updateAnswer(Answer answer, AsyncCallback<Boolean> callback);

	void updateParticipant(Participant participant,
			AsyncCallback<Boolean> callback);

	void updateQuestion(Question question, AsyncCallback<Boolean> callback);

	void updateSurvey(Survey survey, AsyncCallback<Boolean> callback);

	void updateTransaction(Transaction transaction,
			AsyncCallback<Boolean> callback);

	void findSurveys(AsyncCallback<List<Survey>> callback);

	void findParticipantBySurvey(String surveyName,
			AsyncCallback<List<Participant>> callback);

	void findPendingTransactionBySurveyId(int surveyId,
			AsyncCallback<List<CollectionPairs>> callback);

	void findCompleteTransactionsBySurveyId(int surveyId,
			AsyncCallback<List<CollectionPairs>> callback);

	void findTransactionByParticipant(int participantId,
			AsyncCallback<List<CollectionPairs>> callback);

	void findTransactionByParticipantContact(String contact,
			AsyncCallback<List<CollectionPairs>> callback);

	void findTransactionByParticipantName(String name,
			AsyncCallback<List<CollectionPairs>> callback);

	void findTransactionBySurveyId(int surveyId,
			AsyncCallback<List<CollectionPairs>> callback);

	void findCompleteTransactionsBySurveyName(String surveyName,
			AsyncCallback<List<CollectionPairs>> callback);

	void findPendingTransactionsBySurveyName(String surveyName,
			AsyncCallback<List<CollectionPairs>> callback);

	void findIncompleteTransactionBySurveyId(int surveyId,
			AsyncCallback<List<CollectionPairs>> callback);

	void getReportDirectory(AsyncCallback<String> callback);

	void generateReport(String fileName, Parameter[] params, boolean export,
			AsyncCallback<String> callback);

	void generateReportWithSubreport(String fileName, String[] subReports,
			Parameter[] params, boolean export, AsyncCallback<String> callback);

	void getIncompleteSurveuysPercentage(
			AsyncCallback<HashMap<String, Double>> callback);

	void getCompleteSurveuysPercentage(
			AsyncCallback<HashMap<String, Double>> callback);

	void getCompleteSurveuysNumber(
			AsyncCallback<HashMap<String, Integer>> asyncCallback);

	void getIncompleteSurveuysNumber(
			AsyncCallback<HashMap<String, Integer>> asyncCallback);

	void getSkippedSurveuysNumber(
			AsyncCallback<HashMap<String, Integer>> asyncCallback);

	void getSkippedSurveuysPercentage(
			AsyncCallback<HashMap<String, Double>> asyncCallback);

	void getIncompleteTimespan(int timespan,
			AsyncCallback<HashMap<String, Integer>> callback);

	void getSkippedTimespan(AsyncCallback<HashMap<String, Integer>> callback);

	void login(String user, String pass, AsyncCallback<Boolean> callback);



}
