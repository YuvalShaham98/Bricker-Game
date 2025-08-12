package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The SecondPaddle class represents a secondary paddle GameObject that responds to user input for movement.
 * It inherits from Paddle and adds specific behavior related to collision handling with a Ball object.
 */
public class SecondPaddle extends Paddle {

    private static int counter = 0;

    /**
     * Constructs a new SecondPaddle instance with specified initial properties.
     *
     * @param topLeftCorner    The position of the object's top-left corner in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       The width and height of the object in window coordinates.
     * @param renderable       The renderable object representing the object's visual appearance.
     *                         Can be null if the object is not rendered.
     * @param inputListener    The input listener that handles user keyboard input.
     * @param windowDimensions The dimensions of the game window in window coordinates.
     */
    public SecondPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
    }

    /**
     * Handles collision events with other GameObjects.
     *
     * @param other     The GameObject that this object collided with.
     * @param collision The details of the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        // Increment counter if the collision is with a Ball object
        if (other instanceof Ball) {
            counter++;
        }
    }

    /**
     * Returns the count of collisions with Ball objects.
     *
     * @return The count of collisions with Ball objects.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Resets the collision counter.
     */
    public static void resetCounter() {
        counter = 0;
    }

    public String getTag() {
        return "Second Paddle";
    }


}
