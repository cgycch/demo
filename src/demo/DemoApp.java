package demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DemoApp {
	
	public static void main(String[] args) throws ParseException {
		//String frequency = "DAILY";
		String frequency = "WEEKLY";
		//String frequency = "MONTHLY";
		//String frequency = "QUARTERLY";
		//String frequency = "ANNUALLY";
		
		//String frequencyValue = "FBD";
		String frequencyValue = "Tues";
		//String frequencyValue = "25";
		System.out.println("frequency: " + frequency);
		System.out.println("frequencyValue: " + frequencyValue);

		if(frequency.equals("DAILY")) {
			//周一到周五
			if(isWeekDay()) {
				sendMail("daily send");
			}
		}else if(frequency.equals("WEEKLY")) {
			//周一 到周五中的某一天
			if(isWeekDay(frequencyValue)) {
				sendMail("weekly send");
			}
		}else if(frequency.equals("MONTHLY")) {
			//月份某一天
			//FBD/DEFAULT/NA/(01-31)
			String[] monthAry = frequencyValueProcess(frequency, frequencyValue);
			if(isMonthDay(monthAry)) {
				sendMail("send monthAry...");			
			}
		}else if(frequency.equals("QUARTERLY")) {
			//季度某一天，1,4,7,10
			//01-01,04-01,07-01,10-01
			String[] monthAry = frequencyValueProcess(frequency, frequencyValue);
			if(isMonthDay(monthAry)) {
				sendMail("send quarterly...");
			}
		}else if(frequency.equals("ANNUALLY")) {
			//一年某一天
			//08-16
			String[] monthAry = frequencyValueProcess(frequency, frequencyValue);
			if(isMonthDay(monthAry)) {
				sendMail("send annually mail on some day of year");
			}
		}else {
			//暂未支持
			sendMail("not found frequency...");
		}
		
	}
	
	
	/**
	 * 格式化处理
	 * @param frequencyValue
	 * @return
	 * @throws ParseException 
	 */
	private static String[] frequencyValueProcess(String frequency, String frequencyValue) throws ParseException {
		//DEFAULT/NA/FBD/(01--31)/(01-01,04-01,07-01,10-01)
		String[] values= null;
		String yearPrefix = getYear()+"-";
		switch (frequency) {
		case "DAILY":
			//values = new String[] {frequencyValue};
			break;
		case "WEEKLY":
			if("DEFAULT".equals(frequencyValue) || "NA".equals(frequencyValue)) {
				values = new String[] {"Mon"};
			}else {
				values = new String[] {frequencyValue};
			}
			break;
		case "MONTHLY":
			if("DEFAULT".equals(frequencyValue) || "NA".equals(frequencyValue) || "FBD".equals(frequencyValue)) {
				//values = new String[] {"01"};
				frequencyValue = "01";
			}
			frequencyValue = getYear() + "-" + getMonth() + "-" + frequencyValue;
			//更正到当前临近的工作日日期
			values = adjustToWorkDay(frequencyValue);
			break;
		case "QUARTERLY":
			if("DEFAULT".equals(frequencyValue) || "NA".equals(frequencyValue) || "FBD".equals(frequencyValue)) {
				values = new String[] {yearPrefix+"01-01",yearPrefix+"04-01",
						yearPrefix+"07-01",yearPrefix+"10-01"};
			}else {
				values = frequencyValue.split(",");
				for (int i = 0; i < values.length; i++) {
					values[i] += yearPrefix ;
				}
			}
			//更正到当前临近的工作日日期
			values = adjustToWorkDay(values);
			break;
		case "ANNUALLY":
			if("DEFAULT".equals(frequencyValue) || "NA".equals(frequencyValue) || "FBD".equals(frequencyValue)) {
				values = new String[] {yearPrefix + "01-01"};
			}else {
				values = frequencyValue.split(",");
				for (int i = 0; i < values.length; i++) {
					values[i] += yearPrefix ;
				}
			}
			//更正到当前临近的工作日日期
			//更正到当前临近的工作日日期
			values = adjustToWorkDay(values);
			break;

		default:
			break;
		}
		return values;
	}
	

	/**
	 * [2018-01-01,2018-04-01...]
	 * @param frequencyValue
	 * @return
	 * @throws ParseException 
	 */
	private static String[] adjustToWorkDay(String... frequencyValue) throws ParseException {
		int idx = 0;
		int offset = 0;
		Calendar calendar = Calendar.getInstance();
		for (String value : frequencyValue) {
			System.out.print("old value:"+value);
			Date  date = parseToDate(value);
			calendar.setTime(date);
			offset = 0;
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				offset = 2;
			}else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				offset = 1;
			}
			if(offset > 0) {
				calendar.add(Calendar.DAY_OF_MONTH, offset);
				frequencyValue[idx] = fomartToDate(calendar.getTime());
			}
			System.out.println("  new value:"+frequencyValue[idx]);
			idx++;
		}
		return frequencyValue;
	}
	/**
	 * 日期字符转Date
	 * @param value
	 * @return
	 * @throws ParseException 
	 */
	private static String fomartToDate(Date date) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 日期字符转Date
	 * @param value
	 * @return
	 * @throws ParseException 
	 */
	private static Date parseToDate(String value) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(value);
	}
	/**
	 * 获取当前年份
	 * @return
	 */
	private static String getYear() {
		return "2018";
	}

	/**
	 * 获取当前月份
	 * @return
	 */
	private static String getMonth() {
		return "09";
	}
	/**
	 * 是否工作日
	 * @param week
	 * @return
	 */
	private static boolean isWeekDay() {
		return isWeekDay(null);
	}
	/**
	 * 是否工作日
	 * @param week
	 * @return
	 */
	private static boolean isWeekDay(String week) {
		boolean isWeek = false;
		String toDay = getWeekDay();
		if(week == null) {
			String[] weeks = new String[] {"Mon", "Tues", "Wed", "Thur", "Fri"};
			for (String w : weeks) {
				if(toDay.equals(w)) {
					isWeek = true;
					break;
				}
			}
		}
		if(toDay.equals(week)) {
			isWeek = true;
		}
		return isWeek;
	}
	/**
	 * 获取星期缩写
	 * @return
	 */
	private static String getWeekDay() {
		Calendar calendar =Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());//todo
		int idx = calendar.get(Calendar.DAY_OF_WEEK)-1;
		final String dayNames[] = { "Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat" }; 
		return dayNames[idx];
	}
	/**
	 * 判断当前日期是否属于monthAry所指
	 * @param monthAry
	 * @return
	 */
	private static boolean isMonthDay(String... monthAry) {
		boolean isDay = false;
		String curday = getDate();
		for (String day : monthAry) {
			if(curday.equals(day)) {
				isDay = true;
				break;
			}
		}
		return isDay;
	}
	
	private static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	/**
	 * 
	 * @param filePath
	 */
	private static void sendMail(String filePath) {
		System.out.println("send mail:" + filePath);
	}
	
	private enum Frequency{
		DAILY(0,"DAILY"),WEEKLY(1,"WEEKLY"),MONTHLY(2,"MONTHLY"),QUARTERLY(3,"QUARTERLY"), ANNUALLY(4,"ANNUALLY");
		private int idx;
		private String name;
		private Frequency(int idx, String name) {
			this.idx = idx;
			this.name = name;
		}
		public int getIdx() {
			return idx;
		}
		public void setIdx(int idx) {
			this.idx = idx;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public static Frequency getFrequency(int idx) {
			return values()[idx];
		}
	}
	
}
