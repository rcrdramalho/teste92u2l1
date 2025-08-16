import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;

//Tentei usar o mínimo de bibliotecas para resolver os itens, as bibliotecas LocalDate e BigDecimal foram as que eu mais tive que pesquisar
//Os comentários estão um pouco informais porém a ideia é mostrar o fluxo de desenvolvimento e não construir uma documentação formal
//Não me senti seguro o suficiente para usar os try catch então não estão presentes nas tarefas, mas entendo que em uma operação real eles estariam presentes
//Tive que pesquisar boa parte da sintaxe por não usar java a muito tempo

public class Main {


    public static void main(String[] args) {
        //3.1 Chama a função que cria a lista com os funcionários presentes na tabela.
        List<Funcionario> funcionarios = new ArrayList<>(listarFuncionarios());

        //3.2 Remove todos os possíveis funcionários com nome joão.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        //3.3 Chama a função que exibe os funcionários presentes na lista.
        System.out.println("\n============Item 3.3============");
        exibirFuncionarios(funcionarios);

        //3.4 Chama função que aumenta o salário de todos
        aumentoDeSalario(funcionarios, 10);

        //3.5 Chama função que cria o map agrupando os funcionários por função
        Map<String, List<Funcionario>> funcionariosAgrup = agruparFuncionarios(funcionarios);

        //3.6 Chama função que imprime os funcionários por função
        System.out.println("\n============Item 3.6============");
        exibirAgrupamento(funcionariosAgrup);

        //3.8 Concatena duas listas de aniversariante, uma para cada mês requerido
        System.out.println("\n============Item 3.8============");
        List<Funcionario> aniversariantes =  listaAniversariantes(funcionarios, 10);
        aniversariantes.addAll(listaAniversariantes(funcionarios,12));
        exibirFuncionarios(aniversariantes);

        //3.9 Exibe o funcionário mais velho da lista
        System.out.println("\n============Item 3.9============");
        exibirMaisVelho(funcionarios);

        //3.10 cria uma lista cópia da lista original de funcionários, ordena essa lista e depois a exibe
        System.out.println("\n============Item 3.10============");
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort((f1, f2) -> f1.getNome().compareTo(f2.getNome()));
        exibirFuncionarios(funcionariosOrdenados);

        //3.11 Exibe o retorno da função que soma todos os salários.
        System.out.println("\n============Item 3.11============");
        System.out.println("Folha de pagamento total: R$" + somaSalarios(funcionarios));

        //3.12 Exibe quantos salários mínimos cada funcionário recebe
        System.out.println("\n============Item 3.12============");
        exibirSMPorFuncionario(funcionarios);


    }

    //3.1 Função que cria todas as instâncias de funcionários
    // Há um Json com a tabela de funcionários, pois eu tentei criar esta função a partir do Json porém não tive a experiência para usar a biblioteca de json do java.
    public static List<Funcionario> listarFuncionarios() {

        List<Funcionario> funcionarios = new ArrayList<>(); //lista que conterá todos os funcionários

        //atribuições manuais
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Héctor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        return funcionarios;
    }

    //3.3 Função que imprime todos os funcionários presentes na lista de funcionários
    public static void exibirFuncionarios(List<Funcionario> funcionarios) {

        for (Funcionario f : funcionarios) {//para cada funcionário na lista imprima

            String dataNascimento = f.getNascimento().toString();
            dataNascimento = dataNascimento.split("-")[2] + "/" + dataNascimento.split("-")[1] + "/" + dataNascimento.split("-")[0];//separa a data de nascimento e reordena na ordem invertida

            String salario = f.getSalario().toString().replace(".", ","); //substitui o . por , (abrasileirando)
            int i = 6;//começa por 6 pois é a primeira posição a precisar de "." (na ordem invertida)
            while (i < salario.length()) {
                int indice = salario.length() - i;
                salario = salario.substring(0, indice) + "." + salario.substring(indice);//adiciona o . em cada 10³
                i += 4;
            }
            System.out.println("Nome: " + f.nome + "\nNascimento: " + dataNascimento + "\nSalário: R$" + salario + "\nFunção: " + f.getFuncao() + "\n -------------");

        }
    }

    //3.4 Função que dá o aumento aos funcionários
    public static List<Funcionario> aumentoDeSalario(List<Funcionario> funcionarios, double aumento) {
        BigDecimal valor = new BigDecimal((1 + aumento / 100) + ""); //cria um decimal com a percentage a ser aumentada do salário


        for (Funcionario f : funcionarios) {
            f.salario = f.salario.multiply(valor);
            f.salario = f.salario.setScale(2, RoundingMode.UP);//arredonda o novo salário para cima para manter as duas casas (1 centavo não vai matar a empresa)
        }

        return funcionarios;
    }

