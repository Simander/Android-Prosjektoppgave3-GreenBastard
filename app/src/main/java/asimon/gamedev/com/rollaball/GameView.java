package asimon.gamedev.com.rollaball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


import java.util.LinkedList;
import java.util.List;

import asimon.gamedev.com.rollaball.MyFramework.*;


/**
 * Created by simander on 05/11/15.
 */

public class GameView extends SurfaceView {
    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoop gameLoop;



    private MoveableObject player;
    private MoveableObject enemy;
    private MoveableObject collectable;
    private boolean collected = true;

    BoxCollider gameArea = new BoxCollider();
    boolean GAMEAREA_SET_FLAG = false;
    boolean RESET_GAME_FLAG = true;
    boolean PAUSE_FLAG = false;
    private Vector2D playerPreviousSpeed = new Vector2D();
    private Vector2D enemyPreviousSpeed = new Vector2D();
    float enemyPupil = 16;
    int enemyPupilAnim = 1;
    int enemyPupilDirection = 0;
    float enemyPupilX = 0;
    float enemyPupilY = 0;

    boolean GAMEOVER_FLAG = false;
    float timer = 10;
    boolean showRules = false;
    boolean showHighscores = false;
    boolean showGameGraphics = true;
    boolean showEnterHighscore = false;

    char[] initials = new char[3];
    int[] initCount = {0,0,0};

    DBHandler db;

    Context context;

    //ESSENTIAL FOR EDITING INITIALS FOR HIGHSCORE
    BoxCollider initUpA = new BoxCollider();
    BoxCollider initUpB = new BoxCollider();
    BoxCollider initUpC = new BoxCollider();
    BoxCollider initDownA = new BoxCollider();
    BoxCollider initDownB = new BoxCollider();
    BoxCollider initDownC = new BoxCollider();
    BoxCollider registerButton = new BoxCollider();


    //Constructor
    public GameView(Context context) {

        super(context);
        this.context = context;
        db = new DBHandler(context);
      // db.renskDatabase();
        player = new MoveableObject();
        player.speed = 4.0f;

        enemy = new MoveableObject();
        enemy.speed = 5.0f;
        enemy.xForce = 1.0f;
        enemy.yForce = 1.0f;
        enemy.setPostion(getWidth() - 20, getHeight() - 10);

        collectable = new MoveableObject();

        gameLoop = new GameLoop(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoop.setRunning(false);
                while (retry) {
                    try {
                        gameLoop.getThreadT().join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                gameLoop.setRunning(true);
                gameLoop.setThreadT(new Thread(gameLoop));
                gameLoop.getThreadT().start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }


        });
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

    }


