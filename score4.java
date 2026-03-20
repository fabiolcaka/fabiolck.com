import java.util.Scanner;

public class score4 {
    static int rows, cols;
    static char[][] board;
    static String[] playerNames;
    static char[] playerSymbols;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("--- Καλώς ήρθατε στο Score 4! ---");
        
        // Ρύθμιση παιχνιδιού
        setupGame();
        setupPlayers();
        initializeBoard();
        
        // Κύκλος παιχνιδιού
        boolean gameEnded = false;
        int currentPlayer = 0;
        int totalMoves = 0;
        
        while (!gameEnded) {
            printBoard();
            
            String currentName = playerNames[currentPlayer];
            char currentSymbol = playerSymbols[currentPlayer];
            
            System.out.println("Παίζει ο: " + currentName + " (" + currentSymbol + ")");
            
            // Εισαγωγή κίνησης
            int column = getValidColumn();
            
            // Τοποθέτηση πιονιού
            dropPiece(column, currentSymbol);
            totalMoves++;
            
            // Έλεγχος νίκης
            if (checkWin(currentSymbol)) {
                printBoard();
                System.out.println("\n*** ΝΙΚΗΤΗΣ: " + currentName + "! ***");
                gameEnded = true;
            }
            // Έλεγχος ισοπαλίας
            else if (totalMoves == rows * cols) {
                printBoard();
                System.out.println("\n*** ΙΣΟΠΑΛΙΑ! ***");
                gameEnded = true;
            }
            // Αλλαγή παίκτη
            else {
                currentPlayer = (currentPlayer + 1) % playerNames.length;
            }
        }
        
        System.out.println("Τέλος παιχνιδιού!");
    }
    
    // Ρύθμιση διαστάσεων
    public static void setupGame() {
        int numPlayers;
        
        do {
            System.out.print("Αριθμός παικτών (2-4): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Παρακαλώ εισάγετε αριθμό!");
                scanner.next();
            }
            numPlayers = scanner.nextInt();
        } while (numPlayers < 2 || numPlayers > 4);
        
        // Ορισμός διαστάσεων
        if (numPlayers == 2) {
            rows = 6; cols = 7;
        } else if (numPlayers == 3) {
            rows = 7; cols = 9;
        } else {
            rows = 7; cols = 12;
        }
        
        playerNames = new String[numPlayers];
        playerSymbols = new char[numPlayers];
        
        System.out.println("Διαστάσεις πίνακα: " + rows + "x" + cols);
    }
    
    // Εισαγωγή στοιχείων παικτών
    public static void setupPlayers() {
        for (int i = 0; i < playerNames.length; i++) {
            System.out.print("Όνομα Παίκτη " + (i+1) + ": ");
            playerNames[i] = scanner.next();
            
            System.out.print("Σύμβολο για τον " + playerNames[i] + ": ");
            playerSymbols[i] = scanner.next().charAt(0);
        }
    }
    
    // Αρχικοποίηση πίνακα
    public static void initializeBoard() {
        board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = '.';
            }
        }
    }
    
    // Εκτύπωση πίνακα
    public static void printBoard() {
        System.out.println();
        // Αριθμοί στηλών
        for (int j = 1; j <= cols; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        // Πίνακας
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Εισαγωγή έγκυρης στήλης
    public static int getValidColumn() {
        int column = -1;
        boolean valid = false;
        
        while (!valid) {
            System.out.print("Επιλογή στήλης (1-" + cols + "): ");
            
            if (scanner.hasNextInt()) {
                column = scanner.nextInt() - 1;
                
                if (column >= 0 && column < cols) {
                    if (board[0][column] == '.') {
                        valid = true;
                    } else {
                        System.out.println("Η στήλη είναι γεμάτη!");
                    }
                } else {
                    System.out.println("Επιλέξτε στήλη 1-" + cols);
                }
            } else {
                System.out.println("Παρακαλώ εισάγετε αριθμό!");
                scanner.next();
            }
        }
        
        return column;
    }
    
    // Τοποθέτηση πιονιού
    public static void dropPiece(int col, char symbol) {
        for (int i = rows - 1; i >= 0; i--) {
            if (board[i][col] == '.') {
                board[i][col] = symbol;
                return;
            }
        }
    }
    
    // Έλεγχος νίκης
    public static boolean checkWin(char symbol) {
        // Οριζόντια
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c <= cols - 4; c++) {
                if (board[r][c] == symbol && board[r][c+1] == symbol &&
                    board[r][c+2] == symbol && board[r][c+3] == symbol) {
                    return true;
                }
            }
        }
        
        // Κάθετα
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r <= rows - 4; r++) {
                if (board[r][c] == symbol && board[r+1][c] == symbol &&
                    board[r+2][c] == symbol && board[r+3][c] == symbol) {
                    return true;
                }
            }
        }
        
        // Διαγώνια (κάτω δεξιά)
        for (int r = 0; r <= rows - 4; r++) {
            for (int c = 0; c <= cols - 4; c++) {
                if (board[r][c] == symbol && board[r+1][c+1] == symbol &&
                    board[r+2][c+2] == symbol && board[r+3][c+3] == symbol) {
                    return true;
                }
            }
        }
        
        // Διαγώνια (πάνω δεξιά)
        for (int r = 3; r < rows; r++) {
            for (int c = 0; c <= cols - 4; c++) {
                if (board[r][c] == symbol && board[r-1][c+1] == symbol &&
                    board[r-2][c+2] == symbol && board[r-3][c+3] == symbol) {
                    return true;
                }
            }
        }
        
        return false;
    }
}