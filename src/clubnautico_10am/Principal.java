package clubnautico_10am;

import apirest.ISociosAPIRest;
import apirest.SociosDummyAPIRest;
import entidades.Socio;
import guis.SociosForm;
import java.util.List;
import persistencia.ConexionBD;
import persistencia.IConexionBD;
import persistencia.ISociosDAO;
import persistencia.SociosDAO;
import persistencia.SociosListDAO;

public class Principal {

    public static void main(String[] args) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SociosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SociosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SociosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SociosForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
//        IConexionBD conexionBD = new ConexionBD();
//        ISociosDAO sociosDAO = new SociosDAO(conexionBD);
//        ISociosDAO sociosListDAO = new SociosListDAO();
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SociosForm(sociosDAO).setVisible(true);
//                new SociosForm(sociosListDAO).setVisible(true);
//            }
//        });

        ISociosAPIRest sociosAPI = new SociosDummyAPIRest();
        List<Socio> socios = sociosAPI.obtenerSocios();
    }
    
}
