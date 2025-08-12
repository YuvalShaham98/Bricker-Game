package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import java.util.Random;

/**
 * The StrategyFactory class constructs various CollisionStrategy objects
 * based on the specified type. It supports creating different strategies
 * such as basic collision, extra balls, extra paddle, camera change, extra life,
 * and double strategies.
 */
public class StrategyFactory {

    private static final int BASIC = 0;
    private static final int EXTRA_BALLS = 1;
    private static final int EXTRA_PADDLE = 2;
    private static final int CAMERA_CHANGE = 3;
    private static final int EXTRA_LIFE = 4;
    private static final int DOUBLE = 5;

    private static final int NUM_OF_STRATEGIES = 6;
    private static final int NUM_OF_UNIQUE_STRATEGIES = 5;

    private static final int DOUBLE_OKAY = 2;



    private BrickerGameManager gameManager;
    private int strategyCounter;

    /**
     * Constructs a StrategyFactory with the specified game manager.
     *
     * @param gameManager the game manager used to manage game objects
     */
    public StrategyFactory(BrickerGameManager gameManager){
        this.gameManager = gameManager;
        this.strategyCounter = 0;
    }

    /**
     * Constructs and returns a CollisionStrategy object based on the specified type.
     *
     * @param type the type of collision strategy to build
     * @return a CollisionStrategy object corresponding to the specified type
     */
    public CollisionStrategy buildCollisionStrategy(int type){
        if(type <= BASIC){
            return new BasicCollisionStrategy(this.gameManager);
        }
        if (type == EXTRA_BALLS){
            return new ExtraBalls(this.gameManager);
        }
        if(type == EXTRA_PADDLE){
            return new ExtraPaddle(this.gameManager);
        }
        if (type == CAMERA_CHANGE){
            return new CameraChange(this.gameManager);
        }
        if (type == EXTRA_LIFE){
            return new ExtraLife(this.gameManager);
        }

        if (type == DOUBLE){
            this.strategyCounter++;
            DoubleStrategy strategy = new DoubleStrategy(this.gameManager);
            strategy.setCollisionStrategy1(selectStrategy());
            strategy.setCollisionStrategy2(selectStrategy());
            return strategy;
        }
        return null;
    }

    /**
     * Selects and returns a randomly chosen CollisionStrategy object.
     * Limits the selection of certain strategies based on the current
     * strategy counter to prevent excessive recursion.
     *
     * @return a randomly selected CollisionStrategy object
     */
    private CollisionStrategy selectStrategy(){
        Random rand = new Random();
        int randomNum;
        if (this.strategyCounter < DOUBLE_OKAY){
            randomNum = rand.nextInt(NUM_OF_STRATEGIES);
        }
        else {
            randomNum = rand.nextInt(NUM_OF_UNIQUE_STRATEGIES); // Exclude DOUBLE strategy
        }
        return this.buildCollisionStrategy(randomNum);
    }
}
