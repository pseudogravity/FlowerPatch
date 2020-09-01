import java.util.*;

public class Main {
  public static void main(String[] args) {

    // these 4 lines are customizable
    long blockstart = 0;
    int offset = 0; // from 0-5 inclusive
    long blocklength = 2000000000L;
    int[] center = { 99, 68, -100 }; // chosen center coord, relative to flower coords

    long initialDFZ = blockstart + offset;
    long internalseed = LCG.JAVA.combine(initialDFZ).nextSeed(0);

    Random random = new Random(internalseed ^ 0x5DEECE66DL);

    long maxtime = blocklength / 6 + 1;
    // System.out.println("maxtime:" + maxtime);

    int[][] coords = { { 97, 68, -100 }, { 99, 68, -102 }, { 100, 68, -100 }, { 100, 68, -99 }, { 98, 68, -98 },
        { 100, 69, -103 }, { 95, 68, -102 }, { 99, 68, -98 }, { 97, 69, -104 }, { 96, 67, -98 }, { 94, 68, -100 },
        { 99, 68, -97 }, { 102, 68, -102 }, { 100, 68, -96 }, { 93, 67, -99 }, { 102, 67, -96 }, { 95, 70, -107 },
        { 105, 68, -101 } };

    // simple test
    // int[] center = { 7, 3, 7 };
    // int[][] coords = { { 0, 0, 0 }, { 1, 1, 1 }, { 2, 2, 2 }, { 3, 3, 3 } };

    boolean[][][] known = new boolean[15][7][15];
    for (int[] coord : coords) {
      known[coord[0] - center[0] + 7][coord[1] - center[1] + 3][coord[2] - center[2] + 7] = true;
    }

    int missingcount = coords.length;

    int[][][] fcount = new int[15][7][15];

    int[] ibuffer = new int[64];
    int[] jbuffer = new int[64];
    int[] kbuffer = new int[64];

    for (int time = 0; time < 64; time++) {
      // initialize circular buffer
      int ptr = (int) (time & 63);
      int i1 = (7 + random.nextInt(8)) - random.nextInt(8);
      int j1 = (3 + random.nextInt(4)) - random.nextInt(4);
      int k1 = (7 + random.nextInt(8)) - random.nextInt(8);
      ibuffer[ptr] = i1;
      jbuffer[ptr] = j1;
      kbuffer[ptr] = k1;
      if (known[i1][j1][k1] && fcount[i1][j1][k1] == 0) {
        missingcount--;
      }
      // branchless for possible use in C
      // missingcount -= ((fcount[i1][j1][k1] - 1) >> 31) & known[i1][j1][k1];
      fcount[i1][j1][k1]++;
    }

    // int minmissing = 1000;

    for (long time = 0; time < maxtime; time++) {

      if (missingcount == 0) {
        long DFZ = initialDFZ + 6 * time;
        if (DFZ < blockstart + blocklength) {
          System.out.println(DFZ);
        }
      }
      // if (missingcount < minmissing) {
      // System.out.printf("%3d\t%d%n", missingcount, time - 64);
      // minmissing = missingcount;
      // }

      int ptr = (int) (time & 63);

      int i0 = ibuffer[ptr];
      int j0 = jbuffer[ptr];
      int k0 = kbuffer[ptr];
      fcount[i0][j0][k0]--;
      if (known[i0][j0][k0] && fcount[i0][j0][k0] == 0) {
        missingcount++;
      }
      // branchless for possible use in C
      // missingcount += ((fcount[i0][j0][k0] - 1) >> 31) & known[i0][j0][k0];

      int i1 = (7 + random.nextInt(8)) - random.nextInt(8);
      int j1 = (3 + random.nextInt(4)) - random.nextInt(4);
      int k1 = (7 + random.nextInt(8)) - random.nextInt(8);
      ibuffer[ptr] = i1;
      jbuffer[ptr] = j1;
      kbuffer[ptr] = k1;
      if (known[i1][j1][k1] && fcount[i1][j1][k1] == 0) {
        missingcount--;
      }
      // branchless for possible use in C
      // missingcount -= ((fcount[i1][j1][k1] - 1) >> 31) & known[i1][j1][k1];
      fcount[i1][j1][k1]++;

    }

  }

}
