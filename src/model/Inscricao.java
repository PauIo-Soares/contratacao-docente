package model;

public class Inscricao {

    private String codigoProcesso;
    private String cpfProfessor;
    private String codigoDisciplina;

    public String getCodigoProcesso() {
        return codigoProcesso;
    }

    public void setCodigoProcesso(String codigoProcesso) {
        this.codigoProcesso = codigoProcesso;
    }

    public String getCpfProfessor() {
        return cpfProfessor;
    }

    public void setCpfProfessor(String cpfProfessor) {
        this.cpfProfessor = cpfProfessor;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    @Override
    public String toString() {
        return codigoProcesso + ";" + cpfProfessor + ";" + codigoDisciplina;
    }

}