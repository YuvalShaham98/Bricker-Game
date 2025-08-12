package bricker.brick_strategies;

import bricker.gameobjects.SecondPaddle;
import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * The ExtraPaddle class implements the CollisionStrategy interface
 * and provides a collision strategy that creates an extra paddle
 * when a collision with a brick is detected.
 *
 * <p>This strategy handles collisions by removing the first object (assumed
 * to be a brick) and creating a second paddle if it hasn't been created
 * already.</p>
 *
 * @see CollisionStrategy
 * @see BrickerGameManager
 */
public class ExtraPaddle implements CollisionStrategy {

    private final BrickerGameManager gameManager;

    /**
     * Constructs an ExtraPaddle collision strategy with the specified
     * game manager.
     *
     * @param brickerGameManager the game manager used to manage game objects
     */
    public ExtraPaddle(BrickerGameManager brickerGameManager) {
        this.gameManager = brickerGameManager;
    }

    /**
     * Handles the collision between two game objects by removing the
     * first object (assumed to be the brick) and creating a second paddle
     * if it hasn't been created already.
     *
     * @param object1 the first game object involved in the collision,
     *                assumed to be the brick
     * @param object2 the second game object involved in the collision
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {

        // Remove the first game object (assumed to be the brick) from the game
        this.gameManager.removeObject(object1);

        // Create a second paddle if it hasn't been created already
        if (!this.gameManager.isSecondPaddle()) {
            this.gameManager.createSecondPuddle();
        }
    }
}
