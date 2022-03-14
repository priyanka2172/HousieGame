package housie.game;

import java.util.*;

public class HousieTable {
    //This Class's responsibility is sharing platform/data between Players and Dealer
    private int totalRounds;//Total Number of Rounds
    private int currentRound;//current round  indicator
    private boolean newRound;//whether new Round started for player
    private boolean isGameOver;//Flag to check if one Pllayer won or all rounds Completed
    private int totalNumberOfPLayers;// Total Number of players playing
    public int numberOfWinningStrikes;//Num for Strikes for winning
    List<Integer> numberList = new ArrayList<>();//holding the numbers generated by dealer
    private List<Boolean> isPlayerPlayingCurrentRound;
    private List<Boolean> winnerPlayersList;


    public HousieTable(int totalRounds,int totalNumberOfPLayers,int numberOfWinningStrikes ){
        this.totalRounds = totalRounds;
        this.currentRound =0;
        this.setNewRound(false);
        this.totalNumberOfPLayers = totalNumberOfPLayers;
        this.isPlayerPlayingCurrentRound = new ArrayList <Boolean> ();
        this.winnerPlayersList =new ArrayList <Boolean> ();
        this.numberOfWinningStrikes = numberOfWinningStrikes;
        for(int i=0;i< totalNumberOfPLayers; i++ ){
            this.isPlayerPlayingCurrentRound.add(false);
            this.winnerPlayersList.add(false);
        }

    }
    //Default Setting for holding HousieTable
    public HousieTable(int totalNumberOfPLayers) {
        this.totalRounds = 10;
        this.currentRound = 0;
        this.setNewRound(false);
        this.totalNumberOfPLayers = totalNumberOfPLayers;
        this.isPlayerPlayingCurrentRound = new ArrayList<Boolean>();
        this.numberOfWinningStrikes = 3;
        this.winnerPlayersList = new ArrayList<Boolean>();
        for (int i = 0; i < totalNumberOfPLayers; i++) {
            this.isPlayerPlayingCurrentRound.add(false);
            this.winnerPlayersList.add(false);
        }
    }

  //getters and setters

   public int getCurrentRound() { return currentRound;}
   public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }
   public int getTotalRounds() {return totalRounds; }
   public List<Integer> getNumberList(){ return numberList;}
   public boolean isNewRound() {
        return newRound;
    }
   public void setNewRound(boolean newRound) {
        this.newRound = newRound;
    }
   public void setIsGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
    public void setCheckedFlags(boolean b) {
        for(int i=0;i<this.totalNumberOfPLayers;i++)
            this.isPlayerPlayingCurrentRound.set(i,false);
    }
    public boolean getIsPlayerPlayingCurrentRound(int playerId) {
        return this.isPlayerPlayingCurrentRound.get(playerId);
    }
    public void setIsPlayerPlayingCurrentRound(int playerId) {
        this.isPlayerPlayingCurrentRound.set(playerId, true);
    }
    public int getNumberOfWinningStrikes() {
        return this.numberOfWinningStrikes;
    }
    public void setWinnerPlayersList(int value) {
        this.winnerPlayersList.set(value, true);
    }
    public int findHousieWinner(){
        int winnerIndex = this.winnerPlayersList.indexOf(true);
        return winnerIndex;
    }
    //This method will let the dealer all random generted Numbers
    public synchronized void insertValue(int num) {
        this.setCurrentRound(this.getCurrentRound()+1);
        this.numberList.add(num);
    }
    //used by players to retrieve last inserted value in the list of numbers
    public synchronized int getLastNumber() {
        int val = this.numberList.get(this.numberList.size()-1);
        return val;
    }

    public boolean playersPlayingFrRound() { //checks if all the players have finished playing the ongoing round
        if(this.isPlayerPlayingCurrentRound.contains(false))
            return true;
        else
            return false;
    }

    public boolean isGameOver() { //Game ends when either rounds are completed or someone wins
        if(this.currentRound==this.totalRounds || this.winnerPlayersList.contains(true))
            return true;
        else
            return false;
    }

    public boolean isPlayersPlaying() { //checks if all the players have finished playing the ongoing round
        if(this.isPlayerPlayingCurrentRound.contains(false))
            return true;
        else
            return false;
    }

    @Override
    public String toString(){
        return "Table [totalRounds=" + totalRounds + ", currentround=" + currentRound + ", numberList=" + numberList + ", totalNumberOfPLayers="
                + totalNumberOfPLayers + ", numberOfWinningStrikes=" + numberOfWinningStrikes + "]";
    }



}

