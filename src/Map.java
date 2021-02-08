/**
 * Introdução à Análise de Algoritmos
 * 2º semestre de 2020 - Turmas 04 e 94
 * Exercício Programa 2: Labirinto
 * 
 * Integrantes da dupla:
 * Bruno Furquim de Campos Morone - 10403248
 * Thiago de Oliveira Deodato - 10258938
 */


import java.io.*;
import java.util.*;

public class Map {

    public static final char FREE = '.';

    private char[][] map;
    private Item[] items;
    private int nLin, nCol, nItems, startLin, startCol, endLin, endCol, size, dist, maxDist;
    private double minTime;
    public int[][] visited;
    public int[][] visitedLin;
    public int[][] visitedCol;
    public int[][] maxVisited;
    public int[][] maxVisitedLin;
    public int[][] maxVisitedCol;
    public String aux;
    public Item item;

    public Map(String fileName) {

        try {

            BufferedReader in = new BufferedReader(new FileReader(fileName));

            Scanner scanner = new Scanner(new File(fileName));


            nLin = scanner.nextInt();
            nCol = scanner.nextInt();

            map = new char[nLin][nCol];
            visited = new int[nLin][nCol];
            maxVisited = new int[nLin][nCol];
            visitedLin = new int[nLin][nCol];
            visitedCol = new int[nLin][nCol];
            maxVisitedLin = new int[nLin][nCol];
            maxVisitedCol = new int[nLin][nCol];
            size = 0;
            dist = 0;
            minTime = Integer.MAX_VALUE;

            for (int i = 0; i < nLin; i++) {

                String line = scanner.next();

                for (int j = 0; j < nCol; j++) {

                    map[i][j] = line.charAt(j);

                    if (free(i, j)) size++;
                }
            }

            nItems = scanner.nextInt();
            items = new Item[nItems];

            for (int i = 0; i < nItems; i++) {

                items[i] = new Item(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            }

            startLin = scanner.nextInt();
            startCol = scanner.nextInt();
            endLin = scanner.nextInt();
            endCol = scanner.nextInt();
        } catch (IOException e) {

            System.out.println("Error loading map... :(");
            e.printStackTrace();
        }
    }

    public void print() {

        System.out.println("Map size (lines x columns): " + nLin + " x " + nCol);

        for (int i = 0; i < nLin; i++) {

            for (int j = 0; j < nCol; j++) {

                System.out.print(map[i][j]);
            }

            System.out.println();
        }

        System.out.println("Number of items: " + nItems);

        for (int i = 0; i < nItems; i++) {

            System.out.println(items[i]);
        }
    }

    public boolean blocked(int lin, int col) {

        return !free(lin, col);
    }

    public boolean free(int lin, int col) {

        return map[lin][col] == FREE;
    }

    public void step(int lin, int col) {

        map[lin][col] = '*';
    }

    public boolean finished(int lin, int col) {

        return (lin == endLin && col == endCol);
    }

    public int getStartLin() {

        return startLin;
    }

    public int getStartCol() {

        return startCol;
    }

    public int getEndLin() {

        return endLin;
    }

    public int getEndCol() {

        return endCol;
    }

    public int getSize() {

        return size;
    }

    public int nLines() {

        return nLin;
    }

    public int nColumns() {

        return nCol;
    }

    public int getNItems() {

        return nItems;
    }

    public Item getItem(int lin, int col) {

        for (int i = 0; i < items.length; i++) {

            Item item = items[i];

            if (item.getLin() == lin && item.getCol() == col) return item;
        }

        return null;
    }

    public void findLongestPath(int lin, int col) {

        if (lin == endLin && col == endCol) {
            if(dist > maxDist){
                maxDist = dist;
                for(int i = 0; i<nLin; i++){
                    for(int j = 0; j<nCol; j++){
                        maxVisited[i][j] = visited[i][j];
                        maxVisitedLin[i][j] = visitedLin[i][j];
                        maxVisitedCol[i][j] = visitedCol[i][j];
                    }
                }
            }
            return;
        }

        visited[lin][col] = 1;
        //printVisited();

        if (lin - 1 >= 0 && free(lin - 1, col) && visited[lin - 1][col] == 0) {            // cima
            dist++;
            visitedLin[lin][col] = (lin - 1);
            visitedCol[lin][col] = col;
            findLongestPath(lin - 1, col);
        }
        if (col + 1 < nColumns() && free(lin, col + 1) &&  visited[lin][col + 1] == 0) {    // direita
            dist++;
            visitedLin[lin][col] = lin;
            visitedCol[lin][col] = col + 1;
            findLongestPath(lin, col + 1);
        }
        if (lin + 1 < nLines() && free(lin + 1, col) && visited[lin + 1][col] == 0) {    // baixo
            dist++;
            visitedLin[lin][col] = (lin + 1);
            visitedCol[lin][col] = col;
            findLongestPath(lin + 1, col);
        }
        if (col - 1 >= 0 && free(lin, col - 1) && visited[lin][col - 1] == 0) {        // esquerda
            dist++;
            visitedLin[lin][col] = lin;
            visitedCol[lin][col] = col - 1;
            findLongestPath(lin, col - 1);
        }
        visitedLin[lin][col] = 0;
        visitedCol[lin][col] = 0;
        visited[lin][col] = 0;
        dist --;
        //printVisited();
        //System.out.println("---------------------------------------------------------------");
    }

    public void findFastestPath(int lin, int col, double time, int weight) {
        item = null;
        if (lin == endLin && col == endCol) {
            if(time < minTime){
                minTime = time;
                for(int i = 0; i<nLin; i++){
                    for(int j = 0; j<nCol; j++){
                        maxVisited[i][j] = visited[i][j];
                        maxVisitedLin[i][j] = visitedLin[i][j];
                        maxVisitedCol[i][j] = visitedCol[i][j];
                    }
                }
            }
            return;
        }

        visited[lin][col] = 1;
        //printVisited();

        if (lin - 1 >= 0 && free(lin - 1, col) && visited[lin - 1][col] == 0) {            // cima
            item = getItem(lin - 1, col);
            if (item != null){
                weight += item.getWeight();
            }
            time += EP.tempoPasso(weight);
            visitedLin[lin][col] = (lin - 1);
            visitedCol[lin][col] = col;
            findFastestPath(lin - 1, col, time, weight);
        }
        if (col + 1 < nColumns() && free(lin, col + 1) &&  visited[lin][col + 1] == 0) {    // direita
            item = getItem(lin, col + 1);
            if (item != null){
                weight += item.getWeight();
            }
            time += EP.tempoPasso(weight);
            visitedLin[lin][col] = lin;
            visitedCol[lin][col] = col + 1;
            findFastestPath(lin, col + 1, time, weight);
        }
        if (lin + 1 < nLines() && free(lin + 1, col) && visited[lin + 1][col] == 0) {    // baixo
            item = getItem(lin + 1, col);
            if (item != null){
                weight += item.getWeight();
            }
            time += EP.tempoPasso(weight);
            visitedLin[lin][col] = (lin + 1);
            visitedCol[lin][col] = col;
            findFastestPath(lin + 1, col, time, weight);
        }
        if (col - 1 >= 0 && free(lin, col - 1) && visited[lin][col - 1] == 0) {        // esquerda
            item = getItem(lin, col - 1);
            if (item != null){
                weight += item.getWeight();
            }
            time += EP.tempoPasso(weight);
            visitedLin[lin][col] = lin;
            visitedCol[lin][col] = col - 1;
            findFastestPath(lin, col - 1, time, weight);
        }
        visitedLin[lin][col] = 0;
        visitedCol[lin][col] = 0;
        visited[lin][col] = 0;
        //printVisited();
        //System.out.println("---------------------------------------------------------------");

    }

    // classe auxiliar para visualizar campos da matriz visited
    public void printVisited() {

        System.out.println("Map size (lines x columns): " + nLin + " x " + nCol);

        for (int i = 0; i < nLin; i++) {

            for (int j = 0; j < nCol; j++) {

                System.out.print(visited[i][j]);
            }

            System.out.println();
        }
    }
}