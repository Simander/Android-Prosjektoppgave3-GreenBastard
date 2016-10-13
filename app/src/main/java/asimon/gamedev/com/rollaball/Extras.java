package asimon.gamedev.com.rollaball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;

/**
 * Created by simander on 25/11/15.
 */
public class Extras {

    public static char[] alphabeta = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    //Divides a string into a list of words
    public static LinkedList<String> stringToWordList(String s){
        LinkedList<String> words = new LinkedList<>();
        int charCounter = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i)==' ' || s.charAt(i)=='.' || s.charAt(i) == '!' || i == s.length()-1){
                String s2 = "";
                for(int u = charCounter; u <= i; u++){
                    s2+= s.charAt(u);
                }
                words.add(s2);
                charCounter = i+1;
            }
        }
        return words;
    }

    public static LinkedList<String> formatRules(String s, int numOfChars){

        LinkedList<String> words = new LinkedList<>();
        int charCounter = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i)==' ' || s.charAt(i)=='.' || s.charAt(i) == '!' || i == s.length()-1){
                String s2 = "";
                for(int u = charCounter; u <= i; u++){
                    s2+= s.charAt(u);
                }
                words.add(s2);
                charCounter = i+1;
            }
        }
        int lineLength = 0;
        String s3 = "";

        LinkedList<String> lines = new LinkedList<>();

        for(int w = 0; w < words.size(); w++) {

            if (lineLength + words.get(w).length() < numOfChars) {
                s3 += words.get(w) + " ";
                lineLength = s3.length();
            } else {
                lines.add(s3);
                lineLength = 0;
                s3 = "";
                s3 += words.get(w) + " ";

                lineLength = s3.length();
            }
                if(w == words.size()-1){
                    lines.add(s3);
                }
        }
        return lines;
    }



    public static char getLetter(int number){
        return alphabeta[number];

    }
}
