public class Clientes {
    private int codigo;
    private String nome;
    private String cpf;
    private boolean maior_idade;
    private boolean pd_fiado;

    public Clientes(int codigo, String nome, String cpf, boolean pd_fiado, boolean maior_idade){
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.maior_idade = maior_idade;
        this.pd_fiado = pd_fiado;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean isMaiorIdade() {
        return maior_idade;
    }

    public boolean isPodeFiado() {
        return pd_fiado;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setMaiorIdade(boolean maior_idade) {
        this.maior_idade = maior_idade;
    }

    public void setPodeFiado(boolean pd_fiado) {
        this.pd_fiado = pd_fiado;
    }
}