package code;

public class Matrix extends SearchProblem {

    //grid size
    byte M, N;

    byte telephoneX, telephoneY;

    int carryCapacity; // <=4

    int n_hostages, n_pills, n_pads, n_agents;

    int[] startPadsX, startPadsY, finishPadsX, finishPadsY;

    static String genGrid() {
        StringBuilder sb = new StringBuilder();
        int N = 5 + (int)(Math.random()*11);
        int M = 5 + (int)(Math.random()*11);
        sb.append(String.format("%d,%d;", M, N));
        int carryCapacity = 1 + (int)(Math.random()*4);
        sb.append(String.format("%d;", carryCapacity));
        int neoX = (int)(Math.random()*M);
        int neoY = (int)(Math.random()*N);
        sb.append(String.format("%d,%d;", neoX, neoY));
        int telephoneX = (int)(Math.random()*M);
        int telephoneY = (int)(Math.random()*N);
        sb.append(String.format("%d,%d;", telephoneX, telephoneY));
        //No bounds for the number of agents are set in the description. Let them be between 1 and 4 for now
        int n_agents = 1 + (int)(Math.random()*4);
        String[] agents = new String[n_agents<<1];
        for (int i=0; i<agents.length; i+=2){
            int x, y;
            do {
                x = (int) (Math.random() * M);
                y = (int) (Math.random() * N);
            } while (x==neoX && y==neoY);
            agents[i] = x+"";
            agents[i+1] = y+"";
        }
        sb.append(String.join(",", agents) +";");

        //No bounds for the number of pills are set in the description. Let them be between 3 and 8 for now
        int n_pills = 3 + (int)(Math.random()*6);
        String[] pills = new String[n_pills<<1];
        for (int i=0; i<pills.length; i+=2){
            int x = (int)(Math.random()*M);
            int y = (int)(Math.random()*N);
            pills[i] = x+"";
            pills[i+1] = y+"";
        }
        sb.append(String.join(",", pills) +";");

        //No bounds for the number of pills are set in the description. Let them be between 1 and 4 for now
        int nrpads = 1 + (int)(Math.random()*4);
        /*
         String[] pads is a multiple of 8
         *2 to contain x and y for each pad
         *2 so as every start pad Px should have a corresponding finish pad Py
         *2 so as for every pair Px, Py we should output the other direction (Py, Px) as well
         */
        String[] pads = new String[nrpads * 2 * 2 * 2];
        for (int i=0; i<pads.length; i+=8){
            int x1, y1, x2, y2;
            x1 = (int)(Math.random()*M);
            y1 = (int)(Math.random()*N);
            do {
                x2 = (int) (Math.random() * M);
                y2 = (int) (Math.random() * N);
            } while(x1 == x2 && y1 == y2);
            pads[i] = x1+"";
            pads[i+1] = y1+"";
            pads[i+2] = x2+"";
            pads[i+3] = y2+"";
            pads[i+4] = x2+"";
            pads[i+5] = y2+"";
            pads[i+6] = x1+"";
            pads[i+7] = y1+"";
        }
        sb.append(String.join(",", pads) +";");

        int n_hostages = 5 + (int)(Math.random()*6);
        String[] hostages = new String[n_hostages*3];
        for (int i=0; i<hostages.length; i+=3){
            int x = (int)(Math.random()*M);
            int y = (int)(Math.random()*N);
            int damage = 1 + (int)(Math.random()*100);
            hostages[i] = x+"";
            hostages[i+1] = y+"";
            hostages[i+2] = damage+"";
        }
        sb.append(String.join(",", hostages));
        System.out.println("Hostages line: "+ String.join(",", hostages));
        return sb.toString();
    }

    static String solve(String grid, String strategy, boolean visualize) {
        State initialState = new State();


        return "";
    }

    static void visualize(String grid){
        String[] entries = grid.split(";");
//        for (String e: entries){
//            System.out.println(e);
//        }
        String[] dims = entries[0].split(",");
        int M = Integer.parseInt(dims[0]);
        int N = Integer.parseInt(dims[1]);

        int carryCapacity = Integer.parseInt(entries[1]);

        String[] neo = entries[2].split(",");
        int neoX = Integer.parseInt(neo[0]);
        int neoY = Integer.parseInt(neo[1]);

        String[] tel = entries[3].split(",");
        int telephoneX = Integer.parseInt(tel[0]);
        int telephoneY = Integer.parseInt(tel[1]);

        String[] agents = entries[4].split(",");
        int[] agentsX = new int[agents.length>>1];
        int[] agentsY = new int[agents.length>>1];
        for (int i=0; i <agentsX.length; i++){
            agentsX[i] = Integer.parseInt(agents[i<<1]);
            agentsY[i] = Integer.parseInt(agents[1+(i<<1)]);
        }

        String[] pills = entries[5].split(",");
        int[] pillsX = new int[pills.length>>1];
        int[] pillsY = new int[pills.length>>1];
        for (int i=0; i <pillsX.length; i++){
            pillsX[i] = Integer.parseInt(pills[i<<1]);
            pillsY[i] = Integer.parseInt(pills[1+(i<<1)]);
        }

        String[] pads = entries[6].split(",");
        int[] padsX = new int[pads.length>>1];
        int[] padsY = new int[pads.length>>1];
        for (int i=0; i <padsX.length; i++){
            padsX[i] = Integer.parseInt(pads[i<<1]);
            padsY[i] = Integer.parseInt(pads[1+(i<<1)]);
        }

        String[] hostages = entries[7].split(",");
        int[] hostagesX = new int[hostages.length/3];
        int[] hostagesY = new int[hostages.length/3];
        int[] damages = new int[hostages.length/3];
        for (int i=0; i <hostagesX.length; i++){
            hostagesX[i] = Integer.parseInt(hostages[(i*3)]);
            hostagesY[i] = Integer.parseInt(hostages[1+(i*3)]);
            damages[i] = Integer.parseInt(hostages[2+(i*3)]);
        }

        // Preparing to print the state of the grid
        String[][] vis = new String[M][N];
        for (int i=0;i<M;i++){
            for (int j=0;j<N;j++){
                vis[i][j] = "";
            }
        }
        vis[neoX][neoY] += "N";
        vis[telephoneX][telephoneY] += "T";
        for (int i=0; i< pillsX.length;i ++){
            vis[pillsX[i]][pillsY[i]] += "L";
        }
        for (int i=0; i< agentsX.length;i ++){
            vis[agentsX[i]][agentsY[i]] += "A";
        }
        int cntrPds = 0;
        for (int i=0; i< padsX.length;i+=2){
            vis[padsX[i]][padsY[i]] += "F"+cntrPds;
            vis[padsX[i+1]][padsY[i+1]] += "T"+(cntrPds);
            cntrPds++;
        }
        for (int i=0; i< hostagesX.length;i++){
            vis[hostagesX[i]][hostagesY[i]] += String.format("H(%d)", damages[i]);
        }

        // Printing the state of the grid
        int cellMaxSize = 15;
        String strf = "%-"+cellMaxSize+"s ";
        for (int j=-1;j<N;j++){
            System.out.printf(strf, j);
        }
        System.out.println();
        for (int i=0;i<M;i++){
            System.out.printf(strf, i);
            for (int j=0;j<N;j++){
//                System.out.printf("[%d][%d]: %s\n", i, j, vis[i][j]);
                System.out.printf(strf, vis[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
//        for (int i=0; i<1;i ++) {
//            System.out.println(genGrid());
//        }
//        visualize("6,5;4;5,2;2,4;4,3;3,2,1,4,5,4,4,1;0,3,4,1,3,3,3,4;4,2,51,1,4,22,0,1,19,3,4,16,1,3,100,4,2,41,4,1,32,1,2,34;");
//            visualize("5,10;2;0,8;2,0;0,7,2,0;4,0;3,4,2,7,2,7,3,4,4,9,1,3,1,3,4,9,3,7,0,0,0,0,3,7,3,5,0,6,0,6,3,5;4,7,98,1,7,19,1,0,28,3,1,74,4,3,72");
        String p1 = "14,5;2;7,0;2,3;8,3,8,1,9,2,2,0;8,3,9,1,0,4,6,1;2,0,10,1,10,1,2,0;6,2,76,7,2,78,0,0,55,11,2,11,4,4,90,2,4,56,0,2,21,13,2,63,12,3,85,1,2,26";
        String problem = genGrid();
        System.out.println(problem);
        visualize(problem);

    }
}
