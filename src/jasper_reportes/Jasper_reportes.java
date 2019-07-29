/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasper_reportes;

import com.reporte.ConnectionUtils;
import static com.reporte.JavaCallParameter.imprimeReporte;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luis
 */
public class Jasper_reportes  {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        reporteServlet();
        System.out.println("Fin prueba desde main");
    }
    
    public static void reporteServlet(){
        try {
            // TODO code application logic here
            String reportSrcFile = "C:\\Proyectos\\Freelancer\\Jasper\\NetBeansProjects\\reportes\\reporte_factura_sin.jrxml";

            // First, compile jrxml file.
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

            Connection conn = ConnectionUtils.getConnection();

            // Parameters for report
            Map<String, Object> parameters = new HashMap<>();

            JasperPrint print = imprimeReporte(reportSrcFile, parameters);
            JasperViewer jv = new JasperViewer(print);
            jv.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(Jasper_reportes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Excepcion: JRException " + ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Jasper_reportes.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Excepcion: SQLException " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Jasper_reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
