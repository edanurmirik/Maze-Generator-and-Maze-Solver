import java.util.*;
import java.util.Stack;

public class MazeSolver {
    private char[][] maze;
    private boolean[][] visited;
    private int[] start;
    private int[] end;
    private Stack<int[]> path;
    private ArrayList<Yol> gezilenYollar = new ArrayList<>();
    private ArrayList<Yol> gezilmeyenYollar = new ArrayList<>();
    private ArrayList<Yol> solutionPath = new ArrayList<>();
    private ArrayList<String> solution = new ArrayList<>();

    public MazeSolver(char[][] maze, int[] start, int[] end) {
        this.maze = maze;
        this.visited = new boolean[maze.length][maze[0].length];
        this.start = start;
        this.end = end;
        this.path = new Stack<>();
    }

    public boolean solve() {
        visited[start[0]][start[1]] = true;
        path.push(start);
        return dfs(start);
    }

    private boolean dfs(int[] current) {

        if (current[0] == end[0] && current[1] == end[1]) {
            return true;
        }

        int[][] komsular = getKomsu(current);
        for (int[] komsu : komsular) {
            int row = komsu[0];
            int col = komsu[1];

            if (!visited[row][col] && maze[row][col] == '0') {
                visited[row][col] = true;
                path.push(komsu);

                if (dfs(komsu)) {
                    return true;
                }
                path.pop();
            }
        }
        return false;
    }

    private int[][] getKomsu(int[] current) {
        int row = current[0];
        int col = current[1];
        int[][] Komsu = {
                {row - 1, col}, // yukarı
                {row, col + 1}, // sağ
                {row + 1, col}, // aşağı
                {row, col - 1}  // sol
        };
        return Komsu;
    }

    public void print(int rows, int cols) {

        aktarma();

        for (int i = 0; i < solution.size(); i++) {
            int a = Integer.parseInt(solution.get(i).substring(1,solution.get(i).indexOf(',')));
            int b = Integer.parseInt(solution.get(i).substring(solution.get(i).indexOf(' ')+1,solution.get(i).indexOf(']')));
            solutionPath.add(new Yol(b,a));
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (visited[i][j]){
                    gezilenYollar.add(new Yol(j, i));
                } else {
                    gezilmeyenYollar.add(new Yol(j, i));
                }
            }
        }

        System.out.println("Gezilen hücre sayısı : " + gezilenYollar.size());
        /*for (int i = 0; i < gezilenYollar.size(); i++) {
            System.out.println("x, y : " + gezilenYollar.get(i).getX() + " " + gezilenYollar.get(i).getY());
        }*/
    }
    private void aktarma(){
        while(!path.isEmpty()) {
            //System.out.println(Arrays.toString(path.pop()));
            solution.add(Arrays.toString(path.pop()));
            aktarma();
        }
    }
    public ArrayList<Yol> getSolutionPath(){return solutionPath;}
    public ArrayList<Yol> getGezilenYollar(){return gezilenYollar;}
    public ArrayList<Yol> getGezilmeyenYollar(){return gezilmeyenYollar;}
}


