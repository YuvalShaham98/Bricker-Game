package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * The ExtraLife class implements the CollisionStrategy interface
 * and provides a collision strategy that grants an extra life
 * when a collision with a brick is detected.
 *
 * <p>This strategy handles collisions by removing the first object (assumed
 * to be a brick) and creating a fallen heart at the collision point,
 * which represents an extra life.</p>
 *
 * @see CollisionStrategy
 * @see BrickerGameManager
 */
public class ExtraLife implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    /**
     * Constructs an ExtraLife collision strategy with the specified
     * game manager.
     *
     * @param gameManager the game manager used to manage game objects
     */
    public ExtraLife(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Handles the collision between two game objects by removing the
     * first object and creating a fallen heart at the location of the
     * collision.
     *
     * @param object1 the first game object involved in the collision,
     *                assumed to be the brick
     * @param object2 the second game object involved in the collision
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {

        // Remove the first game object (assumed to be the brick) from the game
        this.gameManager.removeObject(object1);

        // Create a fallen heart at the center of the first game object
        this.gameManager.fallenHeart(object1.getCenter());
    }
}
