import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class MazeToSwing {
    Maze m = new Maze();
    int height = m.height;
    int width = m.width;

    int[][] maze = new int[height][width];
    int[][] solvedMaze = new int[height][width];

    public void callMethods() throws FileNotFoundException, InterruptedException {
        readMazeFromFile();
        readSolvedMazeFromFile();
        showInWindow();
    }

    public void readMazeFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("maze.txt")));
        while(sc.hasNextLine()) {
            for(int i = 0; i < maze.length; i++) {
                String[] line = sc.nextLine().split("");
                for(int j = 0; j < line.length; j++) {
                    maze[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void readSolvedMazeFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("solvedMaze.txt")));
        while(sc.hasNextLine()) {
            for(int i = 0; i < solvedMaze.length; i++) {
                String[] line = sc.nextLine().split("");
                for(int j = 0; j < line.length; j++) {
                    solvedMaze[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++){
                System.out.print(solvedMaze[i][j]);
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    public void showInWindow() throws InterruptedException {
        initializeWindow();
        initializeWindowSolved();
        
    }

    private void initializeWindow() {
        JFrame mainFrame = new JFrame("Randomly generated maze");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(maze.length, maze[0].length));// avoid null layouts
        //mainFrame.setSize(1920, 1080); //use preferred size and let layout manager set the size
        mainFrame.setLocationRelativeTo(null);

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                JLabel label = makeLabel(maze[row][col]);
                mainFrame.add(label);
            }
        }
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private JLabel makeLabel(int c) {

        JLabel label= new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(40, 40));
        switch(c) {
            case 1:
                label.setBackground(Color.black);
                break;
            case 0:
                label.setBackground(Color.WHITE);
                break;
            case 5:
                label.setBackground(Color.GREEN);
                break;
            case 7:
                label.setBackground(Color.red);
                break;
            default:
                System.out.println("error when printing to JLabel");
                break;

        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
    }

    private void initializeWindowSolved() {
        JFrame mainFrame = new JFrame("Randomly generated maze (SOLVED)");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(solvedMaze.length, solvedMaze[0].length));// avoid null layouts
        //mainFrame.setSize(1920, 1080); //use preferred size and let layout manager set the size
        mainFrame.setLocationRelativeTo(null);

        for (int row = 0; row < solvedMaze.length; row++) {
            for (int col = 0; col < solvedMaze[0].length; col++) {
                JLabel label = makeLabelSolved(solvedMaze[row][col]);
                mainFrame.add(label);
            }
        }
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private JLabel makeLabelSolved(int c) {

        JLabel label= new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(40, 40));
        switch(c) {
            case 1:
                label.setBackground(Color.black);
                break;
            case 0:
                label.setBackground(Color.WHITE);
                break;
            case 5:
                label.setBackground(new Color(0,102,0));
                break;
            case 7:
                label.setBackground(Color.red);
                break;
            case 3:
                label.setBackground(new Color(102,255,102));
                break;
            default:
                System.out.println("error when printing to JLabel");
                break;

        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
    }
}
