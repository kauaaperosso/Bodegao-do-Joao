public class NaoAlcolica extends Bebida {

        public NaoAlcolica(String codigo, String nome, int ml, double preco, int quantidadeEstoque) {
            super(codigo, nome, ml, preco, quantidadeEstoque);
        }
        @Override 
        public boolean exigeMaioridade() { 
            return false; 
        }
    }