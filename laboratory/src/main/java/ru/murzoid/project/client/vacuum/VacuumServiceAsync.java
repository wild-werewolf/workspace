package ru.murzoid.project.client.vacuum;

import ru.murzoid.project.shared.VacuumData;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface VacuumServiceAsync {
	void getVacuumData(String user, AsyncCallback<VacuumData> callback) ;
}