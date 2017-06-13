package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.page.enums.MerchantType;
import swiftpass.testcase.casebeans.StoreAddCaseBean;
import swiftpass.utils.SwiftPass;

public class XStoreAddTestData {
	private static final HashMap<String, String> bigMerchantInfo = StoreDBOperations.getBigMerchantInfo();
	private static final String NO_SELECT_PARENTMCH="请选择父商户",NO_SELECT_STORENAME="请输入门店名称",CANCEL="正常关闭";
	private static final StoreAddCaseBean tmpCase = new StoreAddCaseBean();
	static{
		tmpCase.setCASE_NAME("");
		tmpCase.setBigMchItem("大商户名称"); //或者大商户编号
		tmpCase.setBigMchNameOrId(bigMerchantInfo.get("MERCHANT_NAME"));
		tmpCase.setDepartName("");
		tmpCase.setStoreName(RandomStringUtils.randomAlphanumeric(6) + "门店");
		tmpCase.setStoreType(MerchantType.DIRECT.getPlainText());
		
		//新增一些控制参数
		tmpCase.setIsSelectBigMch("true");
		tmpCase.setIsConfirmSelectBigMch("true");
		tmpCase.setIsSave("true");
		tmpCase.setErrorMsg("");
	}
	
	private static StoreAddCaseBean[][] data(){
		List<StoreAddCaseBean> list = new ArrayList<>();
		
		StoreAddCaseBean noSelectParentMch =SwiftPass.copy(tmpCase);
		noSelectParentMch.setIsConfirmSelectBigMch("false");
		noSelectParentMch.setErrorMsg(NO_SELECT_PARENTMCH);
		noSelectParentMch.setCASE_NAME("未选择父商户...");
		list.add(noSelectParentMch);
		
		StoreAddCaseBean noInputStoreName =SwiftPass.copy(tmpCase);
		noInputStoreName.setStoreName("");
		noInputStoreName.setErrorMsg(NO_SELECT_STORENAME);
		noInputStoreName.setCASE_NAME("未输入门店名称...");
		list.add(noInputStoreName);
		
		StoreAddCaseBean cancel =SwiftPass.copy(tmpCase);
		cancel.setIsSave("false");
		cancel.setErrorMsg(CANCEL);
		cancel.setCASE_NAME("正常关闭...");
		list.add(cancel);
		
		
		//1.新增成功用例-直营商户 2.门店名称已存在用例添加成功 3.新增成功-加盟商户
		StoreAddCaseBean successCase = SwiftPass.copy(tmpCase);
		successCase.setCASE_NAME("成功添加一条数据...");
		list.add(successCase);
		
		StoreAddCaseBean illegleStoreNameExistCaseMap = SwiftPass.copy(successCase);
		successCase.setErrorMsg("门店名称已存在");
		successCase.setCASE_NAME("门店名称已存在校验...");
		list.add(illegleStoreNameExistCaseMap);
				
		StoreAddCaseBean successCase2_ = SwiftPass.copy(tmpCase);
		successCase2_.setStoreName(RandomStringUtils.randomAlphanumeric(6) + "门店");
		successCase2_.setStoreType(MerchantType.JOIN.getPlainText());
		successCase2_.setCASE_NAME("新增成功-加盟商户...");
		list.add(successCase2_);
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(StoreAddCaseBean[][] ::new);
	}
	
	public static void main(String...strings){
		long start = System.currentTimeMillis();
		for(StoreAddCaseBean[] cases: data()){
			System.out.println(cases[0]);
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
