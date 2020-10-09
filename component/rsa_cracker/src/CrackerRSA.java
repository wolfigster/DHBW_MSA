import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class CrackerRSA {

    private static CrackerRSA instance = new CrackerRSA();
    public Port port;
    private BigInteger e;
    private BigInteger n;
    private BigInteger cipher;

    private CrackerRSA() {
        port = new Port();
    }

    public static CrackerRSA getInstance() {
        return instance;
    }

    public class Port implements ICrackerRSA {
        @Override
        public String crack(String cipher, File keyFile) {
            return crackRSA(cipher, keyFile);
        }
    }

    private String crackRSA(String cipher, File keyFile) {
        getKeyFromFile(keyFile);
        byte[] bytes = Base64.getDecoder().decode(cipher);
        this.cipher = new BigInteger(bytes);

        try {
            BigInteger text = execute();
            return new String(text.toByteArray());
        } catch (CrackerRSAException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void getKeyFromFile(File keyFile) {
        try {
            BigInteger n = null, e = null;
            for(String line : Files.readAllLines(keyFile.toPath(), StandardCharsets.UTF_8)) {
                if(line.contains("n")) n = new BigInteger(line.replaceAll("[^0-9]", ""));
                if(line.contains("e")) e = new BigInteger(line.replaceAll("[^0-9]", ""));
            }
            this.n = n;
            this.e = e;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }


    // 01_algorithms/02_rsa/02_cracker code
    public BigInteger execute() throws CrackerRSAException {
        BigInteger p, q, d;
        List<BigInteger> factorList = factorize(this.n);

        if (factorList.size() != 2) {
            throw new CrackerRSAException("cannot determine factors p and q");
        }

        p = factorList.get(0);
        q = factorList.get(1);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        d = this.e.modInverse(phi);
        return this.cipher.modPow(d, this.n);
    }

    public List<BigInteger> factorize(BigInteger _n) {
        BigInteger two = BigInteger.valueOf(2);
        List<BigInteger> factorList = new LinkedList<>();

        if (_n.compareTo(two) < 0) {
            throw new IllegalArgumentException("must be greater than one");
        }

        while (_n.mod(two).equals(BigInteger.ZERO)) {
            factorList.add(two);
            _n = _n.divide(two);
        }

        if (_n.compareTo(BigInteger.ONE) > 0) {
            BigInteger factor = BigInteger.valueOf(3);
            while (factor.multiply(factor).compareTo(_n) <= 0) {
                if (_n.mod(factor).equals(BigInteger.ZERO)) {
                    factorList.add(factor);
                    _n = _n.divide(factor);
                } else {
                    factor = factor.add(two);
                }
            }
            factorList.add(_n);
        }

        return factorList;
    }
}
