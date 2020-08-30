import java.util.*;

public class Main {
  public static void main(String[] args) {

    Random random = new Random(0);

    boolean[][][] known = genKnown(99, 68, -100); // chosen center coord, relative to flower coords
    int missingcount = 18; // must equal number of observed flowers

    int[][][] fcount = new int[15][7][15];

    int[] ibuffer = new int[64];
    int[] jbuffer = new int[64];
    int[] kbuffer = new int[64];

    long time = 0;

    for (int i = 0; i < 64; i++) {
      // initialize circular buffer
      int ptr = (int) (time & 63);
      int i1 = (7 + random.nextInt(8)) - random.nextInt(8);
      int j1 = (3 + random.nextInt(4)) - random.nextInt(4);
      int k1 = (7 + random.nextInt(8)) - random.nextInt(8);
      fcount[i1][j1][k1]++;
      ibuffer[ptr] = i1;
      jbuffer[ptr] = j1;
      kbuffer[ptr] = k1;
      if (known[i1][j1][k1] && fcount[i1][j1][k1] == 1) {
        missingcount--;
      }
      time++;
    }

    for (long i = 0; i < 10000000; i++) {

      if (missingcount == 0) {
        System.out.println(time - 64);
      }

      int ptr = (int) (time & 63);

      int i0 = ibuffer[ptr];
      int j0 = jbuffer[ptr];
      int k0 = kbuffer[ptr];
      fcount[i0][j0][k0]--;
      if (known[i0][j0][k0] && fcount[i0][j0][k0] == 0) {
        missingcount++;
      }
      // branchless for possible use in CUDA
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
      // branchless for possible use in CUDA
      // missingcount -= ((fcount[i1][j1][k1] - 1) >> 31) & known[i1][j1][k1];
      fcount[i1][j1][k1]++;

      time++;
    }

  }

  public static boolean[][][] genKnown(int xcenter, int ycenter, int zcenter) {
    int[][] coords = { { 97, 68, -100 }, { 99, 68, -102 }, { 100, 68, -100 }, { 100, 68, -99 }, { 98, 68, -98 },
        { 100, 69, -103 }, { 95, 68, -102 }, { 99, 68, -98 }, { 97, 69, -104 }, { 96, 67, -98 }, { 94, 68, -100 },
        { 99, 68, -97 }, { 102, 68, -102 }, { 100, 68, -96 }, { 93, 67, -99 }, { 102, 67, -96 }, { 95, 70, -107 },
        { 105, 68, -101 } };
    boolean[][][] known = new boolean[15][7][15];
    for (int[] coord : coords) {
      known[coord[0] - xcenter + 7][coord[1] - ycenter + 3][coord[2] - zcenter + 7] = true;
    }
    return known;
  }

}
