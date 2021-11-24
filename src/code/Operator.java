package code;

public interface Operator {

    String getCode();

    StateResult applyOperator(State state, Operator op);
}
