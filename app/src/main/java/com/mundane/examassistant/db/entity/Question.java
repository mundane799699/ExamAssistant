package com.mundane.examassistant.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
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
	private String question;//	问题的题目描述
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String optionE;
	private String answer;
	private boolean isCollected;//	是否被收藏了
	private boolean isAnsweredWrong;//	是否被收录到错题集中
	@Generated(hash = 411055870)
	public Question(Long id, String course, String type, String question,
			String optionA, String optionB, String optionC, String optionD,
			String optionE, String answer, boolean isCollected, boolean isAnsweredWrong) {
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
		this.isCollected = isCollected;
		this.isAnsweredWrong = isAnsweredWrong;
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

}
