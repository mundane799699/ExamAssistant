package com.mundane.examassistant.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author : mundane
 * @time : 2017/4/17 13:13
 * @description :
 * @file : Question.java
 */

@Entity
public class Question {
	@Id(autoincrement = true)
	private Long id;
	private String course;// 近代史, 思修, 马克思, 毛概下
	private String type;//	单选一, 单选二, 多选一, 多选二
	@Unique
	private String question;//	问题的题目描述
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String optionE;
	private String answer;
    private boolean isShowAnswer;// 是否显示正确答案, 开挂模式下显示, 答题模式下不显示
	private boolean isCollected;//	是否被收藏了
	private boolean isAnsweredWrong;//	是否被收录到错题集中
	private boolean haveBeenAnswered;//	该问题是否已经被回答过
	private boolean isOptionACorrect;	//	A选项是否是正确答案
	private boolean isOptionBCorrect;	//	B选项是否是正确答案
	private boolean isOptionCCorrect;	//	C选项是否是正确答案
	private boolean isOptionDCorrect;	//	D选项是否是正确答案
	private boolean isOptionECorrect;	//	E选项是否是正确答案

	//	选项的显示状态
	// 0:默认显示状态, 图标灰色字母, 文字是普通灰色;
	// 1:选择正确的状态, 图标是绿色对勾, 文字是绿色;
	// 2:选择错误的状态, 图标是红色叉叉, 文字是红色;
	// 3:多选模式中正在选择还未点提交按钮的选项的状态, 图标是蓝色字母, 文字是蓝色;
	// 4:多选中属于正确选项但是用户未发现这项, 图标是绿色字母, 文字是绿色
	private int optionAStatus;
	private int optionBStatus;
	private int optionCStatus;
	private int optionDStatus;
	private int optionEStatus;
	// 错题答对的次数
	private int answerRightTimes;
	@Generated(hash = 125993387)
	public Question(Long id, String course, String type, String question,
			String optionA, String optionB, String optionC, String optionD,
			String optionE, String answer, boolean isShowAnswer, boolean isCollected,
			boolean isAnsweredWrong, boolean haveBeenAnswered, boolean isOptionACorrect,
			boolean isOptionBCorrect, boolean isOptionCCorrect, boolean isOptionDCorrect,
			boolean isOptionECorrect, int optionAStatus, int optionBStatus,
			int optionCStatus, int optionDStatus, int optionEStatus,
			int answerRightTimes) {
		this.id = id;
		this.course = course;
		this.type = type;
		this.question = question;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.optionE = optionE;
		this.answer = answer;
		this.isShowAnswer = isShowAnswer;
		this.isCollected = isCollected;
		this.isAnsweredWrong = isAnsweredWrong;
		this.haveBeenAnswered = haveBeenAnswered;
		this.isOptionACorrect = isOptionACorrect;
		this.isOptionBCorrect = isOptionBCorrect;
		this.isOptionCCorrect = isOptionCCorrect;
		this.isOptionDCorrect = isOptionDCorrect;
		this.isOptionECorrect = isOptionECorrect;
		this.optionAStatus = optionAStatus;
		this.optionBStatus = optionBStatus;
		this.optionCStatus = optionCStatus;
		this.optionDStatus = optionDStatus;
		this.optionEStatus = optionEStatus;
		this.answerRightTimes = answerRightTimes;
	}
	@Generated(hash = 1868476517)
	public Question() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCourse() {
		return this.course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuestion() {
		return this.question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOptionA() {
		return this.optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return this.optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return this.optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return this.optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getOptionE() {
		return this.optionE;
	}
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}
	public String getAnswer() {
		return this.answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean getIsShowAnswer() {
		return this.isShowAnswer;
	}
	public void setIsShowAnswer(boolean isShowAnswer) {
		this.isShowAnswer = isShowAnswer;
	}
	public boolean getIsCollected() {
		return this.isCollected;
	}
	public void setIsCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}
	public boolean getIsAnsweredWrong() {
		return this.isAnsweredWrong;
	}
	public void setIsAnsweredWrong(boolean isAnsweredWrong) {
		this.isAnsweredWrong = isAnsweredWrong;
	}
	public boolean getHaveBeenAnswered() {
		return this.haveBeenAnswered;
	}
	public void setHaveBeenAnswered(boolean haveBeenAnswered) {
		this.haveBeenAnswered = haveBeenAnswered;
	}
	public boolean getIsOptionACorrect() {
		return this.isOptionACorrect;
	}
	public void setIsOptionACorrect(boolean isOptionACorrect) {
		this.isOptionACorrect = isOptionACorrect;
	}
	public boolean getIsOptionBCorrect() {
		return this.isOptionBCorrect;
	}
	public void setIsOptionBCorrect(boolean isOptionBCorrect) {
		this.isOptionBCorrect = isOptionBCorrect;
	}
	public boolean getIsOptionCCorrect() {
		return this.isOptionCCorrect;
	}
	public void setIsOptionCCorrect(boolean isOptionCCorrect) {
		this.isOptionCCorrect = isOptionCCorrect;
	}
	public boolean getIsOptionDCorrect() {
		return this.isOptionDCorrect;
	}
	public void setIsOptionDCorrect(boolean isOptionDCorrect) {
		this.isOptionDCorrect = isOptionDCorrect;
	}
	public boolean getIsOptionECorrect() {
		return this.isOptionECorrect;
	}
	public void setIsOptionECorrect(boolean isOptionECorrect) {
		this.isOptionECorrect = isOptionECorrect;
	}
	public int getOptionAStatus() {
		return this.optionAStatus;
	}
	public void setOptionAStatus(int optionAStatus) {
		this.optionAStatus = optionAStatus;
	}
	public int getOptionBStatus() {
		return this.optionBStatus;
	}
	public void setOptionBStatus(int optionBStatus) {
		this.optionBStatus = optionBStatus;
	}
	public int getOptionCStatus() {
		return this.optionCStatus;
	}
	public void setOptionCStatus(int optionCStatus) {
		this.optionCStatus = optionCStatus;
	}
	public int getOptionDStatus() {
		return this.optionDStatus;
	}
	public void setOptionDStatus(int optionDStatus) {
		this.optionDStatus = optionDStatus;
	}
	public int getOptionEStatus() {
		return this.optionEStatus;
	}
	public void setOptionEStatus(int optionEStatus) {
		this.optionEStatus = optionEStatus;
	}
	public int getAnswerRightTimes() {
		return this.answerRightTimes;
	}
	public void setAnswerRightTimes(int answerRightTimes) {
		this.answerRightTimes = answerRightTimes;
	}
}
