package edu.utdallas.gamePlayEngine.controller;

import javax.swing.JPanel;

import edu.utdallas.gamePlayEngine.model.adapter.ActAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.ChallengeAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.GameElementAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.SceneAdapter;
import edu.utdallas.gamePlayEngine.model.adapter.ScreenAdapter;

public class GameState {

	/*
	 * Act act; Screen screen; Scene scene; GameElement gameElement; Quiz quiz;
	 * ChallengeStructure challenge;
	 */
	ActAdapter actAdapter;
	SceneAdapter sceneAdapter;
	ScreenAdapter screenAdapter;
	GameElementAdapter gameElement;
	ChallengeAdapter challenegeAdapter;
	int nextQuestion;

	private String title;
	JPanel myMainPanel;// Zac ZHANG Added

	public ChallengeAdapter getChallenegeAdapter() {
		return challenegeAdapter;
	}

	public void setChallenegeAdapter(ChallengeAdapter challenegeAdapter) {
		this.challenegeAdapter = challenegeAdapter;
	}

	public ActAdapter getActAdapter() {
		return actAdapter;
	}

	public void setActAdapter(ActAdapter actAdapter) {
		this.actAdapter = actAdapter;
	}

	public SceneAdapter getSceneAdapter() {
		return sceneAdapter;
	}

	public void setSceneAdapter(SceneAdapter sceneAdapter) {
		this.sceneAdapter = sceneAdapter;
	}

	public ScreenAdapter getScreenAdapter() {
		return screenAdapter;
	}

	public void setScreenAdapter(ScreenAdapter screenAdapter) {
		this.screenAdapter = screenAdapter;
	}

	public GameElementAdapter getGameElementAdapter() {
		return gameElement;
	}

	public void setGameElementAdapter(GameElementAdapter gameElement) {
		this.gameElement = gameElement;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private boolean isCorrect;
	private String correctFeedBack;
	private String inCorrectFeedback;
	private boolean challengeProcessed;
	private int quizReward;
	private String promotedTitle;

	public boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public JPanel getMenuFrame() {
		return this.myMainPanel;
	}

	public GameState(JPanel myMenuPanel) {
		this.myMainPanel = myMenuPanel;
	}

	public String getCorrectFeedBack() {
		return correctFeedBack;
	}

	public void setCorrectFeedBack(String correctFeedBack) {
		this.correctFeedBack = correctFeedBack;
	}

	public String getInCorrectFeedback() {
		return inCorrectFeedback;
	}

	public void setInCorrectFeedback(String inCOrrectFeedback) {
		this.inCorrectFeedback = inCOrrectFeedback;
	}

	public boolean isChallengeProcessed() {
		return challengeProcessed;
	}

	public void setChallengeProcessed(boolean challengeProcessed) {
		this.challengeProcessed = challengeProcessed;
	}

	public int getQuizReward() {
		return quizReward;
	}

	public void setQuizReward(int quizReward) {
		this.quizReward = quizReward;
	}

	public String getPromotedTitle() {
		return promotedTitle;
	}

	public void setPromotedTitle(String promotedTitle) {
		this.promotedTitle = promotedTitle;
	}

	public int getNextQuestion() {
		return nextQuestion;
	}

	public void setNextQuestion(int nextQuestion) {
		this.nextQuestion = nextQuestion;
	}
}
