package ru.rusguardian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class TicTacToeTg extends JComponent {
    public static final int FIELD_EMPTY = 0;
    public static final int FIELD_X = 10;
    public static final int FIELD_O = 200;
    int[][] field;
    boolean isXturn;
    boolean alternativeGame = true;

    public TicTacToeTg() {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        field = new int[3][3];
        initGame();
    }
    public void initGame() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                field[i][j] = FIELD_EMPTY;
            }
        }
        isXturn = true;
    }

    public void Obotturn() {
        Random rand = new Random();
        int i, j;

        do {
            i = rand.nextInt(3);
            j = rand.nextInt(3);
        } while (field[i][j] != FIELD_EMPTY);

        field[i][j] = FIELD_X;
    }

    @Override
    protected void processMouseEvent(MouseEvent mouseEvent) {
        super.processMouseEvent(mouseEvent);
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            int x = mouseEvent.getX();
            int y = mouseEvent.getY();
            int i = (int) ((float) x / getWidth() * 3);
            int j = (int) ((float) y / getHeight() * 3);
            //генерируете 2 числа
            //
            if (!alternativeGame) {
                if (field[i][j] == FIELD_EMPTY) {
                    field[i][j] = isXturn ? FIELD_X : FIELD_O;
                    isXturn = !isXturn;
                    repaint();
                    int res = checkState();
                    if (res != 0) {
                        if (res == FIELD_O * 3) {
                            JOptionPane.showMessageDialog(this, "нолики выйграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                        } else if (res == FIELD_X * 3) {
                            JOptionPane.showMessageDialog(this, "крестики выйграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "ничья!", "Ничья!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        initGame();
                        repaint();
                    }
                }
            } else {
                if (field[i][j] == FIELD_EMPTY) {
                    field[i][j] = FIELD_O;
                    Obotturn();
                    repaint();
                    int res = checkState();
                    if (res != 0) {
                        if (res == FIELD_O * 3) {
                            JOptionPane.showMessageDialog(this, "нолики выйграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                        } else if (res == FIELD_X * 3) {
                            JOptionPane.showMessageDialog(this, "крестики выйграли!", "Победа!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "ничья!", "Ничья!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        initGame();
                        repaint();
                    }
                }
                while (field[i][j] == FIELD_EMPTY) {
                    field[i][j] = FIELD_X;
                }
            }
        }
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.clearRect(0, 0, getWidth(), getHeight());
        drawGrid(graphics);
        drawXO(graphics);
    }
    void drawGrid(Graphics graphics) {
        int w = getWidth();
        int h = getHeight();
        int dw = w / 3;
        int dh = h / 3;
        graphics.setColor(Color.BLACK);
        for (int i = 1; i < 3; i++) {
            graphics.drawLine(0, dh * i, w, dh * i);
            graphics.drawLine(dw * i, 0, dw * i, h);
        }
    }
    void drawX(int i, int j, Graphics graphics) {
        graphics.setColor(Color.RED);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        graphics.drawLine(x, y, x + dw, y + dh);
        graphics.drawLine(x, y + dh, x + dw, y);
    }
    void drawO(int i, int j, Graphics graphics) {
        graphics.setColor(Color.BLUE);
        int dw = getWidth() / 3;
        int dh = getHeight() / 3;
        int x = i * dw;
        int y = j * dh;
        graphics.drawOval(x + 5 * dw / 100, y, dw * 9 / 10, dh);
    }
    void drawXO(Graphics graphics) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (field[i][j] == FIELD_X) {
                    drawX(i, j, graphics);
                } else if (field[i][j] == FIELD_O) {
                    drawO(i, j, graphics);
                }
            }

        }
    }
    int checkState() {
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i < 3; i++) {
            diag += field[i][i];
            diag2 += field[i][2 - i];
        }
        if (diag == FIELD_O * 3 || diag == FIELD_X * 3) {
            return diag;
        }
        if (diag2 == FIELD_O * 3 || diag2 == FIELD_X * 3) {
            return diag2;
        }
        int check_i, check_j;
        boolean hasEmpty = false;
        for (int i = 0; i < 3; i++) {
            check_i = 0;
            check_j = 0;
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == 0) {
                    hasEmpty = true;
                }
                check_i += field[i][j];
                check_j += field[j][i];
            }
            if (check_i == FIELD_O * 3 || check_i == FIELD_X * 3) {
                return check_i;
            }
            if (check_j == FIELD_O * 3 || check_j == FIELD_X * 3) {
                return check_j;
            }
        }
        if (hasEmpty) return 0;
        else return -1;
    }
}