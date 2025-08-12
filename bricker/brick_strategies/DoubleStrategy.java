package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;


/**
 * The DoubleStrategy class implements the CollisionStrategy interface
 * and provides a mechanism to handle collisions using two different
 * collision strategies.
 */
public class DoubleStrategy implements CollisionStrategy {

    private CollisionStrategy collisionStrategy1;
    private CollisionStrategy collisionStrategy2;
    private BrickerGameManager gameManager;

    /**
     * Constructs a DoubleStrategy with the specified game manager.
     *
     * @param gameManager the game manager used to manage game objects
     */
    public DoubleStrategy(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Sets the first collision strategy.
     *
     * @param collisionStrategy1 the first collision strategy
     */
    public void setCollisionStrategy1(CollisionStrategy collisionStrategy1) {
        this.collisionStrategy1 = collisionStrategy1;
    }

    /**
     * Sets the second collision strategy.
     *
     * @param collisionStrategy2 the second collision strategy
     */
    public void setCollisionStrategy2(CollisionStrategy collisionStrategy2) {
        this.collisionStrategy2 = collisionStrategy2;
    }

    /**
     * Handles the collision between two game objects by removing the
     * first object and delegating to two separate collision strategies.
     *
     * @param object1 the first game object involved in the collision
     * @param object2 the second game object involved in the collision
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        // Remove the first game object from the game
        this.gameManager.removeObject(object1);

        // Delegate collision handling to the first strategy
        this.collisionStrategy1.onCollision(object1, object2);

        // Delegate collision handling to the second strategy
        this.collisionStrategy2.onCollision(object1, object2);
    }
}
