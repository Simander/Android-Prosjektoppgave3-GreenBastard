package asimon.gamedev.com.rollaball;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);



        final ActionBar abar = getSupportActionBar();
        if(abar != null){
            abar.setIcon(R.mipmap.ic_launcher_myfinito);
            abar.setTitle("");
            abar.setDisplayShowHomeEnabled(true);
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //QUIT
        if (id == R.id.quit_mbutton) {
            if(gameView.showEnterHighscore){

            }
            else {
                gameView.killGameLoop();
                this.finish();
            }
        }
        //NEW GAME
        else if(id == R.id.newgame_mbutton){
            if(gameView.showEnterHighscore){

            }
            else {
                gameView.RESET_GAME_FLAG = true;
                gameView.PAUSE_FLAG = false;
                gameView.resetGameState();
                MenuItem mItem = menu.findItem(R.id.play_mbutton);
                mItem.setIcon(R.drawable.pause);
            }
        }
        //PLAY/PAUSE
        else if(id == R.id.play_mbutton){
            if(gameView.showEnterHighscore){

            }
            else {

                gameView.showRules(false);
                gameView.showHighscores(false);
                gameView.showGameGraphics(true);
                if (gameView.GAMEOVER_FLAG || gameView.RESET_GAME_FLAG) {

                } else if (gameView.PAUSE_FLAG == true) {
                    gameView.gameUnPause();
                    MenuItem mItem = menu.findItem(R.id.play_mbutton);
                    mItem.setIcon(R.drawable.pause);
                    gameView.PAUSE_FLAG = false;

                } else {
                    gameView.PAUSE_FLAG = true;
                    if (gameView.RESET_GAME_FLAG == true) {
                        gameView.RESET_GAME_FLAG = false;

                    }
                    gameView.gamePause();

                    MenuItem mItem = menu.findItem(R.id.play_mbutton);
                    mItem.setIcon(R.drawable.play);
                }
            }

        }
        //HELP
        else if(id == R.id.help_mbutton){
            if(gameView.showRules == true){

            }
            else {

                MenuItem mItem = menu.findItem(R.id.play_mbutton);
                mItem.setIcon(R.drawable.play);
                gameView.PAUSE_FLAG = true;
                gameView.gamePause();

                gameView.showHighscores(false);
                gameView.showGameGraphics(false);
                gameView.showRules(true);
            }
        }
        //HIGHSCORES
        else if(id==R.id.highscore_mbutton){
            if(gameView.showHighscores == true){

            }
            else {
                gameView.showHighscores(true);
                gameView.showRules(false);
                gameView.showGameGraphics(false);
                MenuItem mItem = menu.findItem(R.id.play_mbutton);
                mItem.setIcon(R.drawable.play);
                gameView.PAUSE_FLAG = true;
                gameView.gamePause();
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
