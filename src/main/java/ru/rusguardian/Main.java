package ru.rusguardian;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("запускаем игру...");
        JFrame window = new JFrame("TicTacToeTg");
        window.setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE));
        window.setSize(400,400);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        TicTacToeTg game = new TicTacToeTg();
        window.add(game);
        System.out.println("конец...");
    }
}