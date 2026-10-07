package view;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Auxiliares para montar formulários de rótulo + campo com GridBagLayout. */
public final class Formulario {

    private Formulario() { }

    public static void adicionarLinha(JPanel painel, int linha, String rotulo, JComponent campo) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = linha;
        c.weightx = 0;
        painel.add(new JLabel(rotulo), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campo, c);
    }

    /** Última linha do formulário: empurra os campos para o topo da aba. */
    public static void fecharFormulario(JPanel painel, int linha) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = linha;
        c.gridwidth = 2;
        c.weighty = 1;
        JPanel vazio = new JPanel();
        vazio.setOpaque(false);
        painel.add(vazio, c);
    }
}
