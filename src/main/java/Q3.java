import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

public class Q3 {
    public static String[] listToArray(String list) throws IOException {
        // list that holds strings of a file
        List<String> listOfStrings
                = new ArrayList<String>();

        // load data from file
        BufferedReader bf = new BufferedReader(
                new FileReader("/Users/angelique/Desktop/Computer Security/Coursework/Auxiliary files/english.txt"));

        // read entire line as string
        String line = null;
        try {
            line = bf.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // checking for end of file
        while (line != null) {
            listOfStrings.add(line);
            line = bf.readLine();
        }
        // closing bufferreader object
        bf.close();
        // storing the data in arraylist to array
        String[] array = listOfStrings.toArray(new String[0]);
        return array;
    }
    static KeyPair kp;
    static PublicKey pub;
    static Cipher pubCipher;

    static BigInteger exponent = new BigInteger("65537");
    static BigInteger modulus = new BigInteger("8256485268962869001259657059768534458613083531686232115114020348084108704327212587350933500875665043128112067620087666058357219348423005042462484509028749");
    public static void RSACipher() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        final KeyFactory factory = KeyFactory.getInstance("RSA");
        pub = factory.generatePublic(new RSAPublicKeySpec(modulus, exponent));
        pubCipher = Cipher.getInstance("RSA/ECB/NoPadding");
        pubCipher.init(Cipher.ENCRYPT_MODE, pub);
    }

    static String myCipher = "9D363AABA425E8EB0707112449C0A0A691B7D5D00DB3DCB2463BCDD28F40EA16313FE14FAF7DD91541C747CF5F754293A33844E86A183F79710CBF65F73B9A84";
    public static void createCipher(String[] array) throws IllegalBlockSizeException, BadPaddingException {
        for (int i = 0; i < array.length; i++) {
            byte[] cipher = pubCipher.doFinal(array[i].getBytes());

            String hex = "";

            // Iterating through each byte in the array
            for (byte j : cipher) {
                hex += String.format("%02X", j);
            }

            if (myCipher.equals(hex)) {
                System.out.println("The cipher is: " + array[i]);
                return;
            }
        }
    }

    public static void main(String[] args) {
try {
            String[] array = listToArray("/Users/angelique/Desktop/Computer Security/Coursework/Auxiliary files/english.txt");
            RSACipher();
            createCipher(array);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
