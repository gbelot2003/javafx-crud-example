package models;
// Generated 10-02-2015 03:33:27 PM by Hibernate Tools 4.3.1



/**
 * Personas generated by hbm2java
 */
public class Personas  implements java.io.Serializable {

     private Integer id;
     private String name;
     private String lastName;

    public Personas() {
    }

    public Personas(String name, String lastName) {
       this.name = name;
       this.lastName = lastName;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}


