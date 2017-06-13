package swiftpass.testcase.cle;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import swiftpass.page.cle.CleResultStatPage;
import swiftpass.page.exceptions.CleResultStatException;
import swiftpass.testcase.TestCaseImpl;
import swiftpass.testcase.testdata.cle.CleStatCaseBean;
import swiftpass.testcase.testdata.cle.CleStatExpectedResultHelper;
import swiftpass.testcase.testdata.cle.CleStatResultBean;

public class CleResStatTestCaseImpl extends TestCaseImpl implements CleResStatTestCase {
	private CleResultStatPage csp;
	
	public CleResStatTestCaseImpl(WebDriver driver) {
		super(driver);
		csp = new CleResultStatPage(this.driver);
	}

	@Override
	public void cleResultStatSearch(CleStatCaseBean cb) {
		CleStatResultBean expected = CleStatExpectedResultHelper.queryCleResult(cb);
		CleStatResultBean actual = null;
		try{
			csp.setCleTime(cb.getCleBeginTime(), cb.getCleEndTime());
			csp.selectCleanStatus(cb.getCleStatus());
			csp.selectCleanType(cb.getCleType());
			if(cb.getIsSelectPayType().equals("true")){
				csp.selectPayType(cb.getIsSelectPayType(), cb.getPtNameOrIdItem(), cb.getPayTypeName(), cb.getPayTypeId(), cb.getIsConfirmSelectPayType());
			} else{
				csp.directSetPayType(cb.getPayTypeName(), cb.getPayTypeId());
			}
			if(cb.getIsSelectChannel().equals("true")){
				csp.selectChannel(cb.getIsSelectChannel(), cb.getChNameOrIdItem(), cb.getChannelName(), cb.getChannelId(), cb.getIsConfirmSelectChannel());
			} else{
				csp.directSetChannel(cb.getChannelName(), cb.getChannelId());
			}
			csp.setIsOnlyQueryChannel(cb.getIsOnlyQueryChannel());
			if(cb.getIsSelectMerchant().equals("true")){
				csp.selectMerchant(cb.getIsSelectMerchant(), cb.getMchNameOrIdItem(), cb.getMerchantName(), cb.getMerchantId(), cb.getIsSelectMerchant());
			} else{
				csp.directSetMerchant(cb.getMerchantName(), cb.getMerchantId());
			}
			csp.setIncomeAccount(cb.getIncomeAccount());
			csp.setSerialNO(cb.getSerialNO());
			csp.setFailReason(cb.getFailReason());
			csp.clickSearch();
			actual = new CleStatResultBean(csp.getPageResult());
		} catch(CleResultStatException ex){
			logger.error(ex);
			Assert.assertEquals(ex.getMessage(), cb.getMessage());
			return;
		}
		logger.debug("Actual: " + actual);
		logger.debug("Expected: " + expected);
		Assert.assertEquals(actual.equals(expected), true);
	}
	
}
