import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class EP {

    public static int linhas, colunas, numeroItens;
    public static ArrayList<ItemMapa> items = new ArrayList<ItemMapa>();
    public static char[][] mapa;
    public static Coordenada[][] mapaCaminho;
    public static Coordenada origem;
    public static Coordenada destino;

    public static final char LIVRE = '.';

    // Imprime o mapa (com ".", "X" e "*")
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

    // Retorna o tempo total levado para realizar o caminho
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

    // Se houver um item na posicao passada como parametro, retorna o item. Senao, retorna null
    public static ItemMapa temItem(Coordenada coordenada) {
        for (int i = 0; i < items.size(); i++) {
            ItemMapa item = items.get(i);
            if (item.coordenada.igual(coordenada)) {
                return item;
            }
        }
        return null;
    }

    // Retorna o tempo de um passo dentro do caminho (baseado no peso no momento do passo)
    public static double tempoPasso(int peso) {
        double base = 1 + ((double) peso / 10);
        return Math.pow(base, 2);
    }

    // Imprime o mapa retornado pelos metodos dos caminhos
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

    // Inicializa as variáveis necessárias a partir da entrada do programa
    public static void inicializarVariaveis(String entrada) {
        String[] comandos = entrada.split("\n");
        int i = 1;

        String coordenadas = comandos[0];

        int linha = Integer.parseInt(coordenadas.split(" ")[0].trim());
        int coluna = Integer.parseInt(coordenadas.split(" ")[1].trim());

        mapa = new char[coluna][linha];

        int itens = Integer.parseInt(comandos[linha + 1].trim());

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
    }

    // Inicializa mapa auxiliar
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

    // Inicializa mapa auxiliar com coordenadas visitadas
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

    // Interpreta o retorno de "caminhoMaisCurto" para que a saída esteja da forma
    // pedida
    public static Coordenada[] interpretaMapaCaminho(Coordenada[][] mapaCaminho) {
        Coordenada d = destino;
        int pesoTotal = 0;
        int valorTotal = 0;
        int contador = 0;
        double tempo = 0;

        // Calcula número de passos do caminho encontrado
        while (!d.igual(origem)) {
            contador++;
            d = mapaCaminho[d.getY()][d.getX()];
        }

        // Inicializa arranjos auxiliares
        Coordenada[] caminhoInvertido = new Coordenada[contador];
        Coordenada[] caminhoCorreto = new Coordenada[contador];
        caminhoInvertido[0] = destino;

        // Lista auxiliar de itens achados pelo caminho para poder calcular o peso total
        // e tempo do caminho
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
        System.out.println(caminhoCorreto.length + 1 + " " + tempo);

        // Calcula peso e valor total do caminho baseado nos itens achados
        for (int x = 0; x < itensAchados.size(); x++) {
            pesoTotal += itensAchados.get(x).peso;
            valorTotal += itensAchados.get(x).valor;
        }

        // Imprime saída da forma pedida
        i = 0;
        while (i < caminhoCorreto.length) {
            caminhoCorreto[i++].imprimirSaida();
        }
        destino.imprimirSaida();

        // Imprime o número de itens achados, o valor total e o peso total
        System.out.println(itensAchados.size() + " " + valorTotal + " " + pesoTotal);

        // Imprime as coordenadas do itens achados
        for (int x = 0; x < itensAchados.size(); x++) {
            itensAchados.get(x).coordenada.imprimirSaida();
        }
        return caminhoCorreto;
    }

    // Lê o arquivo de entrada e retorna o seu conteúdo em uma String concatenada
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

    // Retorna verdadeiro caso a posicao do mapa esteja livre e falso caso
    // contrario.
    public static boolean estaLivre(Coordenada destino) {
        return mapa[destino.getY()][destino.getX()] == '.';
    }

    // Retorna verdadeiro caso seja posicao andar para a posicao desejada (passada
    // como parametro) e falso caso contrário
    public static boolean possivelAndar(Coordenada destinoCaminho) {
        // Está dentro dos limites do mapa
        boolean dentroDoMapa = destinoCaminho.getY() < colunas && destinoCaminho.getX() < linhas
                && destinoCaminho.getX() >= 0 && destinoCaminho.getY() >= 0;

        // Está livre (sem obstáculos e não foi visitado ainda)
        boolean livre = false;
        if (dentroDoMapa)
            livre = estaLivre(destinoCaminho);

        return livre;
    }

    /*
     * Abaixo seguem 4 métodos responsáveis por retornar as coordenadas à direita,
     * esquerda, cima e baixo da coordenada recebida como parâmetro.
     */

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

    // Esse método é responsável por retornar o caminho mais curto entre duas
    // coordenadas (menor número de coordenadas visitadas dentro do mapa)
    public static Coordenada[][] caminhoMaisCurto(Coordenada origem, Coordenada destino) {
        Queue<Coordenada> fila = new LinkedList<Coordenada>();
        Coordenada current;
        Coordenada direita, esquerda, baixo, cima;

        /*
         * Utiliza-se duas matrizes auxiliares, uma contendo um mapa idêntico ao
         * principal para saber quais posições já foram visitadas e outra para manter
         * controle das posições visitadas (origem de cada mudança de coordenada fica
         * salva, possibilitando a reconstrução do caminho posteriormente)
         */
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
        }
        return null;
    }

    // Responsável por rodar o programa, aceita dois argumentos: arquivo de entrada
    // e criteiro de busca
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("QUANTIDADE INSUFICIENTE DE ARGUMENTOS NA EXECUCAO");
            return;
        }
        String arquivoEntrada = args[0];
        String criterio = args[1];

        String entrada = lerArquivo(arquivoEntrada);
        inicializarVariaveis(entrada);

        switch (criterio) {
            case "1":
                Coordenada[][] mapa = caminhoMaisCurto(origem, destino);
                if (mapa != null) {
                    Coordenada caminho[] = interpretaMapaCaminho(mapa);
                } else {
                    System.out.println("NAO HA CAMINHO DISPONIVEL");
                }
                break;
            case "2":
                // Completar aqui
                break;
            case "3":
                // Completar aqui
                break;
            case "4":
                // Completar aqui
                break;    
        }

    }
}
