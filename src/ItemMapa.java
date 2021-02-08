// Classe equivalente a Item do esqueleto do EP
public class ItemMapa {

    // Todo item tem uma coordenada dentro do mapa (Linhas x Colunas)
    public Coordenada coordenada;
    // Todo item tem um valor
    public int valor;
    // Todo item tem um peso
    public int peso;

    // Construtor - inicializa uma instância de ItemMapa a partir de uma String
    public ItemMapa(String infoItem) {
        String[] informacoes = infoItem.split(" ");
        this.coordenada = new Coordenada(Integer.parseInt(informacoes[0]), Integer.parseInt(informacoes[1]));
        this.valor = Integer.parseInt(informacoes[2]);
        this.peso = Integer.parseInt(informacoes[3]);
    }

    // Construtor - inicializa uma instância de ItemMapa a partir de uma coordenada, um valor e um peso
    public ItemMapa(Coordenada coordenada, int valor, int peso) {
        this.coordenada = coordenada;
        this.valor = valor;
        this.peso = peso;
    }

    // Imprime as informações do item (coordenada, valor e peso)
    public void imprimir() {
        System.out.println(this.coordenada.getY() + " " + this.coordenada.getX() + " " + this.valor + " " + this.peso);
    }

    // Imprime a coordenada do item
    public void imprimirCoordenada() {
        System.out.print("[" + this.coordenada.getY() + ", " + this.coordenada.getX() + "]");
    }
}
