package housie.player;

import housie.game.HousieTable;

import java.util.ArrayList;
import java.util.List;

public class PlayerServiceImpl extends Thread implements PlayerService {
    private List<Integer> housieControllerGivenCard;
    private List <Integer> checkedCards; //cards that appeared in dealer's generated cards
    private int playerId; // ID of player as assigned by Controller
    private int checkedNumbers; //number of cards that appreared in dealer's generated cards
    private String name; //name of the player
    private HousieTable housieTable; //shared data


    //player constructor when No name is specified
    public PlayerServiceImpl(List <Integer> list, HousieTable housieTable, int id) {
        super("[Null Name]");
        this.housieControllerGivenCard = list;
        this.playerId = id;
        this.setPlayerName("[Null Name]");
        this.setCheckednumbers(0);
        this.housieTable=housieTable;
        this.checkedCards = new ArrayList<Integer>();
    }


    //Constructor when name is specified
    public PlayerServiceImpl(List<Integer> list, String name, HousieTable housieTable, int id) {
        super(name);
        this.housieControllerGivenCard = list;
        this.playerId = id;
        this.setPlayerName(name);
        this.setCheckednumbers(0);
        this.housieTable=housieTable;
        this.checkedCards = new ArrayList <Integer> ();
    }

    private void findnum(int num) { //finds a number in housieControllerGivenCard, and if found, removes it from hand and adds to checkedNumbers
        int index = this.housieControllerGivenCard.indexOf(num);
        if(index>=0) {
            this.checkedCards.add(this.housieControllerGivenCard.get(index));
            this.housieControllerGivenCard.remove(index);
            this.setCheckednumbers(this.getCheckednumbers() + 1);
        }
    }

    //Getters and Setters
    public int getCheckednumbers() {
        return checkedNumbers;
    }

    private void setCheckednumbers(int checkednumbers) {
        this.checkedNumbers = checkednumbers;
    }
    @Override
    public String getPlayerName() {
        return this.name;
    }
    private void setPlayerName(String name) {
        this.name = name;
    }

    //update method will read the shared data

    @Override
    public void update() {
        if(!this.housieTable.isGameOver()) {
            int num = this.housieTable.getLastNumber();
            this.findnum(num);
        }
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e) {
            System.out.println("Error in player delay for playerService - " + e.getStackTrace());
        }

        System.out.println(this);
        if(this.checkedNumbers==this.housieTable.getNumberOfWinningStrikes())
            this.housieTable.setWinnerPlayersList(playerId-1);
        this.housieTable.setIsPlayerPlayingCurrentRound(playerId-1);
        this.housieTable.notifyAll();
    }

    //Methods for multi-threading
    @Override
    public void start() {
        super.start();
    }
    public void gameOver() {
        try {
            this.join();
        } catch (InterruptedException e) {
            System.err.println("Error in " + this.name + "during Joining - " + e.getStackTrace());
        }
    }
    @Override
    public void run() {
        synchronized(housieTable) {
            while(!this.housieTable.isGameOver()) {
                while(!this.housieTable.isNewRound() || this.housieTable.getIsPlayerPlayingCurrentRound(playerId-1)) {
                    try {
                        this.housieTable.wait(); //waits while other thread has occupied the lock
                    }
                    catch(InterruptedException e) {
                        System.err.println("Error in player waiting - " + e.getStackTrace());
                    }
                }
                this.update(); //calls the update method to do the required process
            }
        }
    }

    @Override
    public String toString() {
        return "PlayerDetailsFromServiceCall [housieControllerGivenCard=" + housieControllerGivenCard + ", checkedCards=" + checkedCards + ", playerId=" + playerId
                + ", checkedNumbers=" + checkedNumbers + ", name=" + name + "]";
    }

}
