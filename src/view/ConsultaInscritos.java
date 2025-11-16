package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Inscricao;
import model.Professor;
import persistence.InscricaoRepository;
import persistence.ProfessorRepository;


import java.io.IOException;
import java.util.Queue;

public class ConsultaInscritos extends VBox {
    private TextField txtCodigoDisciplina;
    private TextArea txtAreaResultado;
    private InscricaoRepository inscricaoRepository;
    private ProfessorRepository professorRepository;

    public ConsultaInscritos() {
        setSpacing(10);
        setPadding(new Insets(20));

        inscricaoRepository = new InscricaoRepository();
        professorRepository = new ProfessorRepository();

        Label lblTitulo = new Label("Consulta de Inscritos por Disciplina");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox hboxBusca = new HBox(10);
        Label lblCodigoDisciplina = new Label("Codigo Disciplina:");
        txtCodigoDisciplina = new TextField();
        Button btnConsultar = new Button("Consultar");

        hboxBusca.getChildren().addAll(lblCodigoDisciplina, txtCodigoDisciplina, btnConsultar);

        Label lblResultado = new Label("Resultado (ordenado por pontuacao):");
        txtAreaResultado = new TextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setPrefHeight(400);

        btnConsultar.setOnAction(e -> consultar());

        getChildren().addAll(lblTitulo, hboxBusca, lblResultado, txtAreaResultado);
    }

    private void consultar() {
        try {
            String codigoDisciplina = txtCodigoDisciplina.getText().trim();

            if (codigoDisciplina.isEmpty()) {
                showError("Informe o codigo da disciplina");
                return;
            }

            ListaSimples inscricoes = inscricaoRepository.buscarInscricoesPorDisciplina(codigoDisciplina);

            if (inscricoes.size() == 0) {
                txtAreaResultado.setText("Nenhuma inscricao encontrada para esta disciplina");
                return;
            }

            ListaSimples professores = new ListaSimples();

            for (int i = 0; i < inscricoes.size(); i++) {
                Inscricao insc = (Inscricao) inscricoes.get(i);
                Queue fila = professorRepository.buscarPorCpfComFila(insc.getCpfProfessor());
                if (!fila.isEmpty()) {
                    Professor prof = (Professor) fila.dequeue();
                    professores.add(prof);
                }
            }

            QuickSort quickSort = new QuickSort();
            quickSort.sortByPontuacao(professores);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < professores.size(); i++) {
                Professor prof = (Professor) professores.get(i);
                sb.append("------------------------\n");
                sb.append("CPF: ").append(prof.getCpf()).append("\n");
                sb.append("Nome: ").append(prof.getNome()).append("\n");
                sb.append("Area Pretensao: ").append(prof.getAreaPretensao()).append("\n");
                sb.append("Pontuacao: ").append(prof.getPontuacao()).append("\n");
            }

            txtAreaResultado.setText(sb.toString());

        } catch (IOException e) {
            showError("Erro: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}