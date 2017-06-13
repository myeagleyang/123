package swiftpass.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import swiftpass.testcase.CaseBean;

public class XXReporter implements IReporter{
	private static final Date reportDate = new Date();
	private static final String outdir = System.getProperty("user.dir") + "/resource/";
	private static final String reportFileName = "测试报告_1.html";
	private ReportInfo reportInfo;
	private PrintWriter writer = null;
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		reportInfo = new ReportInfo(xmlSuites, suites);
		createWriter();
		startHtml();
		generateSuitesSummaryReport();
		generateSuiteTestSummaryReport();
		generateTestDetailReport();
		endHtml();
		closeResource();
	}

	private void createWriter(){
		try {
			File outdirFile = new File(outdir);
			File reportFile = new File(outdir + reportFileName);
			if(!outdirFile.exists()){
				outdirFile.mkdirs();
			}
			if(reportFile.exists()){
				reportFile.delete();
				reportFile.createNewFile();
			}
			writer = new PrintWriter(reportFile, Charset.forName("UTF-8").name());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startHtml(){
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
		writer.println("<title>自动化测试报告</title></head>");
		writer.println("<body width=\"100%\">\n<br/>\n<h2 style=\"text-align:center;\"><font style=\"color:red;font-family:宋体;font-size:36px\">------测试报告------</font></h2>");
		writer.println("<br/><h4 style=\"text-align:right;\">时间： " + reportDate + "</h4><br/>");		
	}
	
	private void closeResource(){
		writer.flush();
		writer.close();
	}
	
	private void endHtml(){
		writer.println("</body></html>");
	}
	
	private void generateSuitesSummaryReport(){
		writer.println("<table style=\"align:center;word-wrap:break-word;layout:fixed\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
		writer.println("<tr><th style=\"color:#00CED1\"><font colspan=\"5\" style=\"font-family:宋体;font-size:26px\">测试套件概要</font></th><tr></table>");
		for(SuiteSummary ss : reportInfo.getSuiteSummarys()){
			suiteSummaryTable(ss);
		}
		writer.println("<br/>");
	}
	
	private void generateSuiteTestSummaryReport(){
		Map<String, List<SuiteTestSummary>> summarys = reportInfo.getSuiteTestSummarys();
		writer.println("<table style=\"align:center;word-wrap:break-word;layout:fixed\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
		writer.println("<tr><th><font style=\"font-size:24px\">测试概要</font></th></tr></table>");
		for(Entry<String, List<SuiteTestSummary>> summary : summarys.entrySet()){
			List<SuiteTestSummary> testSummarys = summary.getValue();
			String suiteName = summary.getKey();
			testSummarys.sort(new Comparator<SuiteTestSummary>(){
				public int compare(SuiteTestSummary o1, SuiteTestSummary o2) {
					return (int)(o1.getStartTime() - o2.getStartTime());
				}
			});
			writer.println("<table style=\"align:center;word-wrap:break-word\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
			writer.println("<tr width=\"100%\"><td style=\"align:left;\">" + suiteName + "</td></tr></table>");
			for(SuiteTestSummary testSummary : testSummarys){
				writer.println("<table style=\"align:center;word-wrap:break-word\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
				writer.println("<tr><td colspan=\"4\" style=\"align:left;\"><font style=\"font-size:20px\">" + testSummary.getSuiteTestName() + "</font></td></tr>");
				writer.println("<trstyle=\"background:#F0FFF0;\">");
				suiteSummaryCell("#总计", null);
				suiteSummaryCell("#通过", null);
				suiteSummaryCell("#失败", null);
				suiteSummaryCell("#未执行（执行异常）", null);
				writer.println("</tr>\n<tr>");
				suiteSummaryCell(String.valueOf(testSummary.getAll()), null);
				suiteSummaryCell(String.valueOf(testSummary.getPass()), "background:#00FA9A");
				suiteSummaryCell(String.valueOf(testSummary.getFail()), "background:#CD5C5C");
				suiteSummaryCell(String.valueOf(testSummary.getSkip()), "background:#F0E68C");
				writer.println("</tr>");
				writer.println("</table>");
			}
		}
	}
	

	private void generateTestDetailReport(){
		int index = 1;
		List<TestCaseDetail> caseDetails = reportInfo.getCaseDetails();
		caseDetails.sort(new Comparator<TestCaseDetail>(){
			@Override
			public int compare(TestCaseDetail o1, TestCaseDetail o2) {
				return (int)(o1.getStartTime() - o2.getStartTime());
			}
		});
		writer.println("<br/><br/><table style=\"word-break:break-all;word-wrap:break-word;layout:fixed\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
		writer.println("<tr><th colspan=\"5\" style=\"color:#00CED1\"><font style=\"font-family:宋体;font-size:26px\">用例执行详情</font></th></tr></table>");
		writer.println("<table style=\"word-break:break-all; word-wrap:break-all;\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
		writer.println("<tr style=\"background:#FFC0CB\">");
		writer.println("<th width=\"5%\">用例编号</th>");
		writer.println("<th width=\"20%\">用例名称</th>");
		writer.println("<th width=\"5%\">执行状态</th>");
		writer.println("<th width=\"40%\">用例参数</th>");
		writer.println("<th width=\"30%\">抛出异常</th></tr>");
		for(TestCaseDetail caseDetail : caseDetails){
			String excpMessage = caseDetail.getThrowMessage();
			excpMessage = excpMessage.length() > 500 ? excpMessage.substring(0, 500) + "...": excpMessage;
/**			**********************************************************************************************************************
			 * 视testng执行case时使用的具体参数类型而决定是否需要进一步处理
			 */
			List<Object> caseParams = caseDetail.getParams();
			String message = "";
			if(caseParams != null && caseParams.size() != 0)
				if(caseParams.get(0) instanceof Map){
					@SuppressWarnings("unchecked")
					Map<String, String> realParams = (Map<String, String>) caseParams.get(0);
					message = realParams.get("message") == null ? realParams.get("errorMsg") : realParams.get("message");
				} else{
					message = ((CaseBean)caseParams.get(0)).getCASE_NAME();
				}
			//			**********************************************************************************************************************
			switch(caseDetail.getStatus()){
			case "Pass":
				writer.println("<tr style=\"background:#90EE90\">");
				break;
			case "Failure":
				writer.println("<tr style=\"background:#FF0000\">");
				break;
			case "Skip":
				writer.println("<tr style=\"background:#F0E68C\">");
				break;
			}
			writer.println("<td width=\"5%\"title=\"用例编号\">" + String.valueOf(index++) + "</td>");
			writer.println("<td width=\"20%\" title=\"用例名称\">" + caseDetail.getTestName() + " (title=" + (message == null ? "" : message) + ")</td>");
			writer.println("<td width=\"5%\" title=\"执行状态\">" + caseDetail.getStatus() + "</td>");
			writer.println("<td width=\"40%\" title=\"用例参数\">" + caseDetail.getParams()+ "</td>");
			writer.println("<td width=\"30%\" title=\"抛出异常\">" + excpMessage + "</td></tr>");
		}
		writer.println("</table>");
	}
	
	private void suiteSummaryCell(String content, String style){
		if(style != null){
			writer.println("<td width=\"25%\" style=\"" + style + "\">" + content + "</td>");
		} else{
			writer.println("<td width=\"25%\">" + content + "</td>");
		}
	}
	
	private void suiteSummaryTable(SuiteSummary ss){
		writer.println("<table style=\"word-break:break-all;word-wrap:break-word;layout:fixed\" width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"5\">");
		writer.println("<tr><td colspan=\"4\" style=\"align:left;\"><font style=\"font-size:20px\">" + ss.getSuiteName() + "</font></td></tr>");
		writer.println("<tr style=\"background:#F0FFF0;\">");
		suiteSummaryCell("#总计", null);
		suiteSummaryCell("#通过", null);
		suiteSummaryCell("#失败", null);
		suiteSummaryCell("未执行（执行异常）", null);
		writer.println("</tr>\n<tr>");
		suiteSummaryCell(String.valueOf(ss.getAll()), null);
		suiteSummaryCell(String.valueOf(ss.getPass()), "background:#00FA9A");
		suiteSummaryCell(String.valueOf(ss.getFailure()), "background:#CD5C5C");
		suiteSummaryCell(String.valueOf(ss.getSkip()), "background:#F0E68C");
		writer.println("</tr>");
		writer.println("</table>");
	}
}