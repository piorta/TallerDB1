/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.taller.bd.Bases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringTokenizer;
/**
 *
 * @author Steven
 */
public class Postgresql {
    static Connection conn = null;
    static PreparedStatement PrepareStat = null;
    
    public void ProcesosPostgesql() throws IOException {
        long start_lecturaArchivo = System.currentTimeMillis();
        File file_personas = new File("C:\\Users\\Steven\\Desktop\\archivo.txt");
        List<String> archivo_personas = new ArrayList<String>();
        archivo_personas = FileUtils.readLines(file_personas);
        long end_lecturaArchivo = System.currentTimeMillis();
        System.out.println("Tiempo de lectura: " + (end_lecturaArchivo - start_lecturaArchivo));

        //CARGA A POSTGRESQL
        long start_insertPostgresql = System.currentTimeMillis();
        Conectar();

        for (int i = 0; i < archivo_personas.size(); i++) {
            StringTokenizer st = new StringTokenizer(archivo_personas.get(i), ",");
            try {
                insertar(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(),st.nextToken(),st.nextToken());
            } catch (Exception ex) {
                System.out.println("Error de inserción");
            }
        }
        long end_insertPostgresql = System.currentTimeMillis();
        System.out.println("Tiempo insert Postgresql: " + (end_insertPostgresql - start_insertPostgresql));

    }

    private static void Conectar() {

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Arquitectura", "postgres", "nevets");
            //crunchifyConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Arquitectura", "root", "nevets");
            if (conn != null) {
                System.out.println("Conexión Postgresql exitosa");
            } else {
                System.out.println("Error de conexión a Postgresql");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
            return;
        }

    }

    private static void insertar(String CEDULA, String APELLIDOS, String NOMBRES, String FECHA, String PROVINCIA,String GENERO, String ESTADO_CIVIL) {

        try {
            String insertQueryStatement = "INSERT  INTO  PERSONAS  VALUES  (?,?,?,?,?,?,?)";

            PrepareStat = conn.prepareStatement(insertQueryStatement);
            PrepareStat.setString(1, CEDULA);
            PrepareStat.setString(2, APELLIDOS);
            PrepareStat.setString(3, NOMBRES);
            PrepareStat.setString(4, FECHA);
            PrepareStat.setString(5, PROVINCIA);
            PrepareStat.setString(6, GENERO);
            PrepareStat.setString(7, ESTADO_CIVIL);
            PrepareStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
