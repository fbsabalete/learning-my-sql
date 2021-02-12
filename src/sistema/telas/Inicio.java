package sistema.telas;

import sistema.Navegador;

import javax.swing.*;

public class Inicio extends JPanel {
    JLabel labelTitulo;

    public Inicio(){
        criarComponentes();
        criarEventos();
        Navegador.habilitaMenu();
    }

    private void criarComponentes(){
        setLayout(null);

        labelTitulo = new JLabel("Escolha uma opção no menu superior", JLabel.CENTER);
        labelTitulo.setBounds(20, 100, 660, 40);
        add(labelTitulo);
        setVisible(true);
    }

    private void criarEventos(){

    }
}
