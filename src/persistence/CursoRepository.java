package persistence;

import model.Curso;
import estrutura.Fila;
import estrutura.Lista;

import java.io.*;

public class CursoRepository {

    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "cursos.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public CursoRepository() {
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

    public void save(String csvCurso) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        fw = new FileWriter(FILE_PATH, true);
        pw = new PrintWriter(fw);
        pw.write(csvCurso);
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
                Curso curso = new Curso();
                curso.setCodigo(vetLinha[0]);
                curso.setNome(vetLinha[1]);
                curso.setAreaConhecimento(vetLinha[2]);
                fila.insert(curso);
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
            Curso curso = new Curso();
            curso.setCodigo(vetLinha[0]);
            curso.setNome(vetLinha[1]);
            curso.setAreaConhecimento(vetLinha[2]);
            lista.addLast(curso);
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
            Curso curso = (Curso) lista.get(i);
            pw.write(curso.toString());
            pw.write("\r\n");
        }
        pw.flush();

        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }

}