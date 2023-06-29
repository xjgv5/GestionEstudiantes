package xjgv.dao;

import xjgv.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static xjgv.conexion.Conexion.getConection;

public class EstudianteDAO {

    public List<Estudiante> listar(){
        List<Estudiante> estudiantes = new ArrayList<>();

        PreparedStatement ps; //prepara la sentencia que vamos a usar
        ResultSet rs; //Almacena el resultado de la consulta
        Connection con = getConection();
        String sql = "SELECT * FROM estudiante ORDER BY idestudiante";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("idestudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch (Exception e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Ocurrio un error al cerrar la conexion : " + e.getMessage());
            }
        }
        return estudiantes;
    }

    public boolean buscarEstudiantePorID(Estudiante estudiante){
        int idBuscar;
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConection();
        String sql = "select * from estudiante where idestudiante= ?" ;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if (rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        }catch (Exception e){
            System.out.println("Ha ocurrido un error al ejecutar la consulta : " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: " + e.getMessage());
            }
        }
        return false;
    }

    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConection();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
                "VALUES(? , ?, ?, ?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            return  true;
        }catch (Exception e){
            System.out.println("Ocurrio un error al agregar al estudiante : " + e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un problema al cerrar la conexion : " + e.getMessage());
            }

        }
        return  false;
    }

    public static void main(String[] args) {
        //listando estudiantes
        var estudianteDao  = new EstudianteDAO();

        //agregar estudiante
        var nuevoEstudiante = new Estudiante("Carlos", "Lara", "21213444", "carlos@email.com");
        var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
        if (agregado)
            System.out.println("Los datos del estudiante se agregaron correctamente: " + nuevoEstudiante);
        else
            System.out.println("No se pudo agregar al estudiante : " + nuevoEstudiante);


        //System.out.println("Listado de estudiantes");
        List<Estudiante> estudiantes = estudianteDao.listar();
        estudiantes.forEach(System.out::println);

        //buscar por id
       /* var estudiante1 = new Estudiante(3);
        System.out.println("Estudiante antes de la busqueda: " + estudiante1); //Objeto vacio
        var encontrado = estudianteDao.buscarEstudiantePorID(estudiante1);
        if(encontrado)
            System.out.println("Estudiante encontrado: " + estudiante1);
        else
            System.out.println("No se encontro el estudiante con id :" + estudiante1.getIdEstudiante()); */
    }
}
