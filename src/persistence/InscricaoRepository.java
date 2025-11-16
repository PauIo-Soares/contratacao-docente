package persistence;

import model.Inscricao;

import java.io.*;
import java.util.Queue;

public class InscricaoRepository {
    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "inscricoes.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public InscricaoRepository() {
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

    public void save(String csvInscricao) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter(FILE_PATH, true);
            pw = new PrintWriter(fw);
            pw.write(csvInscricao);
            pw.write("\r\n");
            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }

    public Queue buscarPorCodigoProcessoComFila(String codigoProcesso) throws IOException {
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
                if (vetLinha[0].equals(codigoProcesso)) {
                    Inscricao inscricao = new Inscricao();
                    inscricao.setCodigoProcesso(vetLinha[0]);
                    inscricao.setCpfProfessor(vetLinha[1]);
                    inscricao.setCodigoDisciplina(vetLinha[2]);
                    fila.enqueue(inscricao);
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
                Inscricao inscricao = new Inscricao();
                inscricao.setCodigoProcesso(vetLinha[0]);
                inscricao.setCpfProfessor(vetLinha[1]);
                inscricao.setCodigoDisciplina(vetLinha[2]);
                lista.add(inscricao);
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
                Inscricao inscricao = (Inscricao) lista.get(i);
                pw.write(inscricao.toString());
                pw.write("\r\n");
            }

            pw.flush();
        } finally {
            if (pw != null) pw.close();
            if (fw != null) fw.close();
        }
    }

    public ListaSimples buscarInscricoesPorDisciplina(String codigoDisciplina) throws IOException {
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
                if (vetLinha[2].equals(codigoDisciplina)) {
                    Inscricao inscricao = new Inscricao();
                    inscricao.setCodigoProcesso(vetLinha[0]);
                    inscricao.setCpfProfessor(vetLinha[1]);
                    inscricao.setCodigoDisciplina(vetLinha[2]);
                    lista.add(inscricao);
                }
                linha = br.readLine();
            }
        } finally {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }

        return lista;
    }

    public HashTable getProcessosAbertosHashTable() throws IOException {
        HashTable hashTable = new HashTable(50);
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return hashTable;
        }

        FileReader fr = null;
        BufferedReader br = null;
        ListaSimples disciplinasAdicionadas = new ListaSimples();

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String linha = br.readLine();

            while (linha != null) {
                String[] vetLinha = linha.split(";");
                String codigoDisciplina = vetLinha[2];

                boolean jaAdicionado = false;
                for (int i = 0; i < disciplinasAdicionadas.size(); i++) {
                    String cod = (String) disciplinasAdicionadas.get(i);
                    if (cod.equals(codigoDisciplina)) {
                        jaAdicionado = true;
                        break;
                    }
                }

                if (!jaAdicionado) {
                    hashTable.put(codigoDisciplina, codigoDisciplina);
                    disciplinasAdicionadas.add(codigoDisciplina);
                }

                linha = br.readLine();
            }
        } finally {
            if (br != null) br.close();
            if (fr != null) fr.close();
        }

        return hashTable;
    }
}