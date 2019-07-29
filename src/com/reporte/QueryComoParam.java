/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reporte;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;

import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luis
 */
public class QueryComoParam {

    Connection con;
    Statement stmt;
    ResultSet rs;

    /**
     * SELECT facturas.`fact_id` AS facturas_fact_id, facturas.`fact_cuit` AS
     * facturas_fact_cuit, facturas.`fact_razonS` AS facturas_fact_razonS,
     * facturas.`fact_iva` AS facturas_fact_iva, facturas.`fact_tipo` AS
     * facturas_fact_tipo, facturas.`fact_nroComp` AS facturas_fact_nroComp,
     * facturas.`fact_periodoD` AS facturas_fact_periodoD,
     * facturas.`fact_periodoH` AS facturas_fact_periodoH,
     * facturas.`fact_detalle` AS facturas_fact_detalle, facturas.`fact_usuario`
     * AS facturas_fact_usuario, facturas.`fact_fecha` AS facturas_fact_fecha,
     * facturas.`fact_importe` AS facturas_fact_importe,
     * facturas_detalle.`fact_detalle_id` AS facturas_detalle_fact_detalle_id,
     * facturas_detalle.`fact_detalle_factura` AS
     * facturas_detalle_fact_detalle_factura,
     * facturas_detalle.`fact_detalle_concepto` AS
     * facturas_detalle_fact_detalle_concepto,
     * facturas_detalle.`fact_detalle_cant` AS
     * facturas_detalle_fact_detalle_cant,
     * facturas_detalle.`fact_detalle_precioUnit` AS
     * facturas_detalle_fact_detalle_precioUnit,
     * facturas_detalle.`fact_detalle_subtotal` AS
     * facturas_detalle_fact_detalle_subtotal,
     * facturas_detalle.`fact_detalle_cuenta` AS
     * facturas_detalle_fact_detalle_cuenta FROM `facturas_detalle`
     * facturas_detalle INNER JOIN `facturas` facturas ON
     * facturas_detalle.`fact_detalle_factura` = facturas.`fact_id`
     */
    public void createReport() {
        try {

            String query = "SELECT facturas.`fact_id` AS facturas_fact_id, facturas.`fact_cuit` AS "
                    + " facturas_fact_cuit, facturas.`fact_razonS` AS facturas_fact_razonS,"
                    + " facturas.`fact_iva` AS facturas_fact_iva, facturas.`fact_tipo` AS"
                    + " facturas_fact_tipo, facturas.`fact_nroComp` AS facturas_fact_nroComp,"
                    + " facturas.`fact_periodoD` AS facturas_fact_periodoD,"
                    + " facturas.`fact_periodoH` AS facturas_fact_periodoH,"
                    + " facturas.`fact_detalle` AS facturas_fact_detalle, facturas.`fact_usuario`"
                    + "  AS facturas_fact_usuario, facturas.`fact_fecha` AS facturas_fact_fecha,"
                    + "  facturas.`fact_importe` AS facturas_fact_importe,\n"
                    + "  facturas_detalle.`fact_detalle_id` AS facturas_detalle_fact_detalle_id,"
                    + "  facturas_detalle.`fact_detalle_factura` AS"
                    + "  facturas_detalle_fact_detalle_factura,"
                    + "  facturas_detalle.`fact_detalle_concepto` AS"
                    + "  facturas_detalle_fact_detalle_concepto,"
                    + "  facturas_detalle.`fact_detalle_cant` AS"
                    + "  facturas_detalle_fact_detalle_cant,"
                    + "  facturas_detalle.`fact_detalle_precioUnit` AS"
                    + "  facturas_detalle_fact_detalle_precioUnit,"
                    + "  facturas_detalle.`fact_detalle_subtotal` AS"
                    + "  facturas_detalle_fact_detalle_subtotal,"
                    + "  facturas_detalle.`fact_detalle_cuenta` AS"
                    + "  facturas_detalle_fact_detalle_cuenta FROM `facturas_detalle`"
                    + " facturas_detalle INNER JOIN `facturas` facturas ON"
                    + " facturas_detalle.`fact_detalle_factura` = facturas.`fact_id`";
//" facturas_detalle.`fact_detalle_factura` = facturas.`fact_id`" +
//"  where facturas.`fact_id`=4";
            Connection con = ConnectionUtils.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            JRResultSetDataSource rsdt = new JRResultSetDataSource(rs);
            String reportSrcFile = "C:\\Proyectos\\Freelancer\\Jasper\\NetBeansProjects\\reportes\\reporte_query.jrxml";

            // First, compile jrxml file.
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

            //JasperPrint jp = JasperFillManager.fillReport("report/report1.jasper", new HashMap(), rsdt);
            JasperPrint jp = JasperFillManager.fillReport(jasperReport, new HashMap(), rsdt);
            if (jp != null) {
                //
                 // 1- export to PDF
                 //
                JasperExportManager.exportReportToPdfFile(jp,
                        "C://jasperoutput//sample_report.pdf");

                //
                // 2- export to HTML
                 //
                JasperExportManager.exportReportToHtmlFile(jp,
                        "C://jasperoutput//sample_report.html");

                //
                // 3- export to Excel sheet
                 //
                JRXlsExporter exporter = new JRXlsExporter();
               // ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
               //OutputStream outputfile = new FileOutputStream(new File("c:/jasperoutput/JasperReport.xls"));
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "C://jasperoutput//sample.xls");
                exporter.exportReport();
                // coding For Excel:

            } 
        
            JasperViewer jv = new JasperViewer(jp);
             jv.setVisible(true);
            con.close();
        } catch (ClassNotFoundException | SQLException | JRException ex) {
            ex.printStackTrace();
        } //catch (FileNotFoundException ex) {
            //Logger.getLogger(QueryComoParam.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    public static void main(String[] args) {
        new QueryComoParam().createReport();
    }
}
