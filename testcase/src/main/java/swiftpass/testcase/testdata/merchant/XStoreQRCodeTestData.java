package swiftpass.testcase.testdata.merchant;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import swiftpass.testcase.casebeans.StoreQRCodeCaseBean;
import swiftpass.utils.SwiftPass;

/**
 * 
 * @author sunhaojie
 * date 2017-04-01
 */
public class XStoreQRCodeTestData {
	private static final StoreQRCodeCaseBean tmpCase = new StoreQRCodeCaseBean();
	
	public static StoreQRCodeCaseBean[][] data(){
		List<StoreQRCodeCaseBean> list = new ArrayList<>();
		
		//成功查看二维码
		StoreQRCodeCaseBean successCase = SwiftPass.copy(tmpCase);
		successCase.setCASE_NAME("成功查看二维码数据...");
		list.add(successCase);
		
		//未选择记录校验
		StoreQRCodeCaseBean IllegleCase = SwiftPass.copy(successCase);
		IllegleCase.setIsClickFirstRowStore("");
		IllegleCase.setErrorMsg("请选择要查看二维码的记录!");
		IllegleCase.setCASE_NAME("未选择任何记录校验...");
		list.add(IllegleCase);
		
		return list.stream().map(element ->ArrayUtils.toArray(element)).toArray(StoreQRCodeCaseBean [][] :: new);
	}
	
	public static void main(String...strings){
		for(StoreQRCodeCaseBean[] map: data()){
			System.out.println(map[0]);
		}
	}

}
