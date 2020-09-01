import java.util.Random;

public class Replicate {
  public static void main(String[] args) {
    long DFZ = 3792043554L; // produces flowers at (0,0,0) (1,1,1) (2,2,2) (3,3,3)
    int[] center = { 7, 3, 7 };
    // int[] center = { 99, 68, -100 }; // for converting to absolute coords

    long internalseed = LCG.JAVA.combine(DFZ).nextSeed(0);
    Random random = new Random(internalseed ^ 0x5DEECE66DL);
    for (int i = 0; i < 64; i++) {
      int i1 = (random.nextInt(8)) - random.nextInt(8);
      int j1 = (random.nextInt(4)) - random.nextInt(4);
      int k1 = (random.nextInt(8)) - random.nextInt(8);
      System.out.printf("%2d\t%2d\t%2d%n", i1 + center[0], j1 + center[1], k1 + center[2]);
    }
  }
}
