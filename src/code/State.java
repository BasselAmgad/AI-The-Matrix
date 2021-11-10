package code;

import java.util.ArrayList;

public class State {

    int neoX, neoY, neoDamage;
    int countKilled;
    ArrayList<Integer> agentsX, agentsY;
    ArrayList<Integer> pillsX, pillsY;
    int countDead;
    ArrayList<Integer> hostagesX, hostagesY, hostagesDamage;
    ArrayList<Integer> carriedDamage;
    //removed carriedX, carriedY because the last updates in the description made them unnecessary
    ArrayList<Integer> mutatedX, mutatedY;

    public String decodeState() {

        String[] agents = new String[agentsX.size() * 2];
        for (int i = 0; i < agentsX.size(); i++) {
            agents[i * 2] = "" + agentsX.get(i);
            agents[i * 2 + 1] = "" + agentsY.get(i);
        }
        String[] pills = new String[pillsX.size() * 2];
        for (int i = 0; i < pillsX.size(); i++) {
            pills[i * 2] = "" + pillsX.get(i);
            pills[i * 2 + 1] = "" + pillsY.get(i);
        }
        String[] hostages = new String[hostagesX.size() * 3];
        for (int i = 0; i < hostagesX.size(); i++) {
            hostages[i * 3] = "" + hostagesX.get(i);
            hostages[i * 3 + 1] = "" + hostagesY.get(i);
            hostages[i * 3 + 2] = "" + hostagesDamage.get(i);
        }

        String[] carried = new String[carriedDamage.size()];
        for (int i = 0; i < carriedDamage.size(); i++) {
            carried[i] = "" + carriedDamage.get(i);
        }
        String[] mutated = new String[mutatedX.size() * 2];
        for (int i = 0; i < mutatedX.size(); i++) {
            mutated[i * 2] = "" + mutatedX.get(i);
            mutated[i * 2 + 1] = "" + mutatedY.get(i);
        }


        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d,%d,%d;", neoX, neoY, neoDamage));
        sb.append(String.format("%d;", countKilled));
        sb.append(String.join(",", agents)).append(";");
        sb.append(String.join(",", pills)).append(";");
        sb.append(String.format("%d;", countDead));
        sb.append(String.join(",", hostages)).append(";");
        sb.append(String.join(",", carried)).append(";");
        sb.append(String.join(",", mutated));
        return sb.toString();
    }

    public State(String stateString) {
        String[] entries = stateString.split(";", -1);

        String[] neo = entries[0].split(",");
        this.neoX = Integer.parseInt(neo[0]);
        this.neoY = Integer.parseInt(neo[1]);
        this.neoDamage = Integer.parseInt((neo[2]));

        this.countKilled = Integer.parseInt(entries[1]);

        String[] agents = entries[2].split((","));
        int numAgents = agents.length / 2;
        this.agentsX = new ArrayList<>(numAgents);
        this.agentsY = new ArrayList<>(numAgents);
        for (int i = 0; i < numAgents; i++) {
            agentsX.add(i, Integer.parseInt(agents[i * 2]));
            agentsY.add(i, Integer.parseInt(agents[i * 2 + 1]));
        }

        String[] pills = entries[3].split((","));
        int numPills = pills.length / 2;
        this.pillsX = new ArrayList<>(numPills);
        this.pillsY = new ArrayList<>(numPills);
        for (int i = 0; i < numPills; i++) {
            pillsX.add(i, Integer.parseInt(pills[i * 2]));
            pillsY.add(i, Integer.parseInt(pills[i * 2 + 1]));
        }

        this.countDead = Integer.parseInt(entries[4]);

        String[] hostages = entries[5].split((","));
        int numHostages = hostages.length / 3;
        this.hostagesX = new ArrayList<>(numHostages);
        this.hostagesY = new ArrayList<>(numHostages);
        this.hostagesDamage = new ArrayList<>(numHostages);
        for (int i = 0; i < numHostages; i++) {
            hostagesX.add(i, Integer.parseInt(hostages[i * 3]));
            hostagesY.add(i, Integer.parseInt(hostages[i * 3 + 1]));
            hostagesDamage.add(i, Integer.parseInt(hostages[i * 3 + 2]));
        }

        String[] carried = entries[6].split((","));
        int numCarried = carried[0].length() == 0 ? 0 : carried.length;
        this.carriedDamage = new ArrayList<>(numCarried);
        for (int i = 0; i < numCarried; i++) {
            carriedDamage.add(i, Integer.parseInt(carried[i]));
        }

        String[] mutated = entries[7].split((","));
        int numMutated = mutated.length / 2;
        this.mutatedX = new ArrayList<>(numMutated);
        this.mutatedY = new ArrayList<>(numMutated);
        for (int i = 0; i < numMutated; i++) {
            mutatedX.add(i, Integer.parseInt(mutated[i * 2]));
            mutatedY.add(i, Integer.parseInt(mutated[i * 2 + 1]));
        }
    }

