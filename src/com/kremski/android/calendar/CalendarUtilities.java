package com.kremski.android.calendar;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Adrian Kremski
 */
public class CalendarUtilities {
	
	private final static Calendar helperCalendar = GregorianCalendar.getInstance();
	
	public static int getYearFromDate(Date date) {
		helperCalendar.setTime(date);
		return helperCalendar.get(Calendar.YEAR);
	}
	
	public static int getMonthFromDate(Date date) {
		helperCalendar.setTime(date);
		return helperCalendar.get(Calendar.MONTH);
	}

	public static int getDayFromDate(Date date) {
		helperCalendar.setTime(date);
		return helperCalendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static List<Day> getLastWeekBeforeSpecifiedMonth(Calendar currentMonth) {
		List<Day> days = new LinkedList<Day>();
		
		generateDaysFromLastWeekBeforeSpecifiedMonth(days, 
				getCalendarSetToLastDayOfPreviousMonth(copyCalendar(currentMonth)));
		
		Collections.reverse(days);
		return days;
	}
	
	public static Calendar getCalendarSetToLastDayOfPreviousMonth(Calendar currentMonth) {
		currentMonth = copyCalendar(currentMonth);
		currentMonth.add(Calendar.MONTH, -1);
		currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
		return currentMonth;
	}
	
	public static List<Day> getAllDaysFromSpecifiedMonth(Calendar currentMonth) {
		currentMonth = copyCalendar(currentMonth);
		currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		List<Day> days = new LinkedList<Day>();
		generateAllDaysFromSpecifiedMonth(days, currentMonth);
		return days;
	}
	
	public static List<Day> getFirstWeekAfterSpecifiedMonth(Calendar currentMonth) {
		currentMonth = copyCalendar(currentMonth);
		List<Day> days = new LinkedList<Day>();
		
		generateDaysFromFirstWeekAfterSpecifiedMonth(days, getCalendarSetToFirstDayOfNextMonth(currentMonth));
		return days;
	}
	
	public static Calendar getCalendarSetToFirstDayOfNextMonth(Calendar currentMonth) {
		currentMonth = copyCalendar(currentMonth);
		currentMonth.add(Calendar.MONTH, 1);
		currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMinimum(Calendar.DAY_OF_MONTH));
		return currentMonth;
	}
	
	public static List<Day> getDaysRangeFromSpecifiedMonth(int start, int end, Calendar currentMonth) {
		currentMonth = copyCalendar(currentMonth);
		currentMonth.set(Calendar.DAY_OF_MONTH, start);
		List<Day> days = new LinkedList<Day>();
		generateDaysRangeFromSpecifiedMonth(days, start, end, currentMonth);
		return days;
	}

	public static Calendar copyCalendar(Calendar calendarToCopy) {
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTime(calendarToCopy.getTime()); 
		return newCalendar;
	}
	
	private static void generateDaysFromLastWeekBeforeSpecifiedMonth(List<Day> days, Calendar endOfMonth) {
		for(int i = convertDay(endOfMonth.get(Calendar.DAY_OF_WEEK)); i >= convertDay(Calendar.MONDAY); --i) {
			days.add(new Day(endOfMonth));
			endOfMonth.add(Calendar.DAY_OF_MONTH, -1);
		}
	}
	
	private static void generateAllDaysFromSpecifiedMonth(List<Day> days, Calendar currentMonth) {
		int lastDay = currentMonth.getActualMaximum(Calendar.DATE);
		for(int i = 0; i < lastDay; ++i) {
			days.add(new Day(currentMonth));
			currentMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	private static void generateDaysFromFirstWeekAfterSpecifiedMonth(List<Day> days, Calendar nextMonth) {
		for(int i = convertDay(nextMonth.get(Calendar.DAY_OF_WEEK)); i <= convertDay(Calendar.SUNDAY); ++i) {
			days.add(new Day(nextMonth));
			nextMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	private static void generateDaysRangeFromSpecifiedMonth(List<Day> days, int start, int end, Calendar currentMonth) {
		for(int i = start; i <= end; ++i) {
			days.add(new Day(currentMonth));
			currentMonth.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	
	private static int convertDay(int day) {
		day = day-1;
		return day == 0 ? 7 : day;
	}
}