    protected void drawEnterHighscore(Canvas canvas){
        //PAINTS
        Paint red = new Paint();
        red.setColor(Color.RED);
        red.setAntiAlias(true);
        red.setTextSize(red.getTextSize() * 6);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setTextSize(yellow.getTextSize()*4);
        yellow.setFakeBoldText(true);
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setTextSize(white.getTextSize() * 2.5f);
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        Paint green = new Paint();
        green.setColor(Color.GREEN);




        //GAMEAREA
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(gameArea.x - 5, gameArea.y - 5, gameArea.width + 5, gameArea.height + 5, blue);
        canvas.drawRect(gameArea.x, gameArea.y, gameArea.width, gameArea.height, black);


        canvas.drawText("New Highscore!", gameArea.x + gameArea.width / 8, gameArea.y + gameArea.height / 12, yellow);


        canvas.drawText("You got " + player.points + " points!", gameArea.x + gameArea.width / 5, gameArea.y + gameArea.height / 6, white);
        white.setTextSize(white.getTextSize()*1.5f);
        BoxCollider initA = new BoxCollider();
        BoxCollider initB = new BoxCollider();
        BoxCollider initC = new BoxCollider();



        initA.update(gameArea.x + gameArea.width / 6, gameArea.y + gameArea.height / 2.5f, gameArea.width / 6, gameArea.height / 8);
        initB.update(initA.x + initA.width + 30, initA.y, initA.width, initA.height);
        initC.update(initB.x + initB.width + 30, initA.y, initA.width, initA.height);

        initUpA.x = initA.x;
        initUpA.y =initA.y-(initA.height+10);
        initUpA.height = initA.height;
        initUpA.width = initA.width;
        initUpB.x = initB.x;
        initUpB.y = initB.y-(initB.height+10);
        initUpB.height = initA.height;
        initUpB.width = initA.width;
        initUpC.x = initC.x;
        initUpC.y = initC.y-(initC.height+10);
        initUpC.height = initA.height;
        initUpC.width = initA.width;

        initDownA.x = initA.x;
        initDownA.y =initA.y+(initA.height+10);
        initDownA.height = initA.height;
        initDownA.width = initA.width;
        initDownB.x = initB.x;
        initDownB.y = initB.y+(initB.height+10);
        initDownB.height = initA.height;
        initDownB.  width = initA.width;
        initDownC.x = initC.x;
        initDownC.y = initC.y+(initC.height+10);
        initDownC.height = initA.height;
        initDownC.width = initA.width;


        canvas.drawRect(initUpA.getRect(), /*yellow*/black);
        canvas.drawRect(initUpB.getRect(), /*yellow*/black);
        canvas.drawRect(initUpC.getRect(), /*yellow*/black);

        canvas.drawRect(initDownA.getRect(), /*yellow*/black);
        canvas.drawRect(initDownB.getRect(), /*yellow*/black);
        canvas.drawRect(initDownC.getRect(), /*yellow*/black);

        canvas.drawRect(initA.getRect(), blue);
        canvas.drawRect(initB.getRect(), blue);
        canvas.drawRect(initC.getRect(), blue);
        canvas.drawRect(initA.x + 2, initA.y + 2, initA.getRect().right - 2, initA.getRect().bottom - 2, black);
        canvas.drawRect(initB.x + 2, initB.y + 2, initB.getRect().right - 2, initB.getRect().bottom - 2, black);
        canvas.drawRect(initC.x + 2, initC.y + 2, initC.getRect().right - 2, initC.getRect().bottom - 2, black);

        Vector2D textA = new Vector2D(initA.getCenter().x-initA.width/3.5f, initA.getCenter().y+initA.height/3.5f);

        black.setTextSize(red.getTextSize());
        yellow.setTextSize(red.getTextSize());
        canvas.drawText("⬆", initUpA.x-initUpA.width/12, initUpA.y+initUpA.height/1.2f, yellow);
        canvas.drawText("⬆", initUpB.x-initUpB.width/12, initUpB.y+initUpB.height/1.2f, yellow);
        canvas.drawText("⬆", initUpC.x-initUpC.width/12, initUpC.y+initUpC.height/1.2f, yellow);
        canvas.drawText("⬇", initDownA.x-initDownA.width/12, initDownA.y+initDownA.height/1.2f, yellow);
        canvas.drawText("⬇", initDownB.x-initDownB.width/12, initDownB.y+initDownB.height/1.2f, yellow);
        canvas.drawText("⬇", initDownC.x-initDownC.width/12, initDownC.y+initDownC.height/1.2f, yellow);


        canvas.drawText("" + Extras.getLetter(initCount[0]), textA.x, textA.y, red);
        canvas.drawText("" + Extras.getLetter(initCount[1]), initB.getCenter().x - initB.width / 3.5f, textA.y, red);
        canvas.drawText("" + Extras.getLetter(initCount[2]), initC.getCenter().x - initC.width / 3.5f, textA.y, red);


        registerButton.update(initA.x  + 20, gameArea.height-gameArea.height/6, initA.width*3, initA.height);
        canvas.drawRect(registerButton.getRect(), red);
        canvas.drawText("Register", registerButton.x + registerButton.width/8, registerButton.y + registerButton.height/1.5f, white);


    }



