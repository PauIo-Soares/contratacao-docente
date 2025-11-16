package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Curso;
import model.Disciplina;
import persistence.CursoRepository;
import persistence.DisciplinaRepository;
import persistence.InscricaoRepository;

import java.io.IOException;
import java.util.Queue;

public class ConsultaProcessosAbertos extends VBox {
    private TextArea txtAreaResultado;
    private InscricaoRepository inscricaoRepository;
    private DisciplinaRepository disciplinaRepository;
    private CursoRepository cursoRepository;

    public ConsultaProcessosAbertos() {
        setSpacing(10);
        setPadding(new Insets(20));

        inscricaoRepository = new InscricaoRepository();
        disciplinaRepository = new DisciplinaRepository();
        cursoRepository = new CursoRepository();

        Label lblTitulo = new Label("Consulta de Processos Abertos");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnConsultar = new Button("Consultar Processos Abertos");
        btnConsultar.setOnAction(e -> consultar());

        Label lblResultado = new Label("Resultado:");
        txtAreaResultado = new TextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setPrefHeight(400);

        getChildren().addAll(lblTitulo, btnConsultar, lblResultado, txtAreaResultado);
    }

    private void consultar() {
        try {
            HashTable hashTable = inscricaoRepository.getProcessosAbertosHashTable();

            if (hashTable.size() == 0) {
                txtAreaResultado.setText("Nenhum processo aberto encontrado");
                return;
            }

            ListaSimples codigosDisciplinas = hashTable.getKeys();

            if (codigosDisciplinas.size() == 0) {
                txtAreaResultado.setText("Nenhum processo aberto encontrado");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < codigosDisciplinas.size(); i++) {
                String codigoDisciplina = (String) codigosDisciplinas.get(i);

                Queue filaDisciplina = disciplinaRepository.buscarPorCodigoComFila(codigoDisciplina);

                if (!filaDisciplina.isEmpty()) {
                    Disciplina disciplina = (Disciplina) filaDisciplina.dequeue();

                    Queue filaCurso = cursoRepository.buscarPorCodigoComFila(disciplina.getCodigoCurso());

                    sb.append("========================\n");
                    sb.append("DISCIPLINA:\n");
                    sb.append("Codigo: ").append(disciplina.getCodigo()).append("\n");
                    sb.append("Nome: ").append(disciplina.getNome()).append("\n");
                    sb.append("Dia Semana: ").append(disciplina.getDiaSemana()).append("\n");
                    sb.append("Horario: ").append(disciplina.getHorarioInicial()).append("\n");
                    sb.append("Horas: ").append(disciplina.getQuantidadeHorasDiaria()).append("\n");

                    if (!filaCurso.isEmpty()) {
                        Curso curso = (Curso) filaCurso.dequeue();
                        sb.append("\nCURSO:\n");
                        sb.append("Codigo: ").append(curso.getCodigo()).append("\n");
                        sb.append("Nome: ").append(curso.getNome()).append("\n");
                        sb.append("Area: ").append(curso.getAreaConhecimento()).append("\n");
                    }

                    sb.append("\n");
                }
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