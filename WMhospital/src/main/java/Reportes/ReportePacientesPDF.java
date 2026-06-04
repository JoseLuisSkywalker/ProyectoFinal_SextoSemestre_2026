package Reportes;

import conexion.ConexionBD;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReportePacientesPDF {

    public static void generarReporte() {

        try {

            ConexionBD bd = new ConexionBD();
            Connection conexion = bd.abrirConexion();

            String sql =
                    "SELECT "
                    + "id_paciente, "
                    + "nombre, "
                    + "apellido, "
                    + "CALCULAR_EDAD(fecha_nacimiento) AS edad, "
                    + "sexo "
                    + "FROM pacientes ";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            PDDocument documento = new PDDocument();
            PDPage pagina = new PDPage();

            documento.addPage(pagina);

            PDPageContentStream contenido =
                    new PDPageContentStream(documento, pagina);

            PDType1Font fuenteTitulo =
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            PDType1Font fuenteTexto =
                    new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            contenido.beginText();
            contenido.setFont(fuenteTitulo, 18);
            contenido.newLineAtOffset(50, 750);
            contenido.showText("WELLMEADOWS HOSPITAL");
            contenido.endText();

            contenido.beginText();
            contenido.setFont(fuenteTitulo, 14);
            contenido.newLineAtOffset(50, 725);
            contenido.showText("Reporte General de Pacientes");
            contenido.endText();

            int y = 680;

            contenido.beginText();
            contenido.setFont(fuenteTexto, 10);
            contenido.newLineAtOffset(50, y);
            contenido.showText(
                    "ID   Nombre   Apellido   Edad   Sexo"
            );
            contenido.endText();

            y -= 20;

            int totalPacientes = 0;

            while (rs.next()) {

                String fila =
                        rs.getInt("id_paciente")
                        + "   "
                        + rs.getString("nombre")
                        + "   "
                        + rs.getString("apellido")
                        + "   "
                        + rs.getInt("edad")
                        + "   "
                        + rs.getString("sexo");

                contenido.beginText();
                contenido.setFont(fuenteTexto, 10);
                contenido.newLineAtOffset(50, y);
                contenido.showText(fila);
                contenido.endText();

                y -= 15;
                totalPacientes++;

            }

            y -= 30;

            contenido.beginText();
            contenido.setFont(fuenteTitulo, 12);
            contenido.newLineAtOffset(50, y);
            contenido.showText(
                    "Total de pacientes: "
                    + totalPacientes
            );
            contenido.endText();

            contenido.close();

            String archivo =
                    "ReportePacientes.pdf";

            documento.save(archivo);
            documento.close();

            Desktop.getDesktop().open(
                    new File(archivo)
            );

            rs.close();
            stmt.close();
            bd.cerrarConexion();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}