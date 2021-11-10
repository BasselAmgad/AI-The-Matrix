package code;

public interface Operator {
    public String getCode();
    StateResult applyOperator(State state);
}
