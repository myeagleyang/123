package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import swiftpass.testcase.casebeans.MerchantSearchCaseBean;
import swiftpass.utils.SwiftPass;

public class XMerchantSearchTestData {
	public static final MerchantSearchCaseBean templateCase = new MerchantSearchCaseBean();
//	private static HashMap<Integer, HashMap<String, String>> mchs = MerchantDBOperations.allMerchants();
	public static MerchantSearchCaseBean[][] data(){
		//dasgdsg
		List<MerchantSearchCaseBean> list = new ArrayList<>();
		//1.��������ѯ
		MerchantSearchCaseBean byBeginCTCase = SwiftPass.copy(templateCase);
		byBeginCTCase.setCASE_NAME("��������ѯ -> ��ʼʱ��...");
		System.out.println(byBeginCTCase);
		list.add(byBeginCTCase);
		for(MerchantSearchCaseBean li:list)
		{
			System.out.println(li.getCASE_NAME());
		}
		MerchantSearchCaseBean byEndCTCase = SwiftPass.copy(templateCase);
		return null;
		
	}
	
	public static void main(String[] args) {
		System.out.println("Test SVN");
	}
}