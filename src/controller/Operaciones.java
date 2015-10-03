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
    
    public void getDeletePersona(Integer id){
        
        SessionFactory sesion = NewHibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        Transaction tx = session.beginTransaction();
        
        Personas persona = new Personas();
        
        persona = (Personas) session.get(Personas.class, id);
               
        System.out.println("Persona = " + persona.getName());        
        
        session.delete(persona);
        
        tx.commit();
        session.close();
   
    }
    
    
        
}
