import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner leer = new Scanner(System.in);
    public static void main(String[] args) {

        // Obtener instancia única de la conexión
        Connection conexion = ConexionBD.getInstancia().getConexion();

        // ejercicio 1

        try {
            // Consulta SQL
            String sql = "SELECT nombre, apellido, edad, email FROM alumnos";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Nombre: " + rs.getString("nombre") +
                        ", Apellido: " + rs.getString("apellido") +
                        ", Edad: " + rs.getInt("edad") +
                        ", Email: " + rs.getString("email")
                        );
            }

            // Cerrar ResultSet y Statement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            ConexionBD.getInstancia().cerrarConexion();
        }


        // ejercicio 2
/*
        String nuevoNombre, nuevoApellido, nuevoEmail;
        int nuevaEdad;
        System.out.println("Ingresando datos de un nuevo alumno: ");
        System.out.print("Nombre: ");
        nuevoNombre = leer.nextLine();
        System.out.print("Apellido: ");
        nuevoApellido = leer.nextLine();
        System.out.print("Edad: ");
        nuevaEdad = leer.nextInt();
        leer.nextLine();
        System.out.print("Email: ");
        nuevoEmail = leer.nextLine();

        try {
            // Consulta SQL
            String sqlInsert = "INSERT INTO alumnos (nombre, apellido, edad, email) VALUES (?,?,?,?);";
            PreparedStatement stmtInsert = conexion.prepareStatement(sqlInsert);
            stmtInsert.setString(1, nuevoNombre);
            stmtInsert.setString(2, nuevoApellido);
            stmtInsert.setInt(3, nuevaEdad);
            stmtInsert.setString(4, nuevoEmail);
            stmtInsert.executeUpdate();

            //conexion.commit();

            String sql = "SELECT id, nombre FROM alumnos";
            stmtInsert = conexion.prepareStatement(sql);
            ResultSet rs = stmtInsert.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nombre: " + rs.getString("nombre"));
            }

            // Cerrar ResultSet y Statement
            rs.close();
            stmtInsert.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            ConexionBD.getInstancia().cerrarConexion();
        }
*/
/*
        // ejercicio 3
        String nuevaCalle;
        int nuevaAltura, idAlumno;
        System.out.println("Ingresando datos de direccion: ");
        System.out.print("Calle: ");
        nuevaCalle = leer.nextLine();
        System.out.print("Altura: ");
        nuevaAltura = leer.nextInt();
        leer.nextLine();
        System.out.print("ID del alumno: ");
        idAlumno = leer.nextInt();
        leer.nextLine();

        try {
            // Buscar si el alumno existe antes de insertar la dirección
            String sqlSelectAlumno = "SELECT id FROM alumnos WHERE id = ?";
            PreparedStatement stmtSelectAlumno = conexion.prepareStatement(sqlSelectAlumno);
            stmtSelectAlumno.setInt(1, idAlumno);
            ResultSet rsAlumno = stmtSelectAlumno.executeQuery();

            // Verificando si el alumno existe
            if (!rsAlumno.next()) {
                System.out.println("El alumno con ID " + idAlumno + " no existe.");
            } else {
                // Si el alumno existe, proceder a insertar la dirección
                String sqlInsert = "INSERT INTO direcciones (calle, altura, alumno_id) VALUES (?,?, ?);";
                PreparedStatement stmtInsert = conexion.prepareStatement(sqlInsert);
                stmtInsert.setString(1, nuevaCalle);
                stmtInsert.setInt(2, nuevaAltura);
                stmtInsert.setInt(3, idAlumno); // Relacionamos con el alumno con el ID que ingresó el usuario
                stmtInsert.executeUpdate();
                System.out.println("Dirección insertada correctamente.");
            }

            // Cerrar ResultSet y Statement para la consulta del alumno
            rsAlumno.close();
            stmtSelectAlumno.close();

            // Consulta SQL para obtener las direcciones y los nombres de los alumnos
            String sql = "SELECT d.id, d.calle, d.altura, a.nombre " +
                    "FROM direcciones d " +
                    "JOIN alumnos a ON d.alumno_id = a.id";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Recorrer el ResultSet para mostrar las direcciones y el nombre del alumno
            while (rs.next()) {
                int direccionId = rs.getInt("id");
                String calle = rs.getString("calle");
                int altura = rs.getInt("altura");
                String nombreAlumno = rs.getString("nombre");

                System.out.println("Dirección ID: " + direccionId + ", Calle: " + calle + ", Altura: " + altura + ", Alumno: " + nombreAlumno);
            }

            // Cerrar ResultSet y Statement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            ConexionBD.getInstancia().cerrarConexion();
        }

        // ejercicio 4

        try { // PROBAR PORQUE ME ANULAR LAS CONSULTAS
            // Consulta SQL
            String sql = "SELECT a.id AS alumno_id, a.nombre AS alumno_nombre, d.id AS direccion_id, d.calle, d.altura\n" +
                    "FROM alumnos a\n" +
                    "LEFT JOIN direcciones d ON a.id = d.alumno_id;";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int alumnoId = rs.getInt("alumno_id");
                String alumnoNombre = rs.getString("alumno_nombre");
                int direccionId = rs.getInt("direccion_id");
                String calle = rs.getString("calle");
                int altura = rs.getInt("altura");

                System.out.println("Alumno ID: " + alumnoId + ", Nombre: " + alumnoNombre +
                        ", Dirección ID: " + direccionId + ", Calle: " + calle + ", Altura: " + altura);
            }

            // Cerrar ResultSet y Statement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            ConexionBD.getInstancia().cerrarConexion();
        }


        // ejercicio 5
        try{
            String sql = "SELECT d.calle, d.altura, a.nombre, a.apellido FROM direcciones d\n" +
                    "LEFT JOIN alumnos a\n" +
                    "ON a.id = d.alumno_id;";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                String calle = rs.getString("calle");
                int altura = rs.getInt("altura");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                System.out.println("Calle: " + calle + ", altura: " + altura + ", nombre: " + nombre + ", apellido: " + apellido);
            }

            statement.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            ConexionBD.getInstancia().cerrarConexion();
        }


        */

        // ejercicio 6
        int id, nuevaEdad;
        System.out.print("Ingrese la id: ");
        id = leer.nextInt();
        leer.nextLine();

        System.out.print("Ingrese la nueva edad: ");
        nuevaEdad = leer.nextInt();
        leer.nextLine();


        try{
            String sql = "UPDATE alumnos SET edad = "+nuevaEdad+" WHERE id = 1;";
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            statement.close();
            rs.close();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            ConexionBD.getInstancia().cerrarConexion();
        }
    }


}