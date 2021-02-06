public class ItemMapa {
    public Coordenada coordenada;
    public int valor;
    public int peso;

    public ItemMapa(String infoItem) {
        String[] informacoes = infoItem.split(" ");
        this.coordenada = new Coordenada(Integer.parseInt(informacoes[0]), Integer.parseInt(informacoes[1]));
        this.valor = Integer.parseInt(informacoes[2]);
        this.peso = Integer.parseInt(informacoes[3]);
    }

    public ItemMapa(Coordenada coordenada, int valor, int peso) {
        this.coordenada = coordenada;
        this.valor = valor;
        this.peso = peso;
    }

    public void imprimir() {
        System.out.println(this.coordenada.getY() + " " + this.coordenada.getX() + " " + this.valor + " " + this.peso);
    }

    public void imprimirCoordenada() {
        System.out.print("[" + this.coordenada.getY() + ", " + this.coordenada.getX() + "]");
    }
}
