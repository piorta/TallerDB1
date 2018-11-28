/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.taller.bd.Bases;

import com.mongodb.MongoClient;
import ec.edu.espe.arquitectura.taller.bd.Modelo.Persona;
import java.util.List;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import redis.clients.jedis.Jedis;

public class Redis {

    public void insertar() {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        //Conexion Redis
        Jedis jedis = new Jedis("localhost");
        //Conexion Mongo
        Morphia morphia = new Morphia();
        morphia.mapPackage("ec.edu.espe.arquitectura.taller.bd.Modelo");
        Datastore ds = morphia.createDatastore(new MongoClient(), "arquitectura");
        System.out.println("Conexion establecida Redis");
        List<Persona> persona = ds.createQuery(Persona.class)
                .field("cedula").exists().asList();
        for (Persona p : persona) {
            p.toString();
            jedis.set(p.getCedula(), " " + p.getNombres() + " " + p.getApellidos()
                    + " " + p.getFecha() + " " + p.getProvincia() + " " + p.getGenero()
                    + " " + p.getEstado_civil());
            endTime = System.currentTimeMillis() - startTime;
        }
        System.out.println("Duración: " + endTime + " ms");

    }

}
