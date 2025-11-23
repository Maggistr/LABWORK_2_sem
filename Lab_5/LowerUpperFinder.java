package Lab_5;
import java.util.regex.*;

public class LowerUpperFinder {
    public static void main(String[] args) {
        String text = "helloWorld javaProgramming testCase";
        Pattern pattern = Pattern.compile("[a-z][A-Z]");
        Matcher matcher = pattern.matcher(text);
        String result = matcher.replaceAll("!$0!");

        System.out.println(result);
    }
}
