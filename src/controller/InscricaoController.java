package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Inscricao;
import persistence.InscricaoRepository;
import persistence.ProfessorRepository;

import java.io.IOException;
import java.util.Queue;

public class InscricaoController implements EventHandler<ActionEvent> {
    private TextField txtCodigoProcesso;
    private TextField txtCpfProfessor;
    private TextField txtCodigoDisciplina;
    private TextArea txtAreaResultado;
    private InscricaoRepository repository;
    private ProfessorRepository professorRepository;

    public InscricaoController(TextField txtCodigoProcesso, TextField txtCpfProfessor,
                               TextField txtCodigoDisciplina, TextArea txtAreaResultado) {
        this.txtCodigoProcesso = txtCodigoProcesso;
        this.txtCpfProfessor = txtCpfProfessor;
        this.txtCodigoDisciplina = txtCodigoDisciplina;
        this.txtAreaResultado = txtAreaResultado;
        this.repository = new InscricaoRepository();
        this.professorRepository = new ProfessorRepository();
    }

    @Override
    public void handle(ActionEvent event) {
        Button source = (Button) event.getSource();
        String buttonText = source.getText();

        try {
            if (buttonText.equals("Cadastrar")) {
                cadastrar();
            } else if (buttonText.equals("Buscar")) {
                buscar();
            } else if (buttonText.equals("Atualizar")) {
                atualizar();
            } else if (buttonText.equals("Remover")) {
                remover();
            }
        } catch (Exception e) {
            showError("Erro: " + e.getMessage());
        }
    }

    private void cadastrar() {
        try {
            String codigoProcesso = txtCodigoProcesso.getText().trim();
            String cpfProfessor = txtCpfProfessor.getText().trim();
            String codigoDisciplina = txtCodigoDisciplina.getText().trim();

            if (codigoProcesso.isEmpty() || cpfProfessor.isEmpty() || codigoDisciplina.isEmpty()) {
                showError("Todos os campos devem ser preenchidos");
                return;
            }

            if (!professorRepository.professorExists(cpfProfessor)) {
                showError("Professor nao cadastrado no sistema");
                return;
            }

            Inscricao inscricao = new Inscricao(codigoProcesso, cpfProfessor, codigoDisciplina);
            repository.save(inscricao.toString());

            txtAreaResultado.setText("Inscricao cadastrada com sucesso!");
            limparCampos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buscar() {
        try {
            String codigoProcesso = txtCodigoProcesso.getText().trim();

            if (codigoProcesso.isEmpty()) {
                showError("Informe o codigo do processo");
                return;
            }

            Queue fila = repository.buscarPorCodigoProcessoComFila(codigoProcesso);

            if (fila.isEmpty()) {
                txtAreaResultado.setText("Inscricao nao encontrada");
            } else {
                Inscricao inscricao = (Inscricao) fila.dequeue();
                StringBuilder sb = new StringBuilder();
                sb.append("Codigo Processo: ").append(inscricao.getCodigoProcesso()).append("\n");
                sb.append("CPF Professor: ").append(inscricao.getCpfProfessor()).append("\n");
                sb.append("Codigo Disciplina: ").append(inscricao.getCodigoDisciplina());
                txtAreaResultado.setText(sb.toString());
            }

            limparCampos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void atualizar() {
        try {
            String codigoProcesso = txtCodigoProcesso.getText().trim();
            String cpfProfessor = txtCpfProfessor.getText().trim();
            String codigoDisciplina = txtCodigoDisciplina.getText().trim();

            if (codigoProcesso.isEmpty() || cpfProfessor.isEmpty() || codigoDisciplina.isEmpty()) {
                showError("Todos os campos devem ser preenchidos");
                return;
            }

            if (!professorRepository.professorExists(cpfProfessor)) {
                showError("Professor nao cadastrado no sistema");
                return;
            }

            ListaSimples lista = repository.loadAllToList();
            boolean encontrado = false;

            for (int i = 0; i < lista.size(); i++) {
                Inscricao insc = (Inscricao) lista.get(i);
                if (insc.getCodigoProcesso().equals(codigoProcesso)) {
                    insc.setCpfProfessor(cpfProfessor);
                    insc.setCodigoDisciplina(codigoDisciplina);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                repository.saveAll(lista);
                txtAreaResultado.setText("Inscricao atualizada com sucesso!");
            } else {
                txtAreaResultado.setText("Inscricao nao encontrada");
            }

            limparCampos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void remover() {
        try {
            String codigoProcesso = txtCodigoProcesso.getText().trim();

            if (codigoProcesso.isEmpty()) {
                showError("Informe o codigo do processo");
                return;
            }

            ListaSimples lista = repository.loadAllToList();
            boolean encontrado = false;

            for (int i = 0; i < lista.size(); i++) {
                Inscricao insc = (Inscricao) lista.get(i);
                if (insc.getCodigoProcesso().equals(codigoProcesso)) {
                    lista.remove(i);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                repository.saveAll(lista);
                txtAreaResultado.setText("Inscricao removida com sucesso!");
            } else {
                txtAreaResultado.setText("Inscricao nao encontrada");
            }

            limparCampos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void limparCampos() {
        txtCodigoProcesso.clear();
        txtCpfProfessor.clear();
        txtCodigoDisciplina.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}