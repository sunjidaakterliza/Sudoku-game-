import javax.swing.*;
import java.awt.*;

public class SudokuGame extends JFrame {

    JTextField[][] cells = new JTextField[4][4];

    // 0 means empty cell
    int[][][] puzzles = {

        // Puzzle 1
        {
            {1, 0, 3, 0},
            {0, 4, 0, 2},
            {2, 0, 4, 0},
            {0, 3, 0, 1}
        },

        // Puzzle 2
        {
            {1, 0, 0, 4},
            {0, 4, 1, 0},
            {0, 1, 4, 0},
            {4, 0, 0, 1}
        },

        // Puzzle 3
        {
            {0, 2, 3, 0},
            {3, 0, 0, 2},
            {2, 0, 0, 3},
            {0, 3, 2, 0}
        }
    };

    int currentPuzzle = 0;

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

                cells[i][j].setHorizontalAlignment(
                    JTextField.CENTER
                );

                cells[i][j].setFont(
                    new Font("Arial", Font.BOLD, 20)
                );

                grid.add(cells[i][j]);
            }
        }

        // Buttons
        JPanel buttons = new JPanel();

        JButton newGame = new JButton("New Game");
        JButton clear = new JButton("Clear");

        buttons.add(newGame);
        buttons.add(clear);

        add(grid, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // Load first puzzle
        loadPuzzle();

        // New Game button
        newGame.addActionListener(e -> {

            currentPuzzle++;

            if (currentPuzzle >= puzzles.length) {
                currentPuzzle = 0;
            }

            loadPuzzle();
        });

        // Clear button
        clear.addActionListener(e -> {

            int[][] puzzle = puzzles[currentPuzzle];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    // Only clear empty cells
                    if (puzzle[i][j] == 0) {
                        cells[i][j].setText("");
                    }
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Entered numbers have been cleared."
            );
        });

        setVisible(true);
    }

    // Load selected puzzle
    void loadPuzzle() {

        int[][] puzzle = puzzles[currentPuzzle];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                // Fixed number
                if (puzzle[i][j] != 0) {

                    cells[i][j].setText(
                        String.valueOf(puzzle[i][j])
                    );

                    // Prevent changing fixed cells
                    cells[i][j].setEditable(false);

                    cells[i][j].setBackground(
                        new Color(180, 200, 220)
                    );

                }

                // Empty cell
                else {

                    cells[i][j].setText("");

                    // Allow player input
                    cells[i][j].setEditable(true);

                    cells[i][j].setBackground(
                        new Color(230, 240, 255)
                    );

                    final int row = i;
                    final int col = j;

                    // Input checking
                    cells[i][j].addActionListener(e -> {

                        String value =
                            cells[row][col].getText();

                        // Check 1-4
                        if (!value.matches("[1-4]")) {

                            JOptionPane.showMessageDialog(
                                this,
                                "Incorrect input! Enter a number from 1 to 4."
                            );

                            cells[row][col].setText("");
                            return;
                        }

                        // Check row
                        for (int k = 0; k < 4; k++) {

                            if (k != col &&
                                cells[row][k].getText()
                                .equals(value)) {

                                JOptionPane.showMessageDialog(
                                    this,
                                    "Incorrect! Duplicate number in the row."
                                );

                                cells[row][col].setText("");
                                return;
                            }
                        }

                        // Check column
                        for (int k = 0; k < 4; k++) {

                            if (k != row &&
                                cells[k][col].getText()
                                .equals(value)) {

                                JOptionPane.showMessageDialog(
                                    this,
                                    "Incorrect! Duplicate number in the column."
                                );

                                cells[row][col].setText("");
                                return;
                            }
                        }

                        // Check 2x2 box
                        int startRow = (row / 2) * 2;
                        int startCol = (col / 2) * 2;

                        for (int r = startRow;
                             r < startRow + 2; r++) {

                            for (int c = startCol;
                                 c < startCol + 2; c++) {

                                if (r != row && c != col &&
                                    cells[r][c].getText()
                                    .equals(value)) {

                                    JOptionPane.showMessageDialog(
                                        this,
                                        "Incorrect! Duplicate number in the 2x2 box."
                                    );

                                    cells[row][col].setText("");
                                    return;
                                }
                            }
                        }

                        // Correct input
                        JOptionPane.showMessageDialog(
                            this,
                            "Number entered correctly!"
                        );
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        new SudokuGame();
    }
}