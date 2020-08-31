import java.util.Random;

public class Replicate {
  public static void main(String[] args) {
    long DFZ = 539778;
    int[] center = { 0, 0, 0 };
    // int[] center = { 99, 68, -100 }; // for converting to absolute coords

    long internalseed = LCG.JAVA.combine(DFZ).nextSeed(0);
    Random random = new Random(internalseed ^ 0x5DEECE66DL);
    for (int i = 0; i < 64; i++) {
      int i1 = (7 + random.nextInt(8)) - random.nextInt(8);
      int j1 = (3 + random.nextInt(4)) - random.nextInt(4);
      int k1 = (7 + random.nextInt(8)) - random.nextInt(8);
      System.out.printf("%2d\t%2d\t%2d%n", i1 + center[0], j1 + center[1], k1 + center[2]);
    }
  }
}
