package code;

public interface StateResult {

    final class None implements StateResult {
    }

    final class NewState implements StateResult {

        private final String result;
        private final String hashResult;
        private final int actionCost;

        public NewState(String result, String hashResult, int actionCost) {
            this.result = result;
            this.hashResult = hashResult;
            this.actionCost = actionCost;
        }

        public String getResult() {
            return result;
        }

        public String getHashResult() {
            return hashResult;
        }

        public int getActionCost() {
            return actionCost;
        }
    }
}
