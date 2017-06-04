package business;

import java.util.List;

/**
 * Class "Settings"
 * with methods
 *
 * @author G. Ramizi
 * @version 1.0
 */

public class Settings {
    private int zahlenRaum;
    private List<Integer> rechenOperatoren;
    private int maximumPoints;
    private int id;
    private int highScore;
    private String name;

    /**
     * Returns the "ZahlenRaum"
     * @return zahlenRaum
     */
    public int getZahlenRaum() {
        return zahlenRaum;
    }

    /**
     * Set the "ZahlenRaum"
     * @param zahlenRaum Highscore ID
     */
    public void setZahlenRaum(int zahlenRaum) {
        this.zahlenRaum = zahlenRaum;
    }

    /**
     * Returns List with "RechenOperatoren"
     * @return rechenOperatoren List with the "RechenOperatoren" by NR
     */
    public List<Integer> getRechenOperatoren() {
        return rechenOperatoren;
    }

    /**
     * Set the "RechenOperatoren"
     * @param rechenOperatoren List with "RechenOperatoren" by NR
     */
    public void setRechenOperatoren(List<Integer> rechenOperatoren) {
        this.rechenOperatoren = rechenOperatoren;
    }

    /**
     * Returns max Points of "Zahlenraum"
     * @return Max Points of "Zahlenraum"
     */
    public int getMaximumPoints() {
        return maximumPoints;
    }

    /**
     * Set the max Points of selected "Zahlenraum"
     * @param maximumPoints max Points of selected "Zahlenraum"
     */
    public void setMaximumPoints(int maximumPoints) {
        this.maximumPoints = maximumPoints;
    }


    /**
     * Returns the id number
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID
     * @param id ID Number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of Player
     * @return Player's Name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the Player
     * @param name Name of the Player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the max possibel Highscore
     * @return Max Highscore
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Set the max possibel Highscore
     * @param highScore Max possibel Highscore
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
