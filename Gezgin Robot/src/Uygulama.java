import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Uygulama{
    int satir,sutun;

    //ArrayList<Koordinat> maze = new ArrayList<>();
    ArrayList<Yol> yollar = new ArrayList<>();

    //ArrayList<Engel> tumEngeller = new ArrayList<>();
    ArrayList<Engel1> engel1 = new ArrayList<>();
    ArrayList<Engel2> engel2 = new ArrayList<>();
    ArrayList<Engel3> engel3 = new ArrayList<>();
    char[][] matris = new char[20][20];
    void girisEkrani(){

        JFrame girisEkrani = new JFrame("Giriş Ekranı");
        JButton problem1 = new JButton("PROBLEM 1");
        JButton problem2 = new JButton("PROBLEM 2");

        problem1.setBounds(80, 200, 200, 50);
        problem2.setBounds(400, 200, 200, 50);

        problem1.setBackground(Color.LIGHT_GRAY);
        problem2.setBackground(Color.LIGHT_GRAY);

        girisEkrani.add(problem1);
        girisEkrani.add(problem2);
        girisEkrani.setSize(700, 500);
        girisEkrani.setResizable(true);
        girisEkrani.setLayout(null);
        girisEkrani.setVisible(true);
        girisEkrani.setDefaultCloseOperation(EXIT_ON_CLOSE);

        problem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                problem1ekrani();
                girisEkrani.setVisible(false);
            }
        });
        problem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                problem2ekrani();
                girisEkrani.setVisible(false);
            }
        });
    }

    void problem1ekrani(){

        JFrame problem1Ekrani = new JFrame("Problem 1 Labirent Ekranı");
        JButton url1 = new JButton("URL1");
        JButton url2 = new JButton("URL2");

        url1.setBounds(80, 200, 200, 50);
        url2.setBounds(400, 200, 200, 50);

        url1.setBackground(Color.LIGHT_GRAY);
        url2.setBackground(Color.LIGHT_GRAY);

        problem1Ekrani.add(url1);
        problem1Ekrani.add(url2);
        problem1Ekrani.setSize(700, 500);
        problem1Ekrani.setResizable(true);
        problem1Ekrani.setLayout(null);
        problem1Ekrani.setVisible(true);
        problem1Ekrani.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        url1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                url1cozum();
                problem1Ekrani.setVisible(false);
            }
        });

        url2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                url2cozum();
                problem1Ekrani.setVisible(false);
            }
        });
    }
    void problem2ekrani(){

        JFrame problem2Ekrani = new JFrame("Problem 2 Labirent Ekranı");
        JLabel satirBoyut = new JLabel("Satır Boyutu");
        JLabel sutunBoyut = new JLabel("Sütun Boyutu");
        JTextField satirBoyut1 = new JTextField();
        JTextField sutunBoyut1 = new JTextField();
        JButton basla = new JButton("BAŞLA");
        basla.setBackground(Color.LIGHT_GRAY);

        satirBoyut.setBounds(150,50,100,20);
        problem2Ekrani.add(satirBoyut);
        satirBoyut1.setBounds(150 , 100 ,100,20);
        problem2Ekrani.add(satirBoyut1);
        sutunBoyut.setBounds(150 , 150 ,100,20);
        problem2Ekrani.add(sutunBoyut);
        sutunBoyut1.setBounds(150 , 200 ,100,20);
        problem2Ekrani.add(sutunBoyut1);
        basla.setBounds(150,250,100,30);
        problem2Ekrani.add(basla);

        basla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int s = Integer.parseInt(satirBoyut1.getText());
                int k = Integer.parseInt(sutunBoyut1.getText());
                Izgara ll = new Izgara();
                ll.rastgeleHaritaCiz(s,k);
            }
        });

        problem2Ekrani.setSize(500, 500);
        problem2Ekrani.setResizable(true);
        problem2Ekrani.setLayout(null);
        problem2Ekrani.setVisible(true);
        problem2Ekrani.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void url1cozum(){

        new Izgara();
        URL webpage = null;
        URLConnection conn = null;
        try {
            webpage = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");
            conn = webpage.openConnection();
            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF8");
            BufferedReader buffer = new BufferedReader(reader);
            String line = "";

            while (true) {
                line = buffer.readLine();
                if (line != null) {

                    for (int j = 0; j < line.length(); j++) {
                        matris[satir][j] = line.charAt(j);
                    }
                } else {
                    break;
                }
                satir++;
                sutun = line.length();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        char[][] izg = duvarCek(matris,satir,sutun);

        for (int i = 0; i < satir+2; i++) {
            for (int j = 0; j < sutun+2; j++) {
                System.out.print(izg[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < satir+2; i++) {
            for (int j = 0; j < sutun+2; j++) {
                if(izg[i][j] == '1'){
                    engel1.add(new Engel1(j,i));
                }else if(izg[i][j] == '2'){
                    engel2.add(new Engel2(j,i));
                }else if(izg[i][j] == '3'){
                    engel3.add(new Engel3(j,i));
                }else{
                    yollar.add(new Yol(j,i));
                }
            }
        }

        System.out.println("Engel 1 sayısı :" + engel1.size());
        System.out.println("Engel 2 sayısı :" + engel2.size());
        System.out.println("Engel 3 sayısı :" + engel3.size());
        System.out.println("Yolların sayısı :" + yollar.size());

        Izgara harita = new Izgara();
        harita.haritaCiz(izg,satir+2,sutun+2,engel1,engel2,engel3,yollar);
    }

    void url2cozum(){

        new Izgara();
        URL webpage = null;
        URLConnection conn = null;
        try {
            webpage = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
            conn = webpage.openConnection();
            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF8");
            BufferedReader buffer = new BufferedReader(reader);
            String line = "";

            while (true) {
                line = buffer.readLine();
                if (line != null) {

                    for (int j = 0; j < line.length(); j++) {
                        matris[satir][j] = line.charAt(j);
                    }
                } else {
                    break;
                }
                satir++;
                sutun = line.length();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        char[][] izg = duvarCek(matris,satir,sutun);

        for (int i = 0; i < satir+2; i++) {
            for (int j = 0; j < sutun+2; j++) {
                System.out.print(izg[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < satir+2; i++) {
            for (int j = 0; j < sutun+2; j++) {
                if(izg[i][j] == '1'){
                    engel1.add(new Engel1(j,i));
                }else if(izg[i][j] == '2'){
                    engel2.add(new Engel2(j,i));
                }else if(izg[i][j] == '3'){
                    engel3.add(new Engel3(j,i));
                }else{
                    yollar.add(new Yol(j,i));
                }
            }
        }

        System.out.println("Engel 1 sayısı :" + engel1.size());
        System.out.println("Engel 2 sayısı :" + engel2.size());
        System.out.println("Engel 3 sayısı :" + engel3.size());
        System.out.println("Yolların sayısı :" + yollar.size());

        Izgara harita2 = new Izgara();
        harita2.haritaCiz(izg,satir+2,sutun+2,engel1,engel2,engel3,yollar);
    }

    public char[][] duvarCek(char[][] matris, int satir, int sutun){
        char[][] izgara = new char[satir+2][sutun+2];

        for (int i = 0; i < satir+2; i++) {
            for (int j = 0; j < sutun+2; j++) {

                if(i == 0){
                    izgara[0][j]='1';
                }
                if(j == 0){
                    izgara[i][0]='1';
                }
                if(i == satir+1){
                    izgara[satir+1][j]='1';
                }
                if(j == sutun+1){
                    izgara[i][sutun+1]='1';
                }
            }
        }

        for (int i = 1; i < satir+1; i++) {
            for (int j = 1; j < sutun+1; j++) {
                izgara[i][j] = matris[i-1][j-1];
            }
        }
        return izgara;
    }
}
