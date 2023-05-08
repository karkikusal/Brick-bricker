

import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

import javax.swing.JPanel;



public class GamePanel extends JPanel implements KeyListener,ActionListener{
    private boolean play = false;
    private int score = 0;
    private int totalBrick = 21;
    private Timer timer;
    private int delay= 8;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int playerX = 350;
    private Mapgenerator map;
    public GamePanel() {
        map = new Mapgenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {

        //background color
        g.setColor(Color.YELLOW);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 12);

        g.setColor(Color.RED);  // ball color
        g.fillOval(ballposX, ballposY, 20, 20);

        g.setColor(Color.black);
        g.setFont(new Font("MV Boli", Font.BOLD, 25));
        g.drawString("Score: " + score, 520, 30);

        //game over
        if(ballposY >=570)
        {
            play =false;
            ballXdir =0;
            ballYdir = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,60));
            g.drawString("**Game-over**Score:"+score,40,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press Enter to Play Again",120,500);
        }

        //win condition
        if (totalBrick <=0){
            play =false;
            ballXdir =0;
            ballYdir = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString(" YOU WIN**Score: "+score,40,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString(" Play Again",120,500);
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    private void moveLeft()
    {
        play = true;
        playerX -=50;
    }
    private void moveRight()
    {
        play = true;
        playerX += 50;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(playerX <= 0){
                playerX =0;
            }else
                moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(playerX >= 600)
            {
                playerX = 600;
            }else
                moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play){
                score = 0;
                totalBrick = 21;
                ballposX = 120;
                ballposY = 350;
                ballXdir= -2;
                ballYdir = -4;
                playerX = 320;
                map = new Mapgenerator(3,7);
            }
        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(play)
        {
            if(ballposX <= 0){
                ballXdir = -ballXdir;
            }
            if(ballposY <= 0){
                ballYdir = -ballYdir;
            }
            if(ballposX >= 670){
                ballXdir = -ballXdir;
            }

            Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
            Rectangle paddleRect = new Rectangle(playerX,550,100,9);

            if(ballRect.intersects(paddleRect))
            {
                ballYdir = -ballYdir;
            }

            A:for(int i=0; i<map.map.length;i++){
                for (int j=0; j<map.map[0].length;j++){
                    if(map.map[i][j] >0){
                        int width = map.brickWidth;
                        int height = map.brickHeight;

                        int brickXpos = 80+j*width;
                        int brickYpos = 50+i*height;

                        Rectangle brickRect = new Rectangle(brickXpos,brickYpos,width,height);

                        if(ballRect.intersects(brickRect)){
                            map.setBrick(0,i,j);
                            totalBrick--;
                            score += 2;

                            if(ballposX+19<=brickXpos || ballposX+1 >=brickXpos+width){
                                ballXdir = -ballXdir;
                            }
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
        }
        repaint();
    }



        }


