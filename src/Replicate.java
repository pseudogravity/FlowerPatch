import java.util.Random;

public class Replicate {
  public static void main(String[] args) {
    int targtime = 71527;
    Random random = new Random(0); // must match seed used in main
    for (int i = 0; i < targtime; i++) {
      random.nextInt(8);
      random.nextInt(8);
      random.nextInt(4);
      random.nextInt(4);
      random.nextInt(8);
      random.nextInt(8);
    }
    for (int i = 0; i < 64; i++) {
      int i1 = (7 + random.nextInt(8)) - random.nextInt(8);
      int j1 = (3 + random.nextInt(4)) - random.nextInt(4);
      int k1 = (7 + random.nextInt(8)) - random.nextInt(8);
      System.out.printf("%2d\t%2d\t%2d%n", i1, j1, k1);
    }
  }
}
