package swiftpass.testcase.copy2;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.testng.TestNG;

public class StartSwiftpass1Test {
	public static void main(String...strings){
		String pro1 = System.getProperty("suite.file.names");
		String suiteXml1 = (pro1==null) ? "./default.xml" : pro1;
		StringTokenizer st = new StringTokenizer(suiteXml1, ";");
		List<String> list = new ArrayList<String>();
		int count = st.countTokens();
		for(int i=0; i<count; i++){
			list.add(st.nextToken());
		}
		System.out.println(list);
		TestNG test1 = new TestNG();
		test1.setOutputDirectory("./test-output1");
		test1.setTestSuites(list);
		test1.run();
	}

}
