package sistema.telas;

import jdk.nashorn.internal.scripts.JO;
import sistema.BancoDeDados;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncionariosEditar extends JPanel {
    Funcionario funcionarioAtual;
    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelSalario, labelCargo, labelEmail;
    JTextField campoNome, campoSobrenome, campoEmail;
    JFormattedTextField campoDataNascimento, campoSalario;
    JButton botaoGravar;
    JComboBox comboboxCargo;

    public FuncionariosEditar(Funcionario func){
        funcionarioAtual = func;
        criarComponentes();
        criarEventos();
    }

    private void criarComponentes() {
        setLayout(null);

        labelTitulo = new JLabel("Editar Funcionario "+funcionarioAtual.getNome()+" "+funcionarioAtual.getSobrenome(), JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelNome = new JLabel("Nome:", JLabel.LEFT);
        campoNome = new JTextField(funcionarioAtual.getNome());
        labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT);
        campoSobrenome = new JTextField(funcionarioAtual.getSobrenome());
        labelDataNascimento = new JLabel("Data de Nascimento:", JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField(funcionarioAtual.getDataNascimento());
        try{
            MaskFormatter dateMask = new MaskFormatter("####-##-##");
            dateMask.install(campoDataNascimento);
        }catch(ParseException ex){
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelEmail = new JLabel("E-mail:", JLabel.LEFT);
        campoEmail = new JTextField(funcionarioAtual.getEmail());
        labelCargo = new JLabel("Cargo:", JLabel.LEFT);
        comboboxCargo = new JComboBox<>();
        labelSalario = new JLabel("Salário:", JLabel.LEFT);
        DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        campoSalario = new JFormattedTextField(formatter);
        campoSalario.setValue(funcionarioAtual.getSalario());
        botaoGravar = new JButton("Editar");
        setVisible(true);

        labelTitulo.setBounds(20, 20, 660, 40);
        labelNome.setBounds(150, 80, 400, 40);
        campoNome.setBounds(150, 100, 400, 40);
        labelSobrenome.setBounds(150, 140, 400, 20);
        campoSobrenome.setBounds(150, 160, 400, 40);
        labelDataNascimento.setBounds(150, 200, 400, 20);
        campoDataNascimento.setBounds(150, 220, 400, 40);
        labelEmail.setBounds(150, 260, 400, 20);
        campoEmail.setBounds(150, 280, 400, 40);
        labelCargo.setBounds(150, 320, 400, 20);
        comboboxCargo.setBounds(150, 340, 400, 40);
        labelSalario.setBounds(150, 380, 400, 20);
        campoSalario.setBounds(150, 400, 400, 40);
        botaoGravar.setBounds(560, 400, 130, 40);

        add(labelTitulo);
        add(labelNome);
        add(campoNome);
        add(labelSobrenome);
        add(campoSobrenome);
        add(labelDataNascimento);
        add(campoDataNascimento);
        add(labelEmail);
        add(campoEmail);
        add(labelCargo);
        add(comboboxCargo);
        add(labelSalario);
        add(campoSalario);
        add(botaoGravar);

        sqlCarregarCargos(funcionarioAtual.getCargo());

        setVisible(true);
    }

    private void criarEventos() {
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionarioAtual.setNome(campoNome.getText());
                funcionarioAtual.setSobrenome(campoSobrenome.getText());
                funcionarioAtual.setDataNascimento(campoDataNascimento.getText());
                funcionarioAtual.setEmail(campoEmail.getText());
                Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
                if(cargoSelecionado != null)funcionarioAtual.setCargo(cargoSelecionado.getId());
                funcionarioAtual.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));


                sqlAtualizarFuncionario();
            }
        });
    }

    private void sqlAtualizarFuncionario() {
        if(campoNome.getText().length() <= 3){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente.");
            return;
        }
        //validando sobrenome
        if(campoSobrenome.getText().length() <= 3){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente");
            return;
        }
        //validando sobrenome
        if(Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o salario corretamente.");
            return;
        }
        //validando email
        Boolean emailValidado = false;
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(campoEmail.getText());
        emailValidado = m.matches();

        if(!emailValidado){
            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente.");
            return;
        }

        //conexão
        Connection conexao;
        //instrucao SQL
        PreparedStatement instrucaoSQL;
        //resultados
        ResultSet results;

        try{
            conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);

            String template = "UPDATE funcionarios set nome=?, sobrenome=?, data_nascimento=?, email=?, cargo=?, salario=?";
            template = template + "WHERE id ="+funcionarioAtual.getId();
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, campoNome.getText());
            instrucaoSQL.setString(2, campoSobrenome.getText());
            instrucaoSQL.setString(3, campoDataNascimento.getText());
            instrucaoSQL.setString(4, campoEmail.getText());
            Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
            if(cargoSelecionado != null){
                instrucaoSQL.setInt(5, cargoSelecionado.getId());
            }else{
                instrucaoSQL.setNull(5, Types.INTEGER);
            }
            instrucaoSQL.setString(6, campoSalario.getText().replace(",","."));
            instrucaoSQL.executeUpdate();

            JOptionPane.showMessageDialog(null, "Funcionario atualizado com sucesso");

            conexao.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Cargo");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void sqlCarregarCargos(int cargoAtual){
        //conexão
        Connection conexao;
        //instrucao SQL
        Statement instrucaoSQL;
        //resultados
        ResultSet results;

        try{
            conexao = DriverManager.getConnection(BancoDeDados.servidor, BancoDeDados.usuario, BancoDeDados.senha);

            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            results = instrucaoSQL.executeQuery("SELECT * FROM cargos ORDER BY nome asc");
            comboboxCargo.removeAll();

            while(results.next()){
                Cargo cargo = new Cargo();
                cargo.setId(results.getInt("id"));
                cargo.setNome(results.getString("nome"));
                if(cargoAtual == cargo.getId()) comboboxCargo.setSelectedItem(cargo);
                comboboxCargo.addItem(cargo);
            }
            comboboxCargo.updateUI();
            conexao.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Cargo");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
