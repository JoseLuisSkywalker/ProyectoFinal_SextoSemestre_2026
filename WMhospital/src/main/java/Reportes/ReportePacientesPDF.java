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

            ConexionBD bd = ConexionBD.getInstance();
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

    
            contenido.setLineWidth(1.5f);
            contenido.moveTo(50, 715);
            contenido.lineTo(550, 715);
            contenido.stroke();

          
            int y = 680;
            int posX_ID = 50;
            int posX_Nombre = 100;
            int posX_Apellido = 220;
            int posX_Edad = 350;
            int posX_Sexo = 420;

           
            contenido.beginText();
            contenido.setFont(fuenteTitulo, 11); 
            
            contenido.newLineAtOffset(posX_ID, y);
            contenido.showText("ID");
            contenido.newLineAtOffset(posX_Nombre - posX_ID, 0);
            contenido.showText("Nombre");
            contenido.newLineAtOffset(posX_Apellido - posX_Nombre, 0);
            contenido.showText("Apellido");
            contenido.newLineAtOffset(posX_Edad - posX_Apellido, 0);
            contenido.showText("Edad");
            contenido.newLineAtOffset(posX_Sexo - posX_Edad, 0);
            contenido.showText("Sexo");
            contenido.endText();

            
            contenido.setLineWidth(1f);
            contenido.moveTo(50, y - 6);
            contenido.lineTo(550, y - 6);
            contenido.stroke();

            y -= 25;
            int totalPacientes = 0;

           
            while (rs.next()) {
              
                String id = String.valueOf(rs.getInt("id_paciente"));
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String edad = String.valueOf(rs.getInt("edad"));
                String sexo = rs.getString("sexo");

                contenido.beginText();
                contenido.setFont(fuenteTexto, 10);
                
           
                contenido.newLineAtOffset(posX_ID, y);
                contenido.showText(id);
                
                contenido.newLineAtOffset(posX_Nombre - posX_ID, 0);
                contenido.showText(nombre != null ? nombre : "-");
                
                contenido.newLineAtOffset(posX_Apellido - posX_Nombre, 0);
                contenido.showText(apellido != null ? apellido : "-");
                
                contenido.newLineAtOffset(posX_Edad - posX_Apellido, 0);
                contenido.showText(edad);
                
                contenido.newLineAtOffset(posX_Sexo - posX_Edad, 0);
                contenido.showText(sexo != null ? sexo : "-");
                
                contenido.endText();

                y -= 18;
                totalPacientes++;
            }

     
            y -= 20;
            contenido.beginText();
            contenido.setFont(fuenteTitulo, 12);
            contenido.newLineAtOffset(50, y);
            contenido.showText("Total de pacientes: " + totalPacientes);
            contenido.endText();

        
            contenido.close();

            String archivo = "ReportePacientes.pdf";
            documento.save(archivo);
            documento.close();

            Desktop.getDesktop().open(new File(archivo));

            
            rs.close();
            stmt.close();
            bd.cerrarConexion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}