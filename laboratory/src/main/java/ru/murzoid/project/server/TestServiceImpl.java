package ru.murzoid.project.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.project.client.test.TestService;
import ru.murzoid.project.server.vacuum.dbtool.LibHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.LabWorkHelper;
import ru.murzoid.project.server.vacuum.dbtool.tables.AnswerTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.LabWorkTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.QuestionsTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.UsersTable;
import ru.murzoid.project.shared.TablesEnum;
import ru.murzoid.project.shared.TestTemplate;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TestServiceImpl extends RemoteServiceServlet implements TestService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8752578528766029955L;
	public static Logger log = LogManager.getLogger(TestServiceImpl.class);
	private String homePath;
	private long time;
	private int number;
	private long startTime;
	private List<TestTemplate> questionsU=new ArrayList<TestTemplate>();
	private List<QuestionsTable> questions=new ArrayList<QuestionsTable>();
	private double rezult;
	@Override
	public List<TestTemplate> start() {
		questionsU.clear();
		questions.clear();
		homePath=ServletInitializer.getHomePath();
		Properties props = new Properties();
		try {
			props.loadFromXML(new FileInputStream(new File(homePath
					+ "/config/test.xml")));
		} catch (InvalidPropertiesFormatException e1) {
			log.error("invalid properties format for DB tool", e1);
			return null;
		} catch (FileNotFoundException e1) {
			log.error("properties file not found for DB tool", e1);
			return null;
		} catch (IOException e1) {
			log.error("proplem with load properties for DB tool", e1);
			return null;
		}
		rezult=0;
		time=Long.parseLong(props.getProperty("time"));
		startTime=System.currentTimeMillis();
		number=Integer.parseInt(props.getProperty("numberQuestion"));
		List<InterfaceTable> list1=LibHelper.getList(TablesEnum.Questions);
		List<InterfaceTable> list2=LibHelper.getList(TablesEnum.Answer);
		generateTest(list1, list2);
		return questionsU;
	}

	private void generateTest(List<InterfaceTable> listQ, List<InterfaceTable> listA) {
		Map<String,List<QuestionsTable>> map=new HashMap<String, List<QuestionsTable>>();
		if(listQ==null || listQ.isEmpty()){
			return;
		}
		for(InterfaceTable table: listQ){
			QuestionsTable tableQ=(QuestionsTable) table;
			List<QuestionsTable> listL=new ArrayList<QuestionsTable>();
			if(map.containsKey(tableQ.getGruppa())){
				listL.addAll(map.get(tableQ.getGruppa()));
			}
			listL.add(tableQ);
			if(listL.isEmpty()){
				continue;
			}
			map.put(tableQ.getGruppa(), listL);
		}
		Set<String> set=map.keySet();
		if(set.size()<number){
			number=set.size();
		}
		log.info("number="+number);
		for(int i=0;i<number;i++){
			QuestionsTable quest=getRandomQuest(map);
			if(quest==null){
				continue;
			}
			questions.add(quest);
		}
		for(QuestionsTable quest: questions){
			questionsU.add(getTestTemplate(quest, listA));
		}
		return;
	}

	private TestTemplate getTestTemplate(QuestionsTable quest, List<InterfaceTable> listA) {
		TestTemplate test=new TestTemplate();
		test.setId(quest.getId());
		test.setImage(quest.getImage());
		test.setQuestion(quest.getQuestion());
		Map<Long,String> map=new HashMap<Long, String>();
		for(Long id: quest.getAnswer()){
			for(InterfaceTable table: listA){
				AnswerTable answer=(AnswerTable) table;
				if(id==answer.getId()){
					map.put(id, answer.getAnswer());
					break;
				}
			}
		}
		test.setAnswers(map);
		return test;
	}

	private QuestionsTable getRandomQuest(Map<String, List<QuestionsTable>> map) {
		Random rnd=new Random(System.currentTimeMillis());
		long num=Math.abs(rnd.nextLong())%(map.keySet().size());
		long i=0;
		List<QuestionsTable> list=new ArrayList<QuestionsTable>();
		for(String str: map.keySet()){
			if(i==num){
				list.addAll(map.get(str));
				map.remove(str);
				break;
			}
			i++;
		}
		int num1= Math.abs(rnd.nextInt())%(list.size());
		if(list==null || list.isEmpty()){
			return null;
		}
		return list.get(num1);
	}

	@Override
	public Double stop(String user, Map<Long, List<Long>> answers) {
		log.info("stop test");
		long id = -1;
		rezult=computedRezult(answers);
		rezult=rezult*100;
		if(rezult>90){
			for(InterfaceTable table:LibHelper.getList(TablesEnum.Users)){
				UsersTable userT=(UsersTable) table;
				if(userT.getLogin().equalsIgnoreCase(user)){
					id=userT.getId();
				}
			}
			if(id<0){
				return rezult;
			}
			LabWorkHelper lw=new LabWorkHelper();
			lw.load(LibHelper.getConn());
			List<LabWorkTable> labWT=lw.getList();
			for(InterfaceTable table: labWT){
				LabWorkTable labT=(LabWorkTable) table;
				if(labT.getIdUser()==id){
					labT.setTestPass(true);
					break;
				}
			}
			
			log.info("start save "+labWT);
			lw.save(LibHelper.getConn(), labWT);
		}
		log.info("stop test end");
		return rezult;
	}

	private double computedRezult(Map<Long, List<Long>> answers) {
		double rezult=0;
		double sum=0;
		for(Long key:answers.keySet()){
			for(QuestionsTable table:questions){
				if(table.getId()==key){
					double tmp=0;
					for(Long ans:answers.get(key)){
						if(table.getTrueAnswer().contains(ans)){
							tmp++;
						} else {
							tmp--;
						}
					}
					if(tmp<=0){
						tmp=0;
					} else {
						tmp/=table.getTrueAnswer().size();
					}
					sum+=tmp;
					break;
				}
			}
		}
		rezult=sum/questionsU.size();
		return rezult;
	}

	@Override
	public Long getTime() {
		if(time==0){
			return -1l;
		}
		long endtime=System.currentTimeMillis()-startTime;
		if(endtime<0){
			endtime=0;
		}
		return endtime;
	}

}
