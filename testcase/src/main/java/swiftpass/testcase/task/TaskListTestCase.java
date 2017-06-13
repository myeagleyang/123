package swiftpass.testcase.task;

import java.util.HashMap;

public interface TaskListTestCase {
	public void searchTask(HashMap<String, String> params);
	
	public void addTask(HashMap<String, String> params);
	
	public void editTask(HashMap<String, String> params);
	
	public void runTask(HashMap<String, String> params);
	
	public void startOrShutdownTask(HashMap<String, String> params);
	
	public void resetRunStatus(HashMap<String, String> params);
	
	
}
