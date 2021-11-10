package code;

public interface StateResult {

    final class None implements StateResult {
    }

    final class NewState implements StateResult {

        private final String result;

        public NewState(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
