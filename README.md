# -_editor_grafico_uml_diagrama_de_clases_- :. 
🧩 Editor Gráfico UML – Diagrama de Clases.

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/849981f8-8439-440f-b07a-4aa81f1a5dde" />  

<img width="2553" height="1079" alt="image" src="https://github.com/user-attachments/assets/fc96bbe3-0cf5-4ac7-a59a-c690ed673b63" />    

Java SE · Swing · Oracle 19c · MVC / DAO 

| Diagrama General | Icono UML | Icono Clase |
|------------------|-----------|-------------|
| <img src="https://github.com/user-attachments/assets/1ac281f3-1592-42cb-a620-8fcaac67e69d" width="400"/> | <img src="https://github.com/user-attachments/assets/34ad178d-698a-4832-bb5a-0e34db5b5720" width="180"/> | <img src="https://github.com/user-attachments/assets/71781218-4c2a-4f22-a17b-98b08f4cc0ad" width="180"/> | 

📌 Descripción General:

Este proyecto implementa una aplicación gráfica en Java SE (Swing) que permite al usuario:

* ✔ Crear diagramas de clases UML
* ✔ Agregar clases, atributos y métodos
* ✔ Persistir la estructura lógica del diagrama 
* ✔ Registrar la información en Oracle 19c
* ✔ Aplicar arquitectura por capas (MVC / DAO)
* ✔ Utilizar Stored Procedures

🎯 Objetivo del Sistema:

Permitir al usuario dibujar un diagrama de clases UML desde una interfaz gráfica, capturar su estructura lógica y almacenarla en una base de datos Oracle 19c para análisis, reutilización o generación de código posterior.

🏗️ Arquitectura General:
```
presentation (Swing GUI)
│
├── controller
│
├── model
│
├── dao
│
└── util
```
🔧 Patrones Aplicados:
* MVC (Model – View – Controller)
* DAO (Data Access Object)
* Stored Procedures
* Separación de responsabilidades

🧠 Modelo Lógico (UML):
* Entidades Principales
- Diagrama
- ClaseUML
- AtributoUML
- MetodoUML
- RelacionUML
- Relaciones

Un Diagrama tiene muchas Clases:
Una ClaseUML tiene muchos Atributos y Metodos.

