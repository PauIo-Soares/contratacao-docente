package view;

import controller.ProfessorController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfessorView extends VBox {
    private TextField txtCpf;
    private TextField txtNome;
    private TextField txtAreaPretensao;
    private TextField txtPontuacao;
    private TextArea txtAreaResultado;
    private ProfessorController controller;

    public ProfessorView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label lblTitulo = new Label("CRUD Professor");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);

        Label lblCpf = new Label("CPF:");
        txtCpf = new TextField();

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();

        Label lblAreaPretensao = new Label("Area Pretensao:");
        txtAreaPretensao = new TextField();

        Label lblPontuacao = new Label("Pontuacao:");
        txtPontuacao = new TextField();

        gridForm.add(lblCpf, 0, 0);
        gridForm.add(txtCpf, 1, 0);
        gridForm.add(lblNome, 0, 1);
        gridForm.add(txtNome, 1, 1);
        gridForm.add(lblAreaPretensao, 0, 2);
        gridForm.add(txtAreaPretensao, 1, 2);
        gridForm.add(lblPontuacao, 0, 3);
        gridForm.add(txtPontuacao, 1, 3);

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

        controller = new ProfessorController(txtCpf, txtNome, txtAreaPretensao,
                txtPontuacao, txtAreaResultado);

        btnCadastrar.setOnAction(controller);
        btnBuscar.setOnAction(controller);
        btnAtualizar.setOnAction(controller);
        btnRemover.setOnAction(controller);

        getChildren().addAll(lblTitulo, gridForm, hboxButtons, lblResultado, txtAreaResultado);
    }
}