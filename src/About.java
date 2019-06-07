
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * About frame
 * @author KhoiLQCE130023
 */
public class About extends javax.swing.JFrame {

    /**
     * Creates new form About
     */
    public About() {
        initComponents();
        this.setLocationRelativeTo(null);
        Image favicon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/favicon.png"));
        this.setIconImage(favicon);
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bttHTPlay = new javax.swing.JLabel();
        bttClose = new javax.swing.JLabel();
        bttContact = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setTitle("About");
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
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

        bttHTPlay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bttHTPlay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttHTPlayMouseClicked(evt);
            }
        });
        getContentPane().add(bttHTPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 370, 170, 40));

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
        getContentPane().add(bttClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 0, -1, -1));

        bttContact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bttContact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttContactMouseClicked(evt);
            }
        });
        getContentPane().add(bttContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 170, 40));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/about.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bttContactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttContactMouseClicked
        try {
            Desktop.getDesktop().browse(new URL("https://facebook.com/LQKPlus").toURI());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_bttContactMouseClicked

    // Coordinates
    private int xCoordinate, yCoordinate;
    
    // Get coordinates in screen
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xCoordinate = evt.getX();
        yCoordinate = evt.getY();
    }//GEN-LAST:event_formMousePressed

    // Set coordinates for frame
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.setLocation(evt.getXOnScreen() - xCoordinate, evt.getYOnScreen() - yCoordinate);
    }//GEN-LAST:event_formMouseDragged

    private void bttCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseEntered
        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close_hover.png")));
    }//GEN-LAST:event_bttCloseMouseEntered

    private void bttCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseExited
        bttClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/close.png")));
    }//GEN-LAST:event_bttCloseMouseExited

    private void bttCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMousePressed

    }//GEN-LAST:event_bttCloseMousePressed

    // close the frame
    private void bttCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttCloseMouseClicked
        Menu menu = new Menu();
        menu.setVisible(true);
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_bttCloseMouseClicked

    // excute button how to play
    private void bttHTPlayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttHTPlayMouseClicked
        try {
            Desktop.getDesktop().browse(new URL("https://www.pcworld.com/article/238724/how_to_play_minesweeper_like_a_pro.html").toURI());
        } catch (MalformedURLException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bttHTPlayMouseClicked

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
            java.util.logging.Logger.getLogger(About.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(About.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(About.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(About.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new About().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JLabel bttClose;
    private javax.swing.JLabel bttContact;
    private javax.swing.JLabel bttHTPlay;
    // End of variables declaration//GEN-END:variables
}
