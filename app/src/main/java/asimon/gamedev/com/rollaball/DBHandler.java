package asimon.gamedev.com.rollaball;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simander on 17/09/15.
 */
public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_HIGHSCORES="Highscores";
    static String KEY_ID="_ID";
    static String KEY_NAME="Navn";
    static String KEY_SCORE="Score";
    static int DATABASE_VERSION=1;
    static String DATABASE_NAME="HangmanDB";

/*
    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_TABELL = "CREATE TABLE "+TABLE_HIGHSCORES+ "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," + KEY_SCORE + " INTEGER" + ")";
        Log.d("SQL", LAG_TABELL);
        db.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES);
        onCreate(db);
    }

    //Adds a Highscore
    public void leggTilHighscore(Highscore highscore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, highscore.getName());
        values.put(KEY_SCORE, highscore.getPoints());

        db.insert(TABLE_HIGHSCORES, null, values);
        db.close();

    }
    //Returns a list of all highscores
    public List<Highscore> finnAlleHighscores(){
        List<Highscore> highscoreliste = new ArrayList<Highscore>();
        String selectQuery = "SELECT * FROM "+TABLE_HIGHSCORES +" ORDER BY "+KEY_SCORE + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Highscore highscore = new Highscore();
                highscore.setId(cursor.getInt(0));
                highscore.setName(cursor.getString(1));
                highscore.setPoints(cursor.getInt(2));
                highscoreliste.add(highscore);
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return highscoreliste;
    }

    //Deletes a single highscore.
    public void slettHighScore(Highscore highscore){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIGHSCORES, KEY_ID + " =? ", new String[]{String.valueOf(highscore.getId())});
        db.close();
    }

    //Erases all database-entries
    public void renskDatabase(){
        List<Highscore> lista = finnAlleHighscores();
        for(int i = 0; i < lista.size(); i++){
            slettHighScore(lista.get(i));
        }
    }


    //Inserts a new Highscore and pushes the old one out of the database.
    public void insertCorrectplacement(Highscore highscore) {
        boolean sattInn = false;
        List<Highscore> lista = finnAlleHighscores();
        if (lista.size() < 10) {
            leggTilHighscore(highscore);

        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (i > 9) {
                    slettHighScore(lista.get(i));
                } else if (highscore.getPoints() > lista.get(i).getPoints() && sattInn == false) {
                    lista.add(i, highscore);
                    leggTilHighscore(highscore);
                    sattInn = true;
                }

            }
        }
    }



}
