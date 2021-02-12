package sistema.telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JPanel {
    JLabel labelUsuario;
    JTextField campoUsuario;
    JLabel labelSenha;
    JPasswordField campoSenha;
    JButton botaoEntrar;

    public Login(){
        criarComponentes();
        criarEventos();
    }

    private void criarComponentes() {
        setLayout(null);

        JLabel labelTitulo = new JLabel("Seja bem vindo ao Sistema da Compania Bonita", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 18));

        labelUsuario = new JLabel("Usuário", JLabel.LEFT);
        campoUsuario = new JTextField();
        labelSenha = new JLabel("Senha", JLabel.LEFT);
        campoSenha = new JPasswordField();
        botaoEntrar = new JButton("Entrar");

        labelTitulo.setBounds(20, 100, 660, 40);
        labelUsuario.setBounds(250, 220, 200, 20);
        campoUsuario.setBounds(250, 240, 200, 40);
        labelSenha.setBounds(250, 280, 200, 20);
        campoSenha.setBounds(250, 300, 200, 40);
        botaoEntrar.setBounds(250, 350, 200, 40);

        add(labelTitulo);
        add(labelUsuario);
        add(campoUsuario);
        add(labelSenha);
        add(campoSenha);
        add(botaoEntrar);

        setVisible(true);
    }

    private void criarEventos() {
        botaoEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
