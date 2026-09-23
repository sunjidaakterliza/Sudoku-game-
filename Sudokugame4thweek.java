import javax.swing.*;
import java.awt.*;

public class SudokuGame extends JFrame {

    JTextField[][] cells = new JTextField[4][4];

    public SudokuGame() {

        setTitle("4x4 Sudoku Game");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Sudoku Grid
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(4, 4, 3, 3));
        grid.setBackground(Color.BLACK);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                cells[i][j] = new JTextField();

                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 20));

                // Cell Color
                cells[i][j].setBackground(new Color(230, 240, 255));
                cells[i][j].setForeground(Color.BLUE);

                grid.add(cells[i][j]);
            }
        }

        // Buttons
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(210, 220, 240));

        JButton newGame = new JButton("New Game");
        JButton clear = new JButton("Clear");

        // Button Colors
        newGame.setBackground(new Color(70, 130, 180));
        newGame.setForeground(Color.WHITE);

        clear.setBackground(new Color(220, 80, 80));
        clear.setForeground(Color.WHITE);

        buttons.add(newGame);
        buttons.add(clear);

        // Clear Button
        clear.addActionListener(e -> {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    cells[i][j].setText("");
                }
            }
        });

        add(grid, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SudokuGame();
    }
}
