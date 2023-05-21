import javax.swing.*;
import java.awt.*;
import java.util.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
public class Izgara {
    private int basX = 0, basY = 0, bitX = 0, bitY = 0;
    private  Yol robot = new Yol();
    private  ArrayList<Yol> robotYol = new ArrayList<>();
    private  ArrayList<Koordinat> rastgele = new ArrayList<>();
    private  ArrayList<Engel1> rastgeleEngel = new ArrayList<>();
    private  ArrayList<Yol> rastgeleYol = new ArrayList<>();
    private  ArrayList<JPanel> hucre = new ArrayList<>();
    private  JFrame haritaEkraniProb1 = new JFrame("Labirent Ekranı");
    private  JFrame haritaEkraniProb2 = new JFrame("Labirent Ekranı");

    public void haritaCiz(char[][] izgara, int satir,int sutun, ArrayList<Engel1> engel1, ArrayList<Engel2> engel2, ArrayList<Engel3> engel3, ArrayList<Yol> yollar) {

        for (int i = 0; i < engel1.size(); i++) {
            hucre.add(new JPanel());
            hucre.get(hucre.size() - 1).setBounds(engel1.get(i).getX() * 31, engel1.get(i).getY() * 31, 30, 30);
            hucre.get(hucre.size() - 1).setBackground(Color.red);
            haritaEkraniProb1.add(hucre.get(hucre.size() - 1));
        }
        for (int i = 0; i < engel2.size(); i++) {
            hucre.add(new JPanel());
            hucre.get(hucre.size() - 1).setBounds(engel2.get(i).getX() * 31, engel2.get(i).getY() * 31, 30, 30);
            hucre.get(hucre.size() - 1).setBackground(Color.BLUE);
            haritaEkraniProb1.add(hucre.get(hucre.size() - 1));
        }
        for (int i = 0; i < engel3.size(); i++) {
            hucre.add(new JPanel());
            hucre.get(hucre.size() - 1).setBounds(engel3.get(i).getX() * 31, engel3.get(i).getY() * 31, 30, 30);
            hucre.get(hucre.size() - 1).setBackground(Color.BLACK);
            haritaEkraniProb1.add(hucre.get(hucre.size() - 1));
        }

        baslangicvebitis(izgara, satir, sutun);

        EnKisaYol sol = new EnKisaYol(izgara, basX, basY, bitX, bitY);
        sol.solve();
        sol.print();

        ArrayList<Yol> gezilen = sol.getGezilenYollar();
        ArrayList<Yol> gezilmeyen = sol.getGezilmeyenYollar();
        ArrayList<Yol> solutionP = sol.getSolutionPath();

        System.out.println("Çıkış yolunun uzunluğu : " + solutionP.size());
        System.out.println("Çıkış yolları : ");

        for (int i = 0; i < solutionP.size(); i++) {
            System.out.println((i+1) + ". adım : x->" + solutionP.get(i).getY() + " y->" + solutionP.get(i).getX());
        }

        for (int i = 0; i < solutionP.size(); i++) {
            if(solutionP.get(i).getY() == basX && solutionP.get(i).getX() == basY ) {
                hucre.add(new JPanel());
                hucre.get(hucre.size()-1).setBounds(solutionP.get(i).getX() * 31, solutionP.get(i).getY() * 31, 30, 30);
                hucre.get(hucre.size()-1).setBackground(Color.YELLOW);
                haritaEkraniProb1.add(hucre.get(hucre.size()-1));

            }else if(solutionP.get(i).getY() == bitX && solutionP.get(i).getX() == bitY ){
                hucre.add(new JPanel());
                hucre.get(hucre.size()-1).setBounds(solutionP.get(i).getX() * 31,solutionP.get(i).getY() * 31,30,30);
                hucre.get(hucre.size()-1).setBackground(Color.MAGENTA);
                haritaEkraniProb1.add(hucre.get(hucre.size()-1));
            }else {
                hucre.add(new JPanel());
                hucre.get(hucre.size() - 1).setBounds(solutionP.get(i).getX() * 31, solutionP.get(i).getY() * 31, 30, 30);
                hucre.get(hucre.size() - 1).setBackground(Color.gray);
                haritaEkraniProb1.add(hucre.get(hucre.size() - 1));
            }
        }

        for (int i = 0; i < gezilen.size(); i++) {
            for (int j = 0; j < solutionP.size(); j++) {
                if (gezilen.get(i).getX() != solutionP.get(j).getX() && gezilen.get(i).getY() != solutionP.get(j).getY()){
                    hucre.add(new JPanel());
                    hucre.get(hucre.size() - 1).setBounds(gezilen.get(i).getX() * 31, gezilen.get(i).getY() * 31, 30, 30);
                    hucre.get(hucre.size() - 1).setBackground(Color.ORANGE);
                    haritaEkraniProb1.add(hucre.get(hucre.size() - 1));
                }
            }
        }
        for (int i = 0; i < gezilmeyen.size(); i++) {
            hucre.add(new JPanel());
            hucre.get(hucre.size() - 1).setBounds(gezilmeyen.get(i).getX() * 31, gezilmeyen.get(i).getY() * 31, 30, 30);
            hucre.get(hucre.size() - 1).setBackground(Color.WHITE);
            haritaEkraniProb1.add(hucre.get(hucre.size() - 1));
        }

        haritaEkraniProb1.setSize(800, 1000);
        haritaEkraniProb1.setResizable(true);
        haritaEkraniProb1.setLayout(null);
        haritaEkraniProb1.setVisible(true);
        haritaEkraniProb1.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void baslangicvebitis(char[][] izgara,int satir, int sutun) {

        Random random = new Random();

        do {
            basX = random.nextInt(satir);
            basY = random.nextInt(sutun);
            bitX = random.nextInt(satir);
            bitY = random.nextInt(sutun);

        } while (basX == bitX && basY == bitY  || izgara[basX][basY] != '0' || izgara[bitX][bitY] != '0');

        System.out.println("Baslangic noktası : " + basX + " " + basY);
        System.out.println("Bitis noktası : " + bitX + " " + bitY);
    }

    public void rastgeleHaritaCiz(int s, int k){

        rastgeleLabirent labirent = new rastgeleLabirent(s,k);
        int bx = labirent.getBaslangicX();
        int by = labirent.getBaslangicY();
        int b_x = labirent.getBitisX();
        int b_y = labirent.getBitisY();

        char[][] rastgeleMaze = labirent.getMaze();

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < k; j++) {
                System.out.print(rastgeleMaze[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < s; i++) {
            for (int j = 0; j < k; j++) {
                rastgele.add(new Koordinat(i,j));
                if(rastgeleMaze[i][j] == '1'){
                    rastgeleEngel.add(new Engel1(j,i));
                }else{
                    rastgeleYol.add(new Yol(j,i));
                }
            }
        }

        int[] setS = {bx,by};
        int[] setE = {b_x,b_y};

        MazeSolver sol = new MazeSolver(rastgeleMaze,setS,setE);
        sol.solve();
        sol.print(s,k);

        ArrayList<Yol> gezilen = sol.getGezilenYollar();
        ArrayList<Yol> gezilmeyen = sol.getGezilmeyenYollar();
        ArrayList<Yol> solutionP = sol.getSolutionPath();

        System.out.println("Çıkış yolunun uzunluğu : " + solutionP.size());
        System.out.println("Çıkış yolları : ");

        for (int i = 0; i < solutionP.size(); i++) {
            System.out.println((i+1) + ". adım : x->" + solutionP.get(i).getY() + " y->" + solutionP.get(i).getX());
        }

        for (int i = 0; i < rastgeleEngel.size(); i++) {
            hucre.add(new JPanel());
            hucre.get(hucre.size() - 1).setBounds(rastgeleEngel.get(i).getX() * 31, rastgeleEngel.get(i).getY() * 31, 30, 30);
            hucre.get(hucre.size() - 1).setBackground(Color.darkGray);
            haritaEkraniProb2.add(hucre.get(hucre.size() - 1));
        }

        for (int i = 0; i < solutionP.size(); i++) {
            if(solutionP.get(i).getX() == bx && solutionP.get(i).getY() == by ) {
                hucre.add(new JPanel());
                hucre.get(hucre.size()-1).setBounds(solutionP.get(i).getX() * 31, solutionP.get(i).getY() * 31, 30, 30);
                hucre.get(hucre.size()-1).setBackground(Color.YELLOW);
                haritaEkraniProb2.add(hucre.get(hucre.size()-1));

            }else if(solutionP.get(i).getX() == b_x && solutionP.get(i).getY() == b_y ){
                hucre.add(new JPanel());
                hucre.get(hucre.size()-1).setBounds(solutionP.get(i).getX()*31,solutionP.get(i).getY()*31,30,30);
                hucre.get(hucre.size()-1).setBackground(Color.MAGENTA);
                haritaEkraniProb2.add(hucre.get(hucre.size()-1));
            }else {
                 hucre.add(new JPanel());
                 hucre.get(hucre.size() - 1).setBounds(solutionP.get(i).getX() * 31, solutionP.get(i).getY() * 31, 30, 30);
                 hucre.get(hucre.size() - 1).setBackground(Color.blue);
                haritaEkraniProb2.add(hucre.get(hucre.size() - 1));
             }
        }

        for (int i = 0; i < gezilen.size(); i++) {
            for (int j = 0; j < solutionP.size(); j++) {
                if (gezilen.get(i).getX() != solutionP.get(j).getX() && gezilen.get(i).getY() != solutionP.get(j).getY()){
                    hucre.add(new JPanel());
                    hucre.get(hucre.size() - 1).setBounds(gezilen.get(i).getX() * 31, gezilen.get(i).getY() * 31, 30, 30);
                    hucre.get(hucre.size() - 1).setBackground(Color.red);
                    haritaEkraniProb2.add(hucre.get(hucre.size() - 1));
                }
            }
        }

        for (int i = 0; i < gezilmeyen.size(); i++) {
            hucre.add(new JPanel());
            hucre.get(hucre.size() - 1).setBounds(gezilmeyen.get(i).getX() * 31, gezilmeyen.get(i).getY() * 31, 30, 30);
            hucre.get(hucre.size() - 1).setBackground(Color.lightGray);
            haritaEkraniProb2.add(hucre.get(hucre.size() - 1));
        }

        haritaEkraniProb2.setSize(800, 1000);
        haritaEkraniProb2.setResizable(true);
        haritaEkraniProb2.setLayout(null);
        haritaEkraniProb2.setVisible(true);
        haritaEkraniProb2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
