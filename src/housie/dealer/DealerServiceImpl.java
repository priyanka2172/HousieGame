package housie.dealer;

import housie.game.HousieTable;
import housie.player.PlayerService;

import java.util.*;

public class DealerServiceImpl extends Thread implements DealerService {
   private List<PlayerService> players;
   private HousieTable housieTable;
  private volatile static DealerServiceImpl gameDealer; //for singleton pattern

    private DealerServiceImpl(HousieTable housieTable) { //Private Constructor that creates empty list of participants
        super("Dealer");
        this.housieTable=housieTable;
        this.players = new ArrayList <PlayerService>();
    }
    private DealerServiceImpl(List <PlayerService> players, HousieTable housieTable) { //Private Constructor that takes in an ArrayList of participants
        super("Moderator");
        this.housieTable=housieTable;
        this.players = players;
    }
    public static DealerServiceImpl createDealer (List <PlayerService> players, HousieTable table) { //method to create singleton object with participants
        if(gameDealer == null) {
            synchronized(DealerService.class) {
                if(gameDealer == null) {
                    gameDealer = new DealerServiceImpl(players,table);
                }
            }
        }
        return gameDealer;
    }

    public static DealerServiceImpl createDealer (HousieTable table) { //method to create singleton object without parameters
        if(gameDealer == null) {
            synchronized(DealerServiceImpl.class) {
                if(gameDealer == null) {
                    gameDealer = new DealerServiceImpl(table);
                }
            }
        }
        return gameDealer;
    }


    @Override
    public void registerHousiePlayer(PlayerService p) {
        players.add(p);
    }

    @Override
    public void removeHousiePlayer(PlayerService p) {
        int index= players.indexOf(p);
        if(index>=0) {
            players.remove(index);
        }
    }

    @Override
    public void notifyObservers() { //the notify function which subject uses to notify observers
        this.housieTable.notifyAll();
    }

    public int generateRandomNumber() {
        Random rand = new Random();
        return (rand.nextInt(51));
    }

    //Methods for multi-threading
    public void startDealer() {
        super.start();
    }
    public void gameOver() {
        try {
            this.join();
        } catch (InterruptedException e) {
            System.err.println("Error in Dealer Joining - " + e.getStackTrace());
        }
    }
    @Override
    public void run() {
        synchronized(housieTable) { //take lock of table
            while(!this.housieTable.isGameOver()) { //check if game is over
                this.housieTable.setNewRound(false);
                this.housieTable.setCheckedFlags(false);
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e) {
                    System.err.println("Error in Dealer delay - " + e.getStackTrace());
                }
                int num = this.generateRandomNumber();
                this.housieTable.insertValue(num); //insert number to table's list
                this.housieTable.setNewRound(true); //declares new round open
                System.out.println("\n Currently Round " + this.housieTable.getCurrentRound() + " Started...\n" + "Dealer generated " + num + "\n" + housieTable);
                this.notifyObservers(); //calls the notifyObservers method
                while(this.housieTable.isPlayersPlaying()) {
                    try {
                        Thread.sleep(1000);
                        this.housieTable.wait(); //waits while other threads release the lock
                    }
                    catch(InterruptedException e) {
                        System.err.println("Interrupted Exception in Moderator - " + e.getStackTrace());
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "DealerService [Players=" + players + "]";
    }

}


