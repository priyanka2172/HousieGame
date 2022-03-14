package test.dealer;

import housie.game.HousieDeck;
import junit.framework.*;
import org.junit.Test;

import java.util.List;

public class HousieDeckTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        System.out.println("Setting it up!");
    }
    @Test
    public void testHouiseDeck(){
        List<Integer> noOfCards =  HousieDeck.generateDeckForHousie(5);
        assertEquals(noOfCards.size(),5);

    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("Running: tearDown");
    }
}
