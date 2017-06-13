package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import irsy.utils.dboperations.StoreDBOperations;
import swiftpass.testcase.casebeans.StoreEditCaseBean;
import swiftpass.utils.SwiftPass;

public class XStoreEditTestData {
	private static final HashMap<String, String> exitStoreInfo = StoreDBOperations.getExitStoreInfo();
	private static final StoreEditCaseBean tmpCase =new StoreEditCaseBean();
	static{
		tmpCase.setStoreName(exitStoreInfo.get("MERCHANT_NAME"));
		tmpCase.setStoreId(exitStoreInfo.get("MERCHANT_ID"));
	}
	private static final String NO_INPUT_STORENAME="请输入门店名称",NO_SELECTDATA="请选择要编辑的记录!",CLOSE="正常关闭",CANCEL="正常取消";
	
	
	private static StoreEditCaseBean[][] data(){
		List<StoreEditCaseBean> list = new ArrayList<>();
		
		//编辑成功用例
		StoreEditCaseBean successCase = SwiftPass.copy(tmpCase);
		successCase.setCASE_NAME("成功编辑一条记录...");
		list.add(successCase);
		
		//编辑输入的门店名称已存在报系统异常错误
		StoreEditCaseBean illegleStoreNameExit =  SwiftPass.copy(tmpCase);
		illegleStoreNameExit.setStoreName(StoreDBOperations.getAnotherExitStoreInfo().get("MERCHANT_NAME"));
		illegleStoreNameExit.setErrorMsg("系统异常");
		illegleStoreNameExit.setCASE_NAME("编辑输入的门店名称已存在报系统异常错误");
		list.add(illegleStoreNameExit);
		
		//未输入门店名称
		StoreEditCaseBean noInputStoreName = SwiftPass.copy(tmpCase);
		noInputStoreName.setStoreName("");
		noInputStoreName.setCASE_NAME(NO_INPUT_STORENAME);
		noInputStoreName.setErrorMsg(NO_INPUT_STORENAME);
		list.add(noInputStoreName);
		
		//未选择记录
		StoreEditCaseBean noSelectData = SwiftPass.copy(tmpCase);
		noSelectData.setStoreName(RandomStringUtils.randomNumeric(7)+"门店");
		noSelectData.setIsClickFirstRowStore("");
		noSelectData.setCASE_NAME(NO_SELECTDATA);
		noSelectData.setErrorMsg(NO_SELECTDATA);
		list.add(noSelectData);
		
		//正常关闭
		StoreEditCaseBean closeCase = SwiftPass.copy(tmpCase);
		closeCase.setStoreName(RandomStringUtils.randomNumeric(7)+"门店");
		closeCase.setIsEdit("");
		closeCase.setErrorMsg(CLOSE);
		closeCase.setCASE_NAME(CLOSE);
		list.add(closeCase);
		
		//正常取消
		StoreEditCaseBean cancelCase = SwiftPass.copy(tmpCase);
		cancelCase.setStoreName(RandomStringUtils.randomNumeric(7)+"门店");
		cancelCase.setIsConfirmEdit("");
		cancelCase.setCASE_NAME(CANCEL);
		cancelCase.setErrorMsg(CANCEL);
		list.add(cancelCase);
		return list.stream().map(element ->ArrayUtils.toArray(element)).toArray(StoreEditCaseBean[][] :: new);
	}
	
	public static void main(String args[]){
		for(StoreEditCaseBean[] cases: data()){
			System.out.println(cases[0]);
		}
	}
}
