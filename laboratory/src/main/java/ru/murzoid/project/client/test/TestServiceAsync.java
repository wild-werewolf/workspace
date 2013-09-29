package ru.murzoid.project.client.test;

import java.util.List;
import java.util.Map;

import ru.murzoid.project.shared.TestTemplate;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface TestServiceAsync {
	void start(AsyncCallback<List<TestTemplate>> callback);
	void stop(String user, Map<Long, List<Long>> answers, AsyncCallback<Double> callback);
	void getTime(AsyncCallback<Long> callback);
}