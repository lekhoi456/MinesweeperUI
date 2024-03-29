
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates new form Score board
 * @author KhoiLQCE130023
 */
public class ScoreBoard extends javax.swing.JFrame {

    // Creates new form Score Board
    public ScoreBoard(int score, int dif) throws Exception {
        initComponents();
        this.setLocationRelativeTo(null);
        Image favicon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/favicon.png"));
        this.setIconImage(favicon);
        this.setVisible(true);
        this.score = score;
        this.dif = dif;
        setScore(score);
        loadData();
        showRank();
    }

    // set title
    @Override
    public void setTitle(String txtTitle) {
        this.txtTitle.setText(String.valueOf(txtTitle));
    }

    // set score
    public void setScore(int score) {
        this.txtScore.setText(String.valueOf(score));
    }
    
    // set difficult
    public void setDiff(int dif) {
        this.dif = dif;
    }

    // set time with time format
    public void setTime(String txtTime) {
        this.txtTime.setText(String.valueOf(txtTime));
    }

    // load data in storage
    private void loadData() {
        String fileName;
        if (dif == 0) {
            fileName = EASY_NAME;
        } else if (dif == 1) {
            fileName = NORMAL_NAME;
        } else {
            fileName = EXPERT_NAME;
        }
        try {
            us = new UserManagement("src/data/" + fileName + ".txt");
            us.loadUsers();
        } catch (Exception ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // show top 5 highest score
    private void showRank() {
        String userId = showTopUserId(Integer.parseInt(txtScore.getText()));
        txt5UserId.setText(userId);
        String userScore = showTopUserScore(Integer.parseInt(txtScore.getText()));
        txt5Score.setText(userScore);
    }

    // show top 5 user id
    private String showTopUserId(int score) {
        boolean isRank = false;
        String strUserId = "";
        int size;
        ArrayList<User> users = us.getUsers();
        if (users.size() < 5) {
            size = users.size();
        } else {
            size = 5;
        }
        for (int i = 0; i < size; i++) {
            if (!isRank && score >= users.get(i).getScore()) {
                strUserId += "Yourname\n";
                isRank = true;
            }
            strUserId += users.get(i).getUserId() + "\n";
        }
        if (!isRank && size < 5) {
            strUserId += "Yourname\n";
        }
        return strUserId;
    }

    // show top 5 user score
    private String showTopUserScore(int score) {
        boolean isRank = false;
        String strUserScore = "";
        int size;
        ArrayList<User> users = us.getUsers();
        if (users.size() < 5) {
            size = users.size();
        } else {
            size = 5;
        }
        for (int i = 0; i < size; i++) {
            if (!isRank && score >= users.get(i).getScore()) {
                strUserScore += String.format("%3s\n", strUserScore);
                isRank = true;
            }
            strUserScore += String.format("%3s\n", users.get(i).getScore());
        }
        if (!isRank && size < 5) {
            strUserScore += String.format("%3d\n", score);
        }
        return strUserScore;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTitle = new javax.swing.JLabel();
        bttClose = new javax.swing.JLabel();
        txtUserId = new javax.swing.JTextField();
        bttSave = new javax.swing.JLabel();
        txtTime = new javax.swing.JLabel();
        txtScore = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt5UserId = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt5Score = new javax.swing.JTextArea();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
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

        txtTitle.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        txtTitle.setForeground(new java.awt.Color(255, 255, 255));
        txtTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(txtTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 34, 390, 40));

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
        getContentPane().add(bttClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 0, -1, -1));

        txtUserId.setBackground(new java.awt.Color(200, 200, 200));
        txtUserId.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtUserId.setForeground(new java.awt.Color(255, 255, 255));
        txtUserId.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUserId.setText("Yourname");
        txtUserId.setAlignmentX(0.0F);
        txtUserId.setAlignmentY(0.0F);
        txtUserId.setBorder(null);
        txtUserId.setSelectionColor(new java.awt.Color(72, 133, 248));
        txtUserId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserIdActionPerformed(evt);
            }
        });
        getContentPane().add(txtUserId, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 345, 240, 30));

        bttSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bttSave.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        bttSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bttSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttSaveMouseClicked(evt);
            }
        });
        getContentPane().add(bttSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 90, 20));

        txtTime.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtTime.setForeground(new java.awt.Color(255, 255, 255));
        txtTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTime.setText("00:00");
        getContentPane().add(txtTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, -1, 30));

        txtScore.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtScore.setForeground(new java.awt.Color(255, 255, 255));
        txtScore.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtScore.setText("0");
        getContentPane().add(txtScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, 50, 30));

        jPanel1.setBackground(new java.awt.Color(127, 127, 127));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setToolTipText("");
        jPanel1.setAlignmentX(0.0F);
        jPanel1.setAlignmentY(0.0F);
        jPanel1.setAutoscrolls(true);
        jPanel1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(127, 127, 127));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txt5UserId.setEditable(false);
        txt5UserId.setBackground(new java.awt.Color(200, 200, 200));
        txt5UserId.setColumns(20);
        txt5UserId.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt5UserId.setForeground(new java.awt.Color(255, 255, 255));
        txt5UserId.setRows(2);
        txt5UserId.setBorder(null);
        jScrollPane1.setViewportView(txt5UserId);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 144));

        jScrollPane2.setBackground(new java.awt.Color(127, 127, 127));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txt5Score.setEditable(false);
        txt5Score.setBackground(new java.awt.Color(200, 200, 200));
        txt5Score.setColumns(20);
        txt5Score.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt5Score.setForeground(new java.awt.Color(255, 255, 255));
        txt5Score.setRows(2);
        txt5Score.setBorder(null);
        jScrollPane2.setViewportView(txt5Score);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 50, 144));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 360, 120));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/score.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttSaveMouseClicked
        try {
            User account = new User(txtUserId.getText(), Integer.parseInt(txtScore.getText()));
            account = us.checkUser(account);
            if (account == null) {
                us.addUser(new User(txtUserId.getText(), Integer.parseInt(txtScore.getText())));
                return;
            }
            int tmp = Integer.parseInt(txtScore.getText());
            if (tmp <= account.getScore()) {
                return;
            }
            us.changeScore(txtUserId.getText(), Integer.parseInt(txtScore.getText()));
        } catch (Exception ex) {
            Logger.getLogger(ScoreBoard.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JOptionPane.showMessageDialog(null, "Saved successfully!");
            Menu menu = new Menu();
            menu.setVisible(true);
            menu.pack();
            menu.setLocationRelativeTo(null);
            menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.dispose();
        }
    }//GEN-LAST:event_bttSaveMouseClicked

    private void bttCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseClicked
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_bttCloseMouseClicked

    private void bttCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseEntered
        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close_hover.png")));
    }//GEN-LAST:event_bttCloseMouseEntered

    private void bttCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseExited
        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close.png")));
    }//GEN-LAST:event_bttCloseMouseExited

    private void bttCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMousePressed
    }//GEN-LAST:event_bttCloseMousePressed

    private int xCoordinate, yCoordinate;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xCoordinate = evt.getX();
        yCoordinate = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.setLocation(evt.getXOnScreen() - xCoordinate, evt.getYOnScreen() - yCoordinate);
    }//GEN-LAST:event_formMouseDragged

    private void txtUserIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserIdActionPerformed
    }//GEN-LAST:event_txtUserIdActionPerformed

    // variables
    private int dif;
    private int score, userScore;
    private ArrayList<User> users;
    private static UserManagement us;

    // constants
    private static final String EASY_NAME = "easy";
    private static final String NORMAL_NAME = "normal";
    private static final String EXPERT_NAME = "expert";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel bttClose;
    private javax.swing.JLabel bttSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txt5Score;
    private javax.swing.JTextArea txt5UserId;
    public javax.swing.JLabel txtScore;
    private javax.swing.JLabel txtTime;
    private javax.swing.JLabel txtTitle;
    public javax.swing.JTextField txtUserId;
    // End of variables declaration//GEN-END:variables

}
