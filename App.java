import javax.swing.*;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int boardWidht = 600;
        int boardHeight = boardWidht;

        JFrame frame = new JFrame ("Snake");
        frame.setVisible(true);
        frame.setSize(boardHeight, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snakeGame SnakeGame = new snakeGame(boardWidht, boardHeight);
        frame.add(SnakeGame);
        frame.pack();
        SnakeGame.requestFocus();
    }
}
