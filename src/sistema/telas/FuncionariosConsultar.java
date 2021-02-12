package sistema.telas;

import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionariosConsultar extends JPanel {
    Funcionario funcionarioAtual;
    JLabel labelTitulo, labelFuncionario;
    JTextField campoFuncionario;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Funcionario> model = new DefaultListModel<>();
    JList<Funcionario> listaFuncionarios;

    public FuncionariosConsultar(){
        criarComponentes();
        criarEventos();
    }

    private void criarComponentes() {
        setLayout(null);

        labelTitulo = new JLabel("Consulta de Funcionarios", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelFuncionario = new JLabel("Nome do funcionario", JLabel.LEFT);
        campoFuncionario = new JTextField();
        botaoPesquisar = new JButton("Pesquisar funcionario");
        botaoEditar = new JButton(("Editar funcionario"));
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir funcionario");
        botaoExcluir.setEnabled(false);
        model = new DefaultListModel();
        listaFuncionarios = new JList<>();
        listaFuncionarios.setModel(model);
        listaFuncionarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        labelTitulo.setBounds(20, 20, 660, 40);
        labelFuncionario.setBounds(150, 120, 400, 20);
        campoFuncionario.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40);
        listaFuncionarios.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40);
        botaoExcluir.setBounds(560, 400, 130, 40);

        add(labelTitulo);
        add(labelFuncionario);
        add(campoFuncionario);
        add(listaFuncionarios);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);

        setVisible(true);
    }

    private void criarEventos(){
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarFuncionario(campoFuncionario.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.funcionariosEditar(funcionarioAtual);
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletetarFuncionario();
            }
        });
        listaFuncionarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                funcionarioAtual = listaFuncionarios.getSelectedValue();
                if(funcionarioAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlDeletetarFuncionario() {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o funcionario "
                + funcionarioAtual .getNome() + "?", "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            //conexão
            Connection conexao;
            //instrucao SQL
            Statement instrucaoSQL;
            //resultados


            try{
                conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);

                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM cargos WHERE id = "+funcionarioAtual.getId()+"");

                JOptionPane.showMessageDialog(null, "Funcionario deletado com sucesso!");
                conexao.close();
                Navegador.inicio();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Funcionario");
                Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    private void sqlPesquisarFuncionario(String nome){
        //conexão
        Connection conexao;
        //instrucao SQL
        Statement instrucaoSQL;
        //resultados
        ResultSet result;


        try{
            conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);

            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = instrucaoSQL.executeQuery("SELECT * FROM funcionarios WHERE nome like '%"+nome+"%' ORDER BY nome ASC");

            model.clear();
            while(result.next()){
                Funcionario func = new Funcionario();
                func.setId(result.getInt("id"));
                func.setNome(result.getString("nome"));
                func.setSobrenome(result.getString("sobrenome"));
                func.setDataNascimento(result.getString("data_nascimento"));
                func.setEmail(result.getString("email"));
                if(result.getString("cargo") != null) func.setCargo(Integer.parseInt(result.getString("cargo")));
                func.setSalario(Double.parseDouble(result.getString("salario")));

                model.addElement(func);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar funcionarios.");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
