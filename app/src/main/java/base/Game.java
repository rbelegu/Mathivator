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

    public Game(Settings settings){
        this.settings = settings;
    }

    public void initializeGame(){
        exerciseList = new ArrayList<>();
        for(int i=0;i<12;i++){
            exerciseList.add(createExercice(settings.getRechenOperatoren(),settings.getZahlenRaum()));

        }
    }

    private Exercise createExercice(List<Integer> operators, int zahlenRaum){
        Exercise ex = new Exercise();
        ex.setOperator(getOperator(operators));
        System.out.print("e0" + ex.getOperator());

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
        int a = getRandomNumber(zahlenRaum);
        int b = getRandomNumber(zahlenRaum);
        ex.setFirst(a);
        ex.setSecond(b);
        ex.setSolution(a+b);
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

    }

    private void createMal(Exercise ex, int zahlenRaum){
        int a = getRandomNumber(zahlenRaum);
        int b = getRandomNumber(zahlenRaum);
        ex.setFirst(a);
        ex.setSecond(b);
        ex.setSolution(a*b);
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
    }

    private int getOperator(List<Integer> operators){
        int count = operators.size();
        int op =  (int)Math.round((Math.random())*count-1);
        return operators.get(op);
    }
    public List<Exercise> getExerciseList() {
        return exerciseList;
    }
    private int getRandomNumber(int zahlenRaum){
        return (int)(Math.random()*zahlenRaum);
    }
}
