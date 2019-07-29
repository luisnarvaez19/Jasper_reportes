/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reporte;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 *
 * @author luis
 */
public class JavaCallParameter {
       // Parameters for report
     public static JasperPrint imprimeReporte(String reporteSrcFile,Map<String, Object> parameters) //throws JRException,
           // ClassNotFoundException, SQLException 
     {
         JasperPrint print = null;
         try //throws JRException,
         // ClassNotFoundException, SQLException
         {
             // First, compile jrxml file.
             JasperReport jasperReport =    JasperCompileManager.compileReport(reporteSrcFile);
             
             Connection conn = ConnectionUtils.getConnection();
             
             print = JasperFillManager.fillReport(jasperReport,
                     parameters, conn);
             
             // Make sure the output directory exists.
             File outDir = new File("C:/jasperoutput");
             outDir.mkdirs();
             
             // PDF Exportor.
             JRPdfExporter exporter = new JRPdfExporter();
             
             ExporterInput exporterInput = new SimpleExporterInput(print);
             // ExporterInput
             exporter.setExporterInput(exporterInput);
             
             // ExporterOutput
             OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                     "C:/jasperoutput/FirstJasperWithParameters.pdf");
             // Output
             exporter.setExporterOutput(exporterOutput);
             
             //
             SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
             exporter.setConfiguration(configuration);
             exporter.exportReport();
             
             System.out.print("Done!");
             
         } catch (JRException ex) {
             Logger.getLogger(JavaCallParameter.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(JavaCallParameter.class.getName()).log(Level.SEVERE, null, ex);
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(JavaCallParameter.class.getName()).log(Level.SEVERE, null, ex);
         }
         return print;
    }
}
