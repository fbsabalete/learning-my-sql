package sistema;

import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.telas.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navegador {

    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
    private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar,
    miCargosConsultar;
    private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalarios;

    public static void login(){
        Sistema.tela = new Login();
        Sistema.frame.setTitle("Funcionarios Compania Bonita");
        Navegador.atualizarTela();
    }

    public static void inicio(){
        Sistema.tela = new Inicio();
        Sistema.frame.setTitle("Funcionários Compania Bonita");
        Navegador.atualizarTela();
    }

    public static void funcionariosCadastrar(){
        Sistema.tela = new FuncionariosInserir();
        Sistema.frame.setTitle("Funcionários Compania Bonita - Cadastrar funcionarios");
        Navegador.atualizarTela();
    }

    public static void funcionariosConsultar(){
        Sistema.tela = new FuncionariosConsultar();
        Sistema.frame.setTitle("Funcionários Compania Bonita - Consultar funcionários");
        Navegador.atualizarTela();
    }

    public static void funcionariosEditar(Funcionario func){
        Sistema.tela = new FuncionariosEditar(func);
        Sistema.frame.setTitle("Funcionarios Compania Bonita - Editar cargos");
        Navegador.atualizarTela();
    }

    public static void cargosCadastrar(){
        Sistema.tela = new CargosInserir();
        Sistema.frame.setTitle("Funcionários Compania Bonita - Cadastrar cargos");
    }

    public static void cargosConsultar(){
        Sistema.tela = new CargosConsultar();
        Sistema.frame.setTitle("Funcionarios Compania Bonita - Consultar cargos");
        Navegador.atualizarTela();
    }

    public static void cargosEditar(Cargo cargo){
        Sistema.tela = new CargosEditar(cargo);
        Sistema.frame.setTitle("Funcionarios Compania Bonita - Editar cargos");
        Navegador.atualizarTela();
    }

    public static void atualizarTela(){
        Sistema.frame.getContentPane().removeAll();
        Sistema.tela.setVisible(true);
        Sistema.frame.add(Sistema.tela);

        Sistema.frame.setVisible(true);
    }

    private static void construirMenu(){
        if(!menuConstruido){
            menuConstruido = true;

            menuBar = new JMenuBar();

            //menu Arquivo:
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);

            //menu Funcionarios:
            menuFuncionarios = new JMenu("Funcionários");
            menuBar.add(menuFuncionarios);
            miFuncionariosCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionariosCadastrar);
            miFuncionariosConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionariosConsultar);

            //menu Cargos:
            menuCargos = new JMenu("Cargos");
            menuBar.add(menuCargos);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargos.add(miCargosCadastrar);
            miCargosConsultar = new JMenuItem("Consultar");
            menuCargos.add(miCargosConsultar);

            //menu Relatórios
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatoriosCargos = new JMenuItem("Funcionários por cargos");
            menuRelatorios.add(miRelatoriosCargos);
            miRelatoriosSalarios = new JMenuItem("Salários dos funcionários");
            menuRelatorios.add(miRelatoriosSalarios);

            criarEventosMenu();
        }

    }

    public static void habilitaMenu() {
        if (!menuConstruido) construirMenu();
        if (!menuHabilitado) {
            menuHabilitado = true;
            Sistema.frame.setJMenuBar(menuBar);
        }
    }

    public static void desabilitaMenu(){
        if(menuHabilitado){
            menuHabilitado = false;
            Sistema.frame.setJMenuBar(null);
        }
    }

    public static void criarEventosMenu(){
        miSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Funcionario
        miFuncionariosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosCadastrar();
            }
        });
        miFuncionariosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionariosConsultar();
            }
        });
        //Cargos
        miCargosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosCadastrar();
            }
        });
        miCargosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargosConsultar();
            }
        });

        miRelatoriosCargos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        miRelatoriosSalarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    //pagina 32 editar cargo
}
