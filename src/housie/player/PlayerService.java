package housie.player;

public interface PlayerService {
    //Interface to define skeleton of a player
    // ('Observer' as per observer pattern)
    public void update();
    public void start();
    public void gameOver();
    public String getPlayerName();


}
