package Lab_5;
import java.util.regex.*;

public class NumberFinder {
    public static void main(String[] args) {
        String text = "Цены: 19.99, 100, 2.50, 75 рублей.";
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
