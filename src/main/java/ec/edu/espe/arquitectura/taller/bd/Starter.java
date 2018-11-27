/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.taller.bd;

import ec.edu.espe.arquitectura.taller.bd.Bases.Mongo;
import ec.edu.espe.arquitectura.taller.bd.Bases.Postgresql;
import java.io.IOException;

/**
 *
 * @author Steven
 */
public class Starter {
    public static void main(String args[]) throws IOException
    {
        Mongo mongo = new Mongo();
        Postgresql post = new Postgresql();
        long start = System.currentTimeMillis();
        post.ProcesosPostgesql();
        mongo.ProcesoMongo();
        long end = System.currentTimeMillis();
        System.out.println("Tiempo total: "+(end-start));
    }
}
