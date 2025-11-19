package persistence;

import estrutura.Fila;
import estrutura.Lista;
import model.Professor;

import java.io.*;

public class ProfessorRepository {
    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "professores.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public ProfessorRepository() {
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

    public void save(String csvProfessor) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        fw = new FileWriter(FILE_PATH, true);
        pw = new PrintWriter(fw);
        pw.write(csvProfessor);
        pw.write("\r\n");
        pw.flush();

        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }

    public Fila buscarPorCpfComFila(String cpf) throws IOException {
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
            if (vetLinha[0].equals(cpf)) {
                Professor professor = new Professor();
                professor.setCpf(vetLinha[0]);
                professor.setNome(vetLinha[1]);
                professor.setAreaPretensao(vetLinha[2]);
                professor.setPontuacao(vetLinha[3]);
                fila.insert(professor);
                break;
            }
            linha = br.readLine();
        }

        if (br != null) br.close();
        if (fr != null) fr.close();

        return fila;
    }

    public boolean professorExists(String cpf) throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return false;
        }

        FileReader fr = null;
        BufferedReader br = null;

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
        if (br != null) br.close();
        if (fr != null) fr.close();

        return false;
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
            Professor professor = new Professor();
            professor.setCpf(vetLinha[0]);
            professor.setNome(vetLinha[1]);
            professor.setAreaPretensao(vetLinha[2]);
            professor.setPontuacao(vetLinha[3]);
            lista.addLast(professor);
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
            Professor professor = (Professor) lista.get(i);
            pw.write(professor.toString());
            pw.write("\r\n");
        }
        pw.flush();

        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }

}