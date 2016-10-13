package asimon.gamedev.com.rollaball.MyFramework;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by simander on 04/11/15.
 */
public class MoveableObject {
    public float xForce;   //Value either -1, 0 or 1 directional force on the x-axis
    public float yForce;   //Value either -1, 0 or 1 directional force on the y-axis
    public float speed;    //movement speed

    public Vector2D position = new Vector2D();
    public Vector2D momentum = new Vector2D();


    public BoxCollider boxCollider;
    public CircleCollider circleCollider;



    public int points = 0;



    public MoveableObject(){
        boxCollider = new BoxCollider();
        circleCollider = new CircleCollider();
    }

    public void setForce(float xForce, float yForce){
        this.xForce = xForce;
        this.yForce = yForce;
    }



    //beregner hvor mye objektet skal flytte seg avhengig av fart og retning
    public void calculateMomentum(){
        momentum.x = xForce*speed;
        momentum.y = yForce*speed;
    }
    public void setPostion(float x, float y){
        position.x = x;
        position.y = y;
    }


    public void updatePosition(){
        position.x += momentum.x;
        position.y += momentum.y;
    }

    public void updateForce(float x, float y){
        xForce = x;
        yForce = y;
    }

    public void changeDirection(float x, float y){

    }

}
