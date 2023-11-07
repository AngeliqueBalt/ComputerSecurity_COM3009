import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Q5 {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKey key = new SecretKeySpec(decodeHexString("bd96aaeb1c3b5149351a9b59d4d52f77"), "AES");
        byte[] IV = decodeHexString("2fc3406b74d7e215c9c00d458d969160");

        byte[] oneBlockMsg = decodeHexString("863a6c6e38a5d69f8924a4a16331e6e4");
        byte[] twoBlockMsg = decodeHexString("5f58a07f218755e2b818f44b21c39e3186fa3f6e0e562d90a9039ad0ae9e587b");
        byte[] threeBlockMsg = decodeHexString("6a7ce3cc20a8bb3858c39562d4bc521e55461c3a1cfcd0e7ad34b98f73f531fb37d9e06b76730723de12ac0ef1b0b69d");

        byte[] message = threeBlockMsg;

        byte[] hashedMsg = Hash(message, key, IV);
        byte[] decrypted = Decrypt(hashedMsg, key, IV);
        byte[] hashedDecrypted = Hash(decrypted, key, IV);

        System.out.println(encodeHexString(message));
        System.out.println(encodeHexString(hashedMsg));
        System.out.println(encodeHexString(decrypted));
        System.out.println(encodeHexString(hashedDecrypted));
    }

    public static byte[] getLastBlock(byte[] msg) {
        byte[] lastBlock = new byte[16];
        System.arraycopy(msg, msg.length - 16, lastBlock, 0, 16);
        return lastBlock;
    }

    public static byte[] Hash(byte[] msg, Key key, byte[] IV) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return getLastBlock(Encrypt(msg, key, IV));
    }

    public static byte[] decodeHexString(String hexKey) {
        if (hexKey.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexKey.length() / 2];
        for (int i = 0; i < hexKey.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexKey.substring(i, i + 2));
        }
        return bytes;
    }

    public static byte hexToByte(String hexKey) {
        int firstDigit = toDigit(hexKey.charAt(0));
        int secondDigit = toDigit(hexKey.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }

    public static byte[] Encrypt(byte[] msg, Key key, byte[] IV) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher ecipher = Cipher.getInstance("AES/CBC/NoPadding");
        ecipher.init(Cipher.ENCRYPT_MODE,key, new IvParameterSpec(IV));
        return ecipher.doFinal(msg);
    }

    public static byte[] Decrypt(byte[] msg, Key key, byte[] IV) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher dcipher = Cipher.getInstance("AES/CBC/NoPadding");
        dcipher.init(Cipher.DECRYPT_MODE,key, new IvParameterSpec(IV));
        return dcipher.doFinal(msg);
    }

    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
}
