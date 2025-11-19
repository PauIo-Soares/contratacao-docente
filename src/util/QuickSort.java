package util;

import estrutura.Lista;
import model.Professor;

public class QuickSort {

    public void sortByPontuacao(Lista lista) throws Exception {
        Professor[] array = new Professor[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            array[i] = (Professor) lista.get(i);
        }

        quickSortProf(array, 0, array.length - 1);

        for (int i = 0; i < array.length; i++) {
            lista.set(i, array[i]);
        }
    }

    private void quickSortProf(Professor[] vetor, int inicio, int fim) {
        if (fim > inicio) {
            int pivo = dividirProf(vetor, inicio, fim);
            quickSortProf(vetor, inicio, pivo - 1);
            quickSortProf(vetor, pivo + 1, fim);
        }
    }

    private int dividirProf(Professor[] vetor, int inicio, int fim) {
        Professor pivo = vetor[inicio];
        int esq = inicio + 1;
        int dir = fim;

        while (esq <= dir) {
            while (esq <= dir && vetor[esq].getPontuacaoInt() >= pivo.getPontuacaoInt()) esq++;
            while (dir >= esq && vetor[dir].getPontuacaoInt() < pivo.getPontuacaoInt()) dir--;

            if (esq < dir) {
                trocar(vetor, esq, dir);
                esq++;
                dir--;
            }
        }
        trocar(vetor, inicio, dir);
        return dir;
    }

    private void trocar(Professor[] vetor, int i, int j) {
        Professor aux = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = aux;
    }

}