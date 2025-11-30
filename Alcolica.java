public class Alcolica extends Bebida {
    private double teorAlcoolico;

    public Alcolica(String codigo, String nome, int ml, double preco, int quantidadeEstoque, double teorAlcoolico) {
        super(codigo, nome, ml, preco, quantidadeEstoque);
        this.teorAlcoolico = teorAlcoolico;
    }

    public double getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(double teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    @Override
    public boolean exigeMaioridade() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | teor: %.1f%%", teorAlcoolico);
    }
}
