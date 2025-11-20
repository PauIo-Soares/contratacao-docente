package persistence;

import estrutura.Fila;
import model.Inscricao;
import estrutura.Lista;
import structure.HashTable;

import java.io.*;

public class InscricaoRepository {
    private static final String DIRECTORY = "csv";
    private static final String FILE_NAME = "inscricoes.csv";
    private static final String FILE_PATH = DIRECTORY + "/" + FILE_NAME;

    public InscricaoRepository() {
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

    public void save(String csvInscricao) throws IOException {
        FileWriter fw = null;
        PrintWriter pw = null;

        fw = new FileWriter(FILE_PATH, true);
        pw = new PrintWriter(fw);
        pw.write(csvInscricao);
        pw.write("\r\n");
        pw.flush();
        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }

    public Fila buscarPorCodigoProcessoComFila(String codigoProcesso) throws IOException {
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
            if (vetLinha[0].equals(codigoProcesso)) {
                Inscricao inscricao = new Inscricao();
                inscricao.setCodigoProcesso(vetLinha[0]);
                inscricao.setCpfProfessor(vetLinha[1]);
                inscricao.setCodigoDisciplina(vetLinha[2]);
                fila.insert(inscricao);
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
            Inscricao inscricao = new Inscricao();
            inscricao.setCodigoProcesso(vetLinha[0]);
            inscricao.setCpfProfessor(vetLinha[1]);
            inscricao.setCodigoDisciplina(vetLinha[2]);
            lista.addLast(inscricao);
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
            Inscricao inscricao = (Inscricao) lista.get(i);
            pw.write(inscricao.toString());
            pw.write("\r\n");
        }

        pw.flush();
        if (pw != null) pw.close();
        if (fw != null) fw.close();
    }

    public Lista buscarInscricoesPorDisciplina(String codigoDisciplina) throws Exception {
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
            if (vetLinha[2].equals(codigoDisciplina)) {
                Inscricao inscricao = new Inscricao();
                inscricao.setCodigoProcesso(vetLinha[0]);
                inscricao.setCpfProfessor(vetLinha[1]);
                inscricao.setCodigoDisciplina(vetLinha[2]);
                lista.addLast(inscricao);
            }
            linha = br.readLine();
        }
        if (br != null) br.close();
        if (fr != null) fr.close();

        return lista;
    }

    public HashTable getProcessosAbertosHashTable() throws Exception {
        HashTable hashTable = new HashTable(50);
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return hashTable;
        }

        FileReader fr = null;
        BufferedReader br = null;
        Lista disciplinasAdicionadas = new Lista();

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
                disciplinasAdicionadas.addLast(codigoDisciplina);
            }
            linha = br.readLine();
        }
        if (br != null) br.close();
        if (fr != null) fr.close();

        return hashTable;
    }

}