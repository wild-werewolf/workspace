package ru.murzoid.project.server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ru.murzoid.project.client.vacuum.VacuumService;
import ru.murzoid.project.server.vacuum.dbtool.LibHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.HaracOsadokHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.HaracPeregHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.LabVariantHelper;
import ru.murzoid.project.server.vacuum.dbtool.helper.mysql.LabWorkHelper;
import ru.murzoid.project.server.vacuum.dbtool.tables.HaracOsadokTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.HaracPeregTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.InterfaceTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.LabVariantTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.LabWorkTable;
import ru.murzoid.project.server.vacuum.dbtool.tables.UsersTable;
import ru.murzoid.project.shared.TablesEnum;
import ru.murzoid.project.shared.VacuumData;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class VacuumServiceImpl  extends RemoteServiceServlet implements VacuumService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8752578528766029955L;
	public static Logger log = LogManager.getLogger(VacuumServiceImpl.class);

	public VacuumData getVacuumData(String user) {
		log.info("get vacuum data for user "+user);
		//search user
		VacuumData rezult=new VacuumData();
		long id = -1;
//		rezult.setPeregLT(LibHelper.getGridData(TablesEnum.HaracPeregorodka));
		rezult.setPeregLT(LibHelper.getGridData(TablesEnum.HaracPeregorodka));
		rezult.setOsadokLT(LibHelper.getGridData(TablesEnum.HaracOsadok));
		for(InterfaceTable table:LibHelper.getList(TablesEnum.Users)){
			UsersTable userT=(UsersTable) table;
			if(userT.getLogin().equalsIgnoreCase(user)){
				id=userT.getId();
			}
		}
		log.info("user id="+id);
		if(id<0){
			return rezult;
		}

		log.info("create new connection");
		Connection conn=LibHelper.getNewConnection();
		try{
			//search work 
			LabWorkHelper lw=new LabWorkHelper();
			lw.load(conn);
			List<LabWorkTable> labWT=lw.getList();
			LabWorkTable labT=null;
			for(LabWorkTable table: labWT){
				if(table.getIdUser()==id){
					labT=table;
					break;
				}
			}
			log.info("get LabWorkTable:\n"+labT);
			if(labT==null){
				return rezult;
			}
			//search vairants
			LabVariantHelper lv=new LabVariantHelper();
			lv.load(conn);
			List<LabVariantTable> labVT=lv.getList();
			LabVariantTable labV=null;
			for(LabVariantTable table: labVT){
				if(table.getId()==labT.getIdLabVar()){
					labV=table;
					break;
				}
			}
			log.info("get LabVariantTable:\n"+labV);
			if(labV==null){
				return rezult;
			}
			//search osadok
			HaracOsadokHelper osadokT=new HaracOsadokHelper();
			osadokT.load(conn);
			HaracOsadokTable osadok=null;
			List<HaracOsadokTable> osadokLT=osadokT.getList();
			for(HaracOsadokTable table: osadokLT){
				if(table.getId()==labV.getId_harac_osad()){
					osadok=table;
					break;
				}
			}
			log.info("get HaracOsadokTable:\n"+osadok);
			if(osadok==null){
				return rezult;
			}
			//search peregorodka
			HaracPeregHelper peregT=new HaracPeregHelper();
			peregT.load(conn);
			HaracPeregTable pereg=null;
			List<HaracPeregTable> peregLT=peregT.getList();
			for(HaracPeregTable table: peregLT){
				if(table.getId()==labV.getId_harac_per()){
					pereg=table;
					break;
				}
			}
			log.info("get HarakPeregTable:\n"+pereg);
			if(pereg==null){
				return rezult;
			}
			//source data
			log.info("setup source data");
			rezult.setOsadok(osadok.getName());
			rezult.setPeregorodka(pereg.getName());
			rezult.setDeltap(labV.getDeltap());
			rezult.setGc(labV.getGc());
			rezult.setTemper(labV.getTemper());
			rezult.setWoc(labV.getWoc());
			rezult.setXc(labV.getXc());
			double A=ServerUtil.formulaA(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),labV.getDeltap());
			rezult.setA(A);
			labT.setConstA(A);
			double B=ServerUtil.formulaB(labV.getTemper(), pereg.getSoprot(), labV.getDeltap());
			rezult.setB(B);
			labT.setConstB(B);
			double H=ServerUtil.formulaHos(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz());
			rezult.setH(H);
			labT.setHos(H);
			double Vf=1000*ServerUtil.formulaVfk(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc());
			rezult.setVf(Vf);
			int timeF=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap());
			rezult.setTimef(timeF);
			
			log.info("rezult is:\n"+rezult);
			log.info("setup computed data");
			
			Random rnd=new Random(System.currentTimeMillis());
			double value=0.2;
			int time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime1(time);
				labT.setTime1(time);
			}
			
			value=0.4;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime2(time);
				labT.setTime2(time);
			}
			value=0.6;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime3(time);
				labT.setTime3(time);
			}
			
			value=0.8;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime4(time);
				labT.setTime4(time);
			}
			
			value=1.0;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime5(time);
				labT.setTime5(time);
			}
			
			value=1.2;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime6(time);
				labT.setTime6(time);
			}
			
			value=1.4;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime7(time);
				labT.setTime7(time);
			}
			
			value=1.6;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime8(time);
				labT.setTime8(time);
			}
			
			value=1.8;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime9(time);
				labT.setTime9(time);
			}
			
			value=2.0;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime10(time);
				labT.setTime10(time);
			}
			
			value=2.2;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime11(time);
				labT.setTime11(time);
			}
			
			value=2.4;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime12(time);
				labT.setTime12(time);
			}
			
			value=2.6;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime13(time);
				labT.setTime13(time);
			}
			
			value=2.8;
			time=(int) ServerUtil.formulaT(labV.getTemper(), labV.getGc(),labV.getWoc(),labV.getXc(), osadok.getPloTverFaz(), osadok.getUdelsopr(),  pereg.getSoprot(), labV.getDeltap(), (value+getRandom(rnd))/1000);
			if(Vf>=value){
				rezult.setTime14(time);
				labT.setTime14(time);
			}
			
			rezult.setEmpty(false);
			log.info("rezult is:\n"+rezult);
			lw.update(LibHelper.getConn(), labT);
			log.info("get vacuum data for user "+user+" success");
			return rezult;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.info("Exception occured when close connection");
			}
		}
	}

	private double getRandom(Random rnd) {
		return (rnd.nextDouble() * 3 - 1) / 100;
	}
}
