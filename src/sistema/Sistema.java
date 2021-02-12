package sistema;

import sistema.telas.CargosConsultar;
import sistema.telas.CargosInserir;
import sistema.telas.Inicio;
import sistema.telas.Login;

import javax.swing.*;
import java.sql.*;

public class Sistema {
    public static JPanel tela;
    public static JFrame frame;
    public static void main(String[] args) {
        criarComponentes();
    }

    private static void criarComponentes() {
        frame = new JFrame("Sistema");
        frame.setSize(700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        tela = new CargosConsultar();
        tela.setVisible(true);
        frame.add(tela);

        frame.setVisible(true);
    }
}
