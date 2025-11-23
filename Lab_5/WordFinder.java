package Lab_5;
import java.util.regex.*;

public class WordFinder {
    public static void main(String[] args) {
        String text = "Java is a programming language. JavaScript is also popular.";
        char startLetter = 'J';
        Pattern pattern = Pattern.compile("\\b" + startLetter + "[a-zA-Z]*\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
