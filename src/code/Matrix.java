package code;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.lang.management.ManagementFactory;

public class Matrix extends SearchProblem {


    public Matrix(String problem){
        this.initialState = ProblemParser.parseProblem(problem);
        this.operators = Action.values();
        System.out.println(Arrays.toString(operators));
    }
    @Override
    public boolean goalTestFUnction(State state) {
        return state.neoX == MatrixConfig.telephoneX && state.neoY==MatrixConfig.telephoneY &&
                state.hostagesDamage.size() == 0 && state.carriedDamage.size() == 0 && state.mutatedX.size() == 0;
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
        return 1000*(state.countDead*250 + state.countKilled);
    }

    @Override
    public String problemOutput(Node goal) {
        State state = new State(goal.state);
        String plan = String.join(",", constructPlan(goal));
        return plan+";"+state.countDead +";" + state.countKilled +";" + this.expandedNodesCnt;
    }
    public int heuristic_1(Node node){
        State state = new State(node.state);
        int sum = 0;
        for (int i=0;i <state.hostagesDamage.size(); i++){
            sum += state.hostagesDamage.get(i);
        }
        return sum;
    }
    public int heuristic_2(Node node){
        State state = new State(node.state);
        int totEst = 0;
        for (int i=0;i <state.hostagesDamage.size(); i++){
            int hx = state.hostagesX.get(i);
            int hy = state.hostagesY.get(i);
            int hd = state.hostagesDamage.get(i);
            int tx = MatrixConfig.telephoneX;
            int ty = MatrixConfig.telephoneY;
            int distance = (int)(Math.abs(hx-tx)+Math.abs(hy-ty));
            totEst += hd * distance;
        }
        return  totEst;
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
        ;
        return sb.toString();
    }

    public static String solve(String grid, String strategy, boolean visualize) {
        Matrix matrix = new Matrix(grid);
        MatrixConfig.visualize = visualize;
        Comparator<Node> chosenStrategy;
        String output = SearchStrategy.parse(strategy).search(matrix);
        System.out.println(output);
        return output;

//        return matrix.genericSearchProcedure(chosenStrategy);
    }

    static void visualize(String currentState) {
        visualize(new State(currentState));
    }
    static void visualize(State currentState) {
        if (!MatrixConfig.visualize)
            return;
        int neoX = currentState.neoX;
        int neoY = currentState.neoY;
        ArrayList<Integer> pillsX = currentState.pillsX;
        ArrayList<Integer> pillsY = currentState.pillsY;
        ArrayList<Integer> agentsX = currentState.agentsX;
        ArrayList<Integer> agentsY = currentState.agentsY;
        ArrayList<Integer> hostagesX = currentState.hostagesX;
        ArrayList<Integer> hostagesY = currentState.hostagesY;
        ArrayList<Integer> hostagesDamage = currentState.hostagesDamage;
        ArrayList<Integer> carriedDamage = currentState.carriedDamage;
        ArrayList<Integer> mutatedX = currentState.mutatedX;
        ArrayList<Integer> mutatedY = currentState.mutatedY;


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
            System.out.printf(strf, j);
        }
        System.out.println();
        System.out.println(line);
        for (int i = 0; i < MatrixConfig.M; i++) {
            System.out.printf(strf, i);
            for (int j = 0; j < MatrixConfig.N; j++) {
                System.out.printf(strf, vis[i][j]);
            }
            System.out.println();
            System.out.println(line);
        }
        System.out.printf("#Dead=%d\t#Killed=%d\tneo Damage=%d\n", currentState.countDead, currentState.countKilled, currentState.neoDamage);
    }


    public static void main(String[] args) {
        System.out.println("Hello world");
        com.sun.management.OperatingSystemMXBean oss= (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        String problem = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
        System.out.println("Hello "+oss.getProcessCpuLoad());
        String s;
        long t0;

        t0 = System.nanoTime();
        s = "ID";
        System.out.println(s);;
        System.out.println(solve(problem, s, false));
        System.out.println("Time taken: "+ (System.nanoTime()-t0)/1e9);
        System.out.println("CPU Utilization: "+String.format("%.02f", oss.getProcessCpuLoad()*100)+"%");
        long total=(oss.getTotalPhysicalMemorySize());
        long free=oss.getFreePhysicalMemorySize();
        System.out.println("Memory Utilization: "+String.format("%.02f",(float)(total-free)/total*100)+"%");
    }


}

/*



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