package swiftpass.report;

public class MainTest {

	public static void main(String[] args) {
		System.out.println(checkPass("123454345"));
	}
	public static boolean checkPass(String password){
		int number = password.matches("[0-9]+")?1:0;
		int string = password.matches("[a-zA-Z]+")?1:0;
		int length = password.length()<8 ||password.length()>22?1:0;
		boolean Ian = (number+string+length) ==0;
		return Ian;
	}
}
