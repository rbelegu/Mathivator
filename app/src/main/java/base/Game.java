package base;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import business.Exercise;
import business.Settings;

/**
 * Created by admin on 26.05.2017.
 * blbla
 */

public class Game {
    private List<Exercise> exerciseList;
    private Settings settings;
    private static final String LOG_TAG = Game.class.getSimpleName();
    public static final int exerciceCount = 12;
    public Game(Settings settings){
        this.settings = settings;
    }

    public void initializeGame(){
        Log.d(LOG_TAG,"Initialize Game: ");
        exerciseList = new ArrayList<>();
        int length = settings.getRechenOperatoren().size();
        int typeLength = exerciceCount / length;
        int typeCount = 0;
        int typeNr = 0;
        for(int i=0;i<exerciceCount;i++){
            if(typeCount == typeLength){
                typeNr++;
                typeCount = 0;
            }
            int operator = settings.getRechenOperatoren().get(typeNr);
            exerciseList.add(createExercice(operator,settings.getZahlenRaum()));
            typeCount++;
        }
    }

    private Exercise createExercice(Integer operator, int zahlenRaum){
        Exercise ex = new Exercise();
        ex.setOperator(operator);
        switch(ex.getOperator()){
            case 1:
                createPlus(ex,zahlenRaum);
                Log.d(LOG_TAG,"create Plus");
                break;
            case 2: createMinus(ex,zahlenRaum);
                Log.d(LOG_TAG,"create Minus");
                break;
            case 3: createMal(ex,zahlenRaum);
                Log.d(LOG_TAG,"create Mal");
                break;
            case 4: createDivision(ex,zahlenRaum);
                Log.d(LOG_TAG,"create teilen");
                break;
        }

        return ex;
    }

    private void createPlus(Exercise ex, int zahlenRaum){
        int solution = getRandomNumber(zahlenRaum);
        int a = getRandomNumber(solution);
        int b = solution - a;
        ex.setFirst(a);
        ex.setSecond(b);
        ex.setSolution(solution);
        ex.setPoints(settings.getMaximumPoints());
    }

    private void createMinus(Exercise ex, int zahlenRaum){
        int a = getRandomNumber(zahlenRaum);
        int b = getRandomNumber(zahlenRaum);
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

    private void createMal(Exercise ex, int zahlenRaum){
        int solution = getRandomNumber(zahlenRaum);
        int bsel = solution;
        ArrayList<Integer> possible = new ArrayList<>();
        int b = getRandomNumber(zahlenRaum);
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

    private void createDivision(Exercise ex, int zahlenRaum){
        int a = getRandomNumber(zahlenRaum);
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

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
    private int getRandomNumber(int zahlenRaum){
        return (int)(Math.random()*zahlenRaum);
    }
}
