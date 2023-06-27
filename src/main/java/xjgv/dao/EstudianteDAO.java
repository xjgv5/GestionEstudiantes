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

    public static void main(String[] args) {
        //listando estudiantes
        var estudianteDao  = new EstudianteDAO();
        //System.out.println("Listado de estudiantes");
        List<Estudiante> estudiantes = estudianteDao.listar();
        //estudiantes.forEach(System.out::println);

        //buscar por id
        var estudiante1 = new Estudiante(3);
        System.out.println("Estudiante antes de la busqueda: " + estudiante1); //Objeto vacio
        var encontrado = estudianteDao.buscarEstudiantePorID(estudiante1);
        if(encontrado)
            System.out.println("Estudiante encontrado: " + estudiante1);
        else
            System.out.println("No se encontro el estudiante con id :" + estudiante1.getIdEstudiante());
    }
}
