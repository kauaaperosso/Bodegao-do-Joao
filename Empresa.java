public class Empresa {
    private String nome_empresa;
    private String cnpj;

    public Empresa(String nome_empresa, String cnpj){
        this.nome_empresa = nome_empresa;
        this.cnpj = cnpj;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }
    public String getCnpj() {
        return cnpj;
    }

    public void setNome_empresa(String nome_empresa) { 
        this.nome_empresa = nome_empresa; 
    }
    public void setCnpj(String cnpj) { 
        this.cnpj = cnpj; 
    }
}
