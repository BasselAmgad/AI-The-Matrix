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
    // 3amalha :)

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


//    public State perform(Action action){
//
//    }
    public State moveUp(byte M, byte N){
        if (neoX == 0)
            return null;
        int[] currentCarried = this.currentCarried.clone();
        int[] pillsX = this.pillsX.clone();
        int[] pillsY = this.pillsY.clone();
        int[] agentsX = this.agentsX.clone();
        int[] agentsY = this.agentsY.clone();
        int[] hostagesX = this.hostagesX.clone();
        int[] hostagesY = this.hostagesY.clone();
        int[] hostagesDamage = this.hostagesDamage.clone();

        State resultState = new State(neoX-1, neoY, neoDamage, currentCarried, pillsX, pillsY, agentsX, agentsY, hostagesX, hostagesY, hostagesDamage);
        return resultState;
    }
    public State moveDown(byte M, byte N){
        if (neoX == M-1)
            return null;
        int[] currentCarried = this.currentCarried.clone();
        int[] pillsX = this.pillsX.clone();
        int[] pillsY = this.pillsY.clone();
        int[] agentsX = this.agentsX.clone();
        int[] agentsY = this.agentsY.clone();
        int[] hostagesX = this.hostagesX.clone();
        int[] hostagesY = this.hostagesY.clone();
        int[] hostagesDamage = this.hostagesDamage.clone();

        State resultState = new State(neoX+1, neoY, neoDamage, currentCarried, pillsX, pillsY, agentsX, agentsY, hostagesX, hostagesY, hostagesDamage);
        return resultState;
    }
    public State moveLeft(byte M, byte N){
        if (neoY == 0)
            return null;
        int[] currentCarried = this.currentCarried.clone();
        int[] pillsX = this.pillsX.clone();
        int[] pillsY = this.pillsY.clone();
        int[] agentsX = this.agentsX.clone();
        int[] agentsY = this.agentsY.clone();
        int[] hostagesX = this.hostagesX.clone();
        int[] hostagesY = this.hostagesY.clone();
        int[] hostagesDamage = this.hostagesDamage.clone();

        State resultState = new State(neoX, neoY-1, neoDamage, currentCarried, pillsX, pillsY, agentsX, agentsY, hostagesX, hostagesY, hostagesDamage);
        return resultState;
    }
    public State moveRight(byte M, byte N){
        if (neoY == N-1)
            return null;
        int[] currentCarried = this.currentCarried.clone();
        int[] pillsX = this.pillsX.clone();
        int[] pillsY = this.pillsY.clone();
        int[] agentsX = this.agentsX.clone();
        int[] agentsY = this.agentsY.clone();
        int[] hostagesX = this.hostagesX.clone();
        int[] hostagesY = this.hostagesY.clone();
        int[] hostagesDamage = this.hostagesDamage.clone();

        State resultState = new State(neoX, neoY+1, neoDamage, currentCarried, pillsX, pillsY, agentsX, agentsY, hostagesX, hostagesY, hostagesDamage);
        return resultState;
    }
}
