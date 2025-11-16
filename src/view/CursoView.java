package view;

import controller.CursoController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CursoView extends VBox {
    private TextField txtCodigo;
    private TextField txtNome;
    private TextField txtAreaConhecimento;
    private TextArea txtAreaResultado;
    private CursoController controller;

    public CursoView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label lblTitulo = new Label("CRUD Curso");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);

        Label lblCodigo = new Label("Codigo:");
        txtCodigo = new TextField();

        Label lblNome = new Label("Nome:");
        txtNome = new TextField();

        Label lblAreaConhecimento = new Label("Area Conhecimento:");
        txtAreaConhecimento = new TextField();

        gridForm.add(lblCodigo, 0, 0);
        gridForm.add(txtCodigo, 1, 0);
        gridForm.add(lblNome, 0, 1);
        gridForm.add(txtNome, 1, 1);
        gridForm.add(lblAreaConhecimento, 0, 2);
        gridForm.add(txtAreaConhecimento, 1, 2);

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

        controller = new CursoController(txtCodigo, txtNome, txtAreaConhecimento, txtAreaResultado);

        btnCadastrar.setOnAction(controller);
        btnBuscar.setOnAction(controller);
        btnAtualizar.setOnAction(controller);
        btnRemover.setOnAction(controller);

        getChildren().addAll(lblTitulo, gridForm, hboxButtons, lblResultado, txtAreaResultado);
    }
}