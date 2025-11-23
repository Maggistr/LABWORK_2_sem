package Lab_5;
import java.util.regex.*;

public class PasswordValidator {
    public static void main(String[] args) {
        String password = "MyPassword123";
        String regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,16}$";

        if (password.matches(regex)) {
            System.out.println("Пароль корректен.");
        } else {
            System.out.println("Пароль не соответствует требованиям.");
        }
    }
}
