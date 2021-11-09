package code;

import java.util.PriorityQueue;

public class Matrix extends SearchProblem {

//    String Search(State initialState) {
//        PriorityQueue<Node> queue = new PriorityQueue<>();
//        Node root = new BST_Node(initialState, null, null, 0, 0);
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            Node node = queue.remove();
//            if (goalTest(node.state)) {
//                visualize(node.state);
//                return "Found a solution here";
//            } else {//Expansion
//                expand(queue, node);
//            }
//        }
//        return "";
//    }

    void expand(PriorityQueue<Node> queue, Node node) {

    }

    static String genGrid() {
        StringBuilder sb = new StringBuilder();
        int M = 5 + (int) (Math.random() * 11);
        int N = 5 + (int) (Math.random() * 11);
//        int M = 5;
//        int N = 5;
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

    static String solve(String grid, String strategy, boolean visualize) {
        String initialState = ProblemParser.parseProblem(grid);
        // TODO: Create root node and (enqueue) it
        Node root = new Node(initialState, null, null, 0, 0);
        if (visualize) {
            System.out.println("Initial State Visualizing");
            visualize(root.state);
        }
        // TODO: Start expanding the nodes with actions
        return "";
    }

    // TODO: Visualise works with  String state;
    // did it temporarily like that; need to discuss where visualize is called so that unnecessary conversions are done
    static void visualize(String currentState){
        visualize(new State(currentState));
    }
    static void visualize(State currentState) {
        int neoX = currentState.neoX;
        int neoY = currentState.neoY;
        int[] pillsX = currentState.pillsX;
        int[] pillsY = currentState.pillsY;
        int[] agentsX = currentState.agentsX;
        int[] agentsY = currentState.agentsY;
        int[] hostagesX = currentState.hostagesX;
        int[] hostagesY = currentState.hostagesY;
        int[] hostagesDamage = currentState.hostagesDamage;

        // Preparing to print the state of the grid
        String[][] vis = new String[MatrixConfig.M][MatrixConfig.N];
        for (int i = 0; i < MatrixConfig.M; i++) {
            for (int j = 0; j < MatrixConfig.N; j++) {
                vis[i][j] = "";
            }
        }
        vis[neoX][neoY] += "N";
        vis[MatrixConfig.telephoneX][MatrixConfig.telephoneY] += "T";
        for (int i = 0; i < pillsX.length; i++) {
            vis[pillsX[i]][pillsY[i]] += "L";
        }
        for (int i = 0; i < agentsX.length; i++) {
            vis[agentsX[i]][agentsY[i]] += "A";
        }
        int cntrPds = 0;
        for (int i = 0; i < MatrixConfig.startPadsX.length; i++) {
            vis[MatrixConfig.startPadsX[i]][MatrixConfig.startPadsY[i]] += "F" + cntrPds;
            vis[MatrixConfig.finishPadsX[i]][MatrixConfig.finishPadsY[i]] += "T" + (cntrPds);
            cntrPds++;
        }
        for (int i = 0; i < hostagesX.length; i++) {
            vis[hostagesX[i]][hostagesY[i]] += String.format("H(%d)", hostagesDamage[i]);
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
    }

    // the goal test should take the variables directly and return false or true, no need for the string state?
    static boolean goalTest(String state) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Hello world");
//      String p1 = "14,5;2;7,0;2,3;8,3,8,1,9,2,2,0;8,3,9,1,0,4,6,1;2,0,10,1,10,1,2,0;6,2,76,7,2,78,0,0,55,11,2,11,4,4,90,2,4,56,0,2,21,13,2,63,12,3,85,1,2,26";
        for (int i = 0; i < 1; i++) {
            String problem = genGrid();
            System.out.println("Gengrid Output:\n"+problem);
            solve(problem, "", true);
        }
    }
}

/*
neo 1
tb  1
                    f(n,m) = N*M - 2

hostages    3:10
pills       1:hostages
agents      1:inf                   INF -> free

 */


/*
------------------------------------------------------------------STATE STRING------------------------------------------
NeoX,NeoY,NeoDamage;
countOfKilledAgents;
AgentX1,AgentY1, ...,AgentXk,AgentYk;
PillX1,PillY1, ...,PillXg,PillYg;
countOfDeadHostages;
HostageX1,HostageY1,HostageDamage1, ...,HostageXw,HostageYw,HostageDamagew;
carriedX1,carriedY1,carriedDamage1, ...,carriedXu,carriedYu,carriedDamageu;
mutatedX1,mutatedY1, ...,mutatedXv,mutatedYv
 */