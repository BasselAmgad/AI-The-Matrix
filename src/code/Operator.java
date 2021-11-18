package code;

public interface Operator {
    public String getCode();
    public  int getCost();
    StateResult applyOperator(State state);
}
