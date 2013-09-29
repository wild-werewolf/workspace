package ru.murzoid.project.client;

import ru.murzoid.project.client.login.LoginData;
import ru.murzoid.project.client.login.LoginListener;
import ru.murzoid.project.client.login.LoginPanel;
import ru.murzoid.project.client.manage.tables.HaracOsadokManage;
import ru.murzoid.project.client.manage.tables.HaracPeregManage;
import ru.murzoid.project.client.manage.tables.LabVariantManage;
import ru.murzoid.project.client.manage.tables.LabWorkManage;
import ru.murzoid.project.client.manage.tables.QuestionsManage;
import ru.murzoid.project.client.manage.tables.UsersManage;
import ru.murzoid.project.client.test.TestVacuumWindow;
import ru.murzoid.project.client.vacuum.LabVacuumWindow;
import ru.murzoid.project.shared.LoginResponce;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Laboratory implements EntryPoint {


	private LabDesctop lDesktop;
	private LoginPanel login;
	private LoginData loginData;

	public void onModuleLoad() {
		loginData=new LoginData();
		lDesktop=new LabDesctop(loginData);
		lDesktop.isVisible(false);
		loginData.getCookie();
		login=new LoginPanel(loginData);
	    login.setGlassEnabled(true);
	    login.setAnimationEnabled(true);
	    login.center();
		reloginToServer();
	    loginData.addLoginListener(new LoginListener() {
			
			@Override
			public void onChange() {
				lDesktop.isVisible(loginData.isSuccess());
				if(loginData.isSuccess()){
					loginData.setCookie();
					new TestVacuumWindow(lDesktop, loginData.getUserName());
					if(loginData.isAdmin()){
						new HaracOsadokManage(lDesktop);
						new HaracPeregManage(lDesktop);
						new QuestionsManage(lDesktop);
						new LabVariantManage(lDesktop);
						new UsersManage(lDesktop);
						new LabWorkManage(lDesktop);
						new LabVacuumWindow(lDesktop, loginData.getUserName());
					} else if(loginData.isTestPass()){
						new LabVacuumWindow(lDesktop, loginData.getUserName());
					}
				}
			}
		});
//	    Window w=new LabVacuumWindow(lDesktop);
//	    Window harOsadok=new HaracOsadokManage(lDesktop);
//	    loginData.setSuccess(true);
	    Window.addWindowClosingHandler(new ClosingHandler() {
			
			@Override
			public void onWindowClosing(ClosingEvent event) {
				if(loginData.isSuccess()){
					LogonManager.getInstance().logout(loginData.getUserName(),new AsyncCallback<String>() {
						
						@Override
						public void onSuccess(String result) {
							loginData.setSuccess(false);
							lDesktop.logoutAction();
						}
						
						@Override
						public void onFailure(Throwable caught) {
						}
					});
				}
			}
		});
	}

	private void reloginToServer() {
		LogonManager.getInstance().relogin(loginData.getSession(), loginData.getUserName(), new AsyncCallback<LoginResponce>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(LoginResponce result) {
				loginData.setAdmin(result.isAdmin());
				loginData.setTestPass(result.isTestPass());
				loginData.setSuccess(result.isSuccess());
			}
		});
	}
}
