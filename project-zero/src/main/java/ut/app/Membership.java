package ut.app;

import java.sql.Date;

public class Membership
{
    private final String teamName;

    private final Date start;

    private final Date end;

    public Membership(String teamName, Date start, Date end)
    {
        if (start.compareTo(end) < 0) {
            throw new IllegalArgumentException("Invalid member ship interval");
        }

        this.teamName = teamName;
        this.start = start;
        this.end = end;
    }
}