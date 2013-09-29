package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsTable implements InterfaceTable{
	private long id;
	private String gruppa;
	private String question;
	private ArrayList<Long> answer;
	private ArrayList<Long> trueAnswer;
	private String image;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getGruppa() {
		return gruppa;
	}
	public void setGruppa(String gruppa) {
		this.gruppa = gruppa;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<Long> getAnswer() {
		return answer;
	}
	public void setAnswer(ArrayList<Long> answer) {
		this.answer = answer;
	}
	public List<Long> getTrueAnswer() {
		return trueAnswer;
	}
	public void setTrueAnswer(ArrayList<Long> trueAnswer) {
		this.trueAnswer = trueAnswer;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "QuestionsTable [id=" + id + ", gruppa=" + gruppa
				+ ", question=" + question + ", answer=" + answer
				+ ", trueAnswer=" + trueAnswer + ", image=" + image + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put("id",id);
		map.put("gruppa",gruppa);
		map.put("question",question);
		System.out.println(answer);
		map.put("variants",answer);
		map.put("variants_true",trueAnswer);
		map.put("image",image);
		return map;
	}
}
