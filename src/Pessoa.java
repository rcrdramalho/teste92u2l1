import java.time.LocalDate;

public class Pessoa {
    String nome;
    LocalDate nascimento;

    public Pessoa(String nome, LocalDate nascimento){
        this.nome = nome;
        this.nascimento = nascimento;
    }

    //gets e sets
    public String getNome() {
        return nome;
    }
    public LocalDate getNascimento() {
        return nascimento;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }
}