    //3.5 Cria um map com uma chave para cada função sendo seu valor os funcionários que exercem aquela função
    public static Map<String, List<Funcionario>> agruparFuncionarios(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> agrupamento = new HashMap<>();//cria o map que armazenará os funcionários agrupados

        for (Funcionario f : funcionarios) {
            if (!agrupamento.containsKey(f.funcao)) {
                agrupamento.put(f.funcao, new ArrayList<>());//adiciona uma única vez cada função existente na empresa ao map
            }

            agrupamento.get(f.funcao).add(f);//adiciona cada funcionário na sua função correspondente
        }

        return agrupamento;
    }


    //3.6 Exibe os funcionários agrupados por função
    public static void exibirAgrupamento(Map<String, List<Funcionario>> funcionariosAgrup) {
        for (String funcao : funcionariosAgrup.keySet()) {//imprime cada função presente no map
            System.out.println("Função: " + funcao);
            List<Funcionario> lista = funcionariosAgrup.get(funcao);
            for (Funcionario f : lista) {//imprime cada funcionário presente na função atual do loop
                System.out.println("\tNome: " + f.getNome());

                String dataNascimento = f.getNascimento().toString();
                dataNascimento = dataNascimento.split("-")[2] + "/" + dataNascimento.split("-")[1] + "/" + dataNascimento.split("-")[0];//separa a data de nascimento e reordena na ordem invertida

                System.out.println("\tNascimento: " + dataNascimento);

                String salario = f.getSalario().toString().replace(".", ","); //substitui o . por , (abrasileirando)
                int i = 6;//começa por 6 pois é a primeira posição a precisar de "." (na ordem invertida)
                while (i < salario.length()) {
                    int indice = salario.length() - i;
                    salario = salario.substring(0, indice) + "." + salario.substring(indice);//adiciona o . em cada 10³
                    i += 4;
                }

                System.out.println("\tSalário: R$" + salario);
                System.out.println("-------------");
            }
        }
    }

    //3.8 Cria uma lista com os aniversariantes do respectivo mês
    public static List<Funcionario> listaAniversariantes(List<Funcionario> funcionarios, int mes){
        List<Funcionario> aniversariantes = new ArrayList<>();//lista que armazenará os funcionarios aniversariantes
        for(Funcionario f : funcionarios){
            int aniversario = Integer.parseInt(f.getNascimento().toString().substring(5,7));//pega o recorte que representa o mês na data de nescimento
            if(aniversario == mes){//compara o recorte com o mês alvo
                aniversariantes.add(f);
            }
        }

        return aniversariantes;
    }

    //3.9 Compara os funcionários e retorna o mais velho
    public static void exibirMaisVelho(List<Funcionario> funcionarios){
        Funcionario maisVelho = funcionarios.get(0);//atribui o primeiro funcionário como mais velho para comparação

        for(Funcionario f : funcionarios){
            if(f.nascimento.isBefore(maisVelho.nascimento)){//compara se o funcionário atual do loop nasceu antes que o mais velho
                maisVelho = f;
            }
        }

        int idade = calcularIdade(maisVelho);

        System.out.println("Funcionário com maior idade\n\tnome: " + maisVelho.nome + "\n\tidade: " + idade);

    }

    //3.9 Calcula a idade de um funcionário
    public static int calcularIdade(Funcionario f) {
        Period idade = Period.between(f.nascimento, LocalDate.now());//usa a biblioteca Period para calcular a idade de um funcionário
        return idade.getYears();
    }


    //3.11 Soma os salários de todos os funcionários
    public static String somaSalarios(List<Funcionario> funcionarios){
        BigDecimal soma = BigDecimal.ZERO;//começa a soma dos salários como 0

        for(Funcionario f : funcionarios){
            soma = soma.add(f.salario);//soma cada salário
        }

        String salario = soma.toString().replace(".", ","); //substitui o . por , (abrasileirando)
        int i = 6;//começa por 6 pois é a primeira posição a precisar de "." (na ordem invertida)
        while (i < salario.length()) {
            int indice = salario.length() - i;
            salario = salario.substring(0, indice) + "." + salario.substring(indice);//adiciona o . em cada 10³
            i += 4;
        }

        return salario;
    }

    //3.12 Calcula e exibe quantos salários mínimos cada funcionário recebe
    public static void exibirSMPorFuncionario (List<Funcionario> funcionarios){
        BigDecimal salario_min = new BigDecimal("1212.00");//define o salário mínimo

        for(Funcionario f : funcionarios){
            BigDecimal salario = f.salario.divide(salario_min,3,RoundingMode.UP);//divide o salário do funcionário pelo salário mínimo com 3 casas de precisão
            System.out.println("A(O) funcionária(o) " + f.nome + " recebe " + salario + " salários mínimos.");
        }
    }
}