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
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel(
            "4 × 4 SUDOKU GAME",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(title, BorderLayout.NORTH);

        // Sudoku Grid
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(4, 4, 4, 4));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                cells[i][j] = new JTextField();

                cells[i][j].setHorizontalAlignment(
                    JTextField.CENTER
                );

                cells[i][j].setFont(
                    new Font("Arial", Font.BOLD, 22)
                );

                grid.add(cells[i][j]);

                final int row = i;
                final int col = j;

                // Input checking
                cells[i][j].addActionListener(e -> {
                    checkInput(row, col);
                });
            }
        }

        add(grid, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 8, 8)
        );

        JButton newGame = new JButton("New Game");
        JButton restart = new JButton("Restart");
        JButton clear = new JButton("Clear");
        JButton instructions = new JButton("Instructions");

        buttons.add(newGame);
        buttons.add(restart);
        buttons.add(clear);
        buttons.add(instructions);

        add(buttons, BorderLayout.SOUTH);

        // Load first puzzle
        loadPuzzle();

        // New Game
        newGame.addActionListener(e -> {

            currentPuzzle++;

            if (currentPuzzle >= puzzles.length) {
                currentPuzzle = 0;
            }

            loadPuzzle();
        });

        // Restart current puzzle
        restart.addActionListener(e -> {

            loadPuzzle();

            JOptionPane.showMessageDialog(
                this,
                "Current puzzle has been restarted."
            );
        });

        // Clear entered numbers
        clear.addActionListener(e -> {

            int[][] puzzle = puzzles[currentPuzzle];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {

                    if (puzzle[i][j] == 0) {
                        cells[i][j].setText("");
                    }
                }
            }
        });

        // Instructions
        instructions.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                this,
                "4x4 Sudoku Instructions\n\n"
                + "1. Enter numbers from 1 to 4.\n"
                + "2. Do not repeat a number in a row.\n"
                + "3. Do not repeat a number in a column.\n"
                + "4. Do not repeat a number in a 2x2 box.\n"
                + "5. Fixed numbers cannot be changed.\n"
                + "6. Complete the puzzle to win!",
                "How to Play",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Load selected puzzle
    void loadPuzzle() {

        int[][] puzzle = puzzles[currentPuzzle];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (puzzle[i][j] != 0) {

                    // Fixed number
                    cells[i][j].setText(
                        String.valueOf(puzzle[i][j])
                    );

                    cells[i][j].setEditable(false);

                    cells[i][j].setBackground(
                        new Color(180, 200, 220)
                    );

                } else {

                    // Empty cell
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);

                    cells[i][j].setBackground(
                        new Color(230, 240, 255)
                    );
                }
            }
        }
    }

    // Check player input
    void checkInput(int row, int col) {

        String value = cells[row][col].getText();

        // Check 1-4
        if (!value.matches("[1-4]")) {

            JOptionPane.showMessageDialog(
                this,
                "Incorrect input!\nEnter a number from 1 to 4."
            );

            cells[row][col].setText("");
            return;
        }

        // Check row
        for (int k = 0; k < 4; k++) {

            if (k != col &&
                cells[row][k].getText().equals(value)) {

                JOptionPane.showMessageDialog(
                    this,
                    "Incorrect!\nDuplicate number in the row."
                );

                cells[row][col].setText("");
                return;
            }
        }

        // Check column
        for (int k = 0; k < 4; k++) {

            if (k != row &&
                cells[k][col].getText().equals(value)) {

                JOptionPane.showMessageDialog(
                    this,
                    "Incorrect!\nDuplicate number in the column."
                );

                cells[row][col].setText("");
                return;
            }
        }

        // Check 2x2 box
        int startRow = (row / 2) * 2;
        int startCol = (col / 2) * 2;

        for (int r = startRow; r < startRow + 2; r++) {

            for (int c = startCol; c < startCol + 2; c++) {

                if (r != row && c != col &&
                    cells[r][c].getText().equals(value)) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Incorrect!\nDuplicate number in the 2x2 box."
                    );

                    cells[row][col].setText("");
                    return;
                }
            }
        }

        // Check if puzzle is solved
        if (isPuzzleSolved()) {

            JOptionPane.showMessageDialog(
                this,
                "🎉 Congratulations!\n"
                + "You solved the Sudoku puzzle!",
                "You Win!",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Check complete puzzle
    boolean isPuzzleSolved() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (cells[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new SudokuGame();
        });
    }
}