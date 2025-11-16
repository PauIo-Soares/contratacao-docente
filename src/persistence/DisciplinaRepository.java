package persistence;

import model.Disciplina;

import java.io.*;
import java.util.Queue;

public class DisciplinaRepository {
    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "disciplinas.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public DisciplinaRepository() {
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

    public void save(String csvDisciplina) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(FILE_PATH, true);
            pw = new PrintWriter(fw);
            pw.write(csvDisciplina);
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
                    Disciplina disciplina = new Disciplina();
                    disciplina.setCodigo(vetLinha[0]);
                    disciplina.setNome(vetLinha[1]);
                    disciplina.setDiaSemana(vetLinha[2]);
                    disciplina.setHorarioInicial(vetLinha[3]);
                    disciplina.setQuantidadeHorasDiaria(Integer.parseInt(vetLinha[4]));
                    disciplina.setCodigoCurso(vetLinha[5]);
                    fila.enqueue(disciplina);
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
                Disciplina disciplina = new Disciplina();
                disciplina.setCodigo(vetLinha[0]);
                disciplina.setNome(vetLinha[1]);
                disciplina.setDiaSemana(vetLinha[2]);
                disciplina.setHorarioInicial(vetLinha[3]);
                disciplina.setQuantidadeHorasDiarias(vetLinha[4]);
                disciplina.setCodigoCurso(vetLinha[5]);
                lista.add(disciplina);
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
                Disciplina disciplina = (Disciplina) lista.get(i);
                pw.write(disciplina.toString());
                pw.write("\r\n");
            }

            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }
}