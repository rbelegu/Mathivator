package business;

import java.util.List;

/**
 * Created by admin on 22.05.2017.
 */

public class Settings {
    private int zahlenRaum;
    private List<Integer> rechenOperatoren;
    private int maximumPoints;
    private int id;
    private int highScore;
    private String name;

    public int getZahlenRaum() {
        return zahlenRaum;
    }

    public void setZahlenRaum(int zahlenRaum) {
        this.zahlenRaum = zahlenRaum;
    }

    public List<Integer> getRechenOperatoren() {
        return rechenOperatoren;
    }

    public void setRechenOperatoren(List<Integer> rechenOperatoren) {
        this.rechenOperatoren = rechenOperatoren;
    }

    public int getMaximumPoints() {
        return maximumPoints;
    }

    public void setMaximumPoints(int maximumPoints) {
        this.maximumPoints = maximumPoints;
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

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
