package swiftpass.testcase.merchant;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import swiftpass.page.exceptions.DepartException;
import swiftpass.page.merchant.DepartAddPage;
import swiftpass.page.merchant.DepartEditPage;
import swiftpass.page.merchant.DepartPage;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.casebeans.DepartmentAddCaseBean;
import swiftpass.testcase.casebeans.DepartmentEditCaseBean;
import swiftpass.testcase.casebeans.DepartmentSearchCaseBean;

public class DepartmentTestCaseImpl extends TestCaseImpl implements DepartmentTestCase {

	// 页初始化页面对象
	private DepartPage page;
	private DepartAddPage addPage;
	private DepartEditPage editPage;

	public DepartmentTestCaseImpl(WebDriver driver) {
		super(driver);
		this.page = new DepartPage(this.driver);
		this.addPage = new DepartAddPage(this.driver);
		this.editPage = new DepartEditPage(this.driver);
	}

	// 部门搜索
	public void searchDepartment(DepartmentSearchCaseBean department) {
		logger.info("本次商户管理——部门管理——部门查询使用的测试数据是： " + department);
		// 获得预期结果
		String expectedCount = department.getExpected();
		// 执行查询操作	
		// 输入测试数据
		page.setDepartName(department.getDepartName());
		page.selectBigMch(
				department.getIsSelectBigMerchant(), 
				department.getMerchantType(),
				department.getMchNameOrId(),
				department.getIsConfirmSelectMerchant()
				);
		page.clickSearch();
		// 获取实际结果
		String actual = page.getDepartCount();
		// 验证比对预期结果和实际结果
		AssertJUnit.assertEquals(actual, expectedCount);
	}

	@Override
	public void addDepartment(DepartmentAddCaseBean department) {
		logger.info("本次部门管理——新增测试使用的测试数据是： " + department);
		//获取测试数据
		String departName = department.getDepartName();
		String parentBigMchId = department.getBigMerchantCode();
		String parentBigMchName = department.getBigMerchantName();
		String isSelectBigMerchant = department.getIsSelectBigMerchant();
		String merchantType = department.getMerchantType();
		String isConfirmSelectBigMerchant =department.getIsConfirmSelectMerChant();
		String parentDepart = department.getParentDepartment();
		String isSave = department.getIsSave();
		String isConfirmSave = department.getIsConfirmSave();
		String message = department.getMessage();
		
		//获取新增前的部门数
		page.clickSearch();
		String old = page.getDepartCount();
		//新增成功的预期结果
		String expected = "" + (Integer.parseInt(old) + 1);
		//页面操作
		try{
			page.clickAdd();
			addPage.setDepartName(departName);
			if(StringUtils.isEmpty(message)){
				if(merchantType.equals("大商户名称"))
					addPage.selectBigMch(isSelectBigMerchant, merchantType, parentBigMchName, isConfirmSelectBigMerchant);
				else
					addPage.selectBigMch(isSelectBigMerchant, merchantType, parentBigMchId, isConfirmSelectBigMerchant);
			} else{
				addPage.directSetBigMchByJS(parentBigMchId, parentBigMchName);
			}
			addPage.selectParentDepart(parentDepart);
			addPage.lastAddOperate(isSave, isConfirmSave);
		} catch(DepartException e){
			logger.error(e);
//			if(e.getMessage().equals(message)){
//				System.err.println(message+": 验证通过！");
//			}else{
//				System.err.println(message+": 验证失败！");
//			}
			Assert.assertEquals(e.getMessage(), message);
			return;
		}	
		//获取新增后的部门数
		String actual = page.getDepartCount();
		//验证比对结果
		Assert.assertEquals(actual, expected);
		
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	@Override
	public void editDepartment(DepartmentEditCaseBean editBean) {
		logger.info("本次部门管理——编辑测试使用的测试数据是： " + editBean);
		//获取测试数据
		String[] text = editBean.getTEXT().split("-");
		String departName = editBean.getDepartName();
		String isEdit = editBean.getIsEdit();
		String isConfirmEdit = editBean.getIsConfirmEdit();
		String message = editBean.getMessage();
		
		//页面操作
		try{
			page.setDepartName(text[1]);
			page.clickSearch();
			page.clickDepartRowByText(text);
			page.clickEdit();
			Assert.assertEquals(editPage.checkUneditablePart(), true);
			editPage.editDepartName(departName);
			editPage.lastEditOperate(isEdit, isConfirmEdit);
		} catch(DepartException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage().trim(), message);
			return;
		}
		text[1] = departName;
		page.setDepartName(text[1]);
		page.clickSearch();
		page.clickDepartRowByText(text);
		page.clickEdit();
		String postInfo = editPage.getDepartInfo();
		
		Assert.assertEquals(postInfo, departName);
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}
}
