package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * The Paddle class represents a paddle GameObject that responds to user input for movement.
 * It inherits from GameObject and moves horizontally based on user keyboard input.
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Constructs a new Paddle instance with specified initial properties.
     *
     * @param topLeftCorner    The position of the object's top-left corner in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       The width and height of the object in window coordinates.
     * @param renderable       The renderable object representing the object's visual appearance.
     *                         Can be null if the object is not rendered.
     * @param inputListener    The input listener that handles user keyboard input.
     * @param windowDimensions The dimensions of the game window in window coordinates.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Gets the tag of the paddle object.
     *
     * @return The tag of the paddle, which is "explicit Paddle".
     */
    @Override
    public String getTag() {
        return "explicit Paddle";
    }

    /**
     * Updates the paddle's position based on user input and ensures it stays within the game window.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;

        // Check for left and right key presses to determine movement direction
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }

        // Limit paddle movement within the game window
        if (getTopLeftCorner().x() < 0) {
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        }
        if (getTopLeftCorner().x() > windowDimensions.x() - getDimensions().x()) {
            setTopLeftCorner(new Vector2(windowDimensions.x() - getDimensions().x(), getTopLeftCorner().y()));
        }

        // Set velocity based on movement direction and speed
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}
