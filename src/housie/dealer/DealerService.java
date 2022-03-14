
package housie.dealer;

import housie.player.PlayerService;

public interface DealerService {
    public void startDealer();
    public void registerHousiePlayer(PlayerService p);
    public void removeHousiePlayer(PlayerService p);
    public void notifyObservers();
    public void gameOver();
}
