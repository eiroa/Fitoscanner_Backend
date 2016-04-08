package ar.edu.unq.tip_eiroa_mauro.server.helpers;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateHelper {
	public static DateTime formatStringDate(String date){
		DateTimeFormatter dateDecoder = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSZ");		
		return  dateDecoder.parseDateTime(date);
	}
	
}