    protected void drawRules(Canvas canvas){


        //PAINTS
        Paint red = new Paint();
        red.setColor(Color.RED);
        red.setAntiAlias(true);
        red.setTextSize(red.getTextSize() * 6);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        //white.setTextSize(white.getTextSize() * 2);
        white.setTextSize(getWidth()/20);

        Paint black = new Paint();
        black.setColor(Color.BLACK);
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        Paint green = new Paint();
        green.setColor(Color.GREEN);




        //GAMEAREA
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(gameArea.x - 5, gameArea.y - 5, gameArea.width + 5, gameArea.height + 5, blue);
        canvas.drawRect(gameArea.x, gameArea.y, gameArea.width, gameArea.height, black);



        canvas.drawText(getContext().getString(R.string.rules), (getWidth()/2 - (red.getTextSize())), 50, red );


        int yVal = getHeight()/7;

        String s = getContext().getString(R.string.ruleNo1);
        LinkedList<String> lines = Extras.formatRules(s, 30);


        for(String line : lines) {
            canvas.drawText(line, gameArea.x+player.circleCollider.radius*5, yVal, white);
            yVal +=getWidth()/16;
        }
        yVal+=player.circleCollider.radius*1.5f;

        s = getContext().getString(R.string.ruleNo2);
        lines = Extras.formatRules(s,30);


        for(String line : lines) {
            canvas.drawText(line, gameArea.x+player.circleCollider.radius*5, yVal, white);
            yVal += getWidth()/16;
        }
        yVal += player.circleCollider.radius*1.5f;

        s = getContext().getString(R.string.ruleNo3);
        lines = Extras.formatRules(s, 30);


        for(String line : lines) {
            canvas.drawText(line, gameArea.x+player.circleCollider.radius*5, yVal, white);
            yVal += getWidth()/16;
        }
        yVal += player.circleCollider.radius*2f;
        //red.setTextSize(red.getTextSize()/2);
        red.setTextSize(getWidth()/10);
        canvas.drawText(getContext().getString(R.string.summary), gameArea.x+player.circleCollider.radius*4, yVal, red);
        yVal += player.circleCollider.radius*1f;
        s = "Gather as many coins as you can while avoiding the red-bastard, try to beat the highscore!";
        lines = Extras.formatRules(s, 40);


        for(String line : lines) {
            canvas.drawText(line, gameArea.x+player.circleCollider.radius, yVal, white);
            yVal += getWidth()/16;
        }


        //DRAWS THE PLAYER OBJECT
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*3, player.circleCollider.radius,green);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*3+10, enemyPupil, black);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f-player.circleCollider.radius/3, gameArea.y+player.circleCollider.radius*3-10, player.circleCollider.radius/3f, white);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f-player.circleCollider.radius/3, gameArea.y+player.circleCollider.radius*3-10, player.circleCollider.radius/6f, blue);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f+player.circleCollider.radius/3, gameArea.y+player.circleCollider.radius*3-10, player.circleCollider.radius/3f, white);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f+player.circleCollider.radius/3, gameArea.y+player.circleCollider.radius*3 - 10, player.circleCollider.radius/6f, blue);


        //DRAWS THE ENEMY   6.2f
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*9.2f, enemy.circleCollider.radius, red);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*9.2f-player.circleCollider.radius/2f, enemy.circleCollider.radius/2, white);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*9.2f-player.circleCollider.radius/2f/*enemy.circleCollider.y-15*/, enemyPupil, black);
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*9.2f + enemy.circleCollider.radius/1.59f, enemy.circleCollider.radius/3, black);

        //DRAWS NEW COINS
        canvas.drawCircle(gameArea.x+player.circleCollider.radius*2.5f, gameArea.y+player.circleCollider.radius*13.5f, player.circleCollider.radius/3.2f, yellow);

    }
    public void showGameGraphics(boolean b){
        showGameGraphics = true;
    }
    public void showRules(boolean b){
        showRules = b;

    }

    public void showHighscores(boolean b){
        showHighscores = b;

    }


    protected void drawHighscoreList(Canvas canvas) {


        //PAINTS
        Paint red = new Paint();
        red.setColor(Color.RED);
        red.setAntiAlias(true);
        red.setTextSize(red.getTextSize() * 5);
        Paint yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        Paint white = new Paint();
        white.setColor(Color.WHITE);
        white.setTextSize(white.getTextSize() * 2);
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        Paint green = new Paint();
        green.setColor(Color.GREEN);


        //GAMEAREA


        canvas.drawColor(Color.BLACK);
        canvas.drawRect(gameArea.x - 5, gameArea.y - 5, gameArea.width + 5, gameArea.height + 5, blue);
        canvas.drawRect(gameArea.x, gameArea.y, gameArea.width, gameArea.height, black);



        canvas.drawText(getContext().getString(R.string.highscores), (getWidth() / 3 - (red.getTextSize())), 50, red);

        String log = "";
        List<Highscore> highscores = db.finnAlleHighscores();
        int counter = 1;
        canvas.drawText(
                "Rank    \t\t\t\tInitials    \t\t\t\t\t\t\t\tScore",
                gameArea.x+gameArea.width/8, (gameArea.y+gameArea.height/15)*counter, white);
        float yVal = 1;
        for(Highscore high:highscores){

            canvas.drawText(
            counter+"."+"    \t\t\t\t\t\t\t\t\t\t"+        high.getName()+"    \t\t\t\t\t\t\t\t\t\t\t\t"+high.getPoints() + "\n",
                    gameArea.x+gameArea.width/6, (gameArea.y+gameArea.height/6)+yVal, white);
            yVal+=30;
            counter++;
        }



    }

        //@Override
    protected void drawGameGraphics(Canvas canvas) {
        if(GAMEAREA_SET_FLAG == false){

            setGameArea(15,60, getWidth()-15, getHeight()-15);
        }
        if(RESET_GAME_FLAG == true){
            resetGameState();
        }
        if(GAMEOVER_FLAG ==true) {
            onGameOver();
        }

        updatePlayer();
        updateEnemy();
        updateEnemyEye();

        //PAINTS
            Paint red = new Paint();
        red.setColor(Color.RED);
            red.setAntiAlias(true);
            //red.setTextSize(red.getTextSize() * 2);
            red.setTextSize(getWidth()/16);
        Paint yellow = new Paint();
            yellow.setColor(Color.YELLOW);
        Paint white = new Paint();
            white.setColor(Color.WHITE);
        Paint black = new Paint();
            black.setColor(Color.BLACK);
        Paint blue = new Paint();
            blue.setColor(Color.BLUE);
            blue.setTextSize(blue.getTextSize()*5);
            blue.setFakeBoldText(true);
        Paint green = new Paint();

            green.setColor(Color.GREEN);
        Paint crimson = new Paint();
        crimson.setColor(Color.parseColor("#DC143C"));
        crimson.setAntiAlias(true);
        crimson.setTextSize(crimson.getTextSize() * 4);




        //GAMEAREA
        canvas.drawColor(Color.BLACK);
        canvas.drawRect(gameArea.x-5, gameArea.y-5, gameArea.width + 5, gameArea.height + 5, blue);
        canvas.drawRect(gameArea.x, gameArea.y, gameArea.width,gameArea.height, black);


        

        canvas.drawText("Points: " + player.points, 15, 50, red);
        String outscore = "";
        List<Highscore> hList = db.finnAlleHighscores();
        if(hList.size() > 0) {
            int points = hList.get(0).getPoints();


            if (points < 10) {
                outscore = "              " + points;
            } else if (points < 100) {
                outscore = "            " + points;
            } else if (points < 1000) {
                outscore = "          " + points;
            } else if (points < 10000) {
                outscore = "       " + points;
            } else if (points < 100000) {
                outscore = "     " + points;
            }
            canvas.drawText("Highscore: " + outscore, gameArea.x+gameArea.width/2.5f, 50, red);
        }



        //DRAWS THE PLAYER OBJECT
        canvas.drawCircle(player.circleCollider.x, player.circleCollider.y, player.circleCollider.radius,green);
        canvas.drawCircle(player.circleCollider.x, player.circleCollider.y+10, enemyPupil, black);
        canvas.drawCircle(player.circleCollider.x-player.circleCollider.radius/3, player.circleCollider.y-player.circleCollider.radius/3, player.circleCollider.radius/3f, white);
        canvas.drawCircle(player.circleCollider.x-player.circleCollider.radius/3, player.circleCollider.y-player.circleCollider.radius/3, player.circleCollider.radius/6f, blue);
        canvas.drawCircle(player.circleCollider.x+player.circleCollider.radius/3, player.circleCollider.y-player.circleCollider.radius/3, player.circleCollider.radius/3f, white);
        canvas.drawCircle(player.circleCollider.x + player.circleCollider.radius/3, player.circleCollider.y - player.circleCollider.radius/3, player.circleCollider.radius/6f, blue);


        //DRAWS THE ENEMY
        canvas.drawCircle(enemy.circleCollider.x, enemy.circleCollider.y, enemy.circleCollider.radius, red);
        canvas.drawCircle(enemy.circleCollider.x, enemy.circleCollider.y-enemy.circleCollider.radius/3f, enemy.circleCollider.radius/2, white);
        canvas.drawCircle(enemyPupilX, enemyPupilY/*enemy.circleCollider.y-15*/, enemyPupil, black);
        canvas.drawCircle(enemy.circleCollider.x, enemy.circleCollider.y + enemy.circleCollider.radius/1.59f, enemy.circleCollider.radius/3, black);

        //DRAWS NEW COINS
        if(collected) {
            spawnCollectable(10);
        }
        canvas.drawCircle(collectable.circleCollider.x, collectable.circleCollider.y, enemyPupil, yellow);
        if(GAMEOVER_FLAG ==true){

            blue.setTextSize(getWidth()/8);
            canvas.drawText("GAME OVER!", gameArea.x+gameArea.width/9, gameArea.getCenter().y, blue);


        }
        if(RESET_GAME_FLAG == true){
            white.setFakeBoldText(true);
            white.setTextSize(getWidth()/10);
        //    white.setTextSize(yellow.getTextSize() * 4);
            white.setAntiAlias(true);
            canvas.drawText("TAP TO START!", gameArea.width/6, gameArea.getCenter().y, white);
        }
    }
    //SETS THE GAME AREA
    public void setGameArea(float x, float y, float width, float height){
        gameArea.x = x;
        gameArea.y = y;
        gameArea.width = width;
        gameArea.height = height;
        GAMEAREA_SET_FLAG = true;
    }

    protected void gamePause(){
        //enemy.circleCollider.radius+=50;
        PAUSE_FLAG = true;
        playerPreviousSpeed.x = player.xForce;
        playerPreviousSpeed.y = player.yForce;
        enemyPreviousSpeed.x = enemy.xForce;
        enemyPreviousSpeed.y = enemy.yForce;
        enemy.xForce = 0;
        enemy.yForce = 0;
        enemy.calculateMomentum();
        player.xForce = 0;
        player.yForce = 0;
        player.calculateMomentum();
        //timer--;
    }
    protected void gameUnPause(){
        PAUSE_FLAG = false;
        if(playerPreviousSpeed != null && enemyPreviousSpeed!= null) {
            player.xForce = playerPreviousSpeed.x;
            player.yForce = playerPreviousSpeed.y;
            enemy.xForce = enemyPreviousSpeed.x;
            enemy.yForce = enemyPreviousSpeed.y;
        }
    }

    public boolean isHighscore(){
        List<Highscore> hlist = db.finnAlleHighscores();

        if(player.points > 0 && hlist.isEmpty()){
            return true;
        }
        else if (player.points > 0 && !hlist.isEmpty()) {
            if(player.points > hlist.get(0).getPoints()){
                return true;
            }
        }
        return false;
    }

    //ON GAMEOVER
    public void onGameOver(){
        enemy.circleCollider.radius+=50;
        enemy.xForce = 0;
        enemy.yForce = 0;
        enemy.calculateMomentum();
        player.xForce = 0;
        player.yForce = 0;
        player.calculateMomentum();
        timer--;
        boolean isHighscore = isHighscore();
        if(isHighscore){
            showEnterHighscore = true;
        }
    }


    //INITIATES A NEW ROUND
    public void resetGameState(){
        showRules = false;
        showHighscores = false;
        GAMEOVER_FLAG = false;
        player.points = 0;

        //GETS THE CENTER OF THE GAME AREA
        Vector2D centerOfGameArea = gameArea.getCenter();
        //PLACES THE PLAYER OBJECT IN THE CENTER OF THE GAME AREA
        player.setPostion(centerOfGameArea.x, centerOfGameArea.y+player.circleCollider.radius*6);

        //PAUSES THE PLAYER POSITION
        player.xForce = 0;
        player.yForce = 0;
        //CALCULATES PLAYER OBJECT MOMENTUM
        player.calculateMomentum();
        enemy.xForce = 0;
        enemy.yForce = 0;
        enemy.calculateMomentum();

        //CALCULATES ENEMY OBJECTS DIRECTIONAL FORCE ON THE X-AXIS
        double enemyStartingDirection = Math.random()*1;
        if(Math.round(enemyStartingDirection) == 0){
            enemy.xForce = -1;
        }
        else{
            enemy.xForce = 1;
        }
        enemy.yForce = 1;

        //PLACES THE ENEMY OBJECT
        enemy.setPostion(gameArea.getCenter().x, gameArea.y+ enemy.circleCollider.radius*2);



    }

    //SPAWNS COLLECTABLE AKA COINS WITHIN THE GAME AREA
    public void spawnCollectable(float collectableRadius){
        collectable.position.x = (float)(Math.random()*(gameArea.width-(gameArea.x+20)-20) + (gameArea.x+20)) ;
        collectable.position.y = (float)(Math.random()*(gameArea.height-(gameArea.y+20)-20) + (gameArea.y+20)) ;
        collectable.circleCollider.update(collectable.position.x, collectable.position.y, collectableRadius);
        collected = false;
    }


    public void CollisionChecker() {

        //UPDATE COLLIDERS
        player.boxCollider.update(player.position.x, player.position.y, 40, 40);
        player.circleCollider.update(player.position.x, player.position.y, getWidth()/16);

        enemy.boxCollider.update(enemy.position.x, enemy.position.y,80, 80);
        enemy.circleCollider.update(enemy.position.x,enemy.position.y, getWidth()/8);

        //CHECKS FOR COLLISION WITH ENEMY OBJECT AND CHANGES GAMEOVER FLAG
        if(enemy.circleCollider.isCollision(player.circleCollider)){

            GAMEOVER_FLAG = true;
        }


        //CHECKS FOR PLAYER COLLISION WITH COLLECTABLE COIN AND UPDATES POINTS SENDS SIGNAL TO SPAWN METHOD
        if(player.circleCollider.isCollision(collectable.circleCollider)){
            player.points++;
            collected = true;

        }

    }

    public void updateInitials(float fx,float fy){

            if(initUpA.isCollision(fx,fy)){
                if(initCount[0] > 0) {
                    initCount[0]--;
                }
                else{
                    initCount[0] = Extras.alphabeta.length-1;
                }
            }
            if(initUpB.isCollision(fx,fy)){
                if(initCount[1] > 0){
                    initCount[1]--;
                }
                else{
                    initCount[1] = Extras.alphabeta.length-1;
                }
            }
            if(initUpC.isCollision(fx,fy)) {
                if (initCount[2] > 0) {
                    initCount[2]--;
                }
                else{
                    initCount[2] = Extras.alphabeta.length-1;
                }

            }
            if(initDownA.isCollision(fx,fy)){
                if(initCount[0] < Extras.alphabeta.length-1) {
                    initCount[0]++;
                }
                else {
                    initCount[0] = 0;
                }

            }
            if(initDownB.isCollision(fx,fy)){
                if(initCount[1] < Extras.alphabeta.length-1) {
                    initCount[1]++;
                }
                else {
                    initCount[1] = 0;
                }
            }
            if(initDownC.isCollision(fx,fy)){
                if(initCount[2] < Extras.alphabeta.length-1) {
                    initCount[2]++;
                }
                else {
                    initCount[2] = 0;
                }
            }

            initials[0] = Extras.getLetter(initCount[0]);
            initials[1] = Extras.getLetter(initCount[1]);
            initials[2] = Extras.getLetter(initCount[2]);

    }
    //PLAYER OBJECT TOUCH CONTROLS
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        //GETS THE X AND Y VALUES OF TOUCHEVENT
        float fx = event.getX();
        float fy = event.getY();
       //
        if(showEnterHighscore){
            updateInitials(fx,fy);
            if(registerButton.isCollision(fx,fy)){
                Highscore nuHighscore = new Highscore(""+initials[0]+initials[1]+initials[2], player.points);
                db.insertCorrectplacement(nuHighscore);
                showEnterHighscore = false;
            }
        }
        else{

        RESET_GAME_FLAG = false;
        if(PAUSE_FLAG == false) {


            //CHANGES PLAYER DIRECTIONAL FORCE ON THE X-AXIS ACCORDINGLY
            if (fx > player.position.x - player.circleCollider.radius && fx < player.position.x + player.circleCollider.radius)
                player.xForce = 0f;
            else if (fx > player.position.x + player.circleCollider.radius) {
                player.xForce = 1.0f;
            } else if (fx < player.position.x - player.circleCollider.radius) {
                player.xForce = -1.0f;
            }

            //CHANGES PLAYER DIRECTIONAL FORCE ON THE Y-AXIS ACCORDINGLY
            if (fy > player.position.y - player.circleCollider.radius && fy < player.position.y + player.circleCollider.radius)
                player.yForce = 0f;

            else if (fy > player.position.y + player.circleCollider.radius) {
                player.yForce = 1.0f;
            } else if (fy < player.position.y - player.circleCollider.radius) {
                player.yForce = -1.0f;
            }

            //COMPENSATING FOR LOSS OF FORCE ON ONE AXIS
            if (player.yForce == 0 && player.xForce != 0) {
                player.xForce *= 1.2;
            } else if (player.yForce != 0 && player.xForce == 0) {
                player.yForce *= 1.2;
            }

            this.invalidate();
        }
        }
            return super.onTouchEvent(event);


    }
    //UPDATES THE PLAYER OBJECT POSITION
    public void updatePlayer(){

        //CHECKS IF THE PLAYER OBJECT COLLIDES WITH THE EDGES OF THE GAMEAREA AND INVERTS DIRECTIONAL FORCE
        if (player.position.x >= gameArea.width - player.circleCollider.radius){
            player.xForce = -1.0f;
        }
        if (player.position.x <= gameArea.x+player.circleCollider.radius) { //0
            player.xForce = 1.0f;
        }
        if (player.position.y >= gameArea.height-player.circleCollider.radius){// - bmp.getHeight()) {
            player.yForce = -1.0f;
        }
        if (player.position.y <= gameArea.y +player.circleCollider.radius) { //0
            player.yForce = 1.0f;
        }
        if(RESET_GAME_FLAG == false) {
            //CALCULATES PLAYER OBJECTS MOMENTUM
            player.calculateMomentum();

        }
        //UPDATES THE PLAYER OBJECTS POSITION INSIDE THE GAME AREA
        player.updatePosition();
    }

    //UPDATES THE ENEMY OBJECTS POSITION
    public void updateEnemy(){


        //CHECKS IF THE ENEMY OBJECT COLLIDES WITH THE EDGES OF THE GAMEAREA AND INVERTS DIRECTIONAL FORCE
        if (enemy.position.x >= gameArea.width-enemy.circleCollider.radius){//- bmp.getWidth()) {
            enemy.xForce = -1.0f;
        }
        if (enemy.position.x <= gameArea.x+enemy.circleCollider.radius) { //0
            enemy.xForce = 1.0f;
        }

        if (enemy.position.y >= gameArea.height - enemy.circleCollider.radius){// - bmp.getHeight()) {
            enemy.yForce = -1.0f;
        }
        if (enemy.position.y <= gameArea.y+enemy.circleCollider.radius) {    //0
            enemy.yForce = 1.0f;
        }
        if(RESET_GAME_FLAG == false) {
            //CALCULATES THE ENEMY OBJECTS MOMENTUM
            enemy.calculateMomentum();
        }
        //UPDATES THE ENEMY OBJECTS POSITION INSIDE THE GAME AREA
        enemy.updatePosition();

    }

    //ANIMATES THE ENEMY OBJECTS GRAPHICS
    public void updateEnemyEye(){
        if(enemyPupilAnim == 0){
            enemyPupil++;

        }
        else if(enemyPupilAnim == 1){
            enemyPupil--;

        }
        if(enemyPupil <= 5){
            enemyPupilAnim = 0;
        }
        else if(enemyPupil >= 16){
            enemyPupilAnim = 1;
        }


        if(player.circleCollider.x > enemy.circleCollider.x-enemy.circleCollider.radius && player.circleCollider.x < enemy.circleCollider.x+enemy.circleCollider.radius){
            enemyPupilX = enemy.circleCollider.x;
        }
        else if(player.circleCollider.x > enemy.circleCollider.x-enemy.circleCollider.radius){
            enemyPupilX = enemy.circleCollider.x+10 ;

        }
        else if(player.circleCollider.x < enemy.circleCollider.x+ enemy.circleCollider.radius){
            enemyPupilX = enemy.circleCollider.x-10;
        }

        if(player.circleCollider.y > enemy.circleCollider.y-enemy.circleCollider.radius && player.circleCollider.y < enemy.circleCollider.y+ enemy.circleCollider.radius){
            enemyPupilY = enemy.circleCollider.y-15;
        }
        else if(player.circleCollider.y > enemy.circleCollider.y-enemy.circleCollider.radius){
            enemyPupilY = enemy.circleCollider.y-15+10 ;

        }
        else if(player.circleCollider.y < enemy.circleCollider.y+enemy.circleCollider.radius){
            enemyPupilY = enemy.circleCollider.y-15-10;
        }

    }

    public void killGameLoop(){
        gameLoop.setRunning(false);
    }


}