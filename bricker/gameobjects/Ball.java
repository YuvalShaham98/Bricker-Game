package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The Ball class represents a game object that behaves like a ball in the game.
 * It inherits from GameObject and implements collision handling and sound effects.
 */
public class Ball extends GameObject {

    private final Sound collisionSound;
    private int collisionCounter;

    /**
     * Constructs a new Ball object with specified initial properties.
     *
     * @param topLeftCorner The position of the ball's top-left corner in window coordinates (pixels).
     * @param dimensions    The width and height of the ball in window coordinates.
     * @param renderable    The renderable object representing the ball's visual appearance.
     *                      Can be null if the ball is not rendered.
     * @param collisionSound The sound played on collision with other objects.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCounter = 0;
    }

    /**
     * Handles the action to take when the ball collides with another game object.
     * It flips the ball's velocity based on the collision normal, increments the collision counter,
     * and plays the collision sound.
     *
     * @param other     The game object with which the ball collided.
     * @param collision The collision details.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        // Calculate and set new velocity after collision
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);

        // Increment collision counter
        collisionCounter++;

        // Play collision sound
        collisionSound.play();
    }

    /**
     * Retrieves the collision counter, which counts the number of collisions the ball has encountered.
     *
     * @return The collision counter object.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }

}
