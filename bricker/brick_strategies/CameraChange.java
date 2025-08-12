package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;


/**
 * the strategy removes the brick, and changes the "camera" of the game to focus on the ball
 */
public class CameraChange implements CollisionStrategy {

    /**
     *fields of class
     */
    private final BrickerGameManager gameManager;


    /**
     * constructor
     * @param gameManager - the game manager
     */

    public CameraChange(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * the behavior in collision -> change the point of view in the game
     * @param object1 that got collided
     * @param object2 object that collided
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameManager.removeObject(object1);
        this.gameManager.setCameraOn(object2);
    }
}
