import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.util.Scanner;

public class SnakeGame extends JFrame {
    private static void recv(ServerSocket serverSocket, byte[] code, int length) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private SnakePanel snakePanel;

    public SnakeGame() {
        snakePanel = new SnakePanel();
        add(snakePanel);

        setResizable(false);
        pack();

        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        String msg,str;  
        Scanner sc=new Scanner(System.in);
ServerSocket ss=new ServerSocket(8000);  
System.out.println("listening to port:8000");
Socket s=ss.accept();//establishes connection   
DataInputStream dis=new DataInputStream(s.getInputStream());  
str=(String)dis.readUTF();  
System.out.println("please confirm the code to join the room"); 
msg=sc.next();
ss.close();  
if(msg.equals(str))
{

 
    SwingUtilities.invokeLater(() -> {
        new SnakeGame().setVisible(true);
    });
}
         
    }
}

class SnakePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private static final int DOT_SIZE = 10;
    private static final int ALL_DOTS = 900;
    private static final int RAND_POS = 29;
    private static final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int appleX;
    private int appleY;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;

    private final int player2KeyCodeLeft = KeyEvent.VK_A;
    private final int player2KeyCodeRight = KeyEvent.VK_D;
    private final int player2KeyCodeUp = KeyEvent.VK_W;
    private final int player2KeyCodeDown = KeyEvent.VK_S;

    private final int[] player2X = new int[ALL_DOTS];
    private final int[] player2Y = new int[ALL_DOTS];
    private int player2Dots;
    private boolean leftDirection2 = false;
    private boolean rightDirection2 = true;
    private boolean upDirection2 = false;
    private boolean downDirection2 = false;

    public SnakePanel() {
        setBackground(Color.black);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(new SnakeAdapter());

        initGame();
    }

    private void initGame() {
        dots = 3;
        player2Dots = 3;

        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }

        for (int i = 0; i < player2Dots; i++) {
            player2X[i] = 150 + i * DOT_SIZE;
            player2Y[i] = 150;
        }

        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawSnake(g);
            drawPlayer2(g);
            drawApple(g);
        } else {
            gameOver(g);
        }
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < dots; i++) {
            if (i == 0) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.white);
            }
            g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
        }
    }

    private void drawPlayer2(Graphics g) {
        for (int i = 0; i < player2Dots; i++) {
            if (i == 0) {
                g.setColor(Color.blue);
            } else {
                g.setColor(Color.white);
            }
            g.fillRect(player2X[i], player2Y[i], DOT_SIZE, DOT_SIZE);
        }
    }

    private void drawApple(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        for (int i = player2Dots; i > 0; i--) {
            player2X[i] = player2X[i - 1];
            player2Y[i] = player2Y[i - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }

        if (leftDirection2) {
            player2X[0] -= DOT_SIZE;
        }

        if (rightDirection2) {
            player2X[0] += DOT_SIZE;
        }

        if (upDirection2) {
            player2Y[0] -= DOT_SIZE;
        }

        if (downDirection2) {
            player2Y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        for (int i = player2Dots; i > 0; i--) {
            if (i > 4 && x[0] == player2X[i] && y[0] == player2Y[i]) {
                inGame = false;
            }
        }

        if (y[0] >= HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (player2Y[0] >= HEIGHT) {
            inGame = false;
        }

        if (player2Y[0] < 0) {
            inGame = false;
        }

        if (player2X[0] >= WIDTH) {
            inGame = false;
        }

        if (player2X[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            locateApple();
        }

        if (player2X[0] == appleX && player2Y[0] == appleY) {
            player2Dots++;
            locateApple();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        appleX = r * DOT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        appleY = r * DOT_SIZE;
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
        if(msg=="Game Over")
        {
            if(dots>player2Dots)
            {
                String win="player1 wins";
     
                //Font small = new Font("Helvetica", Font.BOLD, 14);
                //FontMetrics metr = getFontMetrics(small);

                g.setColor(Color.white);
                 g.setFont(small);
                g.drawString(win, (WIDTH - metr.stringWidth(win)) / 4, HEIGHT / 4);
            }
            if(dots<player2Dots)
            {
                String win1="player2 wins";

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(win1, (WIDTH - metr.stringWidth(win1)) / 4, HEIGHT / 4);
            }
            if(player2Dots==dots)
            {
                String win3="DRAW";

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(win3, (WIDTH - metr.stringWidth(win3)) / 4, HEIGHT / 4);
                
            }
            int i,c=0;
            for(i=0;i<1000;i++)
            {
                c++;
            }
            //timer = new Timer(DELAY, this);
         //new SnakeGame().setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class SnakeAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == player2KeyCodeLeft) && (!rightDirection2)) {
                leftDirection2 = true;
                upDirection2 = false;
                downDirection2 = false;
            }

            if ((key == player2KeyCodeRight) && (!leftDirection2)) {
                rightDirection2 = true;
                upDirection2 = false;
                downDirection2 = false;
            }

            if ((key == player2KeyCodeUp) && (!downDirection2)) {
                upDirection2 = true;
                rightDirection2 = false;
                leftDirection2 = false;
            }

            if ((key == player2KeyCodeDown) && (!upDirection2)) {
                downDirection2 = true;
                rightDirection2 = false;
                leftDirection2 = false;
            }
        }
    }
}