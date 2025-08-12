package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Brick class represents a brick game object that interacts with other game objects,
 * using a specified collision strategy.
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;

    /**
     * Constructs a new Brick object with specified initial properties.
     *
     * @param topLeftCorner     The position of the brick's top-left corner in window coordinates (pixels).
     * @param dimensions        The width and height of the brick in window coordinates.
     * @param renderable        The renderable object representing the brick's visual appearance.
     *                          Can be null if the brick is not rendered.
     * @param collisionStrategy The collision strategy to be used when the brick collides with other objects.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy){
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handles the action to take when another game object collides with this brick.
     * Delegates collision handling to the specified collision strategy.
     *
     * @param other     The game object that collided with this brick.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        this.collisionStrategy.onCollision(this, other);
    }

    /**
     * Determines whether this brick should collide with a specific game object.
     * In this case, it only collides with instances of the Ball class.
     *
     * @param other The game object to check against.
     * @return True if this brick should collide with the other object, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Ball; // Bricks only collide with Balls
    }
}
