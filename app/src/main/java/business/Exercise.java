package business;

/**
 * Class "Exercise"
 * with methods
 *
 * @author R. Belegu
 * @version 1.0
 */

public class Exercise {
    private int solution;
    private int operator;
    private int first;
    private int second;
    private int points;
    public int getSolution() {
        return solution;
    }

    /**
     * Set the solution (result) of the exercise
     * @param solution Solution of the exercise
     */
    public void setSolution(int solution) {
        this.solution = solution;
    }

    /**
     * Returns the operator of the exercise
     * @return exercise operator
     */
    public int getOperator() {
        return operator;
    }

    /**
     * Set the operator of the exercise
     * @param operator Exercise operator
     */
    public void setOperator(int operator) {
        this.operator = operator;
    }

    /**
     * Returns the first value (x) of the exercise equation (x [operator] y = solution)
     * @return First value of the equation
     */
    public int getFirst() {
        return first;
    }

    /**
     * Set the first value (x) of the exercise equation (x [operator] y = solution)
     * @param first First value of the equation
     */
    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Returns the second value (y) of the exercise equation (x [operator] y = solution)
     * @return Second value of the equation
     */
    public int getSecond() {
        return second;
    }

    /**
     * Set the second value (y) of the exercise equation (x [operator] y = solution)
     * @param second Second value of the equation
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * Returns the points of the exercise
     * @return Possible points of the exercise
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set the points of the exercise
     * @param points Possible points of the exercise
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
