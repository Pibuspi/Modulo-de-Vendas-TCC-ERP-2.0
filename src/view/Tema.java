package view;

import javax.swing.UIManager;

/* Aplica o visual padrão (Nimbus) do módulo. */
public final class Tema {
    private Tema() { }

    public static void aplicar() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Mantém o visual padrão do Java Swing.
        }
    }
}
