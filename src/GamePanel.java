
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Game Panel
 * @author KhoiLQCE130023
 */
public class GamePanel extends javax.swing.JFrame {

    // Creates new form Game
    public GamePanel() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setIconImage(favicon);
        this.setVisible(true);
    }

    // set  difficult
    public void setDif(int dif) {
        this.dif = dif;
    }

    // get difficult
    public int getDif() {
        return dif;
    }

    // call to game board
    public void gameBoard() {
        setBoard(dif);
        javax.swing.plaf.InternalFrameUI ui = gameBoard.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) ui).setNorthPane(null);
        cellsUncovered = 0;
        cells = new Cell[width][height];
        initImages(dif);
        setLayouts();
    }

    // set size board with difficult mode
    private void setBoard(int dif) {
        switch (dif) {
            case EASY:
                numMines = EASY_NUM_MINES;
                numMinesLeft = EASY_NUM_MINES;
                width = EASY_WIDTH;
                height = EASY_HEIGHT;
                this.setTitle(EASY_NAME);
                break;
            case NORMAL:
                numMines = NORMAL_NUM_MINES;
                numMinesLeft = NORMAL_NUM_MINES;
                width = NORMAL_WIDTH;
                height = NORMAL_HEIGHT;
                this.setTitle(NORMAL_NAME);
                break;
            case EXPERT:
                numMines = EXPERT_NUM_MINES;
                numMinesLeft = EXPERT_NUM_MINES;
                width = EXPERT_WIDTH;
                height = EXPERT_HEIGHT;
                this.setTitle(EXPERT_NAME);
                break;
            default:
                break;
        }
    }

    // load images and scale its
    private void initImages(int dif) {
        images = new ImageIcon[14];
        for (int i = 0; i < 14; i++) {
            images[i] = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/" + i + ".png")));
        }
        int cellSize = 0;
        switch (dif) {
            case 0:
                cellSize = EASY_SIZE_CELL;
                break;
            case 1:
                cellSize = NORMAL_SIZE_CELL;
                break;
            case 2:
                cellSize = EXPERT_SIZE_CELL;
                break;
        }
        for (int index = 0; index < images.length; index++) {
            Image image = images[index].getImage();
            Image imgScaled = image.getScaledInstance(cellSize, cellSize, java.awt.Image.SCALE_AREA_AVERAGING);
            images[index] = new ImageIcon(imgScaled);
        }
    }

    // set layouts
    private void setLayouts() {
        // time and score
        lblTime.setText("00:00");
        lblScore.setText("" + numMinesLeft);

        // game board
        gameBoard.setLayout(new BorderLayout());
        JPanel gameZone = new JPanel();
        gameZone.setLayout(new GridLayout(height, width));

        cellButtons = new JButton[width][height];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cellButtons[col][row] = new JButton();
                cellButtons[col][row].setIcon(images[UNOPENED]);
                cellButtons[col][row].addMouseListener(new MouseListener(this, row, col));
                gameZone.add(cellButtons[col][row]);
            }
        }

        // adding everything to the frame
        gameBoard.add(gameZone);
        gameBoard.pack();
        gameBoard.setResizable(false);
        gameBoard.setVisible(true);

    }

    // generate cells
    private void initCells(int x, int y) {
        Random rand = new Random();
        ArrayList<Integer> rowMines = new ArrayList<>();
        ArrayList<Integer> colMines = new ArrayList<>();

        // generate mines
        do {
            int tempRow = rand.nextInt(height);
            int tempCol = rand.nextInt(width);
            boolean exists = false;
            for (int i = 0; i < rowMines.size(); i++) {
                if (tempRow == rowMines.get(i) && tempCol == colMines.get(i)) {
                    exists = true;
                }
            }
            // check if the prospective mine is in the cell of the user's click
            if (tempRow == x && tempCol == y) {
                exists = true;
            }
            // check if the prospective mine is in the cells surrounding the user's click
            for (int i = x - 1; !exists && i <= x + 1; i++) {
                for (int j = y - 1; !exists && j <= y + 1; j++) {
                    try {
                        if (tempRow == i && tempCol == j) {
                            exists = true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            }

            // if it passes previous checks, then add the new mine
            if (!exists) {
                rowMines.add(tempRow);
                colMines.add(tempCol);
            }
        } while (rowMines.size() < numMines);

        // initialize cells
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                cells[col][row] = new Cell();
            }
        }
        // set cells with the right status
        for (int i = 0; i < rowMines.size(); i++) {
            int currentCol = colMines.get(i);
            int currentRow = rowMines.get(i);
            cells[currentCol][currentRow].setStatus(MINE);
            // initialize surrounding cells
            for (int j = currentRow - 1; j <= currentRow + 1; j++) {
                for (int k = currentCol - 1; k <= currentCol + 1; k++) {
                    if (j != currentRow || k != currentCol) {
                        try {
                            cells[k][j].addToStatus();
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                }
            }
        }

        // Cheating game: show number of cell to console
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                System.out.printf("%3d ", cells[j][i].getStatus());
            }
            System.out.println("");
        }
        System.out.println("\n");
    }

    // uncover cell
    public void uncover(int row, int col) throws IndexOutOfBoundsException, Exception {
        if (cells[0][0] == null) // first click
        {
            initCells(row, col);
            timerThread.start();
        }
        if (cells[col][row].isMine()) {
            if (!cellButtons[col][row].getIcon().equals(images[FLAG])) {
                mineWrong();
            }
        } else if (cells[col][row].isSafe()) {
            cells[col][row].uncover();
            cellButtons[col][row].setIcon(images[cells[col][row].getStatus()]);
            cellsUncovered++;
            uncoverAll(row, col);
        } else if (!cells[col][row].isUncovered()) {
            cells[col][row].uncover();
            cellButtons[col][row].setIcon(images[cells[col][row].getStatus()]);
            cellsUncovered++;
        }
        // check if all the necessary cells have been uncovered 
        checkWin();

    }

    //Double click to uncover surrounding cell
    public void uncoverAll(int row, int col) throws Exception {
        int numMinesMarked = 0;
        int numMinesSurrounding;
        try {
            numMinesSurrounding = cells[col][row].getStatus();
        } catch (NullPointerException e) {
            return;
        }
        boolean minesMarkedCorrectly = true;
        // see if all surrounding mines have been marked
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != row || j != col) {
                    try {
                        if (cellButtons[j][i].getIcon().equals(images[FLAG])) {
                            numMinesMarked++;
                            if (!cells[j][i].isMine()) {
                                minesMarkedCorrectly = false;
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            }
        }
        // uncover surrounding cells
        if (minesMarkedCorrectly && numMinesMarked == numMinesSurrounding) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (i != row || j != col) {
                        try {
                            if (!cells[j][i].isMine() && !cells[j][i].isUncovered()) {
                                uncover(i, j);
                            }
                        } catch (IndexOutOfBoundsException e) {
                        }
                    }
                }
            }
        } else // the mines were not marked correctly
        {
            if (numMinesMarked == numMinesSurrounding) {
                mineWrong();
            }
        }
    }

    // mark the mine
    public void markMine(int row, int col) {
        if (cellButtons[col][row].isEnabled()) {
            if (cellButtons[col][row].getIcon().equals(images[UNOPENED])) {
                cellButtons[col][row].setIcon(images[FLAG]);
                score(false);
            } else if (cellButtons[col][row].getIcon().equals(images[FLAG])) {
                cellButtons[col][row].setIcon(images[UNOPENED]);
                score(true);
            }
        }
    }

    // the mine is wrong
    private void mineWrong() throws Exception {
        timerThread.interrupt();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                JButton cell = cellButtons[col][row];
                if ((cell.getIcon().equals(images[FLAG])) && (!cells[col][row].isMine())) {
                    cell.setIcon(images[FLATX]);
                } else if ((cell.getIcon().equals(images[FLAG])) && cells[col][row].isMine()) {
                    cell.setIcon(images[MINE]);
                    score += 10;
                } else if (cells[col][row].isMine()) {
                    cell.setIcon(images[HOLE]);
                }
            }
        }
        numMinesLeft--;
        isWin = false;
        JOptionPane.showMessageDialog(null, "Game over. You lose!", "Minesweeper 2019", 1);
        showScoreBoard();
    }

    private void checkWin() throws Exception {
        if (cellsUncovered == (height * width - numMines)) {
            timerThread.interrupt();
            isWin = true;
            JOptionPane.showMessageDialog(null, "Congratulations. You won!", "Minesweeper 2019", 1);
            switch (dif) {
                case 0:
                    score = EASY_NUM_MINES * 10;
                    break;
                case 1:
                    score = NORMAL_NUM_MINES * 10;
                    break;
                case 2:
                    score = EXPERT_NUM_MINES * 10;
                    break;
            }
            showScoreBoard();
        }
    }

    // close this panel and open menu frame
    public void closePanel() throws Exception {
        timerThread.interrupt();
        time = 0;
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }

    // show score board and close this panel
    public void showScoreBoard() throws Exception {
        ScoreBoard gamer = new ScoreBoard(score, dif);
        gamer.setVisible(true);
        gamer.pack();
        gamer.setLocationRelativeTo(null);
        gamer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gamer.setTime(secToTime(time));
        if (isWin) {
            gamer.setTitle("You are the Winner!");
        } else {
            gamer.setTitle("Better Lucky Next Time!");
        }
        time=0;
        this.dispose();
    }

    // the score
    private void score(boolean inc) {
        if (inc) {
            numMinesLeft = numMinesLeft + 1;
        } else {
            numMinesLeft = numMinesLeft - 1;
        }
        lblScore.setText("" + numMinesLeft);
    }

    // label mine left
    public class lblMinesLeft extends JLabel {
        public lblMinesLeft(int numMinesLeft) {
            lblScore = new lblMinesLeft(numMinesLeft);
        }
    }

    // increase time +1 every 1 second
    private class TimerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    lblTime.setText(secToTime(++time));
                    --score;
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    // convert second to MM:SS
    private String secToTime(int sec) {
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("%02d:%02d", minutes, seconds);
    }

    public int getScore() {
        return numMinesLeft;
    }

    public void setTitle(String txtTitle) {
        this.txtTitle.setText(String.valueOf(txtTitle));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bttMinimize = new javax.swing.JLabel();
        txtTitle = new javax.swing.JLabel();
        bttClose = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        gameBoard = new javax.swing.JInternalFrame();
        background = new javax.swing.JLabel();

        setUndecorated(true);
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bttMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bttMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttMinimizeMouseClicked(evt);
            }
        });
        getContentPane().add(bttMinimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 4, 40, 30));

        txtTitle.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(255, 255, 255));
        txtTitle.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtTitle.setText("Minesweeper");
        getContentPane().add(txtTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 6, -1, 20));

        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close.png"))); // NOI18N
        bttClose.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bttClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bttCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bttCloseMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bttCloseMousePressed(evt);
            }
        });
        getContentPane().add(bttClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 0, 50, -1));

        lblScore.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lblScore.setForeground(new java.awt.Color(46, 133, 221));
        getContentPane().add(lblScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 820, 70, 50));

        lblTime.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lblTime.setForeground(new java.awt.Color(46, 133, 221));
        getContentPane().add(lblTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 820, 130, 50));

        gameBoard.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gameBoard.setForeground(new java.awt.Color(255, 0, 0));
        gameBoard.setToolTipText("");
        gameBoard.setAlignmentX(0.0F);
        gameBoard.setAlignmentY(0.0F);
        gameBoard.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        gameBoard.setFocusCycleRoot(false);
        gameBoard.setFont(new java.awt.Font("Segoe UI", 0, 3)); // NOI18N
        gameBoard.setFrameIcon(null);
        gameBoard.setMaximumSize(new java.awt.Dimension(650, 650));
        gameBoard.setMinimumSize(new java.awt.Dimension(50, 50));
        gameBoard.setOpaque(true);
        gameBoard.setPreferredSize(new java.awt.Dimension(650, 650));
        gameBoard.setRequestFocusEnabled(false);
        gameBoard.setVerifyInputWhenFocusTarget(false);
        gameBoard.setVisible(true);
        gameBoard.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(gameBoard, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 650, 650));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/gameboard.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private int xCoordinate, yCoordinate;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xCoordinate = evt.getX();
        yCoordinate = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.setLocation(evt.getXOnScreen() - xCoordinate, evt.getYOnScreen() - yCoordinate);
    }//GEN-LAST:event_formMouseDragged

    private void bttCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseClicked
        try {
            closePanel();
        } catch (Exception ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bttCloseMouseClicked

    private void bttCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseEntered
        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close_hover.png")));
    }//GEN-LAST:event_bttCloseMouseEntered

    private void bttCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseExited
        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close.png")));
    }//GEN-LAST:event_bttCloseMouseExited

    private void bttCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMousePressed

    }//GEN-LAST:event_bttCloseMousePressed

    private void bttMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttMinimizeMouseClicked
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_bttMinimizeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GamePanel().setVisible(true);
            }
        });
    }

    // constants
    private static final int EASY = 0;
    private static final int NORMAL = 1;
    private static final int EXPERT = 2;
    private static final String EASY_NAME = "Minesweeper 2019 - Easy Level";
    private static final String NORMAL_NAME = "Minesweeper 2019 - Normal Level";
    private static final String EXPERT_NAME = "Minesweeper 2019 - Expert Level";
    private static final int EASY_SIZE_CELL = 72;
    private static final int NORMAL_SIZE_CELL = 36;
    private static final int EXPERT_SIZE_CELL = 25;
    private static final int EASY_NUM_MINES = 10;
    private static final int NORMAL_NUM_MINES = 40;
    private static final int EXPERT_NUM_MINES = 99;
    private static final int EASY_HEIGHT = 9;
    private static final int EASY_WIDTH = 9;
    private static final int NORMAL_HEIGHT = 16;
    private static final int NORMAL_WIDTH = 16;
    private static final int EXPERT_HEIGHT = 24;
    private static final int EXPERT_WIDTH = 24;
    private static final int MINE = 9;
    private static final int HOLE = 10;
    private static final int FLAG = 11;
    private static final int FLATX = 12;
    private static final int UNOPENED = 13;

    // instance gameboard
    private JButton[][] cellButtons;
    private Cell[][] cells;

    // image of cells
    private ImageIcon[] images;
    private Image favicon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/favicon.png"));

    private int dif; // difficult
    private boolean isWin;

    // variables of gameboard
    private int score = 0;
    private int numMines;
    public int numMinesLeft;
    private static int height;
    private static int width;
    private static int cellsUncovered;
    private TimerThread timerThread = new TimerThread();
    private static int time = 0;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel bttClose;
    private javax.swing.JLabel bttMinimize;
    private javax.swing.JInternalFrame gameBoard;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel txtTitle;
    // End of variables declaration//GEN-END:variables
}
