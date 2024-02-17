package ut.app;

import util.Date;

public class Membership
{
    private final String teamName;

    private final Date start;

    private final Date end;

    public Membership(String teamName, Date start, Date end)
    {
        if (teamName.isEmpty() || start.compareTo(end) >= 0) {
            throw new IllegalArgumentException("Invalid member ship data");
        }

        this.teamName = teamName;
        this.start = start;
        this.end = end;
    }

    public Date getStart()
    {
        return this.start;
    }

    public Date getEnd()
    {
        return this.end;
    }

    public String toString()
    {
        return this.teamName + '/' + this.start + '-' + this.end;
    }

    public boolean hasOverlapWithMembershipInterval(Date start, Date end)
    {
        return (this.start.compareTo(end) <= 0 && this.end.compareTo(start) >= 0);
    }

    public int getMembershipDaysCount()
    {
        int membershipDaysCount = 0;
        Date current = this.start;
        while (current.compareTo(this.end) != 0) {
            membershipDaysCount++;
            current = current.nextDay();
        }

        return membershipDaysCount;
    }
}