package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Curso;
import estrutura.Fila;
import estrutura.Lista;
import persistence.CursoRepository;

import java.io.IOException;

public class CursoController implements EventHandler<ActionEvent> {
    private TextField txtCodigo;
    private TextField txtNome;
    private TextField txtAreaConhecimento;
    private TextArea txtAreaResultado;
    private CursoRepository repository;

    public CursoController(TextField txtCodigo, TextField txtNome, TextField txtAreaConhecimento, TextArea txtAreaResultado) {
        this.txtCodigo = txtCodigo;
        this.txtNome = txtNome;
        this.txtAreaConhecimento = txtAreaConhecimento;
        this.txtAreaResultado = txtAreaResultado;
        this.repository = new CursoRepository();
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
        String codigo = txtCodigo.getText().trim();
        String nome = txtNome.getText().trim();
        String area = txtAreaConhecimento.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty() || area.isEmpty()) {
            showError("Todos os campos devem ser preenchidos");
            return;
        }

        Curso curso = new Curso(codigo, nome, area);
        repository.save(curso.toString());
        txtAreaResultado.setText("Curso cadastrado com sucesso!");
        limparCampos();
    }

    private void buscar() throws Exception {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            showError("Informe o codigo do curso");
            return;
        }

        Fila fila = repository.buscarPorCodigoComFila(codigo);

        if (fila.isEmpty()) {
            txtAreaResultado.setText("Curso nao encontrado");
        } else {
            Curso curso = (Curso) fila.remove();
            StringBuilder sb = new StringBuilder();
            sb.append("Codigo: ").append(curso.getCodigo()).append("\n");
            sb.append("Nome: ").append(curso.getNome()).append("\n");
            sb.append("Area Conhecimento: ").append(curso.getAreaConhecimento());
            txtAreaResultado.setText(sb.toString());
        }
        limparCampos();
    }

    private void atualizar() throws Exception {
        String codigo = txtCodigo.getText().trim();
        String nome = txtNome.getText().trim();
        String area = txtAreaConhecimento.getText().trim();

        if (codigo.isEmpty() || nome.isEmpty() || area.isEmpty()) {
            showError("Todos os campos devem ser preenchidos");
            return;
        }

        Lista lista = repository.buscarTodosComLista();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Curso c = (Curso) lista.get(i);
            if (c.getCodigo().equals(codigo)) {
                c.setNome(nome);
                c.setAreaConhecimento(area);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            repository.saveAll(lista);
            txtAreaResultado.setText("Curso atualizado com sucesso!");
        } else {
            txtAreaResultado.setText("Curso nao encontrado");
        }
        limparCampos();
    }

    private void remover() throws Exception {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            showError("Informe o codigo do curso");
            return;
        }

        Lista lista = repository.buscarTodosComLista();
        boolean encontrado = false;

        for (int i = 0; i < lista.size(); i++) {
            Curso c = (Curso) lista.get(i);
            if (c.getCodigo().equals(codigo)) {
                lista.remove(i);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            repository.saveAll(lista);
            txtAreaResultado.setText("Curso removido com sucesso!");
        } else {
            txtAreaResultado.setText("Curso nao encontrado");
        }

        limparCampos();
    }

    private void limparCampos() {
        txtCodigo.clear();
        txtNome.clear();
        txtAreaConhecimento.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}