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

/**
 *
 * @author jano
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Horario.findByBloqueAndProfesor",
            query = "SELECT h FROM Horario h WHERE h.bloque = :bloque AND h.profesor.rutProfesor = :rutProfesor"),
    @NamedQuery(name = "Horario.findDisponibleByBloqueAndProfesor",
            query = "SELECT h FROM Horario h WHERE h.bloque = :bloque AND h.profesor.rutProfesor = :rutProfesor AND h.seccion = :seccion"),
    @NamedQuery(name = "Horario.findHorariosDisponiblesByProfesor",
            query = "SELECT h FROM Horario h WHERE h.profesor.rutProfesor = :rutProfesor AND h.seccion IS NULL"),
    @NamedQuery(name = "Horario.findHorariosAsignadosByProfesor",
            query = "SELECT h FROM Horario h WHERE h.profesor.rutProfesor = :rutProfesor AND h.seccion IS NOT NULL"),
    @NamedQuery(name="Horario.findByHorariosNoDisponibles",
            query="SELECT h FROM Horario h WHERE h.seccion IS NOT NULL"),
    @NamedQuery(name="Horario.findBySeleccionados",//esta query hay que sacarla
            query="SELECT h FROM Horario h WHERE h.profesor.rutProfesor = :rutProfesor"),
    @NamedQuery(name = "Horario.findByversionPlanAndSemestreAndAnioAndBloque",
            query = "SELECT h FROM Horario h WHERE h.seccion.coordinacion.asignatura.versionplan.id = :idPlan AND h.seccion.coordinacion.semestre = :semestre AND h.seccion.coordinacion.anio = :anio AND h.seccion.coordinacion.asignatura.nivel = :nivel AND h.bloque = :bloque"),
    @NamedQuery(name = "Horario.findByVersionPlanAndSemestreAndAnio",
            query = "SELECT h FROM Horario h WHERE h.seccion.coordinacion.asignatura.versionplan.id = :idPlan AND h.seccion.coordinacion.semestre = :semestre AND h.seccion.coordinacion.anio = :anio AND h.seccion.coordinacion.asignatura.nivel = :nivel"),
    @NamedQuery(name = "Horario.findAsignadoByBloqueAndProfesor",
            query = "SELECT h FROM Horario h WHERE h.bloque = :bloque AND h.profesor.rutProfesor = :rutProfesor AND h.seccion IS NOT NULL"),
    @NamedQuery(name = "Horario.findHorariosAsignadosByProfesorAndSemestre",
            query = "SELECT h FROM Horario h WHERE h.profesor.rutProfesor = :rutProfesor AND h.seccion.coordinacion.anio = :anio AND h.seccion.coordinacion.semestre = :semestre"),
    @NamedQuery(name = "Horario.finall",
            query = "SELECT h FROM Horario h")
})
public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bloque;
    private String tipo;
    
    @ManyToOne
    private Seccion seccion;
    
    @ManyToOne
    private Profesor profesor;

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.horario[ id=" + id + " ]";
    }
    
}
