package swiftpass.testcase.merchant;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import swiftpass.page.exceptions.DepartException;
import swiftpass.page.merchant.*;
import swiftpass.testcase.TestCaseImpl;

public class DepartTestCaseImpl extends TestCaseImpl implements DepartTestCase{
	//主页
	DepartPage page;
	DepartAddPage addPage;
	DepartEditPage editPage;
	
	public DepartTestCaseImpl(WebDriver driver) {
		super(driver);
		this.page = new DepartPage(this.driver);
		this.addPage = new DepartAddPage(this.driver);
		this.editPage = new DepartEditPage(this.driver);
	}

	public void searchDepart(HashMap<String, String> params) {
		logger.info("本次部门管理——查询测试使用的测试数据是： " + params);
		//获取测试数据参数
		String departName = params.get("departName");
		String isSelectMerchant = params.get("isSelectMerchant");
		String mchNameOrIdItem = params.get("mchNameOrIdItem");
		String mchNameOrId = params.get("mchNameOrId");
		String isConfirmSelectMerchant = params.get("isConfirmSelectMerchant");
		
		//获取预期结果
		String expectedCount = params.get("expectedCount");
		
		//输入测试数据
		page.setDepartName(departName);
		page.selectBigMch(isSelectMerchant, mchNameOrIdItem, mchNameOrId, isConfirmSelectMerchant);
		page.clickSearch();
		//获取实际结果
		String actual = page.getDepartCount();
		//验证比对预期结果和实际结果
		Assert.assertEquals(actual, expectedCount);
		
	}

	public void addDepart(HashMap<String, String> params) {
		logger.info("本次部门管理——新增测试使用的测试数据是： " + params);
		//获取测试数据
		String departName = params.get("departName");
		String parentBigMchId = params.get("parentBigMchId");
		String parentBigMchName = params.get("parentBigMchName");
		String isSelectBigMerchant = params.get("isSelectBigMerchant");
		String mchNameOrIdItem = params.get("mchNameOrIdItem");
		String isConfirmSelectBigMerchant = params.get("isConfirmSelectBigMerchant");
		String parentDepart = params.get("parentDepart");
		String isSave = params.get("isSave");
		String isConfirmSave = params.get("isConfirmSave");
		
		String message = params.get("message");
		
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
				if(mchNameOrIdItem.equals("大商户名称"))
					addPage.selectBigMch(isSelectBigMerchant, mchNameOrIdItem, parentBigMchName, isConfirmSelectBigMerchant);
				else
					addPage.selectBigMch(isSelectBigMerchant, mchNameOrIdItem, parentBigMchId, isConfirmSelectBigMerchant);
			} else{
				addPage.directSetBigMchByJS(parentBigMchId, parentBigMchName);
			}
			addPage.selectParentDepart(parentDepart);
			addPage.lastAddOperate(isSave, isConfirmSave);
		} catch(DepartException e){
			logger.error(e);
			Assert.assertEquals(e.getMessage(), message);
			return;
		}
		//获取新增后的部门数
		String actual = page.getDepartCount();
		//验证比对结果
		Assert.assertEquals(actual, expected);
		
		Assert.assertEquals(StringUtils.isEmpty(message), true);
	}

	public void editDepart(HashMap<String, String> params) {
		logger.info("本次部门管理——编辑测试使用的测试数据是： " + params);
		//获取测试数据
		String[] text = params.get("TEXT").split("-");

		String departName = params.get("departName");
		String isEdit = params.get("isEdit");
		String isConfirmEdit = params.get("isConfirmEdit");
		
		String message = params.get("message");
		
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