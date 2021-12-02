package code;

public interface StateResult {

    final class None implements StateResult {
    }

    final class NewState implements StateResult {

        private final String result;
        private final int actionCost;

        public NewState(String result, int actionCost) {
            this.result = result;
            this.actionCost = actionCost;
        }

        public String getResult() {
            return result;
        }
        public int getActionCost(){return actionCost;}
    }
}
