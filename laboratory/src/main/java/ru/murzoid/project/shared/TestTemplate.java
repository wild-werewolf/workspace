package ru.murzoid.project.shared;

import java.io.Serializable;
import java.util.Map;

public class TestTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 840437701317899797L;
	private long id;
	private String question;
	private Map<Long,String> answers;
	private String image;
	private boolean end=false;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Map<Long, String> getAnswers() {
		return answers;
	}
	public void setAnswers(Map<Long, String> answers) {
		this.answers = answers;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isEnd() {
		return end;
	}
	public void setEnd(boolean end) {
		this.end = end;
	}

}
