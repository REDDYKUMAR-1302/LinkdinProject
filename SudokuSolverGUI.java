import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverGUI extends JFrame {

    private static final int SIZE = 9;
    private JTextField[][] gridFields = new JTextField[SIZE][SIZE];

    public SudokuSolverGUI() {
        setTitle("Sudoku Solver");
        setSize(400, 400);
        setLayout(new GridLayout(SIZE + 1, SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the Sudoku grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JTextField textField = new JTextField(2);
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setPreferredSize(new Dimension(40, 40));
                textField.setFont(new Font("Arial", Font.PLAIN, 20));
                add(textField);
                gridFields[row][col] = textField;
            }
        }

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSolveAction();
            }
        });
        add(solveButton);

        showInitialMessage();

        setVisible(true);
    }

    private void showInitialMessage() {
        JOptionPane.showMessageDialog(this, "Please enter the Sudoku puzzle. You have 10 seconds to start entering.", "Enter Sudoku", JOptionPane.INFORMATION_MESSAGE);

        // Timer to prompt user if no action is taken within 10 seconds
        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGridEmpty()) {
                    JOptionPane.showMessageDialog(SudokuSolverGUI.this, "Please respond and enter the Sudoku puzzle.", "No Input Detected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        timer.setRepeats(false); // Ensure it runs only once
        timer.start();
    }

    private boolean isGridEmpty() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!gridFields[row][col].getText().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void handleSolveAction() {
        int[][] grid = readGrid();
        if (solveSudoku(grid)) {
            updateGrid(grid);
        } else {
            JOptionPane.showMessageDialog(null, "No solution exists", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[][] readGrid() {
        int[][] grid = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = gridFields[row][col].getText();
                try {
                    grid[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    grid[row][col] = 0;
                }
            }
        }
        return grid;
    }

    private void updateGrid(int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                gridFields[row][col].setText(grid[row][col] == 0 ? "" : Integer.toString(grid[row][col]));
            }
        }
    }

    private boolean isValid(int[][] grid, int row, int col, int num) {
        for (int x = 0; x < SIZE; x++) {
            if (grid[row][x] == num) {
                return false;
            }
        }
        for (int x = 0; x < SIZE; x++) {
            if (grid[x][col] == num) {
                return false;
            }
        }
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean findEmptyLocation(int[][] grid, int[] row, int[] col) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] == 0) {
                    row[0] = r;
                    col[0] = c;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean solveSudoku(int[][] grid) {
        int[] row = new int[1];
        int[] col = new int[1];

        if (!findEmptyLocation(grid, row, col)) {
            return true;
        }

        int r = row[0];
        int c = col[0];

        for (int num = 1; num <= SIZE; num++) {
            if (isValid(grid, r, c, num)) {
                grid[r][c] = num;
                if (solveSudoku(grid)) {
                    return true;
                }
                grid[r][c] = 0;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuSolverGUI::new);
    }
}
