package code;

import java.util.ArrayList;

public enum Action implements Operator {
    UP("up"){
        @Override
        public StateResult applyOperator(State state) {
            if (state.neoX != 0 && !state.doesAgentExist(state.neoX - 1, state.neoY)) {
                state.neoX--;
                state.increaseHostagesDamage();
                return new StateResult.NewState(state.decodeState());
            }
            return new StateResult.None();
        }
    },
    DOWN("down"){
        @Override
        public StateResult applyOperator(State state) {
            if (state.neoX != MatrixConfig.M - 1 && !state.doesAgentExist(state.neoX+ 1, state.neoY)) {
                state.neoX++;
                state.increaseHostagesDamage();
                return new StateResult.NewState(state.decodeState());
            }
            return new StateResult.None();
        }
    },
    LEFT("left"){
        @Override
        public StateResult applyOperator(State state) {
            if (state.neoY != 0 && !state.doesAgentExist(state.neoX, state.neoY - 1)) {
                state.neoY--;
                state.increaseHostagesDamage();
                return new StateResult.NewState(state.decodeState());
            }
            return new StateResult.None();
        }
    },
    RIGHT("right"){
        @Override
        public StateResult applyOperator(State state) {
            if (state.neoY != MatrixConfig.N - 1 && !state.doesAgentExist(state.neoX, state.neoY + 1)) {
                state.neoY++;
                state.increaseHostagesDamage();
                return new StateResult.NewState(state.decodeState());
            }
            return new StateResult.None();
        }
    },
    CARRY("carry"){
        @Override
        public StateResult applyOperator(State state) {
            if (state.carriedDamage.size() == MatrixConfig.carryCapacity)
                return new StateResult.None();

            int i = 0;
            while (i < state.hostagesX.size()) {
                if (state.hostagesX.get(i) == state.neoX && state.hostagesY.get(i) == state.neoY) {
                    state.hostagesX.remove(i);
                    state.hostagesY.remove(i);
                    state.carriedDamage.add(state.hostagesDamage.remove(i));
                    state.increaseHostagesDamage();
                    return new StateResult.NewState(state.decodeState());
                }
                i++;
            }
            return new StateResult.None();
        }
    },
    DROP("drop"){
        @Override
        public StateResult applyOperator(State state) {
            if (state.neoX == MatrixConfig.telephoneX && state.neoY == MatrixConfig.telephoneY
                    && state.carriedDamage.size() > 0) {
                state.carriedDamage = new ArrayList<>();
                return new StateResult.NewState(state.decodeState());
            }
            return new StateResult.None();
        }
    },
    KILL("kill"){
        @Override
        public StateResult applyOperator(State state) {
            int[] dx = new int[]{-1, 0, 1, 0};
            int[] dy = new int[]{0, -1, 0, 1};
            boolean isFound = false;
            for (int move = 0; move < dx.length; move++) {
                int x = state.neoX + dx[move];
                int y = state.neoY + dy[move];
                int i = 0;
                while (i < state.agentsX.size()) {
                    if (state.agentsX.get(i) == x & state.agentsY.get(i) == y) {
                        state.agentsX.remove(i);
                        state.agentsY.remove(i);
                        state.countKilled++;
                        isFound = true;
                    } else {
                        i++;
                    }
                }
                i=0;
                while (i < state.mutatedX.size()) {
                    if (state.mutatedX.get(i) == x & state.mutatedY.get(i) == y) {
                        state.mutatedX.remove(i);
                        state.mutatedY.remove(i);
                        state.countKilled++;
                        state.countDead++;
                        isFound = true;
                    } else {
                        i++;
                    }
                }
            }
            if (isFound) {
                state.neoDamage = Math.min(100, state.neoDamage + 20);
                if (state.neoDamage == 100) {
                    return new StateResult.None();
                }
            }
            else{
                return new StateResult.None();
            }
            state.increaseHostagesDamage();
            return new StateResult.NewState(state.decodeState());
        }
    },
    TAKE_PILL("takePill"){
        @Override
        public StateResult applyOperator(State state) {
            for (int i = 0; i < state.pillsX.size(); i++) {
                if (state.pillsX.get(i) == state.neoX && state.pillsY.get(i) == state.neoY) {
                    state.pillsX.remove(i);
                    state.pillsY.remove(i);
                    state.neoDamage = Math.max(0, state.neoDamage - 20);
                    for (int j = 0; j < state.hostagesDamage.size(); j++) {
                        //All hostages remaining in hostagesX, hostagesY, hostagesDamage are still alive
                        int oldDamage = state.hostagesDamage.get(j);
                        state.hostagesDamage.set(j, oldDamage - 20);
                    }
                    for (int j = 0; j < state.carriedDamage.size(); j++) {
                        //All carried remaining with damage<100 carriedDamage are still alive
                        int oldDamage = state.carriedDamage.get(j);
                        if (oldDamage<100)
                            state.carriedDamage.set(j, oldDamage - 20);
                    }
                    return new StateResult.NewState(state.decodeState());
                }
            }
            return new StateResult.None();
        }
    },
    FLY("fly"){
        @Override
        public StateResult applyOperator(State state) {
            for (int i = 0; i < MatrixConfig.startPadsX.length; i++) {
                if (MatrixConfig.startPadsX[i] == state.neoX && MatrixConfig.startPadsY[i] == state.neoY) {
                    state.neoX = MatrixConfig.finishPadsX[i];
                    state.neoY = MatrixConfig.finishPadsY[i];
                    state.increaseHostagesDamage();
                    return new StateResult.NewState(state.decodeState());
                }
            }
            return new StateResult.None();
        }
    };




    public final String code;
    public String getCode(){return code;}
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
