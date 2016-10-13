package asimon.gamedev.com.rollaball;

/**
 * Created by simander on 05/11/15.
 */
import android.graphics.Canvas;

public class GameLoop implements Runnable {
    static final long FPS = 60;
    private GameView view;
    private boolean running = false;
    private boolean pause = false;
    private Thread t;

    public GameLoop(GameView view) {
        this.view = view;
        t = new Thread(this);
    }

    public Thread getThreadT(){
        return  t;
    }
    public void setThreadT(Thread t){
        this.t = t;
    }

    public void setRunning(boolean run) {
        running = run;

    }
    public  boolean getRunning(){
        return running;
    }

    public void setPause(boolean b){
        pause = b;
    }







    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {

            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {

                    if(view.showEnterHighscore == true){
                        view.drawEnterHighscore(c);
                    }
                    else if(view.showRules == true){
                        view.drawRules(c);
                    }
                    else if(view.showHighscores == true){
                        view.drawHighscoreList(c);
                    }
                    else if(view.showGameGraphics == true){
                       // view.drawInputHighscore(c);
                        view.drawGameGraphics(c);
                    }

                }
                view.CollisionChecker();

            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }


            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    t.sleep(sleepTime);
                else
                    t.sleep(10);
            } catch (Exception e) {}
        }
    }
}