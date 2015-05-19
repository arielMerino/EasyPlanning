/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author ariel-linux
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Profesor.findByRut",
            query="SELECT p FROM Profesor p WHERE p.rut = :rut"),

    @NamedQuery(name="Profesor.findDisponiblesByBloque",
            query="SELECT h.profesor FROM Horario h WHERE h.bloque = :bloque AND h.seccion IS NULL AND h.profesor IS NOT NULL"),
    
    @NamedQuery(name="Profesor.getEncuestaBySemestreAndAnio",
            query="SELECT e FROM Encuesta e WHERE e.profesor.id = :id AND e.anio = :anio AND e.semestre = :semestre"),
    
    @NamedQuery(name="Profesor.getProfesorByHorarioAsignado",
            query="SELECT p FROM Profesor p, Coordinacion c, Seccion s, Horario h WHERE c.asignatura.id = :id_asignatura AND c.id = s.coordinacion.id AND s.id = h.seccion.id AND h.profesor.id = p.id AND c.anio = :anio AND c.semestre = :semestre")
})
public class Profesor implements Serializable {
    @OneToMany(mappedBy = "profesor")
    private List<Encuesta> encuestas;
    @ManyToMany(mappedBy = "profesores")
    private List<Asignatura> asignaturas;
    @OneToMany(mappedBy = "profesor")
    private List<Horario> disponibilidad;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nombre;
    
    private String apellido;
    
    private String mail;
    
    @Column(unique=true)
    private String rut;

    public List<Horario> getDisponibilidad() {
        return disponibilidad;
    }

    public List<Encuesta> getEncuestas() {
        return encuestas;
    }

    public void setEncuestas(List<Encuesta> encuestas) {
        this.encuestas = encuestas;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public void setDisponibilidad(List<Horario> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Profesor[ id=" + id + " ]";
    }
    
}
