package persistence;

import model.Curso;

import java.io.*;
import java.util.Queue;

public class CursoRepository {

    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "cursos.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public CursoRepository() {
        createDirectoryAndFile();
    }

    private void createDirectoryAndFile() {

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

        try {
            fw = new FileWriter(FILE_PATH, true);
            pw = new PrintWriter(fw);
            pw.write(csvCurso);
            pw.write("\r\n");
            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }

    public Queue buscarPorCodigoComFila(String codigo) throws IOException {
        Queue fila = new Queue();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return fila;
        }

        FileReader fr = null;
        BufferedReader br = null;

        try {
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
                    fila.enqueue(curso);
                    break;
                }
                linha = br.readLine();
            }
        } finally {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }

        return fila;
    }

    public ListaSimples loadAllToList() throws IOException {
        ListaSimples lista = new ListaSimples();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return lista;
        }

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String linha = br.readLine();

            while (linha != null) {
                String[] vetLinha = linha.split(";");
                Curso curso = new Curso();
                curso.setCodigo(vetLinha[0]);
                curso.setNome(vetLinha[1]);
                curso.setAreaConhecimento(vetLinha[2]);
                lista.add(curso);
                linha = br.readLine();
            }
        } finally {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }

        return lista;

    }

    public void saveAll(ListaSimples lista) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(FILE_PATH, false);
            pw = new PrintWriter(fw);

            for (int i = 0; i < lista.size(); i++) {
                Curso curso = (Curso) lista.get(i);
                pw.write(curso.toString());
                pw.write("\r\n");
            }

            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }

}