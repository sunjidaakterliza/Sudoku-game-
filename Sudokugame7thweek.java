import javax.swing.*;
import java.awt.*;
 class SudokuGame extends JFrame {

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

        loadPuzzle();

        // New Game
        newGame.addActionListener(e -> {

            currentPuzzle++;

            if (currentPuzzle >= puzzles.length) {
                currentPuzzle = 0;
            }

            loadPuzzle();
        });

        // Clear
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

        setVisible(true);
    }

    // Load puzzle
    void loadPuzzle() {

        int[][] puzzle = puzzles[currentPuzzle];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (puzzle[i][j] != 0) {

                    cells[i][j].setText(
                        String.valueOf(puzzle[i][j])
                    );

                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(
                        new Color(180, 200, 220)
                    );

                } else {

                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(
                        new Color(230, 240, 255)
                    );

                    final int row = i;
                    final int col = j;

                    cells[i][j].addActionListener(e -> {

                        String value =
                            cells[row][col].getText();

                        if (!value.matches("[1-4]")) {

                            JOptionPane.showMessageDialog(
                                this,
                                "Enter a number from 1 to 4."
                            );

                            cells[row][col].setText("");
                        }
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        new SudokuGame();
    }
}