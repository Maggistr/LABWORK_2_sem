package Lab_5;
import java.util.regex.*;

public class IPValidator {
    public static void main(String[] args) {
        String ip = "192.168.1.1";
        String regex = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";

        if (ip.matches(regex)) {
            System.out.println("IP-адрес корректен.");
        } else {
            System.out.println("Неверный формат IP-адреса.");
        }
    }

}
