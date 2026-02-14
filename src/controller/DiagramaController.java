package controller;

import dao.ClaseUMLDAO;

public class DiagramaController {

    public void agregarClase(String nombreClase) {

        try {
            ClaseUMLDAO dao = new ClaseUMLDAO();
            dao.guardarClase(1, nombreClase); // diagrama demo .
            System.out.println("clase guardada en oracle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}   
