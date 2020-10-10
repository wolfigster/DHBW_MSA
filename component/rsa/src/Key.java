import java.math.BigInteger;

public class Key {
    private final BigInteger n;
    private final BigInteger de;

    public Key(BigInteger n, BigInteger de) {
        this.n = n;
        this.de = de;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getDorE() {
        return de;
    }
}