public class Coordenada {
    int x;
    int y;

    Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }

    public void imprimir() {
        System.out.println(this.toString());
    }

    public boolean igual(Coordenada coordenada) {
        return this.x == coordenada.getX() && this.y == coordenada.getY();
    }
}
