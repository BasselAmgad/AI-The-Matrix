package code;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Comparator;

public class Matrix extends SearchProblem {

    int[][][][] sh_dist;
    void calculate_shortest_distance(){
        long t0 = System.nanoTime();
        int m = MatrixConfig.M;
        int n = MatrixConfig.N;
        sh_dist = new int[m][n][m][n];
        for (int i=0;i<m;i++)
            for(int j=0;j<n;j++)
                for (int k=0;k<m;k++)
                    Arrays.fill(sh_dist[i][j][k], Integer.MAX_VALUE);
        for (int x1=0; x1<m;x1++){
            for (int y1=0; y1<n; y1++){
                for (int x2=0; x2<m; x2++){
                    for (int y2=0; y2<n; y2++){
                        int d1 = Math.abs(x2-x1) + Math.abs(y2-y1);
                        for (int i=0; i<MatrixConfig.startPadsX.length; i++){
                            int p1x = MatrixConfig.startPadsX[i];
                            int p1y = MatrixConfig.startPadsY[i];
                            int p2x = MatrixConfig.finishPadsX[i];
                            int p2y = MatrixConfig.finishPadsY[i];
                            int d2_1 = Math.abs(p1x-x1)+Math.abs(p1y-y1);
                            int d2_2 = Math.abs(p2x-x2)+Math.abs(p2y-y2);
                            int d2 = d2_1 + d2_2 + 1;
                                int cur_min = Math.min(d1, d2);
                            sh_dist[x1][y1][x2][y2] = Math.min(sh_dist[x1][y1][x2][y2], cur_min);
                        }
                    }
                }
            }
        }
//        int sx=2, sy= 0;
//        for (int x2=0; x2<m; x2++){
//            for (int y2=0; y2<n; y2++) {
//                System.out.printf("Shortest Distance from (%d, %d) to (%d, %d) = %d\t",sx,sy,x2,y2,sh_dist[sx][sy][x2][y2]);
//                System.out.printf("Shortest Distance from (%d, %d) to (%d, %d) = %d\n",sx,sy,x2,y2,sh_dist[x2][y2][sx][sy]);
//            }
//        }
        long t1 = System.nanoTime();
//        System.out.printf("%.6f\n", (t1-t0)/1e9);
    }

    int est_MaxDepth_sol(State state){
        int md = 100;
        int dist = -1;
        for (int i=0; i<state.hostagesDamage.size(); i++){
            int d = state.hostagesDamage.get(i);
            int x = state.hostagesX.get(i);
            int y = state.hostagesY.get(i);
//            int killDist = sh_dist[state.neoX][state.neoY][x][y];
            int saveDist = sh_dist[state.neoX][state.neoY][x][y] + sh_dist[x][y][MatrixConfig.telephoneX][MatrixConfig.telephoneY];
            dist = Math.max(dist, saveDist);
            md = Math.min(md, d);
        }
//        for (int i=0; i<state.carriedDamage.size(); i++){
//            int d = state.carriedDamage.get(i);
//            md = Math.min(md, d);
//        }

        int ans = (int)Math.ceil(1.0*(100-md)/2) + state.pillsX.size()*10 + dist;
//        System.out.println("Est max depth= "+ans);
        return ans;
    }

    public Matrix(String problem) {
        this.initialState = ProblemParser.parseProblem(problem);
        this.operators = Action.values();
        int maxOpValue = -1;
        for (Operator o : operators){
            maxOpValue = Math.max(maxOpValue, o.getConstCost());
        }
        calculate_shortest_distance();
        MatrixConfig.kills_weight = maxOpValue * est_MaxDepth_sol(new State(initialState)); ;//MatrixConfig.M + MatrixConfig.N;
        MatrixConfig.death_weight = MatrixConfig.kills_weight * ((MatrixConfig.M * MatrixConfig.N) +1);
//        System.out.println(Arrays.toString(operators));
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

//    @Override
//    public int pathCostFUnction(String stateString) {
//        State state = new State(stateString);
//        int distance_to_tb = Math.abs(state.neoX - MatrixConfig.telephoneX) + Math.abs(state.neoY - MatrixConfig.telephoneY);
//        return state.countDead * death_weight + state.countKilled * kills_weight + distance_to_tb;
//    }

    @Override
    public String problemOutput(Node goal) {
        State state = new State(goal.state);
        String[] ac = constructPlan(goal);
//        System.out.println(ac.length);
        String plan = String.join(",", ac);
        return plan + ";" + state.countDead + ";" + state.countKilled + ";" + this.expandedNodesCnt;
    }

    @Override
    public int heuristic_1(Node node) {
        State state = new State(node.state);
        int h = 0;
        int mutants_to_kill = state.mutatedX.size();
        int resotredByPills = 20 * state.pillsX.size();
        int hostages_to_die = 0;
        for (int i=0 ; i<state.hostagesX.size(); i++){
            int hx = state.hostagesX.get(i);
            int hy = state.hostagesY.get(i);
            int hd = state.hostagesDamage.get(i);
            int distanceToSave = sh_dist[state.neoX][state.neoY][hx][hy] + 1 + sh_dist[hx][hy][MatrixConfig.telephoneX][MatrixConfig.telephoneY] + 1;
            int futureDamage = distanceToSave*2;
            if (hd + futureDamage - resotredByPills > 100){
                hostages_to_die ++;
            }
        }
        int distanceToSave = sh_dist[state.neoX][state.neoY][MatrixConfig.telephoneX][MatrixConfig.telephoneY];
        int futureDamage = distanceToSave*2;
        for (int i=0 ; i<state.carriedDamage.size(); i++){
            int hd = state.carriedDamage.get(i);
            if (hd + futureDamage - resotredByPills > 100){
                hostages_to_die ++;
            }
        }
        int distance_to_tb = sh_dist[state.neoX][state.neoY][MatrixConfig.telephoneX][MatrixConfig.telephoneY];
        int dropCost = state.carriedDamage.size()>0?Action.DROP.getConstCost():0;
        h = MatrixConfig.death_weight * hostages_to_die + MatrixConfig.kills_weight * mutants_to_kill + distance_to_tb * Action.UP.getConstCost() + dropCost;
        return h;
    }

    @Override
    public int heuristic_2(Node node) {
//        long t0 = System.nanoTime();
        State state = new State(node.state);
        int h = 0;
        int mutants_to_kill = state.mutatedX.size();
        int expected_moves = 0;
        for (int i=0 ; i<state.hostagesX.size(); i++){
            int hx = state.hostagesX.get(i);
            int hy = state.hostagesY.get(i);
            int d = sh_dist[state.neoX][state.neoY][hx][hy] + sh_dist[hx][hy][MatrixConfig.telephoneX][MatrixConfig.telephoneY];
            expected_moves = Math.max(expected_moves, d);
        }
//        expected_moves = Math.max(expected_moves, sh_dist[state.neoX][state.neoY][MatrixConfig.telephoneX][MatrixConfig.telephoneY]);
        int dropCost = state.carriedDamage.size()>0?Action.DROP.getConstCost():0;
        h = expected_moves * Action.UP.getConstCost() + dropCost + mutants_to_kill*MatrixConfig.kills_weight;
//        long t1 = System.nanoTime();
//        System.out.printf("h1: %.8f\n", (t1-t0)/1e9);
        return h;
    }


//    @Override
//    public int heuristic_3(Node node) {
//        State state = new State(node.state);
//        int totEst = 0;
//        for (int i = 0; i < state.hostagesDamage.size(); i++) {
//            int hx = state.hostagesX.get(i);
//            int hy = state.hostagesY.get(i);
//             int hd = state.hostagesDamage.get(i);
//            int tx = MatrixConfig.telephoneX;
//            int ty = MatrixConfig.telephoneY;
//            int distance = (int) (Math.abs(hx - tx) + Math.abs(hy - ty));
//            totEst += distance* hd;
//        }
//        return totEst;
//    }
//
//    @Override
//    public int heuristic_4(Node node) {
//        State state = new State(node.state);
//        int sum = 0;
//        for (int i = 0; i < state.hostagesDamage.size(); i++) {
//            sum += state.hostagesDamage.get(i);
//        }
//        for (int i = 0; i < state.carriedDamage.size(); i++) {
//            if (state.carriedDamage.get(i)<100)
//                sum += state.carriedDamage.get(i);
//        }
//        return sum;
//    }
//
//    @Override
//    public int heuristic_5(Node node) {
//        State state = new State(node.state);
//        int totEst = 0;
//        for (int i = 0; i < state.hostagesDamage.size(); i++) {
//            int hx = state.hostagesX.get(i);
//            int hy = state.hostagesY.get(i);
//            // int hd = state.hostagesDamage.get(i);
//            int tx = MatrixConfig.telephoneX;
//            int ty = MatrixConfig.telephoneY;
//            int distance = (int) (Math.abs(hx - tx) + Math.abs(hy - ty));
//            totEst += distance;//* hd;
//        }
//        return totEst;
//    }

    static String genGrid() {
        StringBuilder sb = new StringBuilder();
        int M = 5 + (int) (Math.random() * 11);
        int N = 5 + (int) (Math.random() * 11);
        boolean[][] taken = new boolean[M][N];
        int carryCapacity = 1 + (int) (Math.random() * 8);
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

        int n_hostages = 3 + (int) (Math.random() * 4);
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
        String output = SearchStrategy.parse(strategy).search(matrix);
        System.out.println(output);
        return output;
    }

    public static void main(String[] args) {
        com.sun.management.OperatingSystemMXBean oss = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        String grid0 = "5,5;2;3,4;1,2;0,3,1,4;2,3;4,4,0,2,0,2,4,4;2,2,91,2,4,62";
        String grid1 = "5,5;1;1,4;1,0;0,4;0,0,2,2;3,4,4,2,4,2,3,4;0,2,32,0,1,38";
        String grid2 = "5,5;2;3,2;0,1;4,1;0,3;1,2,4,2,4,2,1,2,0,4,3,0,3,0,0,4;1,1,77,3,4,34";
        String grid3 = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,1";
        String grid4 = "5,5;1;0,4;4,4;0,3,1,4,2,1,3,0,4,1;4,0;2,4,3,4,3,4,2,4;0,2,98,1,2,98,2,2,98,3,2,98,4,2,98,2,0,98,1,0,98";
        String grid5 = "5,5;2;0,4;3,4;3,1,1,1;2,3;3,0,0,1,0,1,3,0;4,2,54,4,0,85,1,0,43";
        String grid6 = "5,5;2;3,0;4,3;2,1,2,2,3,1,0,0,1,1,4,2,3,3,1,3,0,1;2,4,3,2,3,4,0,4;4,4,4,0,4,0,4,4;1,4,57,2,0,46";
        String grid7 = "5,5;3;1,3;4,0;0,1,3,2,4,3,2,4,0,4;3,4,3,0,4,2;1,4,1,2,1,2,1,4,0,3,1,0,1,0,0,3;4,4,45,3,3,12,0,2,88";
        String grid8 = "5,5;2;4,3;2,1;2,0,0,4,0,3,0,1;3,1,3,2;4,4,3,3,3,3,4,4;4,0,17,1,2,54,0,0,46,4,1,22";
        String grid9 = "5,5;2;0,4;1,4;0,1,1,1,2,1,3,1,3,3,3,4;1,0,2,4;0,3,4,3,4,3,0,3;0,0,30,3,0,80,4,4,80";
        String grid10 = "5,5;4;1,1;4,1;2,4,0,4,3,2,3,0,4,2,0,1,1,3,2,1;4,0,4,4,1,0;2,0,0,2,0,2,2,0;0,0,62,4,3,45,3,3,39,2,3,40";
//        String[] cases =new String[]{grid0, grid1, grid2, grid3, grid4, grid5}
//        String[] cases = new String[]{grid6, grid7, grid8};
        String[] cases = new String[]{grid9, grid10};
        String s;
        long t0, t1;
//        for (String cas : cases) {
        for (int i=0;i<5;i++){
            String cas = genGrid();
            System.out.println(cas);
            t0 = System.nanoTime();
            s = "BF";
            System.out.println(new State(ProblemParser.parseProblem(cas)).toString());
            solve(cas, s, false);
            t1 = System.nanoTime();
            double cpuLoad = oss.getProcessCpuLoad() * 100;
            long total = (oss.getTotalPhysicalMemorySize());
            long free = oss.getFreePhysicalMemorySize();
            System.out.println("Time taken: " + (t1 - t0) / 1e9);
            System.out.println("CPU Utilization: " + String.format("%.02f", cpuLoad) + "%");
            System.out.println("Memory Utilization: " + String.format("%.02f", (float) (total - free) / total * 100) + "%");
        }
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
