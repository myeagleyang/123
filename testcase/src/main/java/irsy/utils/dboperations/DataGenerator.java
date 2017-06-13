package irsy.utils.dboperations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;

import swiftpass.page.enums.BankAccountType;
import swiftpass.utils.SwiftPass;

public class DataGenerator {
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 基于当前日期，指定日期差，生成当前日期
	 * @param gapY	年差，负值为当前年份的前X年，正为当前年份的后X年。下同
	 * @param gapM
	 * @param gapD
	 * @return 返回的日期格式为yyyy-MM-dd HH:mm:ss格式
	 */
	public static String generateDateBaseOnNow(int gapY, int gapM, int gapD){
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.YEAR, gapY);
		c.add(Calendar.MONTH, gapM);
		c.add(Calendar.DAY_OF_MONTH, gapD);
		
		return dateFormatter.format(c.getTime());
	}
	/**
	 * 生成有效的手机随机号码
	 * @return
	 */
	public static String generatePhone(){
		return "13" + RandomStringUtils.randomNumeric(9);
	}
	/**
	 * 生成有效的座机随机电话号码
	 * @return
	 */
	public static String generateTel(){
		return "0755" + RandomStringUtils.randomNumeric(8);
	}
	/**
	 * 生成有效的随机网址
	 * @return
	 */
	public static String generateWebsite(){
		return "www." + RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(2) + ".com";
	}
	/**
	 * 生成有效的随机邮箱
	 * @return
	 */
	public static String generateEmail(){
		return RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(2) + "@" +
			RandomStringUtils.randomAlphabetic(3) + ".com";
	}
	/**
	 * 生成一个随机中文名
	 * @return
	 */
	public static String generateZh_CNName(){
		String[] surnames = "赵、钱、孙、李、周、吴、郑、王、甲、乙、丙、丁".split("、");
		String[] names = "冬虫、夏草、蒹葭、苍苍、儒生、春雨、秋瑾、柳巷、桃园、鲲鹏、大雕".split("、");
		return surnames[SwiftPass.getRandomNumber(surnames.length)] + names[SwiftPass.getRandomNumber(names.length)];
	}
	/**
	 * 生成一个随机的身份证号码
	 * @return
	 */
	public static String generateIdCardCode(){
		String[] addrs = "360731,360732,360733,360734,360735,360736,360737,360738,360739,360730".split(",");
		String birth = new SimpleDateFormat("yyyyMMdd").format(new Date());
		return addrs[SwiftPass.getRandomNumber(addrs.length)] + birth + RandomStringUtils.randomNumeric(4);
	}
	/**
	 * 生成一个随机的银行卡号
	 * @return
	 */
	public static String generateBankCode(){
		return "622202" + RandomStringUtils.randomNumeric(12);
	}
	/**
	 * 生成一个新的渠道名称
	 * @return
	 */
	public static String generateNewChannelName(){
		return "自动化渠道" + RandomStringUtils.randomAlphanumeric(4);
	}
	public static String generateAccountId(){
		return "155" + RandomStringUtils.randomNumeric(6);
	}
	
	public static void main(String...strings){
		System.out.println(BankAccountType.ENTERPRISE.getPlainText());
		System.out.println(generateEmail());
		System.err.println(generateDateBaseOnNow(-2, -5, 0));
		String[] a = "hello\n".split("\\n");
		String[] b = "hello\n".trim().split("\\n");
		System.out.println(ArrayUtils.addAll(a, b)[2]);
		System.out.println(a.length + " : " + b.length);
	}
}
