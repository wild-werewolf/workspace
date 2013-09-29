package ru.murzoid.project.client.test;

import java.util.List;
import java.util.Map;

import ru.murzoid.project.shared.TestTemplate;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("test")
public interface TestService extends RemoteService {
	List<TestTemplate> start();
	Double stop(String user, Map<Long, List<Long>> answers);
	Long getTime();
}