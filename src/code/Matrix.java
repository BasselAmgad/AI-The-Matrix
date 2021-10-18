package code;

public class Matrix extends SearchProblem {

    //grid size
    byte M, N;

    int telephoneX, telephoneY;

    int neoX, neoY;
    int c; // <=4

    // indexes of currently carried hostages ?
    int[] currentC;

    int n_hostages, n_pills, n_pads, n_agents;


    int[] padsX, padsY;

    int[] pillsX, pillsY;

    int[] agentsX, agentsY;

    int[] hostagesX, hostagesY;
    int[] hostagesDamage;

    int cost;

    static String genGrid() {
        // test
        return "";
    }

    static String solve(String grid, String strategy, boolean visualize) {
        return "";
    }

    public static void main(String[] args) {

    }
}
