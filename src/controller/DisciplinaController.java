package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Disciplina;
import model.Inscricao;
import persistence.DisciplinaRepository;
import persistence.InscricaoRepository;

import java.io.IOException;
import java.util.Queue;

public class DisciplinaController implements EventHandler<ActionEvent> {
    private TextField txtCodigo;
    private TextField txtNome;
    private TextField txtDiaSemana;
    private TextField txtHorarioInicial;
    private TextField txtQuantidadeHoras;
    private TextField txtCodigoCurso;
    private TextArea txtAreaResultado;
    private DisciplinaRepository repository;
    private InscricaoRepository inscricaoRepository;

    public DisciplinaController(TextField txtCodigo, TextField txtNome, TextField txtDiaSemana,
                                TextField txtHorarioInicial, TextField txtQuantidadeHoras,
                                TextField txtCodigoCurso, TextArea txtAreaResultado) {
        this.txtCodigo = txtCodigo;
        this.txtNome = txtNome;
        this.txtDiaSemana = txtDiaSemana;
        this.txtHorarioInicial = txtHorarioInicial;
        this.txtQuantidadeHoras = txtQuantidadeHoras;
        this.txtCodigoCurso = txtCodigoCurso;
        this.txtAreaResultado = txtAreaResultado;
        this.repository = new DisciplinaRepository();
        this.inscricaoRepository = new InscricaoRepository();
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
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            String diaSemana = txtDiaSemana.getText().trim();
            String horarioInicial = txtHorarioInicial.getText().trim();
            String quantidadeHoras = txtQuantidadeHoras.getText().trim();
            String codigoCurso = txtCodigoCurso.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty() || diaSemana.isEmpty() ||
                    horarioInicial.isEmpty() || quantidadeHoras.isEmpty() || codigoCurso.isEmpty()) {
                showError("Todos os campos devem ser preenchidos");
                return;
            }

            int qtdHoras = Integer.parseInt(quantidadeHoras);

            Disciplina disciplina = new Disciplina(codigo, nome, diaSemana,
                    horarioInicial, qtdHoras, codigoCurso);
            repository.save(disciplina.toString());

            txtAreaResultado.setText("Disciplina cadastrada com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            showError("Quantidade de horas deve ser um numero");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void buscar() {
        try {
            String codigo = txtCodigo.getText().trim();

            if (codigo.isEmpty()) {
                showError("Informe o codigo da disciplina");
                return;
            }

            Queue fila = repository.buscarPorCodigoComFila(codigo);

            if (fila.isEmpty()) {
                txtAreaResultado.setText("Disciplina nao encontrada");
            } else {
                Disciplina disciplina = (Disciplina) fila.dequeue();
                StringBuilder sb = new StringBuilder();
                sb.append("Codigo: ").append(disciplina.getCodigo()).append("\n");
                sb.append("Nome: ").append(disciplina.getNome()).append("\n");
                sb.append("Dia Semana: ").append(disciplina.getDiaSemana()).append("\n");
                sb.append("Horario Inicial: ").append(disciplina.getHorarioInicial()).append("\n");
                sb.append("Quantidade Horas: ").append(disciplina.getQuantidadeHorasDiaria()).append("\n");
                sb.append("Codigo Curso: ").append(disciplina.getCodigoCurso());
                txtAreaResultado.setText(sb.toString());
            }

            limparCampos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void atualizar() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            String diaSemana = txtDiaSemana.getText().trim();
            String horarioInicial = txtHorarioInicial.getText().trim();
            String quantidadeHoras = txtQuantidadeHoras.getText().trim();
            String codigoCurso = txtCodigoCurso.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty() || diaSemana.isEmpty() ||
                    horarioInicial.isEmpty() || quantidadeHoras.isEmpty() || codigoCurso.isEmpty()) {
                showError("Todos os campos devem ser preenchidos");
                return;
            }

            int qtdHoras = Integer.parseInt(quantidadeHoras);

            ListaSimples lista = repository.loadAllToList();
            boolean encontrado = false;

            for (int i = 0; i < lista.size(); i++) {
                Disciplina d = (Disciplina) lista.get(i);
                if (d.getCodigo().equals(codigo)) {
                    d.setNome(nome);
                    d.setDiaSemana(diaSemana);
                    d.setHorarioInicial(horarioInicial);
                    d.setQuantidadeHorasDiaria(qtdHoras);
                    d.setCodigoCurso(codigoCurso);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                repository.saveAll(lista);
                txtAreaResultado.setText("Disciplina atualizada com sucesso!");
            } else {
                txtAreaResultado.setText("Disciplina nao encontrada");
            }

            limparCampos();
        } catch (NumberFormatException e) {
            showError("Quantidade de horas deve ser um numero");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void remover() {
        try {
            String codigo = txtCodigo.getText().trim();

            if (codigo.isEmpty()) {
                showError("Informe o codigo da disciplina");
                return;
            }

            ListaSimples lista = repository.loadAllToList();
            boolean encontrado = false;

            for (int i = 0; i < lista.size(); i++) {
                Disciplina d = (Disciplina) lista.get(i);
                if (d.getCodigo().equals(codigo)) {
                    lista.remove(i);
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                repository.saveAll(lista);

                ListaSimples inscricoes = inscricaoRepository.loadAllToList();
                for (int i = inscricoes.size() - 1; i >= 0; i--) {
                    Inscricao insc = (Inscricao) inscricoes.get(i);
                    if (insc.getCodigoDisciplina().equals(codigo)) {
                        inscricoes.remove(i);
                    }
                }
                inscricaoRepository.saveAll(inscricoes);

                txtAreaResultado.setText("Disciplina e inscricoes removidas com sucesso!");
            } else {
                txtAreaResultado.setText("Disciplina nao encontrada");
            }

            limparCampos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void limparCampos() {
        txtCodigo.clear();
        txtNome.clear();
        txtDiaSemana.clear();
        txtHorarioInicial.clear();
        txtQuantidadeHoras.clear();
        txtCodigoCurso.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}