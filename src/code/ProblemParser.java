package code;

public final class ProblemParser {

    public static String parseProblem(String problem) {
        String[] entries = problem.split(";", -1);

        String[] dims = entries[0].split(",");
        short M = Short.parseShort(dims[0]);
        short N = Short.parseShort(dims[1]);

        int carryCapacity = Integer.parseInt(entries[1]);

        String[] neo = entries[2].split(",");
        int neoX = Integer.parseInt(neo[0]);
        int neoY = Integer.parseInt(neo[1]);
        int neoDamage = 0;

        String[] tel = entries[3].split(",");
        short telephoneX = Short.parseShort(tel[0]);
        short telephoneY = Short.parseShort(tel[1]);

        int countKilled = 0;

        String[] agents = entries[4].split(",");

        String[] pills = entries[5].split(",");

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

        int countDead = 0;

        String[] hostages = entries[7].split(",");
        String[] carried = new String[0];
        String[] mutated = new String[0];


        MatrixConfig.M = M;
        MatrixConfig.N = N;
        MatrixConfig.carryCapacity = carryCapacity;
        MatrixConfig.telephoneX = telephoneX;
        MatrixConfig.telephoneY = telephoneY;
        MatrixConfig.startPadsX = startPadsX;
        MatrixConfig.startPadsY = startPadsY;
        MatrixConfig.finishPadsX = finishPadsX;
        MatrixConfig.finishPadsY = finishPadsY;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d,%d,%d;", neoX, neoY, neoDamage));
        sb.append(String.format("%d;", countKilled));
        sb.append(String.join(",", agents)).append(";");
        sb.append(String.join(",", pills)).append(";");
        sb.append(String.format("%d;", countDead));
        sb.append(String.join(",", hostages)).append(";");
        sb.append(String.join(",", carried)).append(";");
        sb.append(String.join(",", mutated));

        return sb.toString();
    }
}
