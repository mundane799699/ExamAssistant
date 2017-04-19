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
	private boolean isCollected;//	是否被收藏了
	private boolean isAnsweredWrong;//	是否被收录到错题集中
	private boolean haveBeenAnswered;//	该问题是否已经被回答过
	private boolean showOptionA;	//	是否显示A选项为正确或者错误答案
	private boolean showOptionB;	//	是否显示B选项为正确或者错误答案
	private boolean showOptionC;	//	是否显示C选项为正确或者错误答案
	private boolean showOptionD;	//	是否显示D选项为正确或者错误答案
	private boolean showOptionE;	//	是否显示E选项为正确或者错误答案
	private boolean isOptionACorrect;	//	A选项是否是正确答案
	private boolean isOptionBCorrect;	//	B选项是否是正确答案
	private boolean isOptionCCorrect;	//	C选项是否是正确答案
	private boolean isOptionDCorrect;	//	D选项是否是正确答案
	private boolean isOptionECorrect;	//	E选项是否是正确答案
	@Generated(hash = 732367781)
	public Question(Long id, String course, String type, String question,
			String optionA, String optionB, String optionC, String optionD,
			String optionE, String answer, boolean isCollected, boolean isAnsweredWrong,
			boolean haveBeenAnswered, boolean showOptionA, boolean showOptionB,
			boolean showOptionC, boolean showOptionD, boolean showOptionE,
			boolean isOptionACorrect, boolean isOptionBCorrect, boolean isOptionCCorrect,
			boolean isOptionDCorrect, boolean isOptionECorrect) {
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
		this.haveBeenAnswered = haveBeenAnswered;
		this.showOptionA = showOptionA;
		this.showOptionB = showOptionB;
		this.showOptionC = showOptionC;
		this.showOptionD = showOptionD;
		this.showOptionE = showOptionE;
		this.isOptionACorrect = isOptionACorrect;
		this.isOptionBCorrect = isOptionBCorrect;
		this.isOptionCCorrect = isOptionCCorrect;
		this.isOptionDCorrect = isOptionDCorrect;
		this.isOptionECorrect = isOptionECorrect;
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
	public boolean getHaveBeenAnswered() {
		return this.haveBeenAnswered;
	}
	public void setHaveBeenAnswered(boolean haveBeenAnswered) {
		this.haveBeenAnswered = haveBeenAnswered;
	}
	public boolean getShowOptionA() {
		return this.showOptionA;
	}
	public void setShowOptionA(boolean showOptionA) {
		this.showOptionA = showOptionA;
	}
	public boolean getShowOptionB() {
		return this.showOptionB;
	}
	public void setShowOptionB(boolean showOptionB) {
		this.showOptionB = showOptionB;
	}
	public boolean getShowOptionC() {
		return this.showOptionC;
	}
	public void setShowOptionC(boolean showOptionC) {
		this.showOptionC = showOptionC;
	}
	public boolean getShowOptionD() {
		return this.showOptionD;
	}
	public void setShowOptionD(boolean showOptionD) {
		this.showOptionD = showOptionD;
	}
	public boolean getShowOptionE() {
		return this.showOptionE;
	}
	public void setShowOptionE(boolean showOptionE) {
		this.showOptionE = showOptionE;
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

}
