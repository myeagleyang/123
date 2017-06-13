package swiftpass.testcase.task;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import irsy.utils.dboperations.TaskListDBOperations;
import swiftpass.page.exceptions.TaskListException;
import swiftpass.page.tasklist.TaskAddPage;
import swiftpass.page.tasklist.TaskListPage;
import swiftpass.page.tasklist.TaskResetPage;
import swiftpass.page.tasklist.TaskRunPage;
import swiftpass.page.tasklist.TaskStartOrShutdownPage;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.testdata.tasklist.TaskListResetTestData;
import swiftpass.testcase.testdata.tasklist.TaskListStartOrShutDownTestData;

public class TaskListTestCaseImpl extends TestCaseImpl implements TaskListTestCase{
	private TaskListPage page;
	private TaskAddPage addPage;
	private TaskRunPage runPage;
	private TaskStartOrShutdownPage startOrShutDownPage;
	private TaskResetPage resetPage;
	
	public TaskListTestCaseImpl(WebDriver driver) {
		super(driver);
		page = new TaskListPage(driver);
		addPage = new TaskAddPage(driver);
		runPage = new TaskRunPage(driver);
		startOrShutDownPage = new TaskStartOrShutdownPage(driver);
		resetPage = new TaskResetPage(driver);
	}
	
	public void debug(){
		page.clickTaskRow("定时任务自动扫描");
		System.err.println(page.getTaskExecLogs());
		System.out.println(page.getTaskRowCount() + " ------ " + page.getTaskLogRowCount());
	}
	
	@Override
	public void searchTask(HashMap<String, String> params) {
		logger.info("公共管理——任务管理——任务列表——查询测试用例使用的数据是:" + params);
		String taskName = params.get("taskName");
		String groupName = params.get("taskGroup");
		String expect = params.get("expect");
		
		page.clickSearch();
		page.setTaskName(taskName);
		page.setTaskGroup(groupName);
		page.clickSearch();
		//获取实际查询结果
		String actual = page.getNumbersOfTask();
		Assert.assertEquals(actual, expect);
	}

	@Override
	public void addTask(HashMap<String, String> params) {
		logger.info("公共管理——任务管理——任务列表——新增测试用例使用的数据是:" + params);
		String taskId = params.get("taskId");
		String taskName = params.get("taskName");
		String groupName = params.get("groupName");
		String timeExpress = params.get("timeExpress");
		String interfaceUrl = params.get("interfaceUrl");
		String threadCount = params.get("threadCount");
		String extraParam = params.get("extraParam");
		String isSelectEnable = params.get("isSelectEnable");
		String isEnable = params.get("isEnable");
		String isSave = params.get("isSave");
		String errorMsg = params.get("errorMsg");
		page.clickSearch();
		String preTaskListCount = null;
		if(errorMsg.equals("")) preTaskListCount = page.getNumbersOfTask();
		//执行新增操作
		page.clickAdd();
		try{
			addPage.setTaskId(taskId);
			addPage.setTaskName(taskName);
			addPage.setTaskGroup(groupName);
			addPage.setTaskTimeExpression(timeExpress);
			addPage.setApiUrl(interfaceUrl);
			addPage.setTaskThreadCount(threadCount);
			addPage.setAdditionalPrameter(extraParam);
			addPage.selectIsEnable(isSelectEnable, isEnable);
			addPage.setRemark();
			addPage.lastOperate(isSave);
		}catch(TaskListException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		
		//新增后获取任务列表个数
		page.clickSearch();
		String postTaskListCount = page.getNumbersOfTask();
		Assert.assertEquals("" + (Integer.parseInt(preTaskListCount) + 1), postTaskListCount);
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
		logger.info("新增任务成功！");
	}

	@Override
	public void editTask(HashMap<String, String> params) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void runTask(HashMap<String, String> params) {
		logger.info("公共管理——任务管理——任务列表——立即执行测试用例使用的数据是：" + params);
		String isClickFirstRow = params.get("isClickFirstRow");
		String isConfirm = params.get("isConfirm");
		String errorMsg = params.get("errorMsg");
		
		page.clickSearch();
		try{
			runPage.checkClickFirstRow(isClickFirstRow);
			page.clickExecute();
			runPage.lastOperate(isConfirm);
		}catch(TaskListException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		Assert.assertEquals(StringUtils.isEmpty(errorMsg), true);
	}

	@Override
	public void startOrShutdownTask(HashMap<String, String> params) {
		logger.info("公共管理——任务管理——任务列表——启/停用任务测试用例使用的数据是：" + params);
		String isClickFirstRow = params.get("isClickFirstRow");
		String isConfirm = params.get("isConfirm");
		String usingStatus = params.get("usingStatus");
		String errorMsg = params.get("errorMsg");
		HashMap<String, String> taskInfo = TaskListStartOrShutDownTestData.getTaskInfo(usingStatus);
		String taskName = taskInfo.get("TASK_NAME");
		try{
			page.setTaskName(taskName);
			page.clickSearch();
			startOrShutDownPage.clickEnableTask(isClickFirstRow, usingStatus);
			startOrShutDownPage.lastOperate(isConfirm);
		}catch(TaskListException e){
			logger.info(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		String taskId = taskInfo.get("TASK_ID");
		String actualStatus = TaskListDBOperations.getTaskInfo(taskId).get("STATUS");
		if(usingStatus.equals("0")) Assert.assertEquals(actualStatus, "1");
		if(usingStatus.equals("1")) Assert.assertEquals(actualStatus, "0");
		logger.info("启/停用任务成功");
	}

	@Override
	public void resetRunStatus(HashMap<String, String> params) {
		logger.info("公共管理——任务管理——任务列表——重置运行状态测试用例使用的数据是：" + params);
		String isClickFirstRow = params.get("isClickFirstRow");
		String isConfirm = params.get("isConfirm");
		String runStatus = params.get("runStatus");
		String errorMsg = params.get("errorMsg");
		HashMap<String, String> task = TaskListResetTestData.getTaskInfo(runStatus);
		String taskName = task.get("TASK_NAME");
		try{
			page.setTaskName(taskName);
			page.clickSearch();
			resetPage.clickResetRunStatus(isClickFirstRow, taskName);
			resetPage.lastOperate(isConfirm, runStatus);
		}catch(TaskListException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), errorMsg);
			return;
		}
		
		String taskId = task.get("TASK_ID");
		String actualRunStatus = TaskListDBOperations.getTaskInfo(taskId).get("RUN_STATUS");
		if(runStatus.equals("1")) Assert.assertEquals(actualRunStatus, "0");
		logger.info("初始化任务运行状态成功 ！");
	}

}
