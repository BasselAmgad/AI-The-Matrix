package code;

public class State {

    int neoX, neoY;
    int neoDamage;

    // indexes of currently carried hostages ?
    int[] currentCarried;

    int[] pillsX, pillsY;

    int[] agentsX, agentsY;

    int[] hostagesX, hostagesY;
    int[] hostagesDamage;

    // Eslam mesh 3awz ye3melaha constructor :`D

    public State(int neoX, int neoY, int neoDamage, int[] currentCarried,
                 int[] pillsX, int[] pillsY,
                 int[] agentsX, int[] agentsY,
                 int[] hostagesX, int[] hostagesY, int[] hostagesDamage) {
        this.neoX = neoX;
        this.neoY = neoY;
        this.neoDamage = neoDamage;
        this.currentCarried = currentCarried;
        this.pillsX = pillsX;
        this.pillsY = pillsY;
        this.agentsX = agentsX;
        this.agentsY = agentsY;
        this.hostagesX = hostagesX;
        this.hostagesY = hostagesY;
        this.hostagesDamage = hostagesDamage;
    }
}
