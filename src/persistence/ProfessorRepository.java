package persistence;

import model.Professor;

import java.io.*;
import java.util.Queue;

public class ProfessorRepository {
    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "professores.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public ProfessorRepository() {
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

    public void save(String csvProfessor) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(FILE_PATH, true);
            pw = new PrintWriter(fw);
            pw.write(csvProfessor);
            pw.write("\r\n");
            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }

    public Queue buscarPorCpfComFila(String cpf) throws IOException {
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
                if (vetLinha[0].equals(cpf)) {
                    Professor professor = new Professor();
                    professor.setCpf(vetLinha[0]);
                    professor.setNome(vetLinha[1]);
                    professor.setAreaPretensao(vetLinha[2]);
                    professor.setPontuacao(vetLinha[3]);
                    fila.enqueue(professor);
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

    public boolean professorExists(String cpf) throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return false;
        }

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String linha = br.readLine();

            while (linha != null) {
                String[] vetLinha = linha.split(";");
                if (vetLinha[0].equals(cpf)) {
                    return true;
                }
                linha = br.readLine();
            }
        } finally {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }

        return false;
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
                Professor professor = new Professor();
                professor.setCpf(vetLinha[0]);
                professor.setNome(vetLinha[1]);
                professor.setAreaPretensao(vetLinha[2]);
                professor.setPontuacao(Integer.parseInt(vetLinha[3]));
                lista.add(professor);
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
                Professor professor = (Professor) lista.get(i);
                pw.write(professor.toString());
                pw.write("\r\n");
            }

            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }
}