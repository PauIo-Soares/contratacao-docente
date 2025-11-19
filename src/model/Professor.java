package model;

public class Professor {

    private String cpf;
    private String nome;
    private String areaPretensao;
    private String pontuacao;

    public Professor() {
        super();
    }

    public Professor(String cpf, String nome, String areaPretensao, String pontuacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.areaPretensao = areaPretensao;
        this.pontuacao = pontuacao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAreaPretensao() {
        return areaPretensao;
    }

    public void setAreaPretensao(String areaPretensao) {
        this.areaPretensao = areaPretensao;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getPontuacaoInt() {
        try {
            return Integer.parseInt(pontuacao);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public String toString() {
        return cpf + ";" + nome + ";" + areaPretensao + ";" + pontuacao;
    }

}