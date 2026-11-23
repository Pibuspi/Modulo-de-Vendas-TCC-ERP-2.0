import javax.swing.SwingUtilities;
import view.TelaPrincipalView;

/** Launcher do módulo: inicia o dashboard central no EDT do Swing. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipalView().setVisible(true));
    }
}
