package gradle_jdbc_teacher;

import java.util.Date;

public class Main {
	public static void main(String[] args) {
		String dat = String.format("%1$tF - %1$tT", new Date());
		System.out.println(dat);
	}
}
