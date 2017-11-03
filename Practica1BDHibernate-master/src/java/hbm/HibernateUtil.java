/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hbm;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author RigoBono
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory; //se crea variable tipo final modificada
    private static final ThreadLocal localSession; // se crea variable hilo final de la sesion actual
    
    static {
        try {
           Configuration config = new Configuration(); // creamos objeto configuracion
            config.configure("hibernate.cfg.xml"); // se ingresa a configuracion el valor de hibernate.cfg
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder(). // se inicaliza nuevo standardservice
            applySettings(config.getProperties()); // se generan las propiedades generales en las configuraciones
            sessionFactory = config.buildSessionFactory(builder.build()); //
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        localSession = new ThreadLocal(); // asigna la variable local que se localiza en un hilo
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
     public static Session getLocalSession() {
        Session session = (Session) localSession.get();
        if (session == null) {
            session = sessionFactory.openSession();
            localSession.set(session);
            System.out.println("\nsesion iniciada"); // imprime en pantalla el inicio de la session
        }
        return session; // retorna el iniicio de session
    }
     
     public static void closeLocalSession() {
        Session session = (Session) localSession.get(); //asigna objeto session de  la instancia session
        if (session != null) session.close(); // inicialiaz un if donde el objeto no es igual y asigna un null
        localSession.set(null);
        System.out.println("sesion cerrada\n"); // imprime en pantalla cerrar session
    }
}
