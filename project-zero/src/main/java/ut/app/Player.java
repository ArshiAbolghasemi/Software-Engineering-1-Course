package ut.app;

import java.util.ArrayList;

public class Player 
{
    private final String name;

    private final ArrayList<Membership> memberships;

    public Player(String name) 
    {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid player name");
        }

        this.name = name;
        this.memberships = new ArrayList<Membership>();
    }

    public Player(String name, ArrayList<Membership> memberships)
    {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Invalid player name");
        }

        this.name = name;
        this.memberships = memberships;
    }

    public String toString()
    {
        return this.name + this.hashCode();
    }

    public void addMembership(Membership _membership) throws IllegalArgumentException
    {
        for (Membership membership : this.memberships) {
            if (
                _membership.hasOverlapWithMembershipInterval(
                    membership.getStart(), membership.getEnd())
            ) {
                throw new IllegalArgumentException("member ship has overlap with others!");
            }
        }

        this.memberships.add(_membership);
    }
    
}
