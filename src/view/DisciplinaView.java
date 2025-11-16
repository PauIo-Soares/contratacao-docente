package view;

import controller.DisciplinaController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class DisciplinaView extends VBox {
    private TextField txtCodigo;
    private TextField txtNome;
    private TextField txtDiaSemana;
    private TextField txtHorarioInicial;
    private TextField txtQuantidadeHoras;
    private TextField txtCodigoCurso;
    private TextArea txtAreaResultado;
    private DisciplinaController controller;

    public DisciplinaView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label lblTitulo = new Label("CRUD Disciplina");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);

        Label lblCodigo = new Label("Codigo:");
        txtCodigo = new TextField();

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();

        Label lblDiaSemana = new Label("Dia Semana:");
        txtDiaSemana = new TextField();

        Label lblHorarioInicial = new Label("Horario Inicial:");
        txtHorarioInicial = new TextField();

        Label lblQuantidadeHoras = new Label("Quantidade Horas:");
        txtQuantidadeHoras = new TextField();

        Label lblCodigoCurso = new Label("Codigo Curso:");
        txtCodigoCurso = new TextField();

        gridForm.add(lblCodigo, 0, 0);
        gridForm.add(txtCodigo, 1, 0);
        gridForm.add(lblNome, 0, 1);
        gridForm.add(txtNome, 1, 1);
        gridForm.add(lblDiaSemana, 0, 2);
        gridForm.add(txtDiaSemana, 1, 2);
        gridForm.add(lblHorarioInicial, 0, 3);
        gridForm.add(txtHorarioInicial, 1, 3);
        gridForm.add(lblQuantidadeHoras, 0, 4);
        gridForm.add(txtQuantidadeHoras, 1, 4);
        gridForm.add(lblCodigoCurso, 0, 5);
        gridForm.add(txtCodigoCurso, 1, 5);

        HBox hboxButtons = new HBox(10);
        Button btnCadastrar = new Button("Cadastrar");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");
        Button btnRemover = new Button("Remover");

        hboxButtons.getChildren().addAll(btnCadastrar, btnBuscar, btnAtualizar, btnRemover);

        Label lblResultado = new Label("Resultado:");
        txtAreaResultado = new TextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setPrefHeight(200);

        controller = new DisciplinaController(txtCodigo, txtNome, txtDiaSemana,
                txtHorarioInicial, txtQuantidadeHoras,
                txtCodigoCurso, txtAreaResultado);

        btnCadastrar.setOnAction(controller);
        btnBuscar.setOnAction(controller);
        btnAtualizar.setOnAction(controller);
        btnRemover.setOnAction(controller);

        getChildren().addAll(lblTitulo, gridForm, hboxButtons, lblResultado, txtAreaResultado);
    }
}