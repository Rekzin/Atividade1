import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Produto {
    private int id;
    private String nome;
    private double preco;

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return id + "," + nome + "," + preco;
    }

    public static Produto fromString(String str) {
        String[] partes = str.split(",");
        int id = Integer.parseInt(partes[0]);
        String nome = partes[1];
        double preco = Double.parseDouble(partes[2]);
        return new Produto(id, nome, preco);
    }
}

public class ProdutoManager {
    private static final String FILE_NAME = "produtos.txt";
    private List<Produto> produtos;
    private Scanner scanner;

    public ProdutoManager() {
        produtos = new ArrayList<>();
        scanner = new Scanner(System.in);
        carregarProdutos();
    }

    private void carregarProdutos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                produtos.add(Produto.fromString(linha));
            }
        } catch (IOException e) {
            System.out.println("Nenhum dado carregado. Arquivo não encontrado.");
        }
    }

    private void salvarProdutos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Produto produto : produtos) {
                writer.write(produto.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("1. Criar Produto");
        System.out.println("2. Ler Produtos");
        System.out.println("3. Atualizar Produto");
        System.out.println("4. Deletar Produto");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void criarProduto() {
        System.out.print("Digite o ID do produto: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o preço do produto: ");
        double preco = Double.parseDouble(scanner.nextLine());

        produtos.add(new Produto(id, nome, preco));
        salvarProdutos();
        System.out.println("Produto criado com sucesso.");
    }

    private void lerProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto disponível.");
        } else {
            System.out.println("Lista de Produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto.getId() + ": " + produto.getNome() + " - R$" + produto.getPreco());
            }
        }
    }

    private void atualizarProduto() {
        System.out.print("Digite o ID do produto que deseja atualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Produto produto = encontrarProdutoPorId(id);
        if (produto != null) {
            System.out.print("Digite o novo nome do produto: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o novo preço do produto: ");
            double preco = Double.parseDouble(scanner.nextLine());

            produto.setNome(nome);
            produto.setPreco(preco);
            salvarProdutos();
            System.out.println("Produto atualizado com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void deletarProduto() {
        System.out.print("Digite o ID do produto que deseja deletar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Produto produto = encontrarProdutoPorId(id);
        if (produto != null) {
            produtos.remove(produto);
            salvarProdutos();
            System.out.println("Produto deletado com sucesso.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private Produto encontrarProdutoPorId(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    criarProduto();
                    break;
                case 2:
                    lerProdutos();
                    break;
                case 3:
                    atualizarProduto();
                    break;
                case 4:
                    deletarProduto();
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }

    public static void main(String[] args) {
        ProdutoManager manager = new ProdutoManager();
        manager.executar();
    }
}
