import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class dev {

    public static int linhas, colunas, numeroItens;
    public static ArrayList<ItemMapa> items = new ArrayList<ItemMapa>();
    public static char[][] mapa;
    public static Coordenada[][] mapaCaminho;
    public static Coordenada origem;
    public static Coordenada destino;
    public static Coordenada atual;
    public static ArrayList<Coordenada> historico = new ArrayList<Coordenada>();

    public static double tempo = 0;
    public static int peso = 0;

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

    public static double descobrirTempo(Coordenada[] caminho) {
        int peso = 0;
        double tempoTotal = 0;
        for (int i = 0; i < caminho.length; i++) {
            ItemMapa item = temItem(caminho[i]);
            if (item != null) {
                peso += item.peso;
            }
            tempoTotal += tempoPasso(peso);
        }
        return tempoTotal;
    }

    public static ItemMapa temItem(Coordenada coordenada) {
        for (int i = 0; i < items.size(); i++) {
            ItemMapa item = items.get(i);
            if (item.coordenada.igual(coordenada)) {
                return item;
            }
        }
        return null;
    }

    public static double tempoPasso(int peso) {
        double base = 1 + ((double) peso / 10);
        return Math.pow(base, 2);
    }

    public static Coordenada[] interpretaMapaCaminho(Coordenada[][] mapaCaminho) {
        Coordenada d = destino;
        int pesoTotal = 0;
        int valorTotal = 0;
        int contador = 0;
        double tempo = 0;
        while (!d.igual(origem)) {
            contador++;
            d = mapaCaminho[d.getY()][d.getX()];
        }
        Coordenada[] caminhoInvertido = new Coordenada[contador];
        Coordenada[] caminhoCorreto = new Coordenada[contador];
        caminhoInvertido[0] = destino;

        ArrayList<ItemMapa> itensAchados = new ArrayList<ItemMapa>();

        d = destino;
        int i = 0;
        while (!d.igual(origem)) {
            d = mapaCaminho[d.getY()][d.getX()];
            ItemMapa item = temItem(d);
            if (item != null) {
                itensAchados.add(item);
            }
            caminhoInvertido[i] = d;
            caminhoCorreto[caminhoCorreto.length - 1 - i] = d;
            i++;
        }

        tempo = descobrirTempo(caminhoCorreto);
        System.out.println(caminhoInvertido.length + 1 + " " + tempo);

        for (int x = 0; x < itensAchados.size(); x++) {
            pesoTotal += itensAchados.get(x).peso;
            valorTotal += itensAchados.get(x).valor;
        }

        i = 0;
        while (i < caminhoCorreto.length) {
            caminhoCorreto[i++].imprimirSaida();
        }
        destino.imprimirSaida();

        System.out.println(itensAchados.size() + " " + valorTotal + " " + pesoTotal);

        for (int x = 0; x < itensAchados.size(); x++) {
            itensAchados.get(x).coordenada.imprimirSaida();
        }
        return caminhoInvertido;
    }

    public static void imprimirMapaCaminho(Coordenada[][] mapaCaminho) {
        int x = 0;
        int y = 0;

        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                // if (atual.getX() == x && atual.getY() == y) System.out.print("A ");
                // else
                System.out.print(mapaCaminho[y][x].toString() + " ");
                y++;
            }
            System.out.println();
            x++;
        }
    }

    public static void imprimirHistorico() {
        for (int i = 0; i < historico.size(); i++) {
            Coordenada x = historico.get(i);
            if (i == 0)
                System.out.print("Inicial: ");
            else if (i == historico.size() - 1)
                System.out.print("Final: ");
            x.imprimir();
        }
        System.out.print("Número de passos dados: ");
        System.out.println(historico.size());
    }

    public static void inicializarVariaveis(String entrada) {
        String[] comandos = entrada.split("\n");
        int i = 1;

        String coordenadas = comandos[0];

        int linha = Integer.parseInt(coordenadas.split(" ")[0].trim());
        int coluna = Integer.parseInt(coordenadas.split(" ")[1].trim());

        mapa = new char[coluna][linha];
        mapaCaminho = new Coordenada[coluna][linha];

        int itens = Integer.parseInt(comandos[linha + 1].trim());

        // int contador = 0;

        int x = 0;
        int y = 0;
        while (x < linha) {
            String caminho = comandos[i++];
            y = 0;
            while (y < coluna) {
                mapa[y][x] = caminho.trim().charAt(y);
                mapaCaminho[y][x] = new Coordenada(-1, -1);
                y++;
            }
            x++;
        }

        i = linha + 2;
        int contagem = i + itens;
        while (i < contagem) {
            ItemMapa item = new ItemMapa(comandos[i++].trim());
            items.add(item);
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
        atual = origem;
    }

    public static Boolean[][] inicializarMapaVisitados() {
        Boolean[][] mapa = new Boolean[colunas][linhas];
        int x = 0;
        int y = 0;
        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                mapa[y][x] = false;
                y++;
            }
            x++;
        }
        return mapa;
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
        boolean dentroDoMapa = destinoCaminho.getY() < colunas && destinoCaminho.getX() < linhas
                && destinoCaminho.getX() >= 0 && destinoCaminho.getY() >= 0;

        boolean livre = false;
        if (dentroDoMapa)
            livre = estaLivre(destinoCaminho);
        return livre && !destinoCaminho.visitada;
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

    public static char conteudoPosicao(Coordenada posicao) {
        return mapa[posicao.getY()][posicao.getX()];
    }

    public static void andar(Coordenada destino) {
        mapa[destino.getY()][destino.getX()] = '*';
    }

    public static Coordenada direita(Coordenada atual) {
        return new Coordenada(atual.getX(), atual.getY() + 1);
    }

    public static Coordenada esquerda(Coordenada atual) {
        return new Coordenada(atual.getX(), atual.getY() - 1);
    }

    public static Coordenada cima(Coordenada atual) {
        return new Coordenada(atual.getX() + 1, atual.getY());
    }

    public static Coordenada baixo(Coordenada atual) {
        return new Coordenada(atual.getX() - 1, atual.getY());
    }

    public static void imprimirFila(Queue<Coordenada> fila) {
        Queue<Coordenada> novaFila = new LinkedList<Coordenada>(fila);
        while (!novaFila.isEmpty()) {
            novaFila.poll().imprimirNaFila();
            ;
        }
        System.out.println();
    }

    public static Coordenada[][] inicializarMapaCaminho() {
        Coordenada[][] mapa = new Coordenada[colunas][linhas];
        int x = 0;
        int y = 0;
        while (x < linhas) {
            y = 0;
            while (y < colunas) {
                mapa[y][x] = new Coordenada(-1, -1);
                y++;
            }
            x++;
        }
        return mapa;
    }

    public static Coordenada[][] caminhoMaisCurto(Coordenada origem, Coordenada destino) {
        Queue<Coordenada> fila = new LinkedList<Coordenada>();
        Coordenada current;
        Coordenada direita, esquerda, baixo, cima;

        Boolean[][] visitados = inicializarMapaVisitados();
        Coordenada[][] mapaCaminho = inicializarMapaCaminho();

        fila.add(origem);
        visitados[origem.getY()][origem.getX()] = true;
        mapaCaminho[origem.getY()][origem.getX()] = new Coordenada(origem.getX(), origem.getY());

        while (!fila.isEmpty()) {
            current = fila.poll();
            mapa[current.getY()][current.getX()] = '*';

            direita = direita(current);
            esquerda = esquerda(current);
            baixo = baixo(current);
            cima = cima(current);

            Coordenada[] vizinhos = { direita, esquerda, baixo, cima };

            if (current.igual(destino))
                return mapaCaminho;

            for (int i = 0; i < vizinhos.length; i++) {
                Coordenada vizinho = vizinhos[i];
                if (possivelAndar(vizinho) && !visitados[vizinho.getY()][vizinho.getX()]) {
                    fila.add(vizinho);
                    visitados[vizinho.getY()][vizinho.getX()] = true;
                    mapaCaminho[vizinho.getY()][vizinho.getX()] = new Coordenada(current.getX(), current.getY());
                }
            }
            // if (destino.igual(current)) {
            // mapa[current.getY()][current.getX()] = new Coordenada(anterior.getX(),
            // anterior.getY());
            // andar(anterior, current);
            // return mapa;
            // } else {
            // mapa[current.getY()][current.getX()] = new Coordenada(anterior.getX(),
            // anterior.getY());
            // andar(anterior, current);
            // if (possivelAndar(direita(current)))
            // fila.add(direita(current));
            // if (possivelAndar(esquerda(current)))
            // fila.add(esquerda(current));
            // if (possivelAndar(cima(current)))
            // fila.add(cima(current));
            // if (possivelAndar(baixo(current)))
            // fila.add(baixo(current));
            // }
            // anterior = new Coordenada(current.getX(), current.getY());
        }
        return null;
    }

    public static boolean chegou() {
        return atual.igual(destino);
    }

    public static void main(String[] args) {
        String arquivoEntrada = args[0];
        String entrada = lerArquivo(arquivoEntrada);
        inicializarVariaveis(entrada);

        Coordenada[][] mapa = caminhoMaisCurto(origem, destino);
        if (mapa != null) {
            Coordenada caminho[] = interpretaMapaCaminho(mapa);
        } else {
            System.out.println("NAO HA CAMINHO DISPONIVEL");
        }
    }
}
