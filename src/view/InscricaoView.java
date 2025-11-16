package view;

import controller.InscricaoController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InscricaoView extends VBox {
    private TextField txtCodigoProcesso;
    private TextField txtCpfProfessor;
    private TextField txtCodigoDisciplina;
    private TextArea txtAreaResultado;
    private InscricaoController controller;

    public InscricaoView() {
        setSpacing(10);
        setPadding(new Insets(20));

        Label lblTitulo = new Label("CRUD Inscricao");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(10);

        Label lblCodigoProcesso = new Label("Codigo Processo:");
        txtCodigoProcesso = new TextField();

        Label lblCpfProfessor = new Label("CPF Professor:");
        txtCpfProfessor = new TextField();

        Label lblCodigoDisciplina = new Label("Codigo Disciplina:");
        txtCodigoDisciplina = new TextField();

        gridForm.add(lblCodigoProcesso, 0, 0);
        gridForm.add(txtCodigoProcesso, 1, 0);
        gridForm.add(lblCpfProfessor, 0, 1);
        gridForm.add(txtCpfProfessor, 1, 1);
        gridForm.add(lblCodigoDisciplina, 0, 2);
        gridForm.add(txtCodigoDisciplina, 1, 2);

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

        controller = new InscricaoController(txtCodigoProcesso, txtCpfProfessor,
                txtCodigoDisciplina, txtAreaResultado);

        btnCadastrar.setOnAction(controller);
        btnBuscar.setOnAction(controller);
        btnAtualizar.setOnAction(controller);
        btnRemover.setOnAction(controller);

        getChildren().addAll(lblTitulo, gridForm, hboxButtons, lblResultado, txtAreaResultado);
    }
}