import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class dev {

    public static int linhas, colunas, numeroItens;
    public static ArrayList<Item> items = new ArrayList<Item>();
    public static char[][] mapa;
    public static Coordenada origem;
    public static Coordenada destino;
    public static Coordenada atual;
    public static ArrayList<Coordenada> historico = new ArrayList<Coordenada>();

    public static final char LIVRE = '.';

    public static void imprimirMapa() {
        int x = 0;
        int y = 0;

        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                // if (atual.getX() == x && atual.getY() == y) System.out.print("A ");
                // else 
                System.out.print(mapa[y][x] + " ");
                y++;
            }
            System.out.println();
            x++;
        }
    }

    public static void imprimirHistorico() {
        for (int i = 0; i < historico.size(); i++) {
            Coordenada x = historico.get(i);
            if (i == 0) System.out.print("Inicial: ");
            else if (i == historico.size() - 1) System.out.print("Final: ");
            x.imprimir();
        }
        if (destino.igual(historico.get(historico.size() - 1))) {
            System.out.println("CHEGOU");
        }
    }
    public static void inicializarVariaveis(String entrada) {
        String[] comandos = entrada.split("\n");
        int i = 1;

        String coordenadas = comandos[0];

        int linha = Integer.parseInt(coordenadas.split(" ")[0].trim());
        int coluna = Integer.parseInt(coordenadas.split(" ")[1].trim());

        mapa = new char[coluna][linha];

        int itens = Integer.parseInt(comandos[linha + 1].trim());

        // int contador = 0;

        int x = 0;
        int y = 0;
        while (x < linha) {
            String caminho = comandos[i++];
            y = 0;
            while (y < coluna) {
                mapa[y][x] = caminho.trim().charAt(y);
                y++;
            }
            x++;
        }

        i = linha + 2;
        int contagem = i + itens;
        while (i < contagem) {
            items.add(new Item(comandos[i++].trim()));
        }

        String coordenadasOrigem = comandos[i];
        String coordenadasDestino = comandos[i + 1];

        int origemX = Integer.parseInt(coordenadasOrigem.split(" ")[0].trim());
        int origemY = Integer.parseInt(coordenadasOrigem.split(" ")[1].trim());

        int destinoX = Integer.parseInt(coordenadasDestino.split(" ")[0].trim());
        int destinoY = Integer.parseInt(coordenadasDestino.split(" ")[1].trim());

        linhas = linha;
        colunas = coluna;
        numeroItens = itens;

        origem = new Coordenada(origemY, origemX);
        destino = new Coordenada(destinoY, destinoX);
        atual = origem;
        historico.add(atual);

        mapa[atual.getY()][atual.getX()] = 'O';
    }

    public static String lerArquivo(String arquivoEntrada) {
        Path nomeArquivo = Path.of(arquivoEntrada);
        String entrada = "";
        try {
            entrada = Files.readString(nomeArquivo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entrada;
    }

    public static boolean estaLivre(Coordenada destino) {
        return mapa[destino.getY()][destino.getX()] == '.';
    }

    public static boolean possivelAndar(Coordenada destinoCaminho) {
        // System.out.println("Colunas: " + colunas);
        // System.out.println("Linhas: " + linhas);
        boolean dentroDoMapa = destinoCaminho.getY() < colunas && destinoCaminho.getX() < linhas
                && destinoCaminho.getX() >= 0 && destinoCaminho.getY() >= 0;
        // destinoCaminho.imprimir();
        // System.out.print("DENTRO: ");
        // System.out.println(dentroDoMapa);
        boolean livre = false;
        if (dentroDoMapa)
            livre = estaLivre(destinoCaminho);

        // boolean chegou = false;    

        // if (livre)
        //     chegou = !destino.igual(destinoCaminho);

        // System.out.print("LIVRE: ");
        // System.out.println(livre);
        if (atual.igual(destino)) return false;
        return livre;
    }

    public static void imprimirPosicaoMapa(Coordenada posicao) {
        int x = 0;
        int y = 0;
        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                if (posicao.igual(new Coordenada(y, x))) {
                    System.out.print('+');  
                } else {
                    System.out.print('.');
                }
                y++;
            }
            System.out.println();
            x++;
        }
    }

    public static Coordenada andar(Coordenada destinoCaminho) {
        mapa[destinoCaminho.getY()][destinoCaminho.getX()] = '*';
        historico.add(destinoCaminho);
        return destinoCaminho;
    }

    public static Coordenada direita() {
        return new Coordenada(atual.getX(), atual.getY() + 1);
    }

    public static Coordenada esquerda() {
        return new Coordenada(atual.getX(), atual.getY() - 1);
    }

    public static Coordenada cima() {
        return new Coordenada(atual.getX() + 1, atual.getY());
    }

    public static Coordenada baixo() {
        return new Coordenada(atual.getX() - 1, atual.getY());
    }

    public static void caminhoAleatorio() {
        while (possivelAndar(cima())) {
            atual = andar(cima());
        }

        while (possivelAndar(direita())) {
            atual = andar(direita());
        }

        while (possivelAndar(baixo())) {
            atual = andar(baixo());
        }

        while(possivelAndar(esquerda())) {
            atual = andar(esquerda());
        }
        
        if (possivelAndar(cima()) || possivelAndar(direita()) || possivelAndar(esquerda()) || possivelAndar(baixo())) {
            caminhoAleatorio();
        } else {
            return;
        }
    }

    public static void caminho(Coordenada origem, Coordenada destino) {

        while (possivelAndar(cima())) {
            atual = andar(cima());
        }

        while (possivelAndar(direita())) {
            atual = andar(direita());
        }

        while (possivelAndar(baixo())) {
            atual = andar(baixo());
        }

        while(possivelAndar(esquerda())) {
            atual = andar(esquerda());
        }
        
        while (possivelAndar(cima())) {
            atual = andar(cima());
        }

        if (possivelAndar(direita())) {
            atual = andar(direita());
        }

        while (possivelAndar(baixo())) {
            atual = andar(baixo());
        }

        if (possivelAndar(direita())) {
            atual = andar(direita());
        }
    }

    public static boolean chegou() {
        return atual.igual(destino);
    }

    public static void main(String[] args) {
        String arquivoEntrada = args[0];
        String entrada = lerArquivo(arquivoEntrada);
        inicializarVariaveis(entrada);

        imprimirMapa();

        caminhoAleatorio();
        System.out.println("----------");

        imprimirMapa();
        System.out.println("----------");

        imprimirHistorico();
    }
}
