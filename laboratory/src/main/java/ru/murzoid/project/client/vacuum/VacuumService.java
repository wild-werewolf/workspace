package ru.murzoid.project.client.vacuum;

import ru.murzoid.project.shared.VacuumData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("vacuum")
public interface VacuumService extends RemoteService {
	VacuumData getVacuumData(String user) ;
}