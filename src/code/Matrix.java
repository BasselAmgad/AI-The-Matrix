package code;

import java.lang.management.ManagementFactory;
import java.util.Arrays;

public class Matrix extends SearchProblem {

    int[][][][] sh_dist;
    void calculate_shortest_distance(){
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
    }

    int est_MaxDepth_sol(State state){
        int md = 100;
        int dist = -1;
        for (int i=0; i<state.hostagesDamage.size(); i++){
            int d = state.hostagesDamage.get(i);
            int x = state.hostagesX.get(i);
            int y = state.hostagesY.get(i);
            int saveDist = sh_dist[state.neoX][state.neoY][x][y] + sh_dist[x][y][MatrixConfig.telephoneX][MatrixConfig.telephoneY];
            dist = Math.max(dist, saveDist);
            md = Math.min(md, d);
        }

        int ans = (int)Math.ceil(1.0*(100-md)/2) + state.pillsX.size()*10 + dist;
        return ans;
    }


    public Matrix(String problem) {
        this.initialState = ProblemParser.parseProblem(problem);
        this.operators = Action.values();
        int maxOpValue = -1;
        for (Operator o : operators) {
            maxOpValue = Math.max(maxOpValue, o.getConstCost());
        }
        calculate_shortest_distance();
        MatrixConfig.kills_weight = maxOpValue * est_MaxDepth_sol(new State(initialState));
        MatrixConfig.death_weight = MatrixConfig.kills_weight * ((MatrixConfig.M * MatrixConfig.N) + 1);
    }

    @Override
    public boolean goalTestFUnction(State state) {
        return state.neoX == MatrixConfig.telephoneX
                && state.neoY == MatrixConfig.telephoneY
                && state.hostagesDamage.size() == 0
                && state.carriedDamage.size() == 0
                && state.mutatedX.size() == 0;
    }

    @Override
    public String problemOutput(MNode goal) {
        State state = new State(goal.state);
        String[] ac = constructPlan(goal);
        String plan = String.join(",", ac);
        return plan + ";" + state.countDead + ";" + state.countKilled + ";" + this.expandedNodesCnt;
    }

    @Override
    public int heuristic_1(MNode node) {
        State state = new State(node.state);
        int h = 0;
        int mutants_to_kill = state.mutatedX.size();
        int carried = state.carriedDamage.size();
        int alive_hostages = state.hostagesX.size();
        int h1 = sh_dist[state.neoX][state.neoY][MatrixConfig.telephoneX][MatrixConfig.telephoneY] * Action.UP.getConstCost();
        int h2 = carried>0?  Action.DROP.getConstCost() : 0;
        int h3 = mutants_to_kill * MatrixConfig.kills_weight;
        int h4 = alive_hostages * Action.CARRY.getConstCost();
        h = h1 + h2 + h3 + h4;
        return h;
    }

    @Override
    public int heuristic_2(MNode node) {
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
        if (state.hostagesX.isEmpty()){
            expected_moves = sh_dist[state.neoX][state.neoY][MatrixConfig.telephoneX][MatrixConfig.telephoneY];
        }
        int h1 = expected_moves * Action.UP.getConstCost();
        int h2 = state.carriedDamage.size()>0?Action.DROP.getConstCost():0;
        int h3 = mutants_to_kill*MatrixConfig.kills_weight;
        h =  h1 + h2 + h3;

        return h;
    }

    public static String genGrid() {
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

        int nrpads = 1 + (int) (Math.random() * ((n_free_cells - 1) / 2));

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
