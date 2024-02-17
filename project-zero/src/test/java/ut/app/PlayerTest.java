package ut.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.Date;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest extends TestCase
{
    public PlayerTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( PlayerTest.class );
    }

    public void testInvalidPlayerName()
    {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> {
                new Player("");
            }
        );

        assertEquals("Invalid player name", exception.getMessage());
    }

    public void testMembershipWithOverLap()
    {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> {
                Player player = new Player("test");

                player.addMembership(new Membership(
                    "membership1", 
                    new Date(10, 4, 1402), 
                    new Date(10, 6, 1402)
                ));

                player.addMembership(new Membership(
                    "membership2", 
                    new Date(10, 5, 1402), 
                    new Date(21, 5, 1402)
                ));
            }
        );

        assertEquals("member ship has overlap with others!", exception.getMessage());
    }

    public void testTeamMembershipCountDays()
    {
        Player player = new Player("test");

        player.addMembership(new Membership(
            "team1", 
            new Date(10, 4, 1402), 
            new Date(10, 6, 1402)
        ));

        player.addMembership(new Membership(
            "team2", 
            new Date(20, 6, 1402), 
            new Date(20, 8, 1402)
        ));

        player.addMembership(new Membership(
            "team1", 
            new Date(10, 9, 1402), 
            new Date(10, 11, 1402)
        ));

        assertEquals("Invalid team 1 membership days count", 
            player.getMembershipsDayCount("team1"), 122);
    }
}
