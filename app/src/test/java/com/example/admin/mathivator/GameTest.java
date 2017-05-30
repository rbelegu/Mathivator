package com.example.admin.mathivator;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import base.Game;
import business.Exercise;
import business.Settings;

import static org.junit.Assert.*;
/**
 * Created by admin on 27.05.2017.
 */

public class GameTest {
    @Test
    public void startTest(){
        Settings s = createSettings();
        Game g = new Game(s);
        g.initializeGame();
        int size = g.getExerciseList().size();
        assertTrue(size==12);
        Exercise e = g.getExerciseList().get(0);
        assertTrue(e.getFirst()<101);
        assertTrue(e.getFirst()>0);
        assertTrue(e.getSecond()<101);
        assertTrue(e.getSecond()>0);
        switch(e.getOperator()){
            case 1 : assertTrue(e.getFirst()+e.getSecond() == e.getSolution());
                break;
            case 2 : assertTrue(e.getFirst()-e.getSecond() == e.getSolution());
                break;
            case 3 : assertTrue(e.getFirst()*e.getSecond() == e.getSolution());
                break;
            case 4 : assertTrue(e.getFirst()/e.getSecond() == e.getSolution());
                break;
        }
    }

    private Settings createSettings(){
        Settings s = new Settings();
        List<Integer> operators = new ArrayList<>();
        operators.add(1);
        operators.add(2);
        operators.add(3);
        operators.add(4);
        s.setRechenOperatoren(operators);
        s.setZahlenRaum(100);
        s.setHighScore(0);
        return s;
    }
}
