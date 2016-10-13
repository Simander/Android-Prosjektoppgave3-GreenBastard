package asimon.gamedev.com.rollaball.MyFramework;

/**
 * Created by simander on 04/11/15.
 */
public class CircleCollider {
    public float x;
    public float y;
    public float radius;

    //Constructor
    public CircleCollider(Float x, Float y, Float radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    //Empty Constructor
    public CircleCollider(){}



    //Updates the position of the CircleCollider-object
    public void update(float x, float y, float radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    //Checks if there is a collision with another CircleCollider-object
    public boolean isCollision(CircleCollider cc){
        float dx = this.x - cc.x;
        float dy = this.y - cc.y;
        float radSum = this.radius+cc.radius;
        boolean collision =(dx*dx + dy*dy) < radSum*radSum;
        return collision;
    }
}
