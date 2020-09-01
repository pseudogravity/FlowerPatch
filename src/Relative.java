import java.util.Random;

public class Relative {
  public static void main(String[] args) {

    // these 3 lines are customizable
    long blockstart = 0;
    int offset = 0; // from 0-5 inclusive
    long blocklength = 1L << 25;

    long initialDFZ = blockstart + offset;
    long internalseed = LCG.JAVA.combine(initialDFZ).nextSeed(0);

    Random random = new Random(internalseed ^ 0x5DEECE66DL);

    long maxtime = blocklength / 6 + 1;
    System.out.println("maxtime:" + maxtime);

    int[][] coords = { { 97, 68, -100 }, { 99, 68, -102 }, { 100, 68, -100 }, { 100, 68, -99 }, { 98, 68, -98 },
        { 100, 69, -103 }, { 95, 68, -102 }, { 99, 68, -98 }, { 97, 69, -104 }, { 96, 67, -98 }, { 94, 68, -100 },
        { 99, 68, -97 }, { 102, 68, -102 }, { 100, 68, -96 }, { 93, 67, -99 }, { 102, 67, -96 }, { 95, 70, -107 },
        { 105, 68, -101 } };

    // simple test
    // int[][] coords = { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 0, 2 }, { 0, 0, 3 }, { 0,
    // 0, 4 }, { 0, 0, 5 }, { 0, 0, 6 },
    // { 0, 0, 7 }, { 0, 0, 8 }, { 0, 0, 9 }, { 0, 0, 10 }, { 0, 0, 11 } };

    int[][][] known = new int[15][7][15];
    for (int first = 0; first < coords.length; first++) {
      for (int second = first + 1; second < coords.length; second++) {
        known[Math.abs(coords[first][0] - coords[second][0])][Math.abs(coords[first][1] - coords[second][1])][Math
            .abs(coords[first][2] - coords[second][2])]++;
      }
    }

    int missingcount = 0;
    for (int i = 0; i < known.length; i++) {
      for (int j = 0; j < known[0].length; j++) {
        for (int k = 0; k < known[0][0].length; k++) {
          if (known[i][j][k] > 0) {
            missingcount++;
          }
        }
      }
    }

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
      for (int pair = 1; pair < 64; pair++) {
        int ptr2 = (ptr + pair) & 63;
        int idelta = Math.abs(i1 - ibuffer[ptr2]);
        int jdelta = Math.abs(j1 - jbuffer[ptr2]);
        int kdelta = Math.abs(k1 - kbuffer[ptr2]);
        fcount[idelta][jdelta][kdelta]++;
        if (fcount[idelta][jdelta][kdelta] == known[idelta][jdelta][kdelta]) {
          missingcount--;
        }
      }
    }

    for (long time = 0; time < maxtime; time++) {

      if (missingcount == 0) {
        long DFZ = initialDFZ + 6 * time;
        if (DFZ < blockstart + blocklength) {
          System.out.println(DFZ);
        }
      }

      int ptr = (int) (time & 63);

      int i0 = ibuffer[ptr];
      int j0 = jbuffer[ptr];
      int k0 = kbuffer[ptr];
      for (int pair = 1; pair < 64; pair++) {
        int ptr2 = (ptr + pair) & 63;
        int idelta = Math.abs(i0 - ibuffer[ptr2]);
        int jdelta = Math.abs(j0 - jbuffer[ptr2]);
        int kdelta = Math.abs(k0 - kbuffer[ptr2]);
        if (fcount[idelta][jdelta][kdelta] == known[idelta][jdelta][kdelta]) {
          missingcount++;
        }
        fcount[idelta][jdelta][kdelta]--;
      }

      int i1 = (7 + random.nextInt(8)) - random.nextInt(8);
      int j1 = (3 + random.nextInt(4)) - random.nextInt(4);
      int k1 = (7 + random.nextInt(8)) - random.nextInt(8);
      ibuffer[ptr] = i1;
      jbuffer[ptr] = j1;
      kbuffer[ptr] = k1;
      for (int pair = 1; pair < 64; pair++) {
        int ptr2 = (ptr + pair) & 63;
        int idelta = Math.abs(i1 - ibuffer[ptr2]);
        int jdelta = Math.abs(j1 - jbuffer[ptr2]);
        int kdelta = Math.abs(k1 - kbuffer[ptr2]);
        fcount[idelta][jdelta][kdelta]++;
        if (fcount[idelta][jdelta][kdelta] == known[idelta][jdelta][kdelta]) {
          missingcount--;
        }
      }

    }

  }

}
