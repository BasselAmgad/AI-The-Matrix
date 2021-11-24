package code;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Comparator;

public class Matrix extends SearchProblem {

    public Matrix(String problem) {
        this.initialState = ProblemParser.parseProblem(problem);
        this.operators = Action.values();
        System.out.println(Arrays.toString(operators));
    }

    @Override
    public boolean goalTestFUnction(State state) {
        return state.neoX == MatrixConfig.telephoneX
                && state.neoY == MatrixConfig.telephoneY
                && state.hostagesDamage.size() == 0
                && state.carriedDamage.size() == 0
                && state.mutatedX.size() == 0;
    }

//    @Override
//    public int pathCostFUnction(Operator operator) {
//        Action action = (Action) operator;
//        if (action==Action.KILL)
//            return 30;
//        else if (action==Action.CARRY)
//            return 8;
//        else if (action==Action.UP || action==Action.DOWN || action==Action.RIGHT || action==Action.LEFT)
//            return 4;
//        else if (action==Action.FLY)
//            return 3;
//        else if (action==Action.DROP)
//            return 1;
//        else if(action== Action.TAKE_PILL)
//            return 2;
//        return 0;
//    }

    @Override
    public int pathCostFUnction(String stateString) {
        State state = new State(stateString);
        return 1000 * (state.countDead * ((MatrixConfig.M * MatrixConfig.N) +1) + state.countKilled + 1);
    }

    @Override
    public String problemOutput(Node goal) {
        State state = new State(goal.state);
        String plan = String.join(",", constructPlan(goal));
        return plan + ";" + state.countDead + ";" + state.countKilled + ";" + this.expandedNodesCnt;
    }

    public int heuristic_1(Node node) {
        State state = new State(node.state);
        int sum = 0;
        for (int i = 0; i < state.hostagesDamage.size(); i++) {
            sum += state.hostagesDamage.get(i);
        }
        for (int i = 0; i < state.carriedDamage.size(); i++) {
            if (state.carriedDamage.get(i)<100)
                sum += state.carriedDamage.get(i);
        }
        return sum;
    }

    public int heuristic_2(Node node) {
        State state = new State(node.state);
        int totEst = 0;
        for (int i = 0; i < state.hostagesDamage.size(); i++) {
            int hx = state.hostagesX.get(i);
            int hy = state.hostagesY.get(i);
            // int hd = state.hostagesDamage.get(i);
            int tx = MatrixConfig.telephoneX;
            int ty = MatrixConfig.telephoneY;
            int distance = (int) (Math.abs(hx - tx) + Math.abs(hy - ty));
            totEst += distance;//* hd;
        }
        return totEst;
    }

    @Override
    public int heuristic_3(Node node) {
        State state = new State(node.state);
        int totEst = 0;
        for (int i = 0; i < state.hostagesDamage.size(); i++) {
            int hx = state.hostagesX.get(i);
            int hy = state.hostagesY.get(i);
             int hd = state.hostagesDamage.get(i);
            int tx = MatrixConfig.telephoneX;
            int ty = MatrixConfig.telephoneY;
            int distance = (int) (Math.abs(hx - tx) + Math.abs(hy - ty));
            totEst += distance* hd;
        }
        return totEst;
    }
    @Override
    public int heuristic_4(Node node) {
        return 0;
    }
    @Override
    public int heuristic_5(Node node) {
        return 0;
    }