    @SuppressWarnings("all")
    public boolean doesAgentExist(int x, int y) {
        for (int i = 0; i < agentsX.size(); i++) {
            if (agentsX.get(i) == x && agentsY.get(i) == y) {
                return true;
            }
        }
        return false;
    }

    public void increaseHostagesDamage() {
        int i = 0;
        while (i < hostagesX.size()) {
            int damage = hostagesDamage.get(i);
            if (damage >= 98) {
                mutatedX.add(hostagesX.remove(i));
                mutatedY.add(hostagesY.remove(i));
                hostagesDamage.remove(i);
                //TODO: increment countDead here or when the mutated hostage gets killed
            } else {
                hostagesDamage.set(i, damage + 2);
                i++;
            }
        }
        i = 0;
        while (i < carriedDamage.size()) {
            int damage = carriedDamage.get(i);
            if (damage == 100) {
                i++;
                continue;
            } else if (damage >= 98) {
                int updatedDamage = 100;
                carriedDamage.set(i, updatedDamage);
                countDead++;
            } else {
                int updatedDamage = damage + 2;
                carriedDamage.set(i, updatedDamage);
            }
            i++;
        }
    }

    public StateResult up() {
        if (neoX != 0 && !doesAgentExist(neoX - 1, neoY)) {
            neoX--;
            increaseHostagesDamage();
            return new StateResult.NewState(this.decodeState());
        }
        return new StateResult.None();
    }

    public StateResult down() {
        if (neoX != MatrixConfig.M - 1 && !doesAgentExist(neoX + 1, neoY)) {
            neoX++;
            increaseHostagesDamage();
            return new StateResult.NewState(this.decodeState());
        }
        return new StateResult.None();
    }

    public StateResult right() {
        if (neoY != MatrixConfig.N - 1 && !doesAgentExist(neoX, neoY + 1)) {
            neoY++;
            increaseHostagesDamage();
            return new StateResult.NewState(this.decodeState());
        }
        return new StateResult.None();
    }

    public StateResult left() {
        if (neoY != 0 && !doesAgentExist(neoX, neoY - 1)) {
            neoY--;
            increaseHostagesDamage();
            return new StateResult.NewState(this.decodeState());
        }
        return new StateResult.None();
    }

    public StateResult carry() {
        if (carriedDamage.size() == MatrixConfig.carryCapacity)
            return new StateResult.None();

        int i = 0;
        while (i < hostagesX.size()) {
            if (hostagesX.get(i) == neoX && hostagesY.get(i) == neoY) {
                hostagesX.remove(i);
                hostagesY.remove(i);
                carriedDamage.add(hostagesDamage.remove(i));
                increaseHostagesDamage();
                return new StateResult.NewState(this.decodeState());
            }
            i++;
        }
        return new StateResult.None();
    }

    public StateResult drop() {
        if (neoX == MatrixConfig.telephoneX && neoY == MatrixConfig.telephoneY
                && carriedDamage.size() > 0) {
            carriedDamage = new ArrayList<>();
            return new StateResult.NewState(this.decodeState());
        }
        return new StateResult.None();
    }

    public StateResult kill() {
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};
        boolean isFound = false;
        for (int move = 0; move < dx.length; move++) {
            int x = neoX + dx[move];
            int y = neoY + dy[move];
            int i = 0;
            while (i < agentsX.size()) {
                if (agentsX.get(i) == x & agentsY.get(i) == y) {
                    agentsX.remove(i);
                    agentsY.remove(i);
                    countKilled++;
                    isFound = true;
                } else {
                    i++;
                }
            }
            while (i < mutatedX.size()) {
                if (mutatedX.get(i) == x & mutatedY.get(i) == y) {
                    mutatedX.remove(i);
                    mutatedY.remove(i);
                    countKilled++;
                    countDead++;
                    isFound = true;
                } else {
                    i++;
                }
            }
        }
        if (isFound) {
            neoDamage = Math.min(100, neoDamage + 20);
            if (neoDamage == 100) {
                return new StateResult.None();
            }
        }
        return new StateResult.NewState(this.decodeState());
    }

    public StateResult takePill() {
        for (int i = 0; i < pillsX.size(); i++) {
            if (pillsX.get(i) == neoX && pillsY.get(i) == neoY) {
                neoDamage = Math.max(0, neoDamage - 20);
                for (int j = 0; j < hostagesDamage.size(); j++) {
                    //All hostages remaining in hostagesX, hostagesY, hostagesDamage are still alive
                    int oldDamage = hostagesDamage.get(j);
                    hostagesDamage.set(j, oldDamage - 20);
                }
                return new StateResult.NewState(this.decodeState());
            }
        }
        return new StateResult.None();
    }

    public StateResult fly() {
        for (int i = 0; i < MatrixConfig.startPadsX.length; i++) {
            if (MatrixConfig.startPadsX[i] == neoX && MatrixConfig.startPadsY[i] == neoY) {
                neoX = MatrixConfig.finishPadsX[i];
                neoY = MatrixConfig.finishPadsY[i];
                increaseHostagesDamage();
                return new StateResult.NewState(this.decodeState());
            }
        }
        return new StateResult.None();
    }


}
