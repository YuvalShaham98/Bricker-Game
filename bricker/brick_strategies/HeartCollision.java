package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

/**
 * Represents a collision strategy where a heart is created upon collision
 * if possible lives are available. It implements the CollisionStrategy interface.
 */
public class HeartCollision implements CollisionStrategy {

    private BrickerGameManager gameManager;

    /**
     * Constructor.
     * @param gameManager The game manager that handles game logic.
     */
    public HeartCollision(BrickerGameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Handles the collision between two game objects.
     * Removes the first object and creates a heart if possible lives are available.
     * Then removes the second object.
     * @param object1 The first game object involved in the collision.
     * @param object2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject object1, GameObject object2) {
        this.gameManager.removeObject(object1);

        if (this.gameManager.possibleLives()) {
            this.gameManager.createHeart();
        }

    }
}
