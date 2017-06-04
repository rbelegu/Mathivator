package business;

import java.util.Date;

/**
 * Class "Highscore"
 * with methods
 *
 * @author D. Tsichlakis
 * @version 1.0
 */

public class Highscore {
    private int id;
    private int highscore;
    private String name;
    private int time;
    private Date date;
    private String city;

    /**
     * Returns the ID of the Highscore entry
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Highscore ID
     * @param id Highscore ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Highscore
     * @return highscore Highscroe (Points)
     */
    public int getHighscore() {
        return highscore;
    }

    /**
     * Set the Highscore
     * @param highscore Highscore Points
     */
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    /**
     * Returns the Name of the Player
     * @return name Name of the Player
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name of the Player
     * @param name Name of the Player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Time duration of the game
     * @return time Time duration
     */
    public int getTime() {
        return time;
    }

    /**
     * Set the Time duration of the game
     * @param time Time duration of the game
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Returns the Date from the game session
     * @return date Date of game
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the Date from the game session
     * @param date Date from the game session
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the name of the City (User location - GPS)
     * @return name of the City
     */
    public String getCity() {
        return city;
    }

    /**
     * Set the name of the City (User location - GPS)
     * @param city Name of the users city
     */
    public void setCity(String city) {
        this.city = city;
    }
}