    static String genGrid() {
        StringBuilder sb = new StringBuilder();
        int M = 5 + (int) (Math.random() * 11);
        int N = 5 + (int) (Math.random() * 11);
        boolean[][] taken = new boolean[M][N];
        int carryCapacity = 1 + (int) (Math.random() * 4);
        int n_free_cells = M * N;
        int neoX = (int) (Math.random() * M);
        int neoY = (int) (Math.random() * N);
        n_free_cells--;
        taken[neoX][neoY] = true;
        int telephoneX, telephoneY;
        do {
            telephoneX = (int) (Math.random() * M);
            telephoneY = (int) (Math.random() * N);
        } while (taken[telephoneX][telephoneY]);
        taken[telephoneX][telephoneY] = true;
        n_free_cells--;

        int n_hostages = 3 + (int) (Math.random() * 8);
        String[] hostages = new String[n_hostages * 3];
        for (int i = 0; i < hostages.length; i += 3) {
            int x, y;
            do {
                x = (int) (Math.random() * M);
                y = (int) (Math.random() * N);
            } while (taken[x][y]);
            taken[x][y] = true;
            int damage = 1 + (int) (Math.random() * 100);
            hostages[i] = x + "";
            hostages[i + 1] = y + "";
            hostages[i + 2] = damage + "";
        }
        n_free_cells -= n_hostages;

        int n_pills = 1 + (int) (Math.random() * (n_hostages));
        String[] pills = new String[n_pills << 1];
        for (int i = 0; i < pills.length; i += 2) {
            int x, y;
            do {
                x = (int) (Math.random() * M);
                y = (int) (Math.random() * N);
            } while (taken[x][y]);
            taken[x][y] = true;
            pills[i] = x + "";
            pills[i + 1] = y + "";
        }
        n_free_cells -= n_pills;

        //No bounds for the number of pads are set in the description.
        int nrpads = 1 + (int) (Math.random() * ((n_free_cells - 1) / 2));
        /*
         String[] pads is a multiple of 8
         *2 to contain x and y for each pad
         *2 so as every start pad Px should have a corresponding finish pad Py
         *2 so as for every pair Px, Py we should output the other direction (Py, Px) as well
         */
        String[] pads = new String[nrpads * 1 * 2 * 2 * 2];
        for (int i = 0; i < pads.length; i += 8) {
            int x1, y1, x2, y2;
            do {
                x1 = (int) (Math.random() * M);
                y1 = (int) (Math.random() * N);
                x2 = (int) (Math.random() * M);
                y2 = (int) (Math.random() * N);
            } while (taken[x1][y1] || taken[x2][y2] || (x1 == x2 && y1 == y2));
            taken[x1][y1] = true;
            taken[x2][y2] = true;
            pads[i] = x1 + "";
            pads[i + 1] = y1 + "";
            pads[i + 2] = x2 + "";
            pads[i + 3] = y2 + "";
            pads[i + 4] = x2 + "";
            pads[i + 5] = y2 + "";
            pads[i + 6] = x1 + "";
            pads[i + 7] = y1 + "";
        }
        n_free_cells -= nrpads * 2;

        int n_agents = 1 + (int) (Math.random() * (n_free_cells));
        String[] agents = new String[n_agents << 1];
        for (int i = 0; i < agents.length; i += 2) {
            int x, y;
            do {
                x = (int) (Math.random() * M);
                y = (int) (Math.random() * N);
            } while (taken[x][y]);
            taken[x][y] = true;
            agents[i] = x + "";
            agents[i + 1] = y + "";
        }
        n_free_cells -= n_agents;

        sb.append(String.format("%d,%d;", M, N));
        sb.append(String.format("%d;", carryCapacity));
        sb.append(String.format("%d,%d;", neoX, neoY));
        sb.append(String.format("%d,%d;", telephoneX, telephoneY));
        sb.append(String.join(",", agents)).append(";");
        sb.append(String.join(",", pills)).append(";");
        sb.append(String.join(",", pads)).append(";");
        sb.append(String.join(",", hostages));

        return sb.toString();
    }

    public static String solve(String grid, String strategy, boolean visualize) {
        Matrix matrix = new Matrix(grid);
        MatrixConfig.visualize = visualize;
        Comparator<Node> chosenStrategy;
        String output = SearchStrategy.parse(strategy).search(matrix);
        System.out.println(output);
        return output;
    }

    public static void main(String[] args) {
        com.sun.management.OperatingSystemMXBean oss = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        String grid3 = "6,6;2;2,4;2,2;0,4,1,4,3,0,4,2;0,1,1,3;4,4,3,1,3,1,4,4;0,0,94,1,2,38,4,1,76,4,0,80";
        String grid4 = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,98,1,0,98";
        //        String problem = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
        String problem = genGrid();//grid3;
        System.out.println("Hello " + oss.getProcessCpuLoad());

        String s;
        long t0, t1;

        t0 = System.nanoTime();
        s = "DF";
        solve(problem, s, true);
        t1 = System.nanoTime();
        double cpuLoad = oss.getProcessCpuLoad() * 100;
        long total = (oss.getTotalPhysicalMemorySize());
        long free = oss.getFreePhysicalMemorySize();
        System.out.println("Time taken: " + (t1 - t0) / 1e9);
        System.out.println("CPU Utilization: " + String.format("%.02f", cpuLoad) + "%");
        System.out.println("Memory Utilization: " + String.format("%.02f", (float) (total - free) / total * 100) + "%");
    }
}

/*
-----------------------------------STATE STRING------------------------------------------
NeoX,NeoY,NeoDamage;
countOfKilledAgents;
AgentX1,AgentY1, ...,AgentXk,AgentYk;
PillX1,PillY1, ...,PillXg,PillYg;
countOfDeadHostages;
HostageX1,HostageY1,HostageDamage1, ...,HostageXw,HostageYw,HostageDamagew;
carriedDamage1, ...,carriedDamageu;
mutatedX1,mutatedY1, ...,mutatedXv,mutatedYv
*/
