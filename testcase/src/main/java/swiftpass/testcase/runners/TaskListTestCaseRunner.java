package swiftpass.testcase.runners;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import swiftpass.page.MenuType;
import swiftpass.testcase.task.TaskListTestCase;
import swiftpass.testcase.task.TaskListTestCaseImpl;
import swiftpass.testcase.testdata.tasklist.TaskListAddTestData;
import swiftpass.testcase.testdata.tasklist.TaskListResetTestData;
import swiftpass.testcase.testdata.tasklist.TaskListRunTestData;
import swiftpass.testcase.testdata.tasklist.TaskListSearchTestData;
import swiftpass.testcase.testdata.tasklist.TaskListStartOrShutDownTestData;

public class TaskListTestCaseRunner extends CaseRunner{
	
	@BeforeMethod
	public void refresh(){
		driver.navigate().refresh();
		menu.clickElement(MenuType.HM_PF);
		menu.clickElement(MenuType.HM_PF_TM);
		menu.clickElement(MenuType.HM_PF_TM_TL);
	}
	
	@DataProvider
	public Object[][] taskListTestData(Method method){
		HashMap<String, String>[][] params = null;
		if(method.getName().equals("runSearchTask")){
			params = TaskListSearchTestData.getSearchTaskListCaseMap();
		}else if(method.getName().equals("runAddTask")){
			params = TaskListAddTestData.getTaskListAddTestData();
		}else if(method.getName().equals("runEditTask")){
			//TO DO
		}else if(method.getName().equals("runRunTask")){
			params = TaskListRunTestData.getTaskListRunTestData();
		}else if(method.getName().equals("runStartOrShutdownTask")){
			params = TaskListStartOrShutDownTestData.getTaskListStartOrShutDownTestData();
		}else if(method.getName().equals("runResetRunStatus")){
			params = TaskListResetTestData.getResetTaskTestData();
		}
		return params;
	}
	
	@Test(description = "调试时才应该启用这个")
	public void runDebug(){
		logger.info("开始执行任务列表——编辑任务测试用例......");
		TaskListTestCaseImpl tc = new TaskListTestCaseImpl(driver);
		tc.debug();
		logger.info("任务列表——编辑任务测试用例执行结束！");
	}
	
	@Test(dataProvider = "taskListTestData")
	public void runSearchTask(HashMap<String, String> params){
		logger.info("开始执行公共管理——任务管理——任务列表——查询测试用例！");
		TaskListTestCase tl = new TaskListTestCaseImpl(driver);
		tl.searchTask(params);
		logger.info("公共管理——任务管理——任务列表——查询测试用例执行完成！");
	}
	
	@Test(dataProvider = "taskListTestData")
	public void runAddTask(HashMap<String, String> params){
		logger.info("开始执行公共管理——任务管理——任务列表——新增测试用例！");
		TaskListTestCase tl = new TaskListTestCaseImpl(driver);
		tl.addTask(params);
		logger.info("公共管理——任务管理——任务列表——新增测试用例执行完成！");
	}
	
	@Test(dataProvider = "taskListTestData")
	public void runEditTask(HashMap<String, String> params){
		//TO DO
	}
	
	@Test(dataProvider = "taskListTestData")
	public void runRunTask(HashMap<String, String> params){
		logger.info("开始执行公共管理——任务管理——任务列表——立即执行测试用例！");
		TaskListTestCase tl = new TaskListTestCaseImpl(driver);
		tl.runTask(params);
		logger.info("公共管理——任务管理——任务列表——立即执行测试用例执行完成！");
	}
	
	@Test(dataProvider = "taskListTestData")
	public void runStartOrShutdownTask(HashMap<String, String> params){
		logger.info("开始执行公共管理——任务管理——任务列表——启/停任务测试用例！");
		TaskListTestCase tl = new TaskListTestCaseImpl(driver);
		tl.startOrShutdownTask(params);
		logger.info("公共管理——任务管理——任务列表——启/停任务测试用例执行完成！");
	}
	
	@Test(dataProvider = "taskListTestData")
	public void runResetRunStatus(HashMap<String, String> params){
		logger.info("开始执行公共管理——任务管理——任务列表——重置运行状态测试用例！");
		TaskListTestCase tl = new TaskListTestCaseImpl(driver);
		tl.resetRunStatus(params);
		logger.info("公共管理——任务管理——任务列表——重置运行状态测试用例执行完成！");
	}
	
}
