package xjgv.presentacion;

import xjgv.dao.EstudianteDAO;
import xjgv.dominio.Estudiante;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class SistemaEstudiantesApp {
    public static void main(String[] args) {
        Scanner consola = new Scanner(System.in);
        int response;

        var estudianteDAO = new EstudianteDAO();
        List<Estudiante> estudiantes = estudianteDAO.listar();
        int id;
        boolean verificacion;

        do{
            response = menu(consola);
            switch (response) {
                case 1 -> {
                    var nuevoEstudiante = new Estudiante();
                    nuevoEstudiante = recolectarInformacion(consola);
                    verificacion = verficarAccion(nuevoEstudiante, consola);
                    if(verificacion){
                        var agregado = estudianteDAO.agregarEstudiante(nuevoEstudiante);
                        if (agregado){
                            System.out.println("Se han agregado correctamente los datos del estudiante ! ");
                        }
                        else {
                            System.out.println("No se ha podido agregar al estudiante ! ");
                        }
                    } else {
                        System.out.println("Regresando al menu, no se han realizado los cambios");
                    }
                }
                case 2 -> {
                    estudiantes.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.println("---------- Estudiantes actuales ------------");
                    estudiantes.forEach(System.out::println);
                    System.out.println("Ingresa el id del usuario a modificar");
                    id = Integer.parseInt(consola.next());
                    var estudianteMOdificado = new Estudiante(id);
                    estudianteMOdificado = recolectarInformacion(consola);
                    verificacion = verficarAccion(estudianteMOdificado, consola);
                    if(verificacion){
                        var modificado = estudianteDAO.agregarEstudiante(estudianteMOdificado);
                        if (modificado){
                            System.out.println("Se han modificado correctamente los datos del estudiante ! ");
                        }
                        else {
                            System.out.println("No se ha podido modificar al estudiante ! ");
                        }
                    } else {
                        System.out.println("Regresando al menu, no se han realizado los cambios");
                    }

                }
                case 4 -> {
                    System.out.println("----------------Listando estudiantes actuales-----------");
                    estudiantes.forEach(System.out::println);
                    System.out.print("¿Cuál desea eliminar ?");
                    id = Integer.parseInt(consola.next());
                    var estudianteEliminado = new Estudiante(id);
                    verificacion = verficarAccion(estudianteEliminado, consola);
                    if(verificacion){
                        var eliminado = estudianteDAO.emilinarEstudiante(estudianteEliminado);
                        if (eliminado){
                            System.out.println("Se ha eliminado correctamente el estudiante ! ");
                        }
                        else {
                            System.out.println("No se ha podido eliminar al estudiante");
                        }
                    }
                    else {
                        System.out.println("Regresando al menu, no se ha realizado ninguna modificacion ");
                    }
                }
                case 5 -> {
                    System.out.println("Saliendo del progama ! ");
                }
                default -> {
                    System.out.println("Opcion no válida");
                }
            }
        }while (response!=5);
    }

    private static int menu(Scanner consola){
        int opcion;
        System.out.println("-----------------------------------------");
        System.out.println("""
                Bienvenido al programa de gestion de estudiantes
                ¿Qué desea hacer?
                1.- Agregar un estudiante
                2.- Listar los estudiantes
                3.- Editar un estudiante
                4.- Eliminar un estudiante
                5.- Salir
                
                """);
        opcion = Integer.parseInt(consola.next());
        return opcion;
    }

    private static boolean verficarAccion(Estudiante estudiante, Scanner consola){
        String opcion;
        boolean verificado = false;
        System.out.println("Está por realizar la siguiente modificacion a la base de datos " +
                estudiante +
                "\n ¿Desea realizarla? (Y/N)");
        opcion = consola.next();
        switch (opcion.toUpperCase()){
            case "Y" -> {
                verificado = true;
            }
            case  "N" -> {
                verificado = false;
            }
            default -> {
                System.out.println("Opcion no valida !");
            }
        }

        return verificado;
    }

    private static Estudiante recolectarInformacion(Scanner consola){
        String nombre;
        String apellido;
        String telefono;
        String email;

        System.out.print("Nombre: ");
        nombre = consola.next();
        System.out.print("Apellido : ");
        apellido = consola.next();
        System.out.print("Telefono : ");
        telefono = consola.next();
        System.out.print("Email: ");
        email = consola.next();
        var estudiante = new Estudiante(nombre, apellido, telefono, email);
        return  estudiante;
    }
}