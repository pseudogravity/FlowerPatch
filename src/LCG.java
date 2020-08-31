import java.util.Random;

// from KaptainWutax SeedUtils but reduced to essential parts
public class LCG {
  public static void main(String[] args) {
    Random r = new Random(0);
    for (int i = 0; i < 10; i++) {
      System.out.print(i + " ");
      System.out.println(r.nextInt(1 << 30));
    }

    long skipseed = JAVA.combine(5).nextSeed(0 ^ 0x5DEECE66DL) ^ 0x5DEECE66DL;
    Random r2 = new Random(skipseed);
    System.out.println(r2.nextInt(1 << 30));
  }

  public static final LCG JAVA = new LCG(25214903917L, 11L, 1L << 48);

  public final long multiplier;
  public final long addend;
  public final long modulus;

  public LCG(long multiplier, long addend, long modulus) {
    this.multiplier = multiplier;
    this.addend = addend;
    this.modulus = modulus;
  }

  public long nextSeed(long seed) {
    return this.mod(seed * this.multiplier + this.addend);
  }

  public long mod(long n) {
    return n & (this.modulus - 1);
  }

  public LCG combine(long steps) {
    long multiplier = 1;
    long addend = 0;

    long intermediateMultiplier = this.multiplier;
    long intermediateAddend = this.addend;

    for (long k = steps; k != 0; k >>>= 1) {
      if ((k & 1) != 0) {
        multiplier *= intermediateMultiplier;
        addend = intermediateMultiplier * addend + intermediateAddend;
      }

      intermediateAddend = (intermediateMultiplier + 1) * intermediateAddend;
      intermediateMultiplier *= intermediateMultiplier;
    }

    multiplier = this.mod(multiplier);
    addend = this.mod(addend);

    return new LCG(multiplier, addend, this.modulus);
  }

}