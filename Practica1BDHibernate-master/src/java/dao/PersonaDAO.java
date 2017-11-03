/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import hbm.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import pojo.Persona;
import pojo.TipoPersona;

/**
 *
 * @author RigoBono
 */
public class PersonaDAO { // se crea la variable session en session
    Session session;
    
    public PersonaDAO(){ // muestra la clase persona
        session=HibernateUtil.getLocalSession(); //la variable session se deposita en la calse hibernate
    }
    
    public  Persona getPersonaById(int id){ //** pide a la clase persona que regrese los datos.
        return (Persona)session.load(Persona.class, id); // es la variable
    }
    
    public List<Persona>  getPersonaByName(String nombre){ //**  obtener el valor de nombre declarado en el stringg        
        List<Persona> listaDePersonas=(List<Persona>) // se inicializa la arraylist de persona en el objeto
                session.createCriteria(Persona.class)  //la variable session se integra con persona
                .add(Restrictions.eq("Nombre", nombre)); //
        return listaDePersonas; // se retornan los valores a la lista de datos
    }
    
    public boolean updateById(int id,Persona persona){
        Persona personaAModificar=getPersonaById(id);
        try{
            Transaction transaccion=session.beginTransaction();
            personaAModificar.setNombre(persona.getNombre()); // regresa o pide el conjunto del nombre de personas a modificar
            session.update(personaAModificar); // actualiza el persona en session
            transaccion.commit();
            return true; // retorna verdadero
        }catch(Exception e){
            return false; // retorna falso 
        }
    }
    
    public boolean savePersona(String nombre,String materno,String paterno,String telefono,int idTipoPersona){
        Persona personaDeTanque=new Persona();
        TipoPersona tipoDeTanque=(TipoPersona)session.load(TipoPersona.class, idTipoPersona);
        personaDeTanque.setNombre(nombre); // guarda el nombre del objeto
        personaDeTanque.setMaterno(materno); // guarda el materno del objeto
        personaDeTanque.setPaterno(paterno); // guarda el paterno del objeto
        personaDeTanque.setTelefono(telefono); // guarda el telefono obcy
        personaDeTanque.setTipoPersona(tipoDeTanque);
        try{
            Transaction transaccion=session.beginTransaction();
            session.save(personaDeTanque);
            transaccion.commit();
            return true;
        }catch(Exception e){
            
        }finally{
            
        }
        return true;
    }
    
}
