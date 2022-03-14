package housie.game;

import java.util.*;

public class HousieDeck {
    //responsibility: providing cardsfor the players
    //this class should be responsible for grouping number of cards and sending back to controller and static for maining one copy as needed

    public static List<Integer> generateDeckForHousie(int numbersForCards) {
        List<Integer> housieCard = new ArrayList<>();
        Random generateRandomNumbers = new Random();
        for (int i = 0; i < numbersForCards; i++) {
            housieCard.add(generateRandomNumbers.nextInt(51));
        }
        return housieCard;

    }


}
