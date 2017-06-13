package swiftpass.testcase.casebeans;
import swiftpass.testcase.CaseBean;
import swiftpass.testcase.annotations.CaseBeanField;
/**
 * 
 * @author sunhaojie
 * date 2017-04-01
 */
public class StoreAddCaseBean extends CaseBean {
	private static final long serialVersionUID = 778842876598047181L;
	@CaseBeanField(desc="用例名称",index=0) private String CASE_NAME;
	@CaseBeanField(desc="选择大商户名称或编号",index=1) private String bigMchItem;
	@CaseBeanField(desc="选择的大商户的具体的名称和编码值",index=2) private String bigMchNameOrId;
	@CaseBeanField(desc="部门名称",index=3) private String departName;
	@CaseBeanField(desc="门店名称",index=4) private String storeName;
	@CaseBeanField(desc="门店类型",index=5) private String storeType;
	@CaseBeanField(desc="是否选择大商户",index=6) private String isSelectBigMch;
	@CaseBeanField(desc="是否确认选择大商户",index=7) private String isConfirmSelectBigMch;
	@CaseBeanField(desc="是否保存",index=8) private String isSave;
	@CaseBeanField(desc="异常信息",index=9) private String errorMsg;
	
	public StoreAddCaseBean(){}
	
	public String getCASE_NAME() {
		return CASE_NAME;
	}
	public void setCASE_NAME(String cASE_NAME) {
		CASE_NAME = cASE_NAME;
	}
	public String getBigMchItem() {
		return bigMchItem;
	}
	public void setBigMchItem(String bigMchItem) {
		this.bigMchItem = bigMchItem;
	}
	public String getBigMchNameOrId() {
		return bigMchNameOrId;
	}
	public void setBigMchNameOrId(String bigMchNameOrId) {
		this.bigMchNameOrId = bigMchNameOrId;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	public String getIsSelectBigMch() {
		return isSelectBigMch;
	}
	public void setIsSelectBigMch(String isSelectBigMch) {
		this.isSelectBigMch = isSelectBigMch;
	}
	public String getIsConfirmSelectBigMch() {
		return isConfirmSelectBigMch;
	}
	public void setIsConfirmSelectBigMch(String isConfirmSelectBigMch) {
		this.isConfirmSelectBigMch = isConfirmSelectBigMch;
	}
	public String getIsSave() {
		return isSave;
	}
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}	
}
