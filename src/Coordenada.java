/**
 * Introdução à Análise de Algoritmos
 * 2º semestre de 2020 - Turmas 04 e 94
 * Exercício Programa 2: Labirinto
 * 
 * Integrantes da dupla:
 * Bruno Furquim de Campos Morone - 10403248
 * Thiago de Oliveira Deodato - 10258938
 */


public class Coordenada {
    int x;
    int y;

    public Coordenada(int x, int y) {
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
        return "[" + this.y + ", " + this.x + "]";
    }

    public void imprimir() {
        System.out.println(this.toString());
    }

    public void imprimirNaFila() {
        System.out.print(this.toString() + " ");
    }

    public void imprimirSaida() {
        System.out.println(this.x + " " + this.y);
    }

    public boolean igual(Coordenada coordenada) {
        return this.x == coordenada.getX() && this.y == coordenada.getY();
    }
}
