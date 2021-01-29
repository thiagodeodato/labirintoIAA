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

    public static final char LIVRE = '.';

    public static void imprimirMapa() {
        int x = 0;
        int y = 0;

        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                System.out.print(mapa[y][x] + " ");
                y++;
            }
            System.out.println();
            x++;
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
        return mapa[destino.getX()][destino.getY()] == '.';
    }

    public static boolean possivelAndar(Coordenada destino) {
        // System.out.println("Colunas: " + colunas);
        // System.out.println("Linhas: " + linhas);
        boolean dentroDoMapa = destino.getY() < colunas && destino.getX() < linhas
                && destino.getX() >= 0 && destino.getY() >= 0;
        // System.out.println("X: " + destino.getX() + ", Y: " + destino.getY());
        // System.out.print("DENTRO: ");
        // System.out.println(dentroDoMapa);
        boolean livre = false;
        if (dentroDoMapa)
            livre = estaLivre(destino);
        // System.out.print("LIVRE: ");
        // System.out.println(livre);
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

    public static Coordenada andar(Coordenada destino) {
        mapa[destino.getX()][destino.getY()] = '*';
        return destino;
    }

    public static Coordenada direita() {
        return new Coordenada(atual.getX() + 1, atual.getY());
    }

    public static Coordenada esquerda() {
        return new Coordenada(atual.getX() - 1, atual.getY());
    }

    public static Coordenada cima() {
        Coordenada cima = new Coordenada(atual.getX(), atual.getY() + 1);
        return cima;
    }

    public static Coordenada baixo() {
        return new Coordenada(atual.getX(), atual.getY() - 1);
    }

    public static void caminho(Coordenada origem, Coordenada destino) {
        Coordenada cima = cima();
        Coordenada direita = direita();
        Coordenada esquerda = esquerda();
        Coordenada baixo = baixo();
        System.out.println(possivelAndar(cima));
        while (possivelAndar(cima)) {
            atual = andar(cima);
            cima.imprimir();
            cima = cima();
        }

        atual.imprimir();
        direita = direita();
        while(possivelAndar(direita)) {
            atual = andar(direita);
            direita = direita();
        }

        atual.imprimir();
        baixo = baixo();
        while(possivelAndar(baixo)) {
            atual = andar(baixo);
            baixo = baixo();
        }

        atual.imprimir();
        esquerda = esquerda();
        while(possivelAndar(esquerda)) {
            atual = andar(esquerda);
            esquerda = esquerda();
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
        System.out.println("----------");

        origem.imprimir();
        System.out.println("----------");

        caminho(origem, destino);
        System.out.println("----------");

        atual.imprimir();
        System.out.println("----------");

        destino.imprimir();
        System.out.println("----------");

        imprimirMapa();
    }
}
