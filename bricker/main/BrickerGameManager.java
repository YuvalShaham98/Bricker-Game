
package bricker.main;

/**
 * imports
 */
import bricker.gameobjects.*;
import bricker.brick_strategies.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * Bricker Game Manager. This class is in charge of the game.
 */
public class BrickerGameManager extends GameManager {


    /**
     * Constants
     */
    //Setting game Constants
    private static final int DEFAULT_NUM_ROWS = 7;
    private static final int DEFAULT_NUM_COLS = 8;
    private static final int DEFAULT_LIFE = 3;
    private static final int YELLOW_LIFE = 2;
    private static final int RED_LIFE = 1;
    private static final int MAX_LIVES =4;


    //Design window constants
    private static final int WALL_WIDTH = 5;
    private static final int CEILING_WIDTH = 10;
    private static final int SPACE_FROM_FLOOR = 30;
    private static final int SPACE_FROM_RIGHT = 120;
    private static final int GAP_BETWEEN_BRICKS = 5;
    private static final int GAP_BETWEEN_HEARTS = 25;
    private static final int NUM_OF_WALLS = 2;
    private static final float MIDDLE = 0.5f;

    //Design objects constants
    private static final Vector2 BALL_SIZE = new Vector2(20,20);
    private static final Vector2 HEART_SIZE = new Vector2(20,20);
    private static final Vector2 TEXT_SIZE = new Vector2(20,20);
    private static final float BRICK_HEIGHT = 15;
    private static final Vector2 PADDLE_SIZE = new Vector2(100,15);
    private static final Vector2 HEART_SPEED = new Vector2(0,100);
    private static final Vector2 PUCK_SIZE= new Vector2(15,15);



    //Game process constants
    private static final int BALL_SPEED = 250;
    private static final int OPPOSITE_DIERCTION =-1;
    private static final float  CAMERA_CHANGE = 1.2f;
    private static final int MAX_PADDLE_COLLISION =4;
    private static final int MAX_BALL_COLLISION =4;

    //Probability constants
    private static final int SIGMA = 10;
    private static final int OPTIONS = 5;



    /**
     * Fields
     */

    //Game Process Field
    private final int numRows;
    private final int numCols;
    private final Vector2 windowDimensions;
    private WindowController windowController;
    private TextRenderable text;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private final Random rand = new Random();



    //Objects
    private Ball ball;
    private SecondPaddle secondPaddle;


    //Game Parameters
    private Counter lives;
    private final GameObject[] heartArr = new GameObject[MAX_LIVES];
    private GameObject livesInText;
    private Counter brickCounter;
    private int ballCounter;






    /**
     * Constructor
     * @param windowTitle - title of game
     * @param windowDimensions - dimensions of window
     * @param numRows - of bricks
     * @param numCols - of bricks
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numRows, int numCols){
        super(windowTitle, windowDimensions);
        this.windowDimensions = windowDimensions;
        this.numRows = numRows;
        this.numCols = numCols;
        this.lives = new Counter();
        this.brickCounter = new Counter();
        this.ballCounter = 0;
    }

    /**
     * Default Constructor
     * @param windowTitle - title of game
     * @param windowDimensions - dimensions of window
     * makes game with default number of bricks
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        this(windowTitle,windowDimensions,
                DEFAULT_NUM_ROWS,DEFAULT_NUM_COLS);

    }



    /**
     * initializing game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);


        //saving needed fields in class
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.secondPaddle = null;

        //reset all counters for new game
        this.brickCounter.reset();
        this.ballCounter = 0;
        this.lives.reset();

        //setting new game
        gameBackground();
        createBall();
        createPuddle(new Vector2(windowDimensions.x()*MIDDLE,(int)windowDimensions.y()-SPACE_FROM_FLOOR));
        createBricks();

    }

    /**
     * this method adds background, walls and how many lives are left in the game
     */
    private void gameBackground(){
        createBackground();
        createWalls();
        livesText();
        for(int i = 0; i < DEFAULT_LIFE; i++) {
            createHeart();
        }
    }

    /**
     * this method checks if it is possible to add 1 life to the game
     * @return if possible
     */
    public boolean possibleLives(){
        return this.lives.value() < MAX_LIVES;
    }

    //SCREEN

    /**
     * This method creates background to the game
     */
    private void createBackground() {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg"
                , false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /**
     * This method creates invisible walls to the game
     */
    private void createWalls(){


        //Ceiling
        GameObject topWall = new Walls(Vector2.ZERO, new Vector2(windowDimensions.x(),CEILING_WIDTH),
                null);
        topWall.setCenter(new Vector2(windowDimensions.x(),CEILING_WIDTH).mult(MIDDLE));
        this.gameObjects().addGameObject(topWall);

        //side walls
        Vector2[] sideWallsPlacement =
                {new Vector2(windowDimensions.x()-WALL_WIDTH,windowDimensions.y()*MIDDLE),
                new Vector2(WALL_WIDTH,windowDimensions.y()*MIDDLE),};

        for (Vector2 sideWall : sideWallsPlacement) {
            GameObject wall = new Walls(Vector2.ZERO, new Vector2(WALL_WIDTH, windowDimensions.y()),
                    null);
            wall.setCenter(sideWall);
            this.gameObjects().addGameObject(wall);
        }
    }

    /**
     * this method present on the game window how many lives are left (in text)
     */
    private void livesText(){
        text = new TextRenderable("Lives left: " + lives);
        text.setColor(Color.green);
        livesInText = new GamesLeft(Vector2.ZERO, TEXT_SIZE, text);
        livesInText.setCenter(new Vector2(windowDimensions.x() - SPACE_FROM_RIGHT,
                windowDimensions.y()-SPACE_FROM_FLOOR));
        this.gameObjects().addGameObject(livesInText, Layer.UI);
    }



    //OBJECTS

    /**
     * this method creates a single heart to the preview of the game
     */
    public void createHeart(){
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        Heart heart = new Heart(Vector2.ZERO, HEART_SIZE, heartImage);
        heart.setCenter(new Vector2((GAP_BETWEEN_HEARTS+WALL_WIDTH + lives.value() * GAP_BETWEEN_HEARTS),
                windowDimensions.y() - SPACE_FROM_FLOOR));
        this.gameObjects().addGameObject(heart, Layer.UI);
        heartArr[lives.value()] = heart;
        this.lives.increment();
        this.colorChangeScore();
    }

    /**
     * this method creates a ball for the game
     */
    public void createBall(){
        Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        Sound ballSound = soundReader.readSound("assets/blop.wav");
        ball = new Ball( Vector2.ZERO, BALL_SIZE,ballImage,ballSound);
        setBall(ball,BALL_SPEED, BALL_SPEED);
        this.gameObjects().addGameObject(ball);
    }

    /**
     * this method places a given ball in the game window and sets if it would fall down or up
     * @param gameObject - the ball
     * @param ballVelX - the velocity of the ball at the X-axis
     * @param ballVelY - the velocity of the ball at the X-axis
     */
    private void setBall(Ball gameObject,float ballVelX, float ballVelY){

        gameObject.setCenter(windowDimensions.mult(MIDDLE));
        if(rand.nextBoolean()){
            ballVelX *= OPPOSITE_DIERCTION;
        }
        if(rand.nextBoolean()){
            ballVelY *= OPPOSITE_DIERCTION;
        }
        gameObject.setVelocity(new Vector2(ballVelX,ballVelY));
    }

    /**
     * this method creates a paddle in given position
     * @param position - in game window
     */
    public void createPuddle(Vector2 position){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        Paddle paddle = new Paddle(Vector2.ZERO, PADDLE_SIZE,paddleImage,inputListener,
                windowDimensions);
        paddle.setCenter(position);
        this.gameObjects().addGameObject(paddle);
    }

    /**
     * this method creates the bricks of the game; the method gives each brick its behavior when breaks
     */
    private void createBricks(){

        float brickWidth = (windowDimensions.x()-(numCols*GAP_BETWEEN_BRICKS))/numCols;
        float brickPlacementRow = WALL_WIDTH*NUM_OF_WALLS;
        float brickPlacementCol = WALL_WIDTH*NUM_OF_WALLS;


        for(int i = 0; i < numCols; i++){
            for (int j = 0; j < numRows; j++) {

                StrategyFactory strategyFactory = new StrategyFactory(this);
                Renderable brickImage = imageReader.readImage("assets/brick.png",false);
                CollisionStrategy collisionStrategy = strategyFactory.
                        buildCollisionStrategy(this.rand.nextInt(SIGMA)-OPTIONS);
                Brick brick = new Brick(Vector2.ZERO, new Vector2(brickWidth,BRICK_HEIGHT),brickImage,
                collisionStrategy);
                brick.setCenter(new Vector2(brickPlacementRow + i*(brickWidth) + brickWidth*MIDDLE,
                        brickPlacementCol + j*(BRICK_HEIGHT)+BRICK_HEIGHT*MIDDLE));
                this.gameObjects().addGameObject(brick,Layer.STATIC_OBJECTS);
                this.brickCounter.increment();
            }

        }
    }

    /**
     * this method creates "Puck" balls that are added to the game
     * @param ballCount - number of pucks to add
     * @param position - of pucks
     */
    public void createPuck (int ballCount,Vector2 position){
        for(int i = 0; i < ballCount; i++){

            Renderable ballImage = imageReader.readImage("assets/mockBall.png",true);
            Sound ballSound = soundReader.readSound("assets/blop.wav");
            Ball puck = new Ball(position,PUCK_SIZE ,ballImage,ballSound);
            double angle = rand.nextDouble()*Math.PI;
            float velocityX = (float)Math.cos(angle)*BALL_SPEED;
            float velocityY = (float)Math.sin(angle)*BALL_SPEED;
            setBall(puck,velocityX, velocityY);
            puck.setCenter(position);
            this.gameObjects().addGameObject(puck);
        }

    }

    /**
     * this method creates hearts the player didn't collect yet
     * @param position of the heart
     */
    public void fallenHeart(Vector2 position){
        Renderable heartImage = imageReader.readImage("assets/heart.png",true);
        CollisionStrategy collisionStrategy = new HeartCollision(this);
        Sound ballSound = soundReader.readSound("assets/blop.wav");
        Heart fallenHeart = new Heart(Vector2.ZERO,HEART_SIZE,heartImage,
                collisionStrategy,ballSound);
        fallenHeart.setVelocity(HEART_SPEED);
        fallenHeart.setCenter(position);
        this.gameObjects().addGameObject(fallenHeart);
    }

    /**
     * this method creates a second paddle
     */
    public void createSecondPuddle(){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        this.secondPaddle = new SecondPaddle(this.windowDimensions.mult(MIDDLE),
                PADDLE_SIZE,paddleImage,inputListener,
                windowDimensions);
        this.gameObjects().addGameObject(this.secondPaddle);
    }



    //SPECIAL BEHAVIORS

    /**
     * this method changes the camera to focus on specific object
     * @param object to focus on
     */
    public void setCameraOn(GameObject object) {
        if (object == this.ball && this.camera() == null) {
            this.setCamera(new Camera(this.ball, Vector2.ZERO, this.windowDimensions.mult(CAMERA_CHANGE),
                    windowDimensions));
            ballCounter = this.ball.getCollisionCounter();

        }
    }



    //UPDATES

    /**
     * this method removes a given object from game
     * @param object - to remove
     */
    public void removeObject(GameObject object){
        if (object instanceof Brick) {
            if (this.gameObjects().removeGameObject(object, Layer.STATIC_OBJECTS)) {
                this.brickCounter.decrement();
            }
        }
        else {
            this.gameObjects().removeGameObject(object);
        }
        if( object instanceof Paddle){
            System.out.println("bad");
        }
    }

    /**
     * this method updates presentation of lives in text
     */
    private void colorChangeScore() {

        text.setString("Lives left: " + lives.value());

        if (lives.value() == YELLOW_LIFE) {
            text.setColor(Color.yellow);
        }

        if (lives.value() == RED_LIFE) {
            text.setColor(Color.red);
        }
        if(lives.value() >= DEFAULT_LIFE){
            text.setColor(Color.green);
        }
    }


    /**
     * this method updates the game
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        String prompt = "";

        float ballHeight = ball.getCenter().y();

        //STRIKE
        if(ballHeight > this.windowDimensions.y()){
            prompt=strike();
        }

        //WIN
        if(brickCounter.value() == 0 || this.inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = "You Won! ";
        }

        //GAME OVER
        if(!prompt.isEmpty()){
           gameOver(prompt);
        }


        removeOutOfBoundsPucks();

        if (secondPaddle != null) {
            if(secondPaddle.getCounter() == MAX_PADDLE_COLLISION){
                removeObject(secondPaddle);
                secondPaddle = null;
                SecondPaddle.resetCounter();
            }
        }

        if(this.camera() != null){
            if (this.ball.getCollisionCounter()-ballCounter >= MAX_BALL_COLLISION){
                this.setCamera(null);
            }
        }


    }


    /**
     * this method is activated after the game is over (win or lose)
     * the method asks the player for another
     * @param prompt - if the game ended from win or lose
     */
    private void gameOver(String prompt){
        prompt += "Play Again?";
        if(windowController.openYesNoDialog(prompt)){
            windowController.resetGame();
        }
        else{
            windowController.closeWindow();
        }
    }

    /**
     * this method updates the game after the player has a strike
     * @return the prompt (it is changed after the player lost)
     */
    private String strike (){
        lives.decrement();
        colorChangeScore();
        gameObjects().removeGameObject(heartArr[lives.value()],Layer.UI);
        if(lives.value() == 0){
            return "You Lose! ";
        }
        setBall(ball,BALL_SPEED,BALL_SPEED);
        return "";
    }

    /**
     * this method removes all pucks that have fallen
     */
    private void removeOutOfBoundsPucks() {
        for (GameObject puck : this.gameObjects().objectsInLayer(Layer.DEFAULT)) {
            if (puck != this.ball && puck instanceof Ball && isOutOfBounds(puck)) {
                this.gameObjects().removeGameObject(puck, Layer.DEFAULT);
            }
        }
    }

    /**
     * this method checks if an object is out of bounds
     * @param gameObject to check
     * @return true if the object is out of bpunds
     */
    private boolean isOutOfBounds(GameObject gameObject) {
        Vector2 position = gameObject.getCenter();
        return position.x() < 0 || position.x() > windowDimensions.x() || position.y() < 0 || position.y() >
                windowDimensions.y();
    }

    /**
     * Checks if a second paddle is present in the game.
     *
     * @return true if the second paddle is present, false otherwise.
     */
    public boolean isSecondPaddle(){
        return this.secondPaddle != null;
    }


    /**
     * main method starts the bricker game according to the arguments the user gives
     *
     * @param args - the size of board (optional)
     */
    public static void main(String[] args){
        if(args.length == 0) {
            BrickerGameManager bricker = new BrickerGameManager("Bouncing Ball",
                    new Vector2(700, 500));
            bricker.run();
        }
        else if(args.length == 2){
            int row = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);
            BrickerGameManager bricker = new BrickerGameManager("Bouncing Ball",
                    new Vector2(700, 500),row,cols);
            bricker.run();
        }


    }
}
