package com.owl.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List; // Importar solo java.util.List

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.owl.Models.DetallePedido;
import com.owl.Models.Pedidos;

public class BoletaUtils {

    public static void generarBoleta(Pedidos pedido, List<DetallePedido> detalles, String outputPath) {
        try {
            // Obtener el archivo PDF base como un recurso
            InputStream pdfBaseStream = BoletaUtils.class.getClassLoader()
                    .getResourceAsStream("boleta/boletabase.pdf");

            if (pdfBaseStream == null) {
                throw new FileNotFoundException("No se encontró el archivo boletabase.pdf en la carpeta resources/boleta.");
            }

            // Cargar el PDF base desde el recurso
            PdfReader reader = new PdfReader(pdfBaseStream);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPath));

            // Obtener el contenido del PDF
            PdfContentByte canvas = stamper.getOverContent(1); // Página 1

            // Configurar la fuente
            BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
            canvas.setFontAndSize(font, 10);

            // Escribir los datos del cliente con un tamaño de fuente más grande
            canvas.beginText();
            canvas.setFontAndSize(font, 14); // Cambiar el tamaño de la fuente a 14
            canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, "Cliente: " + pedido.getCliente().getNombre(), 75, 520, 0);
            canvas.setFontAndSize(font, 10); // Restaurar el tamaño de la fuente a 10
            canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, "" + pedido.getFechaPedido().toString(), 450, 695, 0);
            canvas.endText();

            // Escribir los detalles del pedido
            int y = 450; // Posición vertical inicial para los detalles
            for (DetallePedido detalle : detalles) {
                canvas.beginText();
                canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, detalle.getNombreConexion(), 150, y, 0); // Nombre de la conexión
                canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(detalle.getCantidad()), 385, y, 0); // Cantidad
                canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(detalle.getPrecioUnitario())+ "/S", 450, y, 0); // Precio unitario
                canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, String.valueOf(detalle.getTotal())+ "/S", 520, y, 0); // Subtotal
                canvas.endText();
                y -= 20; // Mover hacia abajo para el siguiente ítem
            }

            // Escribir el total
            canvas.beginText();
            canvas.showTextAligned(PdfContentByte.ALIGN_LEFT, "" + pedido.getPrecioTotal() + "/S", 515, 153, 0);
            canvas.endText();

            // Cerrar el PDF
            stamper.close();
            reader.close();

            System.out.println("Boleta generada correctamente en: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}