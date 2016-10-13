package asimon.gamedev.com.rollaball.MyFramework;

import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;


/**
 * Created by simander on 04/11/15.
 */
public class BoxCollider {
    public float x;
    public float y;
    public float width;
    public float height;

    //Constructor
    public BoxCollider(int x, int y, int width, int height )
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //Empty Constructor
    public BoxCollider(){
    }

    //Returns a rectangle representing the BoxCollider-object
    public RectF getRect(){
        return new RectF(x,y,x+width,y+height);
    }

    //Returns a Vector2D-object representing the center of the BoxCollider-object
    public Vector2D getCenter(){
        Vector2D point = new Vector2D();
        point.x = width/2 + this.x;
        point.y = height/2 + this.y;
        return point;
    }


    //Updates the position of the BoxCollider
    public void update(float x, float y, float width, float height){

            this.x = (float)x;
            this.y = (float)y;
            this.width = width;
            this.height = height;


    }

    //Checks if there is a collision with another BoxCollider-object
    public boolean isCollision(BoxCollider r)
       {
             return r.width > 0 && r.height > 0 && width > 0 && height > 0
               && r.x < x + width && r.x + r.width > x
               && r.y < y + height && r.y + r.height > y;
       }
    public boolean isCollision(float x, float y){
        if(getRect().contains(x,y)) {
            return true;
        }
        else{
            return  false;
        }
    }

}
