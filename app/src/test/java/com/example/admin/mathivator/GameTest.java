package com.example.admin.mathivator;
import android.util.Log;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import base.Game;
import business.Exercise;
import business.Settings;

import static org.junit.Assert.*;
/**
 * Class GameTest
 *
 * Tests Excercises
 *
 *
 * @author N.Hafen
 * @version 1.0
 */
public class GameTest {
    private int Zahlenraum = 10;    //<-- Define Zahlenraum
    @Test
    public void startTest(){
        System.out.println("Zahlenraum = " + Zahlenraum + "\n");
        for(int i = 1; i <= 100;i++) {
            System.out.println("Start Test : " + i);
            Settings s = createSettings();
            Game g = new Game(s);
            g.initializeGame();
            int size = g.getExerciseList().size();
            assertTrue(size == 12);
            Exercise e = g.getExerciseList().get(0);

            assertTrue(e.getFirst() <= Zahlenraum);
            assertTrue(e.getFirst() >= 0);
            assertTrue(e.getSecond() <= Zahlenraum);
            assertTrue(e.getSecond() >= 0);
            assertTrue(e.getSolution() <= Zahlenraum);
            assertTrue(e.getSolution() >= 0);
            switch (e.getOperator()) {
                case 1:
                    System.out.println(e.getFirst() + " + " + e.getSecond() + " = " + e.getSolution());
                    assertTrue(e.getFirst() + e.getSecond() == e.getSolution());
                    break;
                case 2:
                    System.out.println(e.getFirst() + " - " + e.getSecond() + " = " + e.getSolution());
                    assertTrue(e.getFirst() - e.getSecond() == e.getSolution());
                    break;
                case 3:
                    System.out.println(e.getFirst() + " * " + e.getSecond() + " = " + e.getSolution());
                    assertTrue(e.getFirst() * e.getSecond() == e.getSolution());
                    break;
                case 4:
                    System.out.println(e.getFirst() + " / " + e.getSecond() + " = " + e.getSolution());
                    assertTrue(e.getFirst() / e.getSecond() == e.getSolution());
                    break;
            }
            System.out.println("------------------------------------------");
        }
    }

    private Settings createSettings(){
        Settings s = new Settings();
        List<Integer> operators = new ArrayList<>();
        int aOperator = (int) (4 * Math.random()) + 1;
        operators.add(aOperator);

        s.setRechenOperatoren(operators);
        s.setZahlenRaum(Zahlenraum);
        s.setHighScore(0);
        return s;
    }
}