Las Relaciones conectan Clases:
- 🗄️ Base de Datos – Oracle 19c.
- 📋 Tablas.
```
CREATE TABLE DIAGRAMA (
    ID_DIAGRAMA NUMBER GENERATED ALWAYS AS IDENTITY,
    NOMBRE VARCHAR2(100),
    FECHA_CREACION DATE DEFAULT SYSDATE,
    CONSTRAINT PK_DIAGRAMA PRIMARY KEY (ID_DIAGRAMA)
);
```
```
CREATE TABLE CLASE_UML (
    ID_CLASE NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_DIAGRAMA NUMBER,
    NOMBRE VARCHAR2(100),
    CONSTRAINT PK_CLASE PRIMARY KEY (ID_CLASE),
    CONSTRAINT FK_CLASE_DIAGRAMA FOREIGN KEY (ID_DIAGRAMA)
        REFERENCES DIAGRAMA(ID_DIAGRAMA)
);
```
```
CREATE TABLE ATRIBUTO_UML (
    ID_ATRIBUTO NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_CLASE NUMBER,
    NOMBRE VARCHAR2(100),
    TIPO VARCHAR2(50),
    CONSTRAINT PK_ATRIBUTO PRIMARY KEY (ID_ATRIBUTO)
);
```
```
CREATE TABLE METODO_UML (
    ID_METODO NUMBER GENERATED ALWAYS AS IDENTITY,
    ID_CLASE NUMBER,
    NOMBRE VARCHAR2(100),
    RETORNO VARCHAR2(50),
    CONSTRAINT PK_METODO PRIMARY KEY (ID_METODO)
);
```
⚙️ Stored Procedure Principal.
```
CREATE OR REPLACE PROCEDURE SP_GUARDAR_CLASE (
    P_ID_DIAGRAMA IN NUMBER,
    P_NOMBRE_CLASE IN VARCHAR2
) AS
BEGIN
    INSERT INTO CLASE_UML (ID_DIAGRAMA, NOMBRE)
    VALUES (P_ID_DIAGRAMA, P_NOMBRE_CLASE);
END;
/
```
- 🧱 Capa Modelo (Model).
- 📦 ClaseUML.java
```
package model;

public class ClaseUML {

    private int idClase;
    private String nombre;

    public ClaseUML(String nombre) {
        this.nombre = nombre;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
```
- 🔌 Capa DAO – Acceso a Oracle.
- 📦 ClaseUMLDAO.java
```
package dao;

import util.DBConnection;
import java.sql.*;

public class ClaseUMLDAO {

    public void guardarClase(int idDiagrama, String nombre) throws Exception {

        Connection con = DBConnection.getConnection();
        CallableStatement cs =
                con.prepareCall("{ call SP_GUARDAR_CLASE(?,?) }");

        cs.setInt(1, idDiagrama);
        cs.setString(2, nombre);

        cs.execute();

        cs.close();
        con.close();
    }
}
```
- 🔗 Util – Conexión a Oracle.
- 📦 DBConnection.java
```
package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
        "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USER = "UML_APP";
    private static final String PASS = "oracle";

    public static Connection getConnection() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
```
🖥️ Interfaz Gráfica (Swing).
| Componente UML | Vista Editor | Diagrama Completo |
|----------------|--------------|-------------------|
| <img src="https://github.com/user-attachments/assets/b520d087-1ccf-4e2b-81f8-a8e96bcc2980" width="280"/> | <img src="https://github.com/user-attachments/assets/7e1f2c26-c901-42cb-b363-6c5f551b31c3" width="200"/> | <img src="https://github.com/user-attachments/assets/2ccb394e-07bf-44cd-93ba-83a679319b1e" width="420"/> |

Funciones Principales:
- ✔ Agregar Clase
- ✔ Capturar nombre
- ✔ Guardar en Oracle
- ✔ Interfaz simple y extensible

📦 FrmDiagrama.java
```
package presentation;

import javax.swing.*;
import controller.DiagramaController;

public class FrmDiagrama extends JFrame {

    private JTextField txtClase;
    private JButton btnAgregar;

    public FrmDiagrama() {

        setTitle("Editor UML - Diagrama de Clases");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtClase = new JTextField();
        txtClase.setBounds(50, 50, 200, 30);
        add(txtClase);

        btnAgregar = new JButton("Agregar Clase");
        btnAgregar.setBounds(270, 50, 150, 30);
        add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            new DiagramaController()
                    .agregarClase(txtClase.getText());
        });

        setVisible(true);
    }
}
```
- 🎮 Controller.
- 📦 DiagramaController.java
```
package controller;

import dao.ClaseUMLDAO;

public class DiagramaController {

    public void agregarClase(String nombreClase) {

        try {
            ClaseUMLDAO dao = new ClaseUMLDAO();
            dao.guardarClase(1, nombreClase); // Diagrama demo
            System.out.println("Clase guardada en Oracle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
- 🚀 Ejecución del Sistema.
- 📦 Main.java
```
import presentation.FrmDiagrama;

public class Main {
    public static void main(String[] args) {
        new FrmDiagrama();
    }
}
```
📌 Posibles Extensiones.
* ✔ Dibujar clases con Graphics2D
* ✔ Arrastrar y soltar (Drag & Drop)
* ✔ Relaciones UML (Herencia, Asociación, Composición)
* ✔ Exportar diagrama a PNG / PDF
* ✔ Generar código Java desde el diagrama
* ✔ Versionado de diagramas
* ✔ Edición visual avanzada

✅ Conclusión.
Este proyecto constituye una base sólida, profesional y extensible para un editor UML gráfico en Java SE, con:
- Arquitectura limpia
- Persistencia real en Oracle 19c
- Codigo claro y mantenible
- Enfoque académico y empresarial / .
