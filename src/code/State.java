package code;

public class State {

    int neoX, neoY, neoDamage;
    int countKilled;
    int[] agentsX, agentsY;
    int[] pillsX, pillsY;
    int countDead;
    int[] hostagesX, hostagesY, hostagesDamage;
    int[] carriedX, carriedY, carriedDamage;
    int[] mutatedX, mutatedY, mutatedDamage;

    public String decodeState(){

        String[] agents = new String[agentsX.length * 2];
        for (int i=0; i< agentsX.length; i++){
            agents[i*2] =""+ agentsX[i];
            agents[i*2 + 1] =""+ agentsY[i];
        }
        String[] pills = new String[pillsX.length * 2];
        for (int i=0; i< pillsX.length; i++){
            pills[i*2] =""+ pillsX[i];
            pills[i*2 + 1] =""+ pillsY[i];
        }
        String[] hostages = new String[hostagesX.length * 3];
        for (int i=0; i< hostagesX.length; i++){
            hostages[i*3] = "" + hostagesX[i];
            hostages[i*3 + 1] = "" + hostagesY[i];
            hostages[i*3 + 2] = "" + hostagesDamage[i];
        }

        String[] carried = new String[carriedX.length * 3];
        for (int i=0; i< carriedX.length; i++){
            carried[i*3] = "" + carriedX[i];
            carried[i*3 + 1] = "" + carriedY[i];
            carried[i*3 + 2] = "" + carriedDamage[i];
        }
        String[] mutated = new String[mutatedX.length * 3];
        for (int i=0; i< mutatedX.length; i++){
            mutated[i*3] = "" + mutatedX[i];
            mutated[i*3 + 1] = "" + mutatedY[i];
            mutated[i*3 + 2] = "" + mutatedDamage[i];
        }


        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d,%d;", neoX, neoY, neoDamage));
        sb.append(String.format("%d;", countKilled));
        sb.append(String.join(",", agents)).append(";");
        sb.append(String.join(",", pills)).append(";");
        sb.append(String.format("%d;", countDead));
        sb.append(String.join(",", hostages)).append(";");
        sb.append(String.join(",", carried)).append(";");
        sb.append(String.join(",", mutated));
        return sb.toString();
    }

    public State(String stateString){
        String[] entries = stateString.split(";", -1);

        String[] neo = entries[0].split(",");
        this.neoX = Integer.parseInt(neo[0]);
        this.neoY = Integer.parseInt(neo[1]);
        this.neoDamage = Integer.parseInt((neo[2]));

        this.countKilled = Integer.parseInt(entries[1]);

        String[] agents = entries[2].split((","));
        this.agentsX = new int[agents.length/2];
        this.agentsY = new int[agents.length/2];
        for (int i=0; i< agentsX.length; i++){
            agentsX[i] = Integer.parseInt(agents[i*2]);
            agentsY[i] = Integer.parseInt(agents[i*2 + 1]);
        }

        String[] pills = entries[3].split((","));
        this.pillsX = new int[pills.length/2];
        this.pillsY = new int[pills.length/2];
        for (int i=0; i< pillsX.length; i++){
            pillsX[i] = Integer.parseInt(pills[i*2]);
            pillsY[i] = Integer.parseInt(pills[i*2 + 1]);
        }

        this.countDead = Integer.parseInt(entries[4]);

        String[] hostages = entries[5].split((","));
        this.hostagesX = new int[hostages.length/3];
        this.hostagesY = new int[hostages.length/3];
        this.hostagesDamage = new int[hostages.length/3];
        for (int i=0; i< hostagesX.length; i++){
            hostagesX[i] = Integer.parseInt(hostages[i*3]);
            hostagesY[i] = Integer.parseInt(hostages[i*3 + 1]);
            hostagesDamage[i] = Integer.parseInt(hostages[i*3 + 2]);
        }

        String[] carried = entries[6].split((","));
        this.carriedX = new int[carried.length/3];
        this.carriedY = new int[carried.length/3];
        this.carriedDamage = new int[carried.length/3];
        for (int i=0; i< carriedX.length; i++){
            carriedX[i] = Integer.parseInt(carried[i*3]);
            carriedY[i] = Integer.parseInt(carried[i*3 + 1]);
            carriedDamage[i] = Integer.parseInt(carried[i*3 + 2]);
        }

        String[] mutated = entries[7].split((","));
        this.mutatedX = new int[mutated.length/3];
        this.mutatedY = new int[mutated.length/3];
        this.mutatedDamage = new int[mutated.length/3];
        for (int i=0; i< mutatedX.length; i++){
            mutatedX[i] = Integer.parseInt(mutated[i*3]);
            mutatedY[i] = Integer.parseInt(mutated[i*3 + 1]);
            mutatedDamage[i] = Integer.parseInt(mutated[i*3 + 2]);
        }

    }





}
