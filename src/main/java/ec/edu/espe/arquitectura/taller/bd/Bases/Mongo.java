/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.taller.bd.Bases;

import com.mongodb.MongoClient;
import ec.edu.espe.arquitectura.taller.bd.Modelo.Persona;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 * @author Steven
 */
public class Mongo {
    static Connection conn = null;
    static PreparedStatement PrepareStat = null;

    public void ProcesoMongo() {
        System.out.println("Conectandose a Mongo");
        Morphia morphia = new Morphia();
        morphia.mapPackage("ec.edu.espe.arquitectura.deber.mongo.Modelo");
        Datastore ds = morphia.createDatastore(new MongoClient(), "arquitectura");
        ds.ensureIndexes();
        System.out.println("Conexión establecida");

        List<Persona> pers = new ArrayList<Persona>();
        Conectar();
        long start_extraer = System.currentTimeMillis();
        pers = select();
        long end_extraer = System.currentTimeMillis();
        System.out.println("Tiempo de extracción: " + (end_extraer - start_extraer));

        long start_mongo = System.currentTimeMillis();
        for (int i = 0; i < pers.size(); i++) {
            ds.save(pers.get(i));
        }
        long end_mongo = System.currentTimeMillis();
        System.out.println("Tiempo de inserción Mongo: " + (end_mongo - start_mongo));

    }

    private static void Conectar() {

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Arquitectura", "postgres", "nevets");
            //crunchifyConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Arquitectura", "root", "nevets");
            if (conn != null) {
                System.out.println("Conexion exitosa");
            } else {
                System.out.println("Error de conexion");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión");
            e.printStackTrace();
            return;
        }

    }

    private static List<Persona> select() {
        List<Persona> pers = new ArrayList<Persona>();
        try {
            String getQueryStatement = "SELECT * FROM PERSONAS";
            PrepareStat = conn.prepareStatement(getQueryStatement);
            ResultSet rs = PrepareStat.executeQuery();
            while (rs.next()) {
                String CEDULA = rs.getString("cedula");
                String APELLIDOS = rs.getString("apellidos");
                String NOMBRES = rs.getString("nombres");
                String FECHA = rs.getString("fecha");
                String PROVINCIA = rs.getString("provincia");
                String GENERO = rs.getString("genero");
                String ESTADO_CIVIL = rs.getString("estado_civil");
                // Simply Print the results
                Persona persona = new Persona();
                persona.setCedula(CEDULA);
                persona.setApellidos(APELLIDOS);
                persona.setNombres(NOMBRES);
                persona.setFecha(FECHA);
                persona.setProvincia(PROVINCIA);
                persona.setGenero(GENERO);
                persona.setEstado_civil(ESTADO_CIVIL);
                pers.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pers;
    }
}
