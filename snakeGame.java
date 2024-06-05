import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class snakeGame extends JPanel implements ActionListener,KeyListener {
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidht;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    //Food
    Tile food;
    Random random;

    //Game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;

    snakeGame(int boardWidht, int boardHeight) {
        this.boardWidht = boardWidht;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidht,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;

               // game timer 
               gameLoop = new  Timer(350,this); //how long it takes to start timer ,milliseconds gone between frames
            gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

        public void draw(Graphics g) {
            //Grid Lines
            for (int i = 0; i < boardWidht/tileSize; i++) {
                //(x1,y1,x2,y2)
                g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
                g.drawLine(0, i*tileSize, boardWidht,i*tileSize);
            }

            //Food
            g.setColor(Color.red);
            //g.fillRect(Food.x*tileSize, food.y*tileSize, tileSize, tileSize);
            g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize , true);

            //Snake Head
            g.setColor(Color.green);
            //g.fillRect(snakeHead.x, snakeHead.y,tileSize , tileSize); 
            //g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize,tileSize, true);
            
            //snack Body
            for(int i = 0; i < snakeBody.size(); i++) {
                Tile snakePart = snakeBody.get(i); 
                //g.fillRect(Food.x*tileSize, food.y*tileSize, tileSize, tileSize);
                g.fillRect(snakePart.x*tileSize, snakePart.y * tileSize, tileSize, tileSize);
            }

            // Score
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            if(gameOver) {
                g.setColor(Color.red);
                g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            }
            else {
                g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            } 
        }

        public void placeFood() {
            food.x = random.nextInt(boardHeight/tileSize); //600/25 =24
            food.y = random.nextInt(boardHeight/tileSize);
        }

        public void move() {
            //eat food 
            if (collision(snakeHead, food)){
                snakeBody.add(new Tile(food.x, food.y));
                placeFood();
            }

            //Move Snake Body
            for (int i = snakeBody.size()-1; i >= 0; i-- ) {
                Tile snakePart = snakeBody.get(i);
                if (i == 0) {
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;
                }
                else{
                    Tile prevSnakePart = snakeBody.get(i-1);
                    snakePart.x = prevSnakePart.x;
                    snakePart.y = prevSnakePart.y;
                }
            }

            //Move Snake Head
            snakeHead.x += velocityX;
            snakeHead.y += velocityY;

            //game over conditions
            for (int i = 0; i < snakeBody.size();i++) {
                Tile snakepart = snakeBody.get(i);

                // collide with the snake head
                if (collision(snakeHead, snakepart)){
                    gameOver = true;
                }                
            }
            if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidht || // passed left border or right broder
                snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight) { //passed top border or bottom broder
                gameOver = true;
                }
        }

        public boolean collision(Tile tile1,Tile tile2) {
            return tile1.x == tile2.x && tile1.y == tile2.y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            move();
            repaint();
            if (gameOver) {
                gameLoop.stop();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // System.out.println("KeyEvent: " + e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
                velocityX = 0;
                velocityY = -1;
                
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
                velocityX = 0;
                velocityY = 1;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
                velocityX = -1;
                velocityY = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
                velocityX = 1;
                velocityY = 0;
            }
        }

        // do not need
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }
