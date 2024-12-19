package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTING
    final int FPS = 60;
    final int originalTileSize = 16;    //16x16 tile
    final int scale = 3;
    final int tileSize = originalTileSize*scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize*maxScreenCol;
    final int screenHeight = tileSize*maxScreenRow;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    //player's attribute
    int playerX = 100, playerY = 100, playerSpeed = 4;

    public GamePanel(){
        setPreferredSize(new Dimension(screenWidth,screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @SuppressWarnings("BusyWait")
//    @Override
//    public void run() {
//        double drawInterval = (double) 1000000000 /FPS; //0.01666 seconds per frame
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while(gameThread!=null){
//            update();
//            repaint();
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime /= 1000000;   //nano time to millisecond
//                if (remainingTime<0) remainingTime = 0;
//                Thread.sleep((long) remainingTime);
//                nextDrawTime += drawInterval;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public void run(){
        double drawInterval = 1000000000.0 / FPS; //time each frame should take (in nanoseconds)
        double delta = 0;   //Tracks the progress in drawing frame relative to the desired `drawInterval`
        long lastTime = System.nanoTime(); //stores the timestamp of the last cycle iteration
        long currentTime;   //stores the timestamp of the current cycle iteration (in nanoseconds)
        long timer = 0; //Tracks the total elapsed time over multiple iterations to keep track of FPS
        int drawCount = 0;  //Tracks the number of frames drawn (or updated) in one second

        while(gameThread!=null){
            currentTime = System.nanoTime();
            timer += currentTime - lastTime;
            delta = delta+ ((currentTime - lastTime) / drawInterval);
            lastTime = currentTime;

            if(delta >= 1){
                System.out.println("Number of frames: " + drawCount);
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void update() {
        if (keyH.upPressed) playerY -= playerSpeed;
        else if (keyH.downPressed) playerY += playerSpeed;
        else if (keyH.rightPressed) playerX += playerSpeed;
        else if (keyH.leftPressed) playerX -= playerSpeed;
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        g2.fillRect(playerX,playerY,tileSize, tileSize);
        g2.dispose();
    }
}
