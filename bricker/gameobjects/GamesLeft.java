package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The GamesLeft class represents a game object that displays the remaining number of games.
 * It inherits from GameObject and can be rendered on the screen.
 */
public class GamesLeft extends GameObject {

    /**
     * Constructs a new GamesLeft instance with specified initial properties.
     *
     * @param topLeftCorner The position of the object's top-left corner in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    The width and height of the object in window coordinates.
     * @param renderable    The renderable object representing the object's visual appearance.
     *                      Can be null if the object is not rendered.
     */
    public GamesLeft(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }
}
