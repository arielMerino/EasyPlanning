/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author ariel-linux
 */
@Entity
@NamedQueries({
    @NamedQuery(
            name = "Checklist.findProfesoresByAsgAnioAndSemestre",
            query = "SELECT DISTINCT c.encuesta.profesor.rutProfesor FROM Checklist c WHERE c.asignatura.id = :asg AND c.encuesta.anio = :anio AND c.encuesta.semestre = :semestre AND c.aceptado = 'true'"
    ),
    @NamedQuery(
            name="Checklist.findAsignaturaByEncuestaId",
            query="SELECT c.asignatura from Checklist c WHERE c.encuesta.id = :idEncuesta"
    ),
    @NamedQuery(
            name="Checklist.deleteChecklist",
            query="DELETE FROM Checklist c WHERE c.encuesta.id = :idEncuesta AND c.asignatura.id = :idAsignatura"
            
    ),
    @NamedQuery(
            name = "Checklist.findChecklistByIdEncuesta",
            query = "SELECT c FROM Checklist c WHERE c.encuesta.id = :idEncuesta"            
    ),
})
public class Checklist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Encuesta encuesta;
    @OneToOne
    private Asignatura asignatura;
    
    private boolean aceptado;
    
    public Long getId() {
        return id;
    }

    public Encuesta getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Encuesta encuesta) {
        this.encuesta = encuesta;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
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
        if (!(object instanceof Checklist)) {
            return false;
        }
        Checklist other = (Checklist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Checklist[ id=" + id + " ]";
    }
    
}
