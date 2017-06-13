package swiftpass.testcase.testdata.cle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CleResStatCaseBeanIterator implements Iterator<CleStatCaseBean>{
	private List<CleStatCaseBean> beanList = new ArrayList<>();
	private Iterator<CleStatCaseBean> it = null;
	
	public CleResStatCaseBeanIterator(){
		init();
		it = beanList.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public CleStatCaseBean next() {
		return it.next();
	}

	private void init(){
		CleStatCaseBean defaultCase = new CleStatCaseBean();
		defaultCase.setCASE_NAME("默认查询清分统计结果用例");
		beanList.add(defaultCase);
	}
	
	
}
