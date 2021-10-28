package code;

import java.util.PriorityQueue;

public class Matrix extends SearchProblem {

    //grid size
    short M, N;

    short telephoneX, telephoneY;

    int carryCapacity; // <=4

    int[] startPadsX, startPadsY, finishPadsX, finishPadsY;

    int timepnt = 0;

    String Search(State initialState) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node root = new BST_Node(initialState, null, null, 0, 0, timepnt++);
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (goalTest(node.state)) {
                visualize(node.state);
                return "Found a solution here";
            } else {//Expansion
                expand(queue, node);
            }
        }
        return "";
    }

    void expand(PriorityQueue<Node> queue, Node node) {

    }

    static String genGrid() {
        StringBuilder sb = new StringBuilder();
        int M = 5 + (int)(Math.random()*11);
        int N = 5 + (int)(Math.random()*11);
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
            } while(taken[x][y]);
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
        int nrpads = 1 + (int) (Math.random() * ((n_free_cells-1) / 2));
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
        sb.append(String.join(",", agents) + ";");
        sb.append(String.join(",", pills) + ";");
        sb.append(String.join(",", pads) + ";");
        sb.append(String.join(",", hostages));
        return sb.toString();
    }

    State parseProblem(String problem) {
        String[] entries = problem.split(";");

        String[] dims = entries[0].split(",");
        byte M = Byte.parseByte(dims[0]);
        byte N = Byte.parseByte(dims[1]);

        int carryCapacity = Integer.parseInt(entries[1]);

        String[] neo = entries[2].split(",");
        int neoX = Integer.parseInt(neo[0]);
        int neoY = Integer.parseInt(neo[1]);

        String[] tel = entries[3].split(",");
        byte telephoneX = Byte.parseByte(tel[0]);
        byte telephoneY = Byte.parseByte(tel[1]);

        String[] agents = entries[4].split(",");
        int[] agentsX = new int[agents.length >> 1];
        int[] agentsY = new int[agents.length >> 1];
        for (int i = 0; i < agentsX.length; i++) {
            agentsX[i] = Integer.parseInt(agents[i << 1]);
            agentsY[i] = Integer.parseInt(agents[1 + (i << 1)]);
        }

        String[] pills = entries[5].split(",");
        int[] pillsX = new int[pills.length >> 1];
        int[] pillsY = new int[pills.length >> 1];
        for (int i = 0; i < pillsX.length; i++) {
            pillsX[i] = Integer.parseInt(pills[i << 1]);
            pillsY[i] = Integer.parseInt(pills[1 + (i << 1)]);
        }

        String[] pads = entries[6].split(",");
        int[] startPadsX = new int[pads.length / 4];
        int[] startPadsY = new int[pads.length / 4];
        int[] finishPadsX = new int[pads.length / 4];
        int[] finishPadsY = new int[pads.length / 4];
        for (int i = 0; i < startPadsX.length; i++) {
            startPadsX[i] = Integer.parseInt(pads[i * 4]);
            startPadsY[i] = Integer.parseInt(pads[1 + (i * 4)]);
            finishPadsX[i] = Integer.parseInt(pads[2 + (i * 4)]);
            finishPadsY[i] = Integer.parseInt(pads[3 + (i * 4)]);
        }

        String[] hostages = entries[7].split(",");
        int[] hostagesX = new int[hostages.length / 3];
        int[] hostagesY = new int[hostages.length / 3];
        int[] hostagesDamage = new int[hostages.length / 3];
        for (int i = 0; i < hostagesX.length; i++) {
            hostagesX[i] = Integer.parseInt(hostages[(i * 3)]);
            hostagesY[i] = Integer.parseInt(hostages[1 + (i * 3)]);
            hostagesDamage[i] = Integer.parseInt(hostages[2 + (i * 3)]);
        }

        State initialState = new State(neoX, neoY, 0, new int[carryCapacity], pillsX, pillsY, agentsX, agentsY, hostagesX, hostagesY, hostagesDamage);
        this.M = M;
        this.N = N;
        this.carryCapacity = carryCapacity;
        this.telephoneX = telephoneX;
        this.telephoneY = telephoneY;
        this.startPadsX = startPadsX;
        this.startPadsY = startPadsY;
        this.finishPadsX = finishPadsX;
        this.finishPadsY = finishPadsY;
        return initialState;
    }

    static String solve(String grid, String strategy, boolean visualize) {
        return "";
    }

    void visualize(State currentState) {
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
        String[][] vis = new String[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                vis[i][j] = "";
            }
        }
        vis[neoX][neoY] += "N";
        vis[telephoneX][telephoneY] += "T";
        for (int i = 0; i < pillsX.length; i++) {
            vis[pillsX[i]][pillsY[i]] += "L";
        }
        for (int i = 0; i < agentsX.length; i++) {
            vis[agentsX[i]][agentsY[i]] += "A";
        }
        int cntrPds = 0;
        for (int i = 0; i < startPadsX.length; i++) {
            vis[startPadsX[i]][startPadsY[i]] += "F" + cntrPds;
            vis[finishPadsX[i]][finishPadsY[i]] += "T" + (cntrPds);
            cntrPds++;
        }
        for (int i = 0; i < hostagesX.length; i++) {
            vis[hostagesX[i]][hostagesY[i]] += String.format("H(%d)", hostagesDamage[i]);
        }

        // Printing the state of the grid
        int cellMaxSize = 8;
        String strf = "%-" + cellMaxSize + "s | ";
        String line = "";
        for (int i = 0; i < (N + 1) * (cellMaxSize + 3) - 1; i++) line += "-";
        for (int j = -1; j < N; j++) {
            System.out.printf(strf, j);
        }
        System.out.println();
        System.out.println(line);
        for (int i = 0; i < M; i++) {
            System.out.printf(strf, i);
            for (int j = 0; j < N; j++) {
                System.out.printf(strf, vis[i][j]);
            }
            System.out.println();
            System.out.println(line);
        }
    }

    boolean goalTest(State state) {
        if (state.neoDamage >= 100)
            return false;
        for (int i = 0; i < state.hostagesX.length; i++) {
            int x = state.hostagesX[i];
            int y = state.hostagesY[i];
            int damage = state.hostagesDamage[i];
            if (damage < 100 && (x != telephoneX || y != telephoneY))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Hello world");
//      String p1 = "14,5;2;7,0;2,3;8,3,8,1,9,2,2,0;8,3,9,1,0,4,6,1;2,0,10,1,10,1,2,0;6,2,76,7,2,78,0,0,55,11,2,11,4,4,90,2,4,56,0,2,21,13,2,63,12,3,85,1,2,26";
        for (int i=0; i<10; i++) {
            String problem = genGrid();
            System.out.println(problem);
            Matrix mat = new Matrix();
            State initialState = mat.parseProblem(problem);
            mat.visualize(initialState);
        }
    }
}

/*
neo 1
tb  1
                    f(n,m) = N*M - 2

hostages    3:10
pills       1:hostages
pads        2:inf                   INF -> free - 1
agents      1:inf                   INF -> free

 */