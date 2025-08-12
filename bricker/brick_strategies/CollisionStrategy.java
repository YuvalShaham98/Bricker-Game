package bricker.brick_strategies;

import danogl.GameObject;


/**
 * The CollisionStrategy interface defines a strategy for handling collisions between two GameObjects.
 * Classes implementing this interface should provide a concrete implementation of the onCollision method,
 * specifying the actions to take when two GameObjects collide.
 */
public interface CollisionStrategy {

    /**
     * Defines the actions to be taken when two GameObjects collide.
     *
     * @param object1 The first GameObject involved in the collision.
     * @param object2 The second GameObject involved in the collision.
     */
    void onCollision(GameObject object1, GameObject object2);
}
