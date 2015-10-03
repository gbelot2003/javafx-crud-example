/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import models.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
/**
 *
 * @author ajax
 */
public class Operaciones {
    
    /**
     * getPersonas2() 
     * Aquí retornamos todos los registros de la tabla en una 
     * lista observable propia de javaFx.
     * 
     * Notece que  es debuelta como FXCollections.observableArrayList
     * haciendo un casiting de los registros que nos debuelve la consulta
     * con hibernate.
     * 
     * @return 
     */
    public ObservableList<Personas> getPersonas2(){
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        List listado = session.createCriteria(Personas.class).list();
        session.close();
        return FXCollections.observableArrayList(listado);
    }
    
    /**
     * crearPersonas
     * 
     * Notece que se debe pasar la estructura del objeto propiamente.
     * 
     * @param persona 
     */
    public void crearPersonas(Personas persona){
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        session.save(persona);
        tx.commit();
        session.close();
    }
    
    /**
     * Aquí le pasamos como parametros cadenas de texto simple, aunque
     * podria ser un objeto, que contenga la informacíon respectiva en 
     * caso de ser formularios mas grandes.
     * 
     * @param name
     * @param lastName
     * @param id 
     */
    public void getPersona(String name, String lastName, Integer id){
        
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        
        Personas persona = new Personas();
        
        persona = (Personas) session.get(Personas.class, id);
        
        persona.setName(name);
        persona.setLastName(lastName);
        
        System.out.println("Persona = " + persona.getName());        
        
        session.update(persona);
        tx.commit();
        session.close();
    }
    
    /***
     * getDeletePersona()
     * 
     * Se pasa unicamente el id del registro 
     * que se desea eliminar, similar al update hace una
     * busqueda en la sesion y por ultimo elimina en transacción.
     * 
     * @param id 
     */
    public void getDeletePersona(Integer id){
        
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        
        Personas persona = new Personas();
        
        persona = (Personas) session.get(Personas.class, id);
               
        System.out.println("Persona = " + persona.getName());        
        
        /**
         * Aquí borra el objeto que se le pasa.
         */
        session.delete(persona);
        
        tx.commit();
        session.close();
  
    }
          
}
