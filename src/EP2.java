import java.io.*;

public class EP2 {

    public static final boolean DEBUG = false;

    public static int [] findPath(Map map, int criteria){

        int lin, col; // coordenadas (lin, col) da posição atual

        // path é um vetor de inteiro usado para guardar as coordenadas do caminho conforme vai sendo calculado.
        // path_index é usado para gerenciar a ocupação deste vetor. O vetor é usado da seguinte forma:
        //
        // path[0] = quantidade de valores efetivamente armazenados no vetor (não necessáriamente coincide com o tamanho real do vetor)
        // path[1] = linha da 1.a coordenada que faz parte do caminho
        // path[2] = coluna da 1.a coordenada que faz parte do caminho
        // path[3] = linha da 2.a coordenada que faz parte do caminho
        // path[4] = coluna da 2.a coordenada que faz parte do caminho
        // ... etc

        int [] path;
        int path_index;

        path = new int[2 * map.getSize()];
        path_index = 1;

        lin = map.getStartLin();
        col = map.getStartCol();

        // efetivação de um passo
        map.step(lin, col);		// marcamos no mapa que a posição está sendo ocupada.
        path[path_index] = lin;		// adicionamos as coordenadas da posição (lin, col) no path
        path[path_index + 1] = col;
        path_index += 2;

        if(DEBUG){

            map.print();
            System.out.println("---------------------------------------------------------------");
        }

        while(!map.finished(lin, col)){

            if(lin - 1 >= 0 && map.free(lin - 1, col)){			// cima

                //System.out.println("UP");
                lin = lin - 1;
            }
            else if(col + 1 < map.nColumns() && map.free(lin, col + 1)){	// direita

                //System.out.println("RIGHT");
                col = col + 1;
            }
            else if(lin + 1 < map.nLines() && map.free(lin + 1, col)){	// baixo

                //System.out.println("DOWN");
                lin = lin + 1;
            }
            else if(col - 1 >= 0 && map.free(lin, col - 1)){		// esquerda

                //System.out.println("LEFT");
                col = col - 1;
            }
            else{
                //System.out.println("BREAK!");
                break; // não existe passo a ser dado a partir da posição atual...
            }

            map.step(lin, col);
            path[path_index] = lin;
            path[path_index + 1] = col;
            path_index += 2;

            if(DEBUG){
                map.print();
                System.out.println("---------------------------------------------------------------");
            }
        }

        path[0] = path_index;
        return path;
    }

    public static void printSolution(Map map, int [] path){

        // A partir do mapa e do path contendo a solução, imprime a saída conforme especificações do EP.

        int totalItems = 0;
        int totalValue = 0;
        int totalWeight = 0;

        int path_size = path[0];

        System.out.println((path_size - 1)/2 + " " + 0.0);

        for(int i = 1; i < path_size; i += 2){

            int lin = path[i];
            int col = path[i + 1];
            Item item = map.getItem(lin, col);

            System.out.println(lin + " " + col);

            if(item != null){

                totalItems++;
                totalValue += item.getValue();
                totalWeight += item.getWeight();
            }
        }

        // Estamos ignorando os itens que são coletados no caminho. Isso precisa ser modificado para a versão final.
        System.out.println("0 0 0");
    }

    public static void main(String [] args) throws IOException {

        Map map = new Map(args[0]);

        if(DEBUG){
            map.print();
            System.out.println("---------------------------------------------------------------");
        }

        int criteria = Integer.parseInt(args[1]);
        int [] path = findPath(map, criteria);
        printSolution(map, path);
    }
}