public abstract class Bebida {
    private String codigo;
    private String nome;
    private int ml;
    private double preco;
    private int quantidadeEstoque;

    public Bebida(String codigo, String nome, int ml, double preco, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.ml = ml;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }


    public String getCodigo() { 
        return codigo; 
    }
    public String getNome() { 
        return nome; 
    }
    public int getMl() { 
        return ml; 
    }
    public double getPreco() { 
        return preco;
    }
    public int getQuantidadeEstoque() { 
        return quantidadeEstoque; 
    }
    
    public void setCodigo(String codigo) { 
        this.codigo = codigo; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    public void setMl(int ml) { 
        this.ml = ml; 
    }
    public void setPreco(double preco) { 
        this.preco = preco; 
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) { 
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void compraBebida(int quantidade) {
        if (quantidade > 0) {
            quantidadeEstoque += quantidade;
        }
    }

    public boolean vendeBebida(int quantidade) {
        if (quantidade <= 0) return false;
        if (quantidade > quantidadeEstoque) return false;

        quantidadeEstoque -= quantidade;
        return true;
    }

    public abstract boolean exigeMaioridade();

    @Override
    public String toString() {
        return String.format(
            "%s - %s | %dml | R$ %.2f | estoque: %d | %s",
            codigo, nome, ml, preco, quantidadeEstoque,
            exigeMaioridade() ? "Alcoólica" : "Não-alcoólica"
        );
    }
}
