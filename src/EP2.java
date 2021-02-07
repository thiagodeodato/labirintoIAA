import java.io.*;

public class EP2 {

    public static final boolean DEBUG = false;

    public static int [] findPath(Map map, int criteria){

        int lin, col, linAux, colAux; // coordenadas (lin, col) da posição atual

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

            if (DEBUG) {

                map.print();
                System.out.println("---------------------------------------------------------------");
            }

            if(criteria == 2) {
                map.findLongestPath(lin, col);
                for(int a = 0; a<map.nLines(); a++){
                    for (int b = 0; b<map.nColumns(); b++){
                        if(map.maxVisited[a][b] != 0){
                            map.step(a,b);
                        }
                    }
                }
                while(lin != -1 && col != -1){
                    linAux = map.maxVisitedLin[lin][col];
                    colAux = map.maxVisitedCol[lin][col];
                    path[path_index] = linAux;
                    path[path_index + 1] = colAux;
                    path_index += 2;
                    if(lin == map.getEndLin() && col == map.getEndCol()){
                        lin = -1;
                        col = -1;
                    } else {
                        lin = linAux;
                        col = colAux;
                    }
                }
                map.step(map.getEndLin(),map.getEndCol());
                path_index -=2;
            }

        path[0] = path_index;
        return path;
    }

    public static void printSolution(Map map, int [] path){

        // A partir do mapa e do path contendo a solução, imprime a saída conforme especificações do EP.

        int totalItems = 0;
        int totalValue = 0;
        int totalWeight = 0;
        double pathTime = 0;
        Item [] items = new Item[map.getNItems()];
        int nextEmptyIndex = 0;
        String [] solution = new String[2 * map.getSize()];
        int indexSolution = 0;

        int path_size = path[0];

        solution[indexSolution] = "" + (path_size - 1)/2 + " ";
        indexSolution++;
        //System.out.println((path_size - 1)/2 + " " + 0.0);

        for(int i = 1; i < path_size; i += 2){

            int lin = path[i];
            int col = path[i + 1];
            Item item = map.getItem(lin, col);

            solution[indexSolution] = "" + lin + " " + col;
            indexSolution++;
            // lista dos itens coletados
            if(item != null){
                items[nextEmptyIndex] = item;
                nextEmptyIndex++;


                totalItems++;
                totalValue += item.getValue();
                totalWeight += item.getWeight();
            }if(i + 2 < path_size){
                pathTime += dev.tempoPasso(totalWeight);
            } else{
                solution[0] += pathTime;
            }
        }
        int printingSolutionIndex = 0;
        while(printingSolutionIndex < indexSolution){
            if(solution[printingSolutionIndex] != null){
                System.out.println(solution[printingSolutionIndex]);
            }
            printingSolutionIndex++;
        }
        System.out.println(totalItems + " " + totalValue + " " + totalWeight);
        for(int i = 0; i < nextEmptyIndex; i++){
            System.out.println(items[i].getLin() + " " + items[i].getCol());
        }
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