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

    public static void imprimirMapa() {
        int x = 0;
        int y = 0;

        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                System.out.print(mapa[x][y] + " ");
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

        mapa = new char[linha][coluna];

        int itens = Integer.parseInt(comandos[linha + 1].trim());

        // int contador = 0;

        int x = 0;
        int y = 0;
        while (x < linha) {
            String caminho = comandos[i++];
            y = 0;
            while (y < coluna) {
                mapa[x][y] = caminho.trim().charAt(y);
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

        origem = new Coordenada(origemX, origemY);
        destino = new Coordenada(destinoX, destinoY); 
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
    public static void main(String[] args) {
        String arquivoEntrada = args[0];
        String entrada = lerArquivo(arquivoEntrada);
        inicializarVariaveis(entrada);

        System.out.println(linhas);
        System.out.println(colunas);

        System.out.println(origem.getX() + ", " + origem.getY());
        System.out.println(destino.getX() + ", " + destino.getY());

        imprimirMapa();
    }
}
