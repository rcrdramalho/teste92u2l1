import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa{

    BigDecimal salario;
    String funcao;

    public Funcionario(String nome, LocalDate nascimento, BigDecimal salario, String funcao) {
        super(nome, nascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    //gets e sets
    public BigDecimal getSalario() {
        return salario;
    }
    public String getFuncao() {
        return funcao;
    }
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

}
