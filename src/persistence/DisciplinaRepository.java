package persistence;

import model.Disciplina;
import estrutura.Fila;
import estrutura.Lista;

import java.io.*;

public class DisciplinaRepository {
    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "disciplinas.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public DisciplinaRepository() {
        criarCsv();
    }

    private void criarCsv() {
        try {
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdir();
            }

            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar diretorio ou arquivo: " + e.getMessage());
        }
    }

    public void save(String csvDisciplina) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        fw = new FileWriter(FILE_PATH, true);
        pw = new PrintWriter(fw);
        pw.write(csvDisciplina);
        pw.write("\r\n");
        pw.flush();

        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }

    public Fila buscarPorCodigoComFila(String codigo) throws IOException {
        Fila fila = new Fila();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return fila;
        }

        FileReader fr = null;
        BufferedReader br = null;

        fr = new FileReader(file);
        br = new BufferedReader(fr);
        String linha = br.readLine();

        while (linha != null) {
            String[] vetLinha = linha.split(";");
            if (vetLinha[0].equals(codigo)) {
                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(vetLinha[0]);
                disciplina.setNome(vetLinha[1]);
                disciplina.setDiaSemana(vetLinha[2]);
                disciplina.setHorarioInicial(vetLinha[3]);
                disciplina.setQuantidadeHorasDiarias(vetLinha[4]);
                disciplina.setCodigoCurso(vetLinha[5]);
                fila.insert(disciplina);
                break;
            }
            linha = br.readLine();
        }
        if (br != null) br.close();
        if (fr != null) fr.close();

        return fila;
    }

    public Lista buscarTodosComLista() throws Exception {
        Lista lista = new Lista();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return lista;
        }

        FileReader fr = null;
        BufferedReader br = null;

        fr = new FileReader(file);
        br = new BufferedReader(fr);
        String linha = br.readLine();

        while (linha != null) {
            String[] vetLinha = linha.split(";");
            Disciplina disciplina = new Disciplina();
            disciplina.setCodigo(vetLinha[0]);
            disciplina.setNome(vetLinha[1]);
            disciplina.setDiaSemana(vetLinha[2]);
            disciplina.setHorarioInicial(vetLinha[3]);
            disciplina.setQuantidadeHorasDiarias(vetLinha[4]);
            disciplina.setCodigoCurso(vetLinha[5]);
            lista.addLast(disciplina);
            linha = br.readLine();
        }
        if (br != null) br.close();
        if (fr != null) fr.close();

        return lista;
    }

    public void saveAll(Lista lista) throws Exception {
        FileWriter fw = null;
        PrintWriter pw = null;

        fw = new FileWriter(FILE_PATH, false);
        pw = new PrintWriter(fw);

        for (int i = 0; i < lista.size(); i++) {
            Disciplina disciplina = (Disciplina) lista.get(i);
            pw.write(disciplina.toString());
            pw.write("\r\n");
        }
        pw.flush();

        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }
}