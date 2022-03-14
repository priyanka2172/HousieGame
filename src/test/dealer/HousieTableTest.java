package test.dealer;

import housie.game.HousieTable;
import junit.framework.TestCase;
import org.junit.Test;

public class HousieTableTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        System.out.println("Setting it up!");
    }

    @Test
    public void testDefaultHouiseTable() {
        HousieTable ht = new HousieTable(5);
        assertEquals(ht.getTotalRounds(), 10);
        assertEquals(ht.getCurrentRound(), 0);

    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("Running: tearDown");
    }
}
