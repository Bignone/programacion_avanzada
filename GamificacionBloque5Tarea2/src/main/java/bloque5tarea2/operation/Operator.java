package bloque5tarea2.operation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class Operator {

	public static String operationString(String birthDate) {
		String res;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date d = sdf.parse(birthDate);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DATE);
			LocalDate l1 = LocalDate.of(year, month, date);
			LocalDate now1 = LocalDate.now();
			Period diff1 = Period.between(l1, now1);
			res = "You are " + diff1.getYears() + " years old";
		} catch (Exception e) {
			res = "Error: " + e.getMessage();
		}

		return res;
	}

}
