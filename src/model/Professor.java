package model;

public class Professor {

    private String cpf;
    private String nome;
    private String areaPretensao;
    private String pontuacao;

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

    @Override
    public String toString() {
        return cpf + ";" + nome + ";" + areaPretensao + ";" + pontuacao;
    }

}