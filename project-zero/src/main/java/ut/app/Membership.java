package ut.app;

import java.sql.Date;
import java.util.function.Function;

public class Membership
{
    private final String teamName;

    private final Date start;

    private final Date end;

    public Membership(String teamName, Date start, Date end)
    {
        if (teamName.isEmpty() || start.compareTo(end) < 0) {
            throw new IllegalArgumentException("Invalid member ship interval");
        }

        this.teamName = teamName;
        this.start = start;
        this.end = end;
    }

    public String toString()
    {
        return this.teamName + '/' + this.start + '-' + this.end;
    }

    public boolean hasOverlapWithMembershipInterval(Date start, Date end)
    {
        return (this.start.compareTo(end) <= 0 && this.end.compareTo(start) >= 0);
    }
}