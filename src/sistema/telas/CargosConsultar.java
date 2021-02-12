package sistema.telas;

import sistema.BancoDeDados;
import sistema.entidades.Cargo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargosConsultar extends JPanel {
    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Cargo> listasCargosModelo = new DefaultListModel();
    JList<Cargo> listaCargos;

    public CargosConsultar(){
        criarComponentes();
        criarEventos();
    }

    private void criarComponentes() {
        setLayout(null);

        labelTitulo = new JLabel("Consulta de Cargos", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Cargo");
        botaoEditar = new JButton(("Editar cargo"));
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Cargo");
        botaoExcluir.setEnabled(false);
        listasCargosModelo = new DefaultListModel();
        listaCargos = new JList<>();
        listaCargos.setModel(listasCargosModelo);
        listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(150, 120, 400, 20);
        campoCargo.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40);
        listaCargos.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40);
        botaoExcluir.setBounds(560, 400, 130, 40);
        
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(listaCargos);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }
    
    private void criarEventos(){
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarCargos(campoCargo.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletetarCargo();
            }
        });
        listaCargos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                cargoAtual = listaCargos.getSelectedValue();
                if(cargoAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlDeletetarCargo() {
    }

    private void sqlPesquisarCargos(String nome){
        //conexão
        Connection conexao;
        //instrucao SQL
        Statement instrucaoSQL;
        //resultados
        ResultSet result;


        try{
            conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);

            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = instrucaoSQL.executeQuery("SELECT * FROM cargos WHERE nome like '%"+nome+"%'");

            listasCargosModelo.clear();
            while(result.next()){
                Cargo cargo = new Cargo();
                cargo.setId(result.getInt("id"));
                cargo.setNome(result.getString("nome"));
                
                listasCargosModelo.addElement(cargo);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar cargos.");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
