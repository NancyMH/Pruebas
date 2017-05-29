/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.jpa.PendienteC;
import com.klan.proyecto.jpa.PuestoC;
import com.klan.proyecto.jpa.UsuarioC; // Para consultar usuarios de la BD.
import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.Pendiente;
import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.modelo.Usuario; // Para construir un usuario.

import javax.faces.application.FacesMessage; // Para mostrar y obtener mensajes de avisos.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.
import java.util.List;
import org.primefaces.model.UploadedFile;

/**
 *
 * Bean utilizado para pruebas al perfil de un puesto.
 * @author anahiqj
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class ModificacionPuesto implements Serializable{
    /**
     * Obtiene información de las peticiones de usuario.
     */
    private final HttpServletRequest httpServletRequest;
    /**
     * Obtiene información de la aplicación.
     */
    private final FacesContext faceContext;

    private Puesto puesto;
        
    public String rutaImagen;
     
    public UploadedFile archivo;
     
    public String descripcion;

    public boolean editable;

    public boolean esEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    
    public void editar() {
		editable = !editable;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public ModificacionPuesto() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    public void cambiarDescripcion (){
        try{
            EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("YumYum-Ciencias");
            Puesto p = (Puesto) httpServletRequest.getSession()
                .getAttribute("puesto");
            System.out.println(p.getNombre());
            PuestoC puestoC = new PuestoC(emf);
            puestoC.actualizaDescripcion(p, this.descripcion);
            httpServletRequest.getSession().setAttribute("puesto", p);
                    FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "¡Descripción actualizada!.", null));
           
        } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error al guardar descripcion controlador", null));
                System.err.println("Error al guardar descripcion controlador" + ex.getMessage());
            
        }
    }
    
    public void borrarComida(Comida comida) {
        FacesMessage message;
        try {
            // Se realiza la conexión a la BD.
            
            EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("YumYum-Ciencias");
            Puesto p = (Puesto) httpServletRequest.getSession()
                .getAttribute("puesto");
            PuestoC puestoC = new PuestoC(emf);
            List<Comida> nueva = p.getComidas();
            nueva.remove(comida);
            p.setComidas(nueva);
            puestoC.editar(p);
            httpServletRequest.getSession().setAttribute("puesto", p);
                    FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "¡Comida borrada exitosamente!", null));
        } catch (Exception e) {
                System.err.println(" Error al borrar la comida controlador" + e.getMessage());
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Error al borrar la comida controlador", e.getMessage()));
        } 
    }

    
    
}