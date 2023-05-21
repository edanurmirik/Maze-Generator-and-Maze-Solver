import java.util.*;

public class EnKisaYol {
    private static final int[] ROW_MOVES = {-1, 0, 1, 0};
    private static final int[] COL_MOVES = {0, 1, 0, -1};
    private char[][] maze;
    private int rows;
    private int cols;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean[][] visited;
    private ArrayList<Yol> gezilenYollar = new ArrayList<>();
    private ArrayList<Yol> gezilmeyenYollar = new ArrayList<>();
    private ArrayList<Yol> solutionPath = new ArrayList<>();
    private ArrayList<String> solution = new ArrayList<>();
    List<int[]> path = new ArrayList<>();
    Queue<int[]> queue = new LinkedList<>();
    Map<int[], int[]> prev = new HashMap<>();

    public EnKisaYol(char[][] maze, int startX, int startY, int endX, int endY) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.visited = new boolean[rows][cols];
    }

    public void solve() {

        int[] start = {startX, startY};
        queue.offer(start);
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            if (curr[0] == endX && curr[1] == endY) {
                int[] node = curr;
                while (node != null) {
                    path.add(node);
                    node = prev.get(node);
                }
                Collections.reverse(path);
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextRow = curr[0] + ROW_MOVES[i];
                int nextCol = curr[1] + COL_MOVES[i];

                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols
                        && maze[nextRow][nextCol] == '0' && !visited[nextRow][nextCol]) {
                    int[] next = {nextRow, nextCol};
                    queue.offer(next);
                    visited[nextRow][nextCol] = true;
                    prev.put(next, curr);
                }
            }
        }
    }

    public void print(){

        for (int i = 0; i < path.size(); i++) {
            int a = Integer.parseInt(Arrays.toString(path.get(i)).substring(1,Arrays.toString(path.get(i)).indexOf(',')));
            int b = Integer.parseInt(Arrays.toString(path.get(i)).substring(Arrays.toString(path.get(i)).indexOf(' ')+1,Arrays.toString(path.get(i)).indexOf(']')));
            solutionPath.add(new Yol(b,a));
        }

        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
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

    public ArrayList<Yol> getSolutionPath(){return solutionPath;}
    public ArrayList<Yol> getGezilenYollar(){return gezilenYollar;}
    public ArrayList<Yol> getGezilmeyenYollar(){return gezilmeyenYollar;}
}

