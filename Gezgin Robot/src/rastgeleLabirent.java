import java.util.Random;

public class rastgeleLabirent {

    private final char[][] maze;
    private final int rows;
    private final int cols;
    private int baslangicX=0;
    private int baslangicY=0;
    private int bitisX=0;
    private int bitisY=0;
    public rastgeleLabirent(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new char[rows][cols];
        atamaYap();
        generate();
    }
    private void atamaYap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = '1';
            }
        }
        for (int i = 0; i < rows; i++) {
            maze[i][0] = '1';
            maze[i][cols-1] = '1';
        }
        for (int j = 0; j < cols; j++) {
            maze[0][j] = '1';
            maze[rows-1][j] = '1';
        }
    }

    private void generate() {
        Random random = new Random();
        int b = random.nextInt(4);

        switch(b){
            case 0:
                baslangicX = 1;
                baslangicY = 1;
                bitisX = rows-2;
                bitisY = cols-2;
                maze[baslangicX][baslangicY] = 0;
                break;

            case 1:
                baslangicX = rows-2;
                baslangicY = 1;
                bitisX = 1;
                bitisY = cols-2;
                maze[baslangicX][baslangicY] = 0;
                break;

            case 2:
                baslangicX = 1;
                baslangicY = cols-2;
                bitisX = rows-2;
                bitisY = 1;
                maze[baslangicX][baslangicY] = 0;
                break;

            case 3:
                baslangicX = rows-2;
                baslangicY = cols-2;
                bitisX = 1;
                bitisY = 1;
                maze[baslangicX][baslangicY] = 0;
                break;

        }
        System.out.println("Baslangıç noktası : " + baslangicY + " "+ baslangicX);
        System.out.println("Bitiş noktası : " + bitisY + " " + bitisX);

        dfs(baslangicX, baslangicY);
    }

    private void dfs(int row, int col) {
        int[][] komsular = {{row-2, col}, {row+2, col}, {row, col-2}, {row, col+2}};
        olustur(komsular);

        for (int[] komsu : komsular) {
            int r = komsu[0];
            int c = komsu[1];

            if (r < 1 || c < 1 || r >= rows-1 || c >= cols-1 || maze[r][c] != '1') {
                continue;
            }
            if (r == row-2) {
                maze[row-1][col] = '0';
            } else if (r == row+2) {
                maze[row+1][col] = '0';
            } else if (c == col-2) {
                maze[row][col-1] = '0';
            } else if (c == col+2) {
                maze[row][col+1] = '0';
            }
            maze[r][c] = '0';
            dfs(r, c);
        }
    }

    private void olustur(int[][] arr) {
        Random random = new Random();
        for (int i = arr.length-1; i > 0; i--) {
            int j = random.nextInt(i+1);
            int[] temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public char[][] getMaze() {
        return maze;
    }
    public int getBaslangicX(){return baslangicX;}
    public int getBaslangicY(){return baslangicY;}
    public int getBitisX(){
        return bitisX;
    }
    public int getBitisY(){
        return bitisY;
    }
}
