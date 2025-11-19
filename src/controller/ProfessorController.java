package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import estrutura.Fila;
import estrutura.Lista;
import model.Professor;
import persistence.ProfessorRepository;

import java.io.IOException;

public class ProfessorController implements EventHandler<ActionEvent> {
    private TextField txtCpf;
    private TextField txtNome;
    private TextField txtAreaPretensao;
    private TextField txtPontuacao;
    private TextArea txtAreaResultado;
    private ProfessorRepository repository;

    public ProfessorController(TextField txtCpf, TextField txtNome, TextField txtAreaPretensao, TextField txtPontuacao, TextArea txtAreaResultado) {
        this.txtCpf = txtCpf;
        this.txtNome = txtNome;
        this.txtAreaPretensao = txtAreaPretensao;
        this.txtPontuacao = txtPontuacao;
        this.txtAreaResultado = txtAreaResultado;
        this.repository = new ProfessorRepository();
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

    private void cadastrar() throws IOException {

        String cpf = txtCpf.getText().trim();
        String nome = txtNome.getText().trim();
        String area = txtAreaPretensao.getText().trim();
        String pontuacao = txtPontuacao.getText().trim();

        if (cpf.isEmpty() || nome.isEmpty() || area.isEmpty() || pontuacao.isEmpty()) {
            showError("Todos os campos devem ser preenchidos");
            return;
        }

        Professor professor = new Professor(cpf, nome, area, pontuacao);
        repository.save(professor.toString());
        txtAreaResultado.setText("Professor cadastrado com sucesso!");
        limparCampos();
    }

    private void buscar() throws Exception {

        String cpf = txtCpf.getText().trim();

        if (cpf.isEmpty()) {
            showError("Informe o CPF do professor");
            return;
        }

        Fila fila = repository.buscarPorCpfComFila(cpf);

        if (fila.isEmpty()) {
            txtAreaResultado.setText("Professor nao encontrado");
        } else {
            Professor professor = (Professor) fila.remove();
            StringBuilder sb = new StringBuilder();
            sb.append("CPF: ").append(professor.getCpf()).append("\n");
            sb.append("Nome: ").append(professor.getNome()).append("\n");
            sb.append("Area Pretensao: ").append(professor.getAreaPretensao()).append("\n");
            sb.append("Pontuacao: ").append(professor.getPontuacao());
            txtAreaResultado.setText(sb.toString());
        }
        limparCampos();
    }

    private void atualizar() throws Exception {

        String cpf = txtCpf.getText().trim();
        String nome = txtNome.getText().trim();
        String area = txtAreaPretensao.getText().trim();
        String pontuacao = txtPontuacao.getText().trim();

        if (cpf.isEmpty() || nome.isEmpty() || area.isEmpty() || pontuacao.isEmpty()) {
            showError("Todos os campos devem ser preenchidos");
            return;
        }

        Lista lista = repository.buscarTodosComLista();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Professor p = (Professor) lista.get(i);
            if (p.getCpf().equals(cpf)) {
                p.setNome(nome);
                p.setAreaPretensao(area);
                p.setPontuacao(pontuacao);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            repository.saveAll(lista);
            txtAreaResultado.setText("Professor atualizado com sucesso!");
        } else {
            txtAreaResultado.setText("Professor nao encontrado");
        }
        limparCampos();
    }

    private void remover() throws Exception {

        String cpf = txtCpf.getText().trim();

        if (cpf.isEmpty()) {
            showError("Informe o CPF do professor");
            return;
        }

        Lista lista = repository.buscarTodosComLista();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Professor p = (Professor) lista.get(i);
            if (p.getCpf().equals(cpf)) {
                lista.remove(i);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            repository.saveAll(lista);
            txtAreaResultado.setText("Professor removido com sucesso!");
        } else {
            txtAreaResultado.setText("Professor nao encontrado");
        }
        limparCampos();
    }

    private void limparCampos() {
        txtCpf.clear();
        txtNome.clear();
        txtAreaPretensao.clear();
        txtPontuacao.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}