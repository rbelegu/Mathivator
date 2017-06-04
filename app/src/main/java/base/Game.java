package base;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import business.Exercise;
import business.Settings;

/**
 * Class Game
 *
 * Sets up the Game
 *
 *
 * @author R. Belegu + D. Tsichlakis
 * @version 1.0
 */

public class Game {
    private List<Exercise> exerciseList;
    private Settings settings;
    private static final String LOG_TAG = Game.class.getSimpleName();
    public static final int EXCERCICECOUNT = 12;
    public Game(Settings settings){
        this.settings = settings;
    }

    /**
     * This handles the initialization of the Game and
     * all excercises
     */

    public void initializeGame(){
//        Log.d(LOG_TAG,"Initialize Game: ");
        exerciseList = new ArrayList<>();
        int length = settings.getRechenOperatoren().size();
        int typeLength =  EXCERCICECOUNT / length;
        int typeCount = 0;
        int typeNr = 0;
        for(int i=0;i< EXCERCICECOUNT;i++){
            if(typeCount == typeLength){
                typeNr++;
                typeCount = 0;
            }
            int operator = settings.getRechenOperatoren().get(typeNr);
            exerciseList.add(createExercice(operator,settings.getZahlenRaum()));
            typeCount++;
        }
    }

    /**
     * This handles the creation of Excercices depending on Operator
     * @param operator The chosen math operators.
     * @param zahlenRaum the highest Number in the Math excercises
     */

    private Exercise createExercice(Integer operator, int zahlenRaum){
        Exercise ex = new Exercise();
        ex.setOperator(operator);
        switch(ex.getOperator()){
            case 1:
                createPlus(ex,zahlenRaum);
                //Log.d(LOG_TAG,"create Plus");
                break;
            case 2: createMinus(ex,zahlenRaum);
                //Log.d(LOG_TAG,"create Minus");
                break;
            case 3: createMal(ex,zahlenRaum);
                //Log.d(LOG_TAG,"create Mal");
                break;
            case 4: createDivision(ex,zahlenRaum);
                //Log.d(LOG_TAG,"create teilen");
                break;
        }

        return ex;
    }
    /**
     * Creates an summation excercise
     * Based on the zahlenRaum it defines an possible solution
     * Based on the solution it defines the possible summand
     * @param ex the Excercise.
     * @param zahlenRaum the highest possible Number in the Math excercises
     */

    private void createPlus(Exercise ex, int zahlenRaum){
        int solution = getRandomNumber(0,zahlenRaum);
        int a = getRandomNumber(0, solution);
        int b = solution - a;
        ex.setFirst(a);
        ex.setSecond(b);
        ex.setSolution(solution);
        ex.setPoints(settings.getMaximumPoints());
    }
    /**
     * Creates an subtraction excercise
     * Based on the zahlenRaum it defines an possible minued and subtrahend
     * Based on the minued and the subtrahend the solution is calculated
     * @param ex the Excercise.
     * @param zahlenRaum the highest possible Number in the Math excercises
     */
    private void createMinus(Exercise ex, int zahlenRaum){
        int a = getRandomNumber(0, zahlenRaum);
        int b = getRandomNumber(0, zahlenRaum);
        if(a>b){
            ex.setFirst(a);
            ex.setSecond(b);
            ex.setSolution(a-b);
        }else{
            ex.setFirst(b);
            ex.setSecond(a);
            ex.setSolution(b-a);
        }
        ex.setPoints(settings.getMaximumPoints());
    }
    /**
     * Creates an multiplication excercise
     * Based on the zahlenRaum it defines an possible solution
     * With the solution an Array list is build of possible factors
     * For defining the first Factor "a" a random Object out of the Array is taken
     * Compared to basic math excercises the points are doubled
     * @param ex the Excercise.
     * @param zahlenRaum the highest possible Number in the Math excercises
     */

    private void createMal(Exercise ex, int zahlenRaum){
        int solution = getRandomNumber(1, zahlenRaum);
        int bsel = solution;
        ArrayList<Integer> possible = new ArrayList<>();
        while(bsel>0){
            if((solution%bsel) == 0){
                possible.add(bsel);
            }
            bsel--;
        }
        int a = possible.get((int)Math.round((Math.random())*(possible.size()-1)));
        ex.setFirst(a);
        ex.setSecond(solution/a);
        ex.setSolution(solution);
        ex.setPoints(2 * settings.getMaximumPoints());

    }

    /**
     * Creates an division excercise
     * Based on the zahlenRaum it defines an dividend
     * With the dividend an Array list is build of possible divisors
     * For defining the divisor a random Object out of the Array is taken
     * Compared to basic math excercises the points are doubled
     * @param ex the Excercise.
     * @param zahlenRaum the highest possible Number in the Math excercises
     */

    private void createDivision(Exercise ex, int zahlenRaum){
        int a = getRandomNumber(1, zahlenRaum);
        ArrayList<Integer> possible = new ArrayList<>();
        int bsel = a;
        while(bsel>0){
            if((a%bsel) == 0){
                possible.add(bsel);
            }
            bsel--;
        }
        int b = possible.get((int)Math.round((Math.random())*(possible.size()-1)));
        ex.setFirst(a);
        ex.setSecond(b);
        ex.setSolution(a/b);
        ex.setPoints(2 * settings.getMaximumPoints());
    }

    /**
     * Returns the List of Excercises
     */


    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    /**
     * Returns an Random integer between two values
     * @param min the minimum Number.
     * @param max the highest possible Number
     */

    private int getRandomNumber(int min, int max){
        return (new Random()).nextInt((max-min)+1)+min;
    }
}
