package asimon.gamedev.com.rollaball;

/**
 * Created by simander on 17/09/15.
 */
public class Highscore {
    private int id;
    private String name;
    private int points;

    public Highscore() {
    }

    public Highscore(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
