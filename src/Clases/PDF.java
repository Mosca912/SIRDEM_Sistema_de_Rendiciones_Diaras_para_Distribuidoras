/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import com.itextpdf.text.BaseColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDF {

    public static void save_PDF_Transfers(Connection con, String rutaArchivo, int id, int indice) throws Exception {

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada;
        fechaFormateada = hoy.format(formatoDeseado);
        float pageHeight = PageSize.A4.getHeight();

        // 📸 Cargar imagen (desde carpeta del proyecto o ruta absoluta)
        com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(PDF.class.getResource("/Recursos/Controldetransferencias.png"));

        // 📏 Ajustar tamaño y posición
        img.setAbsolutePosition(0, 590);

        // 🧩 Agregar al documento
        document.add(img);

        PdfContentByte canvas = writer.getDirectContent();

        String sql = "SELECT Nombre, Apellido, DNI FROM preventista WHERE id_Preventista=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String pre = rs.getString("Nombre") + " " + rs.getString("Apellido");
                String dni = rs.getString("DNI");

                com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(pre, font),
                        98, 652, 0);

                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(dni, font),
                        315, 652, 0);

                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(fechaFormateada, font),
                        65, 692, 0);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }

        document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n")); // 10 saltos de línea = ~180 pts de margen
        String anio = String.valueOf(LocalDate.now().getYear());
        if (indice == 0) {
            int numMes = LocalDate.now().getMonthValue();
            PreparedStatement stm = con.prepareStatement("SELECT clientes.Nombre, clientes.Apellido, transferencia.Saldo, zonas.Nombre, estado.Estado from  transferencia inner join ficha on transferencia.id_Ficha=ficha.id_Ficha inner join estado on transferencia.id_Estado=estado.idEstado inner join clientes on transferencia.id_cliente=clientes.id_Clientes inner join zonas on clientes.Zona=zonas.id_Zonas inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_preventista=? and MONTH(fecha.fecha)=? AND YEAR(fecha.fecha) = YEAR(CURDATE())and id_Estado=2;",
                    java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            stm.setInt(1, id);
            stm.setInt(2, numMes);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph titulo = new Paragraph("Transferencias del Mes:" + numMes + " - AÑO: " + anio + "\n\n", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                // Crear una tabla de 3 columnas en el PDF
                PdfPTable pdfTable = new PdfPTable(4);
                pdfTable.setWidthPercentage(100);
                pdfTable.setHeaderRows(1); // primera fila = encabezado

                // Fuente para encabezado
                com.itextpdf.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

                // Encabezados
                String[] headers = {"Nombre y Apellido", "Saldo", "Zona", "Estado"};
                for (String h : headers) {
                    PdfPCell header = new PdfPCell(new Phrase(h, fontHeader));
                    header.setBackgroundColor(BaseColor.DARK_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(5);
                    pdfTable.addCell(header);
                }

                // Cargar filas
                com.itextpdf.text.Font fontFila = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
                BaseColor colorFondo;
                while (rs.next()) {
                    // 1. OBTENER EL VALOR DE ESTADO
                    String estado = rs.getString("estado.Estado");

                    // 2. DETERMINAR EL COLOR
                    if ("Aprobado".equalsIgnoreCase(estado) || "Acreditado".equalsIgnoreCase(estado)) {
                        colorFondo = BaseColor.GREEN;
                    } else if ("Pendiente".equalsIgnoreCase(estado)) {
                        colorFondo = BaseColor.YELLOW;
                    } else {
                        colorFondo = BaseColor.WHITE;
                    }

                    String nombreCompleto = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
                    PdfPCell cellNombre = new PdfPCell(new Phrase(nombreCompleto, fontFila));
                    cellNombre.setBackgroundColor(colorFondo);
                    cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellNombre);

                    // Columna 2: Saldo
                    String saldo = rs.getString("transferencia.Saldo");
                    PdfPCell cellSaldo = new PdfPCell(new Phrase(saldo, fontFila));
                    cellSaldo.setBackgroundColor(colorFondo);
                    cellSaldo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellSaldo);

                    String zona = rs.getString("zonas.Nombre");
                    PdfPCell cellZona = new PdfPCell(new Phrase(zona, fontFila));
                    cellZona.setBackgroundColor(colorFondo);
                    cellZona.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellZona);

                    // Columna 3: Estado
                    PdfPCell cellEstado = new PdfPCell(new Phrase(estado, fontFila));
                    cellEstado.setBackgroundColor(colorFondo);
                    cellEstado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellEstado);
                }

                // Agregar la tabla al documento
                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(null, "Guardado con exito en " + rutaArchivo);
            } else {
                JOptionPane.showMessageDialog(null, "Este preventista no tiene transferencias pendientes!");
            }
        } else {
            PreparedStatement stm = con.prepareStatement("SELECT clientes.Nombre, clientes.Apellido, transferencia.Saldo, zonas.Nombre, estado.Estado from  transferencia inner join ficha on transferencia.id_Ficha=ficha.id_Ficha inner join estado on transferencia.id_Estado=estado.idEstado inner join clientes on transferencia.id_cliente=clientes.id_Clientes inner join zonas on clientes.Zona=zonas.id_Zonas inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_preventista=? and MONTH(fecha.fecha)=? AND YEAR(fecha.fecha) = YEAR(CURDATE())and id_Estado=2;",
                    java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            System.out.println(id);
            System.out.println(indice);
            stm.setInt(1, id);
            stm.setInt(2, indice);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                rs.beforeFirst();
                com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph titulo = new Paragraph("Transferencias del Mes:" + indice + " - AÑO: " + anio + "\n\n", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                PdfPTable pdfTable = new PdfPTable(4);
                pdfTable.setWidthPercentage(100);
                pdfTable.setHeaderRows(1); // primera fila = encabezado

                // Fuente para encabezado
                com.itextpdf.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

                // Encabezados
                String[] headers = {"Nombre y Apellido", "Saldo", "Zona", "Estado"};
                for (String h : headers) {
                    PdfPCell header = new PdfPCell(new Phrase(h, fontHeader));
                    header.setBackgroundColor(BaseColor.DARK_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(5);
                    pdfTable.addCell(header);
                }

                // Cargar filas
                com.itextpdf.text.Font fontFila = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
                BaseColor colorFondo;
                while (rs.next()) {
                    // 1. OBTENER EL VALOR DE ESTADO
                    String estado = rs.getString("estado.Estado");

                    // 2. DETERMINAR EL COLOR
                    if ("Aprobado".equalsIgnoreCase(estado) || "Acreditado".equalsIgnoreCase(estado)) {
                        colorFondo = BaseColor.GREEN;
                    } else if ("Pendiente".equalsIgnoreCase(estado)) {
                        colorFondo = BaseColor.YELLOW;
                    } else {
                        colorFondo = BaseColor.WHITE;
                    }

                    String nombreCompleto = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
                    PdfPCell cellNombre = new PdfPCell(new Phrase(nombreCompleto, fontFila));
                    cellNombre.setBackgroundColor(colorFondo);
                    cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellNombre);

                    // Columna 2: Saldo
                    String saldo = rs.getString("transferencia.Saldo");
                    PdfPCell cellSaldo = new PdfPCell(new Phrase(saldo, fontFila));
                    cellSaldo.setBackgroundColor(colorFondo);
                    cellSaldo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellSaldo);

                    String zona = rs.getString("zonas.Nombre");
                    PdfPCell cellZona = new PdfPCell(new Phrase(zona, fontFila));
                    cellZona.setBackgroundColor(colorFondo);
                    cellZona.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellZona);

                    // Columna 3: Estado
                    PdfPCell cellEstado = new PdfPCell(new Phrase(estado, fontFila));
                    cellEstado.setBackgroundColor(colorFondo);
                    cellEstado.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellEstado);
                }

                // Agregar la tabla al documento
                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(null, "Guardado con exito en " + rutaArchivo);
            } else {
                JOptionPane.showMessageDialog(null, "Este preventista no tiene transferencias pendientes!");
            }
        }

    }

    public static void save_PDF_Trusted(Connection con, String rutaArchivo, int id, int indice, int verificacionpdf) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada;
        fechaFormateada = hoy.format(formatoDeseado);
        float pageHeight = PageSize.A4.getHeight();

        // 📸 Cargar imagen (desde carpeta del proyecto o ruta absoluta)
        com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(PDF.class.getResource("/Recursos/ControldeFiados.png"));

        // 📏 Ajustar tamaño y posición
        img.setAbsolutePosition(0, 590);

        // 🧩 Agregar al documento
        document.add(img);

        PdfContentByte canvas = writer.getDirectContent();

        String sql = "SELECT Nombre, Apellido, DNI FROM preventista WHERE id_Preventista=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String pre = rs.getString("Nombre") + " " + rs.getString("Apellido");
                String dni = rs.getString("DNI");

                com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(pre, font),
                        98, 652, 0);

                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(dni, font),
                        315, 652, 0);

                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Phrase(fechaFormateada, font),
                        65, 692, 0);

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }

        document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n")); // 10 saltos de línea = ~180 pts de margen
        String anio = String.valueOf(LocalDate.now().getYear());
        if (indice == 0) {
            int numMes = LocalDate.now().getMonthValue();
            int anio2 = LocalDate.now().getYear();
            PreparedStatement stm = con.prepareStatement("SELECT f.idFiado, f.Saldo AS SaldoInicialFiado, COALESCE(h.total_historial, 0) AS TotalPagosAcumulados, (f.Saldo - COALESCE(h.total_historial, 0)) AS SaldoRestante,  fe.fecha, c.Nombre, c.Apellido, z.Nombre as Zonas FROM fiado f INNER JOIN clientes c ON f.id_Cliente = c.id_Clientes INNER JOIN zonas as z on c.Zona=z.id_Zonas INNER JOIN ficha fi ON f.id_Ficha = fi.id_Ficha INNER JOIN fecha fe ON fi.id_Fecha = fe.id_Fecha LEFT JOIN (SELECT id_fiado, SUM(saldo) AS total_historial FROM historial GROUP BY id_fiado) AS h ON f.idFiado = h.id_fiado WHERE fi.id_Preventista =? AND MONTH(fe.fecha) = ? AND YEAR(fe.fecha) = ? AND (f.Saldo - COALESCE(h.total_historial, 0)) > 0;",
                    java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            stm.setInt(1, id);
            stm.setInt(2, numMes);
            stm.setInt(3, anio2);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph titulo = new Paragraph("Fiados del Mes:" + numMes + " - AÑO: " + anio + "\n\n", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                // Crear una tabla de 3 columnas en el PDF
                PdfPTable pdfTable = new PdfPTable(6);
                pdfTable.setWidthPercentage(100);
                pdfTable.setHeaderRows(1); // primera fila = encabezado

                // Fuente para encabezado
                com.itextpdf.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

                // Encabezados
                String[] headers = {"Nombre y Apellido", "Saldo Inicial", "Total pagado", "Saldo Restante", "Zona", "Fecha"};
                for (String h : headers) {
                    PdfPCell header = new PdfPCell(new Phrase(h, fontHeader));
                    header.setBackgroundColor(BaseColor.DARK_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(5);
                    pdfTable.addCell(header);
                }

                // Cargar filas
                com.itextpdf.text.Font fontFila = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
                while (rs.next()) {

                    String nombreCompleto = rs.getString("c.Nombre") + " " + rs.getString("c.Apellido");
                    PdfPCell cellNombre = new PdfPCell(new Phrase(nombreCompleto, fontFila));
                    cellNombre.setBackgroundColor(BaseColor.YELLOW);
                    cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellNombre);

                    // Columna 2: Saldo
                    String saldo = rs.getString("SaldoInicialFiado");
                    PdfPCell cellSaldo = new PdfPCell(new Phrase(saldo, fontFila));
                    cellSaldo.setBackgroundColor(BaseColor.YELLOW);
                    cellSaldo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellSaldo);

                    // Columna 3: Estado
                    String TPA = rs.getString("TotalPagosAcumulados");
                    PdfPCell cellTPA = new PdfPCell(new Phrase(TPA, fontFila));
                    cellTPA.setBackgroundColor(BaseColor.YELLOW);
                    cellTPA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellTPA);

                    String sr = rs.getString("SaldoRestante");
                    PdfPCell cellsr = new PdfPCell(new Phrase(sr, fontFila));
                    cellsr.setBackgroundColor(BaseColor.YELLOW);
                    cellsr.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellsr);

                    String zon = rs.getString("Zonas");
                    PdfPCell cellzon = new PdfPCell(new Phrase(zon, fontFila));
                    cellzon.setBackgroundColor(BaseColor.YELLOW);
                    cellzon.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellzon);

                    String fec = rs.getString("fe.fecha");
                    PdfPCell cellfec = new PdfPCell(new Phrase(fec, fontFila));
                    cellfec.setBackgroundColor(BaseColor.YELLOW);
                    cellfec.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellfec);
                }

                // Agregar la tabla al documento
                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(null, "Guardado con exito en " + rutaArchivo);
            } else {
                JOptionPane.showMessageDialog(null, "Este preventista no tiene fiados pendientes!");
            }
        } else {
            int numMes = LocalDate.now().getMonthValue();
            int anio2 = LocalDate.now().getYear();
            PreparedStatement stm = con.prepareStatement("SELECT f.idFiado, f.Saldo AS SaldoInicialFiado, COALESCE(h.total_historial, 0) AS TotalPagosAcumulados, (f.Saldo - COALESCE(h.total_historial, 0)) AS SaldoRestante,  fe.fecha, c.Nombre, c.Apellido, z.Nombre as Zonas FROM fiado f INNER JOIN clientes c ON f.id_Cliente = c.id_Clientes INNER JOIN zonas as z on c.Zona=z.id_Zonas INNER JOIN ficha fi ON f.id_Ficha = fi.id_Ficha INNER JOIN fecha fe ON fi.id_Fecha = fe.id_Fecha LEFT JOIN (SELECT id_fiado, SUM(saldo) AS total_historial FROM historial GROUP BY id_fiado) AS h ON f.idFiado = h.id_fiado WHERE fi.id_Preventista =? AND MONTH(fe.fecha) = ? AND YEAR(fe.fecha) = ? AND (f.Saldo - COALESCE(h.total_historial, 0)) > 0;",
                    java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            stm.setInt(1, id);
            stm.setInt(2, indice);
            stm.setInt(3, anio2);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                rs.beforeFirst();
                com.itextpdf.text.Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
                Paragraph titulo = new Paragraph("Fiados del Mes:" + indice + " - AÑO: " + anio + "\n\n", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                // Crear una tabla de 3 columnas en el PDF
                PdfPTable pdfTable = new PdfPTable(6);
                pdfTable.setWidthPercentage(100);
                pdfTable.setHeaderRows(1); // primera fila = encabezado

                // Fuente para encabezado
                com.itextpdf.text.Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);

                // Encabezados
                String[] headers = {"Nombre y Apellido", "Saldo Inicial", "Total pagado", "Saldo Restante", "Zona", "Fecha"};
                for (String h : headers) {
                    PdfPCell header = new PdfPCell(new Phrase(h, fontHeader));
                    header.setBackgroundColor(BaseColor.DARK_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPadding(5);
                    pdfTable.addCell(header);
                }

                // Cargar filas
                com.itextpdf.text.Font fontFila = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
                while (rs.next()) {

                    String nombreCompleto = rs.getString("c.Nombre") + " " + rs.getString("c.Apellido");
                    PdfPCell cellNombre = new PdfPCell(new Phrase(nombreCompleto, fontFila));
                    cellNombre.setBackgroundColor(BaseColor.YELLOW);
                    cellNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellNombre);

                    // Columna 2: Saldo
                    String saldo = rs.getString("SaldoInicialFiado");
                    PdfPCell cellSaldo = new PdfPCell(new Phrase(saldo, fontFila));
                    cellSaldo.setBackgroundColor(BaseColor.YELLOW);
                    cellSaldo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellSaldo);

                    // Columna 3: Estado
                    String TPA = rs.getString("TotalPagosAcumulados");
                    PdfPCell cellTPA = new PdfPCell(new Phrase(TPA, fontFila));
                    cellTPA.setBackgroundColor(BaseColor.YELLOW);
                    cellTPA.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellTPA);

                    String sr = rs.getString("SaldoRestante");
                    PdfPCell cellsr = new PdfPCell(new Phrase(sr, fontFila));
                    cellsr.setBackgroundColor(BaseColor.YELLOW);
                    cellsr.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellsr);

                    String zon = rs.getString("Zonas");
                    PdfPCell cellzon = new PdfPCell(new Phrase(zon, fontFila));
                    cellzon.setBackgroundColor(BaseColor.YELLOW);
                    cellzon.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellzon);

                    String fec = rs.getString("fe.fecha");
                    PdfPCell cellfec = new PdfPCell(new Phrase(fec, fontFila));
                    cellfec.setBackgroundColor(BaseColor.YELLOW);
                    cellfec.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cellfec);
                }

                // Agregar la tabla al documento
                document.add(pdfTable);
                document.close();
                JOptionPane.showMessageDialog(null, "Guardado con exito en " + rutaArchivo);
            } else {
                JOptionPane.showMessageDialog(null, "Este preventista no tiene fiados pendientes!");
            }
        }
    }

    public static void abrirPDF(String recurso) {
        try {
        InputStream is = New_file_window.class.getResourceAsStream(recurso);

        if (is == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el recurso: " + recurso);
            return;
        }

        // Crear archivo temporal
        File temp = File.createTempFile("manual_", ".pdf");
        temp.deleteOnExit();

        Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);

        Desktop.getDesktop().open(temp);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al abrir el PDF: " + e.getMessage());
    }
    }

}
