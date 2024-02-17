package util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DateTest extends TestCase {
    
    public DateTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(DateTest.class);
    }

    public void testNextDay()
    {
        Date date = new Date(18, 11, 1402);
        Date nextDay = date.nextDay();
        assertEquals("Invalid day for next day date", nextDay.getDay(), 19);
        assertEquals("Invalid month for next day", nextDay.getMonth(), 11);
        assertEquals("Invalid year for next day", nextDay.getYear(), 1402);
    }

    public void testComparableDate()
    {
        Date date1 = new Date(18, 11, 1402);
        Date date2 = new Date(19, 10, 1402);

        int compareResult = date1.compareTo(date2);

        assertTrue("date 1 should be greater than date 2", compareResult > 0);
    }
}
