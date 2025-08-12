package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * The ExtraBalls class implements the CollisionStrategy interface
 * and provides a collision strategy that creates additional balls
 * when a collision with a brick is detected.
 *
 * <p>This strategy handles collisions by removing the first object (assumed
 * to be a brick) and creating two new pucks at the collision point.</p>
 *
 * @see CollisionStrategy
 * @see BrickerGameManager
 */
public class ExtraBalls implements CollisionStrategy {

    //Constants
    private static final int DEFAULT_NUM_PUCKS =2;

    //fields
    private final BrickerGameManager gameManager;


    /**
     * Constructs an ExtraBalls collision strategy with the specified
     * game manager.
     *
     * @param brickerGameManager the game manager used to manage game objects
     */
    public ExtraBalls(BrickerGameManager brickerGameManager) {
        this.gameManager = brickerGameManager;
    }

    /**
     * Handles the collision between two game objects by removing the
     * first object and creating two new pucks at the location of the
     * collision.
     *
     * @param object1 the first game object involved in the collision,
     *                assumed to be the brick
     * @param object2 the second game object involved in the collision
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        // Log the collision detection with a brick
        System.out.println("collision with brick detected");

        // Remove the first game object (assumed to be the brick) from the game
        this.gameManager.removeObject(object1);

        // Create two new pucks at the center of the first game object
        this.gameManager.createPuck(DEFAULT_NUM_PUCKS, object1.getCenter());
    }
}

