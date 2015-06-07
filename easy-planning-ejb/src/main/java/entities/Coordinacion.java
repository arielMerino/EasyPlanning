/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author jano
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Coordinacion.findByAsignaturaAndAnioAndSemestre",
            query = "SELECT c FROM Coordinacion c WHERE c.asignatura = :asg AND c.anio = :anio AND c.semestre = :sem"),
    
})
public class Coordinacion implements Serializable {
    @OneToMany(mappedBy = "coordinacion")
    private List<Seccion> secciones;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int anio;
    private int semestre;
    private int cantAlumnosReal;
    private int cantAlumnosEstimado;


    @ManyToOne
    private Asignatura asignatura;

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
    
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getCantAlumnosReal() {
        return cantAlumnosReal;
    }

    public void setCantAlumnosReal(int cantAlumnosReal) {
        this.cantAlumnosReal = cantAlumnosReal;
    }

    public int getCantAlumnosEstimado() {
        return cantAlumnosEstimado;
    }

    public void setCantAlumnosEstimado(int cantAlumnosEstimado) {
        this.cantAlumnosEstimado = cantAlumnosEstimado;
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
        if (!(object instanceof Coordinacion)) {
            return false;
        }
        Coordinacion other = (Coordinacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Coordinacion[ id=" + id + " ]";
    }
    
}
