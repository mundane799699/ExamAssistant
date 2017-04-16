package com.mundane.examassistant.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mundane on 2017/4/16.
 */

@Entity
public class MaoGaiXiaQuestion {
    @Id(autoincrement = true)
    private Long id;
    private String question;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String answer;

    private boolean isCollected;
    private boolean isAnsweredWrong;
    @Generated(hash = 1875725043)
    public MaoGaiXiaQuestion(Long id, String question, String optionA,
            String optionB, String optionC, String optionD, String answer,
            boolean isCollected, boolean isAnsweredWrong) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.isCollected = isCollected;
        this.isAnsweredWrong = isAnsweredWrong;
    }
    @Generated(hash = 788624399)
    public MaoGaiXiaQuestion() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
