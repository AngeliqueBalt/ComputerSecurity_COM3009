import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Q1 {
    // puts list into array
    public static String[] readDictionary() throws IOException {
        Path filePath = Paths.get("10letterwordslist.txt");
        try (Stream<String> lineStream = Files.lines(filePath)) {
            return lineStream.toList().toArray(new String[0]);
        }
    }

    // calculate difference between ciphertexts
    String c1 = "PSWRRULZVV";
    String c2 = "DEAVBXAUFJ";
    public static int[] calcDiff(String c1, String c2) {
        int[] diff = new int[10];

        for (int i = 0; i < c1.length(); i++) {
            int difference = (c1.charAt(i)-65 - c2.charAt(i)-65 + 26) % 26;
            //System.out.println(difference);
            diff[i] = (char) (difference);
            //System.out.println(diff);
        }
        return diff;
    }

    public static void findWords(String[] array, int[] diff) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
                calcDiff(array[i], array[j]);
                if (Arrays.equals(calcDiff(array[i], array[j]), diff)) {
                    System.out.println("match found: " + array[i] + " " + array[j]);
                    return;
                } else {
                    //System.out.println("no match found");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int[] diff = calcDiff("PSWRRULZVV", "DEAVBXAUFJ");
        findWords(readDictionary(), diff);

    }

}
