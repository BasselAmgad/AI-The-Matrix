package code;

public interface Operator {

    String getCode();
    int getConstCost();
    StateResult applyOperator(State state, Operator prevOp);
}
