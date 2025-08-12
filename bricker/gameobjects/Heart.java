package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Heart class represents a game object that behaves like a heart in the game.
 * It inherits from GameObject and can interact with other game objects upon collision.
 */
public class Heart extends GameObject {

    private final CollisionStrategy collisionStrategy;

    /**
     * Constructs a new Heart instance with specified initial properties.
     *
     * @param topLeftCorner The position of the object's top-left corner in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    The width and height of the object in window coordinates.
     * @param renderable    The renderable object representing the object's visual appearance.
     *                      Can be null if the object is not rendered.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = null;
    }

    /**
     * Constructs a new Heart instance with specified initial properties and collision behavior.
     *
     * @param topLeftCorner     The position of the object's top-left corner in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        The width and height of the object in window coordinates.
     * @param renderable        The renderable object representing the object's visual appearance.
     *                          Can be null if the object is not rendered.
     * @param collisionStrategy The strategy to be executed upon collision with another GameObject.
     * @param sound             The sound to play upon collision.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Sound sound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handles the action to be taken upon collision with another game object.
     * Executes the collision strategy defined for this Heart instance.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (collisionStrategy != null) {
            collisionStrategy.onCollision(this, other);
        }
    }

    /**
     * Determines whether this Heart object should collide with the specified other game object.
     * Hearts should only collide with game objects tagged as "explicit Paddle".
     *
     * @param other The other game object to check collision eligibility with.
     * @return true if collision should occur, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals("explicit Paddle");
    }

}
