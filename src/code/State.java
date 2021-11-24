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
    public boolean isCellDangerous(int x, int y) {
        return doesAgentExist(x, y) || willMutatedExist(x, y);
    }
    public boolean doesAgentExist(int x, int y) {
        for (int i = 0; i < agentsX.size(); i++) {
            if (agentsX.get(i) == x && agentsY.get(i) == y) {
                return true;
            }
        }
        return false;
    }
    public boolean willMutatedExist(int x, int y) {
        for (int i = 0; i < hostagesX.size(); i++) {
            if (hostagesX.get(i) == x && hostagesY.get(i) == y && hostagesDamage.get(i)>=98) {
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
    public String tosString(){
        if (!MatrixConfig.visualize)
            return "";
        StringBuilder builder = new StringBuilder();
        int neoX = this.neoX;
        int neoY = this.neoY;
        ArrayList<Integer> pillsX = this.pillsX;
        ArrayList<Integer> pillsY = this.pillsY;
        ArrayList<Integer> agentsX = this.agentsX;
        ArrayList<Integer> agentsY = this.agentsY;
        ArrayList<Integer> hostagesX = this.hostagesX;
        ArrayList<Integer> hostagesY = this.hostagesY;
        ArrayList<Integer> hostagesDamage = this.hostagesDamage;
        ArrayList<Integer> carriedDamage = this.carriedDamage;
        ArrayList<Integer> mutatedX = this.mutatedX;
        ArrayList<Integer> mutatedY = this.mutatedY;


        // Preparing to print the state of the grid
        String[][] vis = new String[MatrixConfig.M][MatrixConfig.N];
        for (int i = 0; i < MatrixConfig.M; i++) {
            for (int j = 0; j < MatrixConfig.N; j++) {
                vis[i][j] = "";
            }
        }
        vis[neoX][neoY] += "N";
        vis[MatrixConfig.telephoneX][MatrixConfig.telephoneY] += "T";
        for (int i = 0; i < pillsX.size(); i++) {
            vis[pillsX.get(i)][pillsY.get(i)] += "P";
        }
        for (int i = 0; i < agentsX.size(); i++) {
            vis[agentsX.get(i)][agentsY.get(i)] += "A";
        }
        int cntrPds = 0;
        for (int i = 0; i < MatrixConfig.startPadsX.length; i++) {
            vis[MatrixConfig.startPadsX[i]][MatrixConfig.startPadsY[i]] += "F" + cntrPds;
            vis[MatrixConfig.finishPadsX[i]][MatrixConfig.finishPadsY[i]] += "T" + (cntrPds);
            cntrPds++;
        }
        for (int i = 0; i < hostagesX.size(); i++) {
            vis[hostagesX.get(i)][hostagesY.get(i)] += String.format("H(%d)", hostagesDamage.get(i));
        }
        for (int i = 0; i < carriedDamage.size(); i++) {
            vis[neoX][neoY] += String.format("C(%d)", carriedDamage.get(i));
        }
        for (int i = 0; i < mutatedY.size(); i++) {
            vis[mutatedX.get(i)][mutatedY.get(i)] += "M";
        }

        // Printing the state of the grid
        int cellMaxSize = 8;
        String strf = "%-" + cellMaxSize + "s | ";
        String line = "";
        for (int i = 0; i < (MatrixConfig.N + 1) * (cellMaxSize + 3) - 1; i++) line += "-";
        for (int j = -1; j < MatrixConfig.N; j++) {
            builder.append(String.format(strf, j));
        }
        builder.append("\n");
        builder.append(line+"\n");
        for (int i = 0; i < MatrixConfig.M; i++) {
            builder.append(String.format(strf, i));
            for (int j = 0; j < MatrixConfig.N; j++) {
                builder.append(String.format(strf, vis[i][j]));
            }
            builder.append("\n");
            builder.append(line+"\n");
        }
        builder.append(String.format("#Dead=%d\t#Killed=%d\tneo Damage=%d\n", this.countDead, this.countKilled, this.neoDamage));
        return builder.toString();
    }
}
