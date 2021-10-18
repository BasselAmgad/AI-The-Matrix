package code;

public enum Action {

    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right"),
    CARRY("carry"),
    DROP("drop"),
    TAKE_PILL("takePill"),
    KILL("kill"),
    FLY("fly");

    public final String code;

    Action(String code) {
        this.code = code;
    }

    public static Action parse(String action) {
        for (Action actionVal : Action.values()) {
            if (actionVal.code.equals(action)) {
                return actionVal;
            }
        }
        throw new IllegalArgumentException(String.format("Couldn't find an action with value %s.", action));
    }
}
