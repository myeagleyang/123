package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import swiftpass.testcase.casebeans.StoreDetailCaseBean;
import swiftpass.utils.SwiftPass;

public class XStoreDetailTestData {
	private static final StoreDetailCaseBean tmpCase = new StoreDetailCaseBean();
	static{
		tmpCase.setCASE_NAME("");
		tmpCase.setErrorMsg("");
		tmpCase.setIsClickFirstRowStore("true");
	}
	public static StoreDetailCaseBean[][] data(){
		List<StoreDetailCaseBean> list = new ArrayList<>();
		StoreDetailCaseBean successCase = SwiftPass.copy(tmpCase);
		successCase.setCASE_NAME("成功查看门店详情...");
		list.add(successCase);
		
		StoreDetailCaseBean noSelectData = SwiftPass.copy(successCase);
		noSelectData.setIsClickFirstRowStore("");
		noSelectData.setErrorMsg("请选择您需要查看的记录!");
		noSelectData.setCASE_NAME("未选择任何记录...");
		list.add(noSelectData);
		//正常查看门店详情的用例
		
		return list.stream().map(element -> ArrayUtils.toArray(element)).toArray(StoreDetailCaseBean[][] :: new);
	}
	
	public static void main(String...strings){
		for(StoreDetailCaseBean[] cases: data()){
			System.out.println(cases[0]);
		}
	}

}
