import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bodega {
    private Empresa empresa;
    private List<Bebida> bebidas;
    private List<Clientes> clientes;

    private static final String ARQUIVO_DADOS = "bodega.txt";

    public Bodega(Empresa empresa) {
        this.empresa = empresa;
        this.bebidas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public void adicionarBebida(Bebida b) {
        bebidas.add(b);
    }

    public void adicionarCliente(Clientes c) {
        clientes.add(c);
    }

    public Bebida buscarBebidaPorCodigo(String codigo) {
        for (Bebida b : bebidas) {
            if (b.getCodigo().equalsIgnoreCase(codigo)) return b;
        }
        return null;
    }

    public Clientes buscarClientePorCodigo(int codigo) {
        for (Clientes c : clientes) {
            if (c.getCodigo() == codigo) return c;
        }
        return null;
    }

    public void mostrarBebidas() {
        if (bebidas.isEmpty()) {
            System.out.println("Nenhuma bebida cadastrada.");
            return;
        }
        for (Bebida b : bebidas) System.out.println(b);
    }

    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Clientes c : clientes) {
            String maior = c.isMaiorIdade() ? "Sim" : "Não";
            String fiado = c.isPodeFiado() ? "Sim" : "Não";
            System.out.println(
                    "Código: " + c.getCodigo() +
                    " | Nome: " + c.getNome() +
                    " | CPF: " + c.getCpf() +
                    " | Maior de idade: " + maior +
                    " | Pode fiado: " + fiado
            );
        }
    }

    public boolean salvarDados() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO_DADOS))) {
            writer.println("EMPRESA");
            writer.println(empresa.getNome_empresa());
            writer.println(empresa.getCnpj());
            writer.println();
            writer.println("BEBIDAS");
            for (Bebida b : bebidas) {
                if (b instanceof Alcolica) {
                    Alcolica a = (Alcolica) b;
                    writer.printf("ALCOOLICA;%s;%s;%d;%.2f;%d;%.2f\n",
                            b.getCodigo(), b.getNome(), b.getMl(),
                            b.getPreco(), b.getQuantidadeEstoque(),
                            a.getTeorAlcoolico()
                    );
                } else {
                    writer.printf("NAOALCOOLICA;%s;%s;%d;%.2f;%d\n",
                            b.getCodigo(), b.getNome(), b.getMl(),
                            b.getPreco(), b.getQuantidadeEstoque()
                    );
                }
            }
            writer.println();
            writer.println("CLIENTES");
            for (Clientes c : clientes) {
                String maior = c.isMaiorIdade() ? "Sim" : "Não";
                String fiado = c.isPodeFiado() ? "Sim" : "Não";
                writer.printf("Código: %d | Nome: %s | CPF: %s | Pode fiado: %s | Maior de idade: %s\n",
                        c.getCodigo(), c.getNome(), c.getCpf(), fiado, maior
                );
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Bodega carregarDadosSeExistir() {
        File arq = new File(ARQUIVO_DADOS);
        if (!arq.exists()) return null;
        try (BufferedReader reader = new BufferedReader(new FileReader(arq))) {
            String linha;
            reader.readLine();
            String nomeEmpresa = reader.readLine();
            String cnpjEmpresa = reader.readLine();
            reader.readLine();
            Bodega bodega = new Bodega(new Empresa(nomeEmpresa, cnpjEmpresa));
            reader.readLine();
            while ((linha = reader.readLine()) != null && !linha.isBlank()) {
                String[] p = linha.split(";");
                if (p[0].equals("ALCOOLICA")) {
                    bodega.adicionarBebida(
                            new Alcolica(
                                    p[1], p[2],
                                    Integer.parseInt(p[3]),
                                    Double.parseDouble(p[4]),
                                    Integer.parseInt(p[5]),
                                    Double.parseDouble(p[6])
                            )
                    );
                } else {
                    bodega.adicionarBebida(
                            new NaoAlcolica(
                                    p[1], p[2],
                                    Integer.parseInt(p[3]),
                                    Double.parseDouble(p[4]),
                                    Integer.parseInt(p[5])
                            )
                    );
                }
            }
            reader.readLine();
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split("\\|");
                String sCodigo = partes.length > 0 ? partes[0].trim() : "";
                String sNome = partes.length > 1 ? partes[1].trim() : "";
                String sCpf = partes.length > 2 ? partes[2].trim() : "";
                String sFiado = partes.length > 3 ? partes[3].trim() : "";
                String sMaior = partes.length > 4 ? partes[4].trim() : "";
                if (sCodigo.startsWith("Código:")) sCodigo = sCodigo.substring("Código:".length()).trim();
                if (sNome.startsWith("Nome:")) sNome = sNome.substring("Nome:".length()).trim();
                if (sCpf.startsWith("CPF:")) sCpf = sCpf.substring("CPF:".length()).trim();
                if (sFiado.startsWith("Pode fiado:")) sFiado = sFiado.substring("Pode fiado:".length()).trim();
                if (sMaior.startsWith("Maior de idade:")) sMaior = sMaior.substring("Maior de idade:".length()).trim();
                int codigo = 0;
                try { codigo = Integer.parseInt(sCodigo); } catch (Exception ex) { codigo = 0; }
                boolean fiado = sFiado.equalsIgnoreCase("Sim");
                boolean maior = sMaior.equalsIgnoreCase("Sim");
                bodega.adicionarCliente(new Clientes(codigo, sNome, sCpf, fiado, maior));
            }
            return bodega;
        } catch (Exception e) {
            return null;
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n1. Cadastrar bebida");
        System.out.println("2. Mostrar bebidas");
        System.out.println("3. Comprar bebida");
        System.out.println("4. Vender bebida");
        System.out.println("5. Cadastrar cliente");
        System.out.println("6. Mostrar clientes");
        System.out.println("7. Salvar dados");
        System.out.println("8. Sair");
        System.out.print("Opção: ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bodega bodega = carregarDadosSeExistir();
        if (bodega == null) {
            System.out.print("Nome da empresa: ");
            String nome = sc.nextLine().trim();
            System.out.print("CNPJ: ");
            String cnpj = sc.nextLine().trim();
            bodega = new Bodega(new Empresa(nome, cnpj));
        }
        boolean run = true;
        while (run) {
            mostrarMenu();
            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("Código: ");
                    String codigo = sc.nextLine().trim();
                    System.out.print("Nome: ");
                    String nomeB = sc.nextLine().trim();
                    System.out.print("Conteúdo (ml): ");
                    int ml = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Preço: ");
                    double preco = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Estoque inicial: ");
                    int est = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Alcoólica? (s/n): ");
                    if (sc.nextLine().trim().equalsIgnoreCase("s")) {
                        System.out.print("Teor alcoólico: ");
                        double teor = Double.parseDouble(sc.nextLine().trim());
                        bodega.adicionarBebida(new Alcolica(codigo, nomeB, ml, preco, est, teor));
                    } else {
                        bodega.adicionarBebida(new NaoAlcolica(codigo, nomeB, ml, preco, est));
                    }
                    break;
                case "2":
                    bodega.mostrarBebidas();
                    break;
                case "3":
                    System.out.print("Código da bebida: ");
                    Bebida bcomp = bodega.buscarBebidaPorCodigo(sc.nextLine().trim());
                    if (bcomp != null) {
                        System.out.print("Quantidade a comprar: ");
                        bcomp.compraBebida(Integer.parseInt(sc.nextLine().trim()));
                        System.out.println("Estoque atual: " + bcomp.getQuantidadeEstoque());
                    } else {
                        System.out.println("Bebida não encontrada.");
                    }
                    break;
                case "4":
                    System.out.print("Código da bebida: ");
                    Bebida bvenda = bodega.buscarBebidaPorCodigo(sc.nextLine().trim());
                    if (bvenda == null) {
                        System.out.println("Bebida não encontrada.");
                        break;
                    }
                    System.out.print("Código do cliente: ");
                    int codCli = Integer.parseInt(sc.nextLine().trim());
                    Clientes cliente = bodega.buscarClientePorCodigo(codCli);
                    if (cliente == null) {
                        System.out.println("Cliente não encontrado.");
                        break;
                    }
                    if (bvenda.exigeMaioridade() && !cliente.isMaiorIdade()) {
                        System.out.println("Venda negada: cliente não é maior de idade.");
                        break;
                    }
                    System.out.print("Quantidade a vender: ");
                    int qvenda = Integer.parseInt(sc.nextLine().trim());
                    boolean ok = bvenda.vendeBebida(qvenda);
                    if (ok) {
                        System.out.printf("Venda realizada. Total: R$ %.2f\n", qvenda * bvenda.getPreco());
                        System.out.println("Estoque restante: " + bvenda.getQuantidadeEstoque());
                        if (cliente.isPodeFiado()) System.out.println("Cliente autorizado a fiado.");
                    } else {
                        System.out.println("Venda não realizada.");
                    }
                    break;
                case "5":
                    System.out.print("Código (número): ");
                    int cc = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Nome: ");
                    String nc = sc.nextLine().trim();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine().trim();
                    System.out.print("Pode fiado? (s/n): ");
                    boolean pf = sc.nextLine().trim().equalsIgnoreCase("s");
                    System.out.print("Maior de idade? (s/n): ");
                    boolean mi = sc.nextLine().trim().equalsIgnoreCase("s");
                    bodega.adicionarCliente(new Clientes(cc, nc, cpf, pf, mi));
                    break;
                case "6":
                    bodega.mostrarClientes();
                    break;
                case "7":
                    if (bodega.salvarDados()) System.out.println("Dados salvos em " + ARQUIVO_DADOS);
                    else System.out.println("Falha ao salvar dados.");
                    break;
                case "8":
                    System.out.print("Salvar antes de sair? (s/n): ");
                    String s = sc.nextLine().trim();
                    if (s.equalsIgnoreCase("s") || s.equalsIgnoreCase("sim")) bodega.salvarDados();
                    run = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}