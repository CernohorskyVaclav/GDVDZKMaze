// 5 => start
// 7 => finish
// 0 => volná cesta
// 1 => zeď
// 2 => rozcestí
// 3 => už prošlé políčko
// 4 => špatná cesta



import java.util.*;
import java.io.*;


public class Maze {
    final public int height = 37;
    final public int width = 37;
    int rs;
    int cs;
    int[][] maze = new int[height][width];
    int[][] solvedMaze;
    boolean solved;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Maze m = new Maze();
        MazeToSwing mts = new MazeToSwing();
        m.generateMaze();
        mts.callMethods();
    }

    public void generateMaze() {
        Random random = new Random();

        // Vytvoří 2d pole a ná na každé políčko 1. Jednička je zeď. Nula je volné políčko
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                maze[i][j] = 1;
            }
        }
        //vygereruje random řadu (r => row)
        int r = random.nextInt(height);
        while (r % 2 == 0) {
            r = random.nextInt(height);
        }

        // vygereruje random sloupec (c => column)
        int c = random.nextInt(width);
        while (c % 2 == 0) {
            c = random.nextInt(width);
        }

        //Startovní pozice pozice
        maze[r][c] = 0;
        /*
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");*/

        rs = r;
        cs = c;
        recursion(r, c);
        printMaze();
        writeMazeToFile();
        solveMaze();
        writeSolvedMazeToFile();
    }

    public void recursion(int r, int c) {
        // 4 náhodné směry
        Integer[] randDirs = generateRandomDirections();
        // Zkontroluje každý směr
        randDirs = generateRandomDirections();
        for (int i = 0; i < (randDirs.length); i++) {
            switch(randDirs[i]){
                case 1: // Up
                    // Jestli když půjde o dva bloky nahoru tak vypadne z pole
                    if (r - 2 <= 0)
                        continue;
                    if (maze[r - 2][c] != 0) {
                        maze[r-2][c] = 0;
                        maze[r-1][c] = 0;
                        recursion(r - 2, c);
                    }
                    break;
                case 2: // Right
                    // Jestli když půjde o dva bloky doprava tak vypadne z pole
                    if (c + 2 >= width - 1)
                        continue;
                    if (maze[r][c + 2] != 0) {
                        maze[r][c + 2] = 0;
                        maze[r][c + 1] = 0;
                        recursion(r, c + 2);
                    }
                    break;
                case 3: // Down
                    // Jestli když půjde o dva bloky dolu tak vypadne z pole
                    if (r + 2 >= height - 1)
                        continue;
                    if (maze[r + 2][c] != 0) {
                        maze[r+2][c] = 0;
                        maze[r+1][c] = 0;
                        recursion(r + 2, c);
                    }
                    break;
                case 4: // Left
                    // Jestli když půjde o dva bloky doleva tak vypadne z pole
                    if (c - 2 <= 0)
                        continue;
                    if (maze[r][c - 2] != 0) {
                        maze[r][c - 2] = 0;
                        maze[r][c - 1] = 0;
                        recursion(r, c - 2);
                    }
                    break;
            }

        }

    }

    public void writeMazeToFile() {
        String valueOfMaze;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("maze.txt", false))) {

            for(int i = 0; i < height; i++) {
                for(int y = 0; y < width; y++) {
                    valueOfMaze = String.valueOf(maze[i][y]);
                    writer.write(valueOfMaze);
                }
                writer.write("\n");
            }
            System.out.println("Successfully wrote to the file.");
        } catch(IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }

    }

    //Generuje array s náhodnými směry (1-4)
    public Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }

    //vypíše pole do cmd
    public void printMaze() {
        //startovni pozice
        maze[rs][cs] = 5;

        maze[width-1][height - 2] = 7;

        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");*/
    }

    public void solveMaze() {
        int sr = rs;
        int sc = cs;
        int pathCounter = 0;
        solvedMaze = maze;
        while(!solved) {
            solvedMaze = maze;
            pathCounter = 0;

            //rozcestí
            for(int i = 1; i < 5; i++) {
                switch(i){
                    case 1: // Up
                        if (solvedMaze[sr - 1][sc] == 0) {
                            pathCounter++;
                        }
                        break;
                    case 2: // Right
                        if (solvedMaze[sr][sc+1] == 0) {
                            pathCounter++;
                        }
                        break;
                    case 3: // Down
                        if (solvedMaze[sr + 1][sc] == 0) {
                            pathCounter++;
                        }
                        break;
                    case 4: // Left
                        if (solvedMaze[sr][sc-1] == 0) {
                            pathCounter++;
                        }
                        break;
                }
            }

            //označení rozcestí
            if(pathCounter > 1) {
                solvedMaze[sr][sc] = 2;
            }

            //označování správné cesty
            if(solvedMaze[sr + 1][sc] == 0 || solvedMaze[sr + 1][sc] == 7) {
                if(solvedMaze[sr + 1][sc] == 0) {
                    solvedMaze[sr + 1][sc] = 3;
                    sr += 1;
                } else {
                    System.out.println("Cesta nalezena");
                    solved = true;
                    break;
                }
            } else if(solvedMaze[sr][sc + 1] == 0 || solvedMaze[sr + 1][sc] == 7) {
                if(solvedMaze[sr][sc + 1] == 0) {
                    solvedMaze[sr][sc + 1] = 3;
                    sc += 1;
                } else {
                    System.out.println("Cesta nalezena");
                    solved = true;
                    break;
                }
            } else if(solvedMaze[sr - 1][sc] == 0 || solvedMaze[sr - 1][sc] == 7) {
                if(solvedMaze[sr - 1][sc] == 0) {
                    solvedMaze[sr - 1][sc] = 3;
                    sr -=1;
                } else {
                    System.out.println("Cesta nalezena");
                    solved = true;
                    break;
                }
            } else if(solvedMaze[sr][sc - 1] == 0 || solvedMaze[sr][sc - 1] == 0) {
                if(solvedMaze[sr][sc - 1] == 0) {
                    solvedMaze[sr][sc - 1] = 3;
                    sc -= 1;
                } else {
                    System.out.println("Cesta nalezena");
                    solved = true;
                    break;
                }
            }
            //přepisování správné cesty tou špatnou (4)
            else {
                solvedMaze[sr][sc] = 4;
                if(solvedMaze[sr + 1][sc] == 3) {
                    solvedMaze[sr + 1][sc] = 4;
                    sr += 1;
                } else if(solvedMaze[sr][sc + 1] == 3) {
                    solvedMaze[sr][sc + 1] = 4;
                    sc += 1;
                } else if(solvedMaze[sr - 1][sc] == 3) {
                    solvedMaze[sr - 1][sc] = 4;
                    sr -=1;
                } else if(solvedMaze[sr][sc - 1] == 3) {
                    solvedMaze[sr][sc - 1] = 4;
                    sc -= 1;
                }
                //přepisování rozceztí na správnou cestu (2 => 3)
                else {
                    System.out.println("fucking shit");
                    if(solvedMaze[sr + 1][sc] == 2) {
                        sr += 1;
                        solvedMaze[sr][sc] = 3;
                    } else if(solvedMaze[sr][sc + 1] == 2) {
                        sc += 1;
                        solvedMaze[sr][sc] = 3;
                    } else if(solvedMaze[sr - 1][sc] == 2) {
                        sr -=1;
                        solvedMaze[sr][sc] = 3;
                    } else if(solvedMaze[sr][sc - 1] == 2) {
                        sc -= 1;
                        solvedMaze[sr][sc] = 3;
                    } else {
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                if(solvedMaze[i][j] == 4) {
                    solvedMaze[i][j] = 0;
                } else if(solvedMaze[i][j] == 2) {
                    solvedMaze[i][j] = 3;
                }
            }
        }
        printSolvedMaze();
    }

    public void printSolvedMaze() {
        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                System.out.print(solvedMaze[i][j]);
            }
            System.out.println();
        }*/
    }

    public void writeSolvedMazeToFile() {
        String valueOfMaze;

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("solvedMaze.txt", false))) {

            for(int i = 0; i < height; i++) {
                for(int y = 0; y < width; y++) {
                    valueOfMaze = String.valueOf(solvedMaze[i][y]);
                    writer.write(valueOfMaze);
                }
                writer.write("\n");
            }
            System.out.println("Successfully wrote to the file.");
        } catch(IOException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
    }
}