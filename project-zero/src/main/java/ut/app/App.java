package ut.app;

import util.Date;

public class App 
{
    public static void main( String[] args )
    {
        Date date = new Date(18, 11, 1402);
        System.out.println(date.nextDay());
    }
}
