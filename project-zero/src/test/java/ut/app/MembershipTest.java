package ut.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.Date;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MembershipTest extends TestCase
{
    public MembershipTest(String testName)
    {
        super(testName);
    }

    public static Test suite()
    {
        return new TestSuite(MembershipTest.class);
    }

    public void testOverLapMembershipInterval()
    {
        Membership membership = new Membership(
            "membership", 
            new Date(10, 4, 1402), 
            new Date(10, 6, 1402)
        );

        assertTrue(membership.hasOverlapWithMembershipInterval(
            new Date(10, 5, 1402), 
            new Date(20, 5, 1402)
        ));
    }

    public void testMembershipCountDays()
    {
        Membership membership = new Membership(
            "membership", 
            new Date(10, 4, 1402), 
            new Date(10, 6, 1402)
        );

        assertEquals("Invalid member ship count days", 62, membership.getMembershipDaysCount());
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentMembership()
    {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> {
                new Membership(
                    "membership test", 
                    new Date(10, 11, 1402), 
                    new Date(10, 9, 1402)
                );
            }
        );

        assertEquals("Invalid member ship data", exception.getMessage());
    }
}
