package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;


/**
 * the strategy removes the brick from game
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    /**
     *fields of class
     */
    private final BrickerGameManager gameManager;

    /**
     * constructor
     * @param brickerGameManager - the game manager
     */
    public  BasicCollisionStrategy(BrickerGameManager brickerGameManager ){
        this.gameManager = brickerGameManager;
    }


    /**
     * the behavior in collision
     * @param object1 that got collided
     * @param object2 object that collided
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameManager.removeObject(object1);
    }
}
