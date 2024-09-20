package comdev;

import entity.Player;
import object.SuperObject;
import tiles.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen setting
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize*maxWorldCol;
    public final int worldHeight = tileSize*maxWorldRow;


    // FPS
    final int fps = 60;

    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this,keyH);
    TileManager tileManager = new TileManager(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public SuperObject obj[] = new SuperObject[10];





    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {
        aSetter.setObjectsOnMap();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps; //0.016 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            // 1 Update:update information such as character position
            update();
            //2 Draw: redraw the screen with updated info
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

            // Draw Tiles
        tileManager.draw(g2);

            // Draw Objects
        for(int i =0; i < obj.length; i++){
            if(obj[i] != null) {
                obj[i].draw(g2,this);
            }
        }

            // Draw Player
        player.draw(g2);

        g2.dispose();
    }
}
