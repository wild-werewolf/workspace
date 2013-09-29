package ru.murzoid.project.server.vacuum.dbtool.tables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class AnswerTable implements InterfaceTable{
	private long id;
	private String answer;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "AnswerTable [id=" + id + ", answer=" + answer + "]";
	}
	
	public Map<String, Serializable> getGridData(){
		Map<String, Serializable> map=new HashMap<String, Serializable>();
		map.put(new String("id"), id);
		map.put(new String("variant"), answer);
		return map;
	}
}
