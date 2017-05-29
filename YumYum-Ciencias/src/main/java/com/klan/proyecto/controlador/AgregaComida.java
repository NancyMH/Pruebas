/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.jpa.ComidaC;
import com.klan.proyecto.jpa.exceptions.EntidadExistenteException;
import com.klan.proyecto.modelo.Comida; // Para construir una comida.
import java.util.List;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase bean con la que se realiza la inserción de comida a la BD
 * @author nancy
 */
@ManagedBean
@RequestScoped
public class AgregaComida {
private String nombre;
private List<String> lista;
Comida comida;
private final FacesContext faceContext; // Obtiene información de la aplicación
    

    /**
     * Crea una nueva instancia de AgregaComida
     */
    public AgregaComida() {
        faceContext = FacesContext.getCurrentInstance();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método de acceso a una comida.
     *
     * @return Devuelve el puesto que el usuario haya
     * elegido.
     */
    public Comida getPuesto() {
        return comida;
    }
    
    
    public void agregar() throws EntidadExistenteException, Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                                                            "YumYum-Ciencias");
        nombre = nombre.toUpperCase();
        ComidaC c = new ComidaC(emf);
        Comida comida = new Comida(nombre);
        
        try{
            if(c.crear(comida)){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
            "¡"+ nombre + " ha sido agregad@ exitosamente!", null);
            faceContext.addMessage(null, message);
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error al insertar. Posiblemente "+ nombre +" ya esté registrada.", null));
            }
        } catch(Exception e){
            System.err.println("Error al insertar la comida:\n" 
                                + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Error al insertar la comida.", null));
            //return "modificarComida?faces-redirect=true";
        }
            
            //return "modificarComida?faces-redirect=true";
    }
    
    public List<Comida> comida(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                                                            "YumYum-Ciencias");
        List<Comida> comidas = new ComidaC(emf).buscaComidas();
         /*for (int i = 0; i < comidas.size(); i++){
            System.out.println(comidas.toString());
            lista.add(comidas.toString());
         }*/
         return comidas;
    }
    
    
    
}