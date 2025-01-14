import view.LoginFrame;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        // Menjalankan LoginFrame pertama kali saat aplikasi dijalankan
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
