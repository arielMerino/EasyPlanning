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
    
    @NamedQuery(name = "Seccion.findByAsignaturaAnioYSemestre",
            query = "SELECT s FROM Seccion s WHERE s.coordinacion.asignatura.id = :idAsg AND s.coordinacion.anio = :anio AND s.coordinacion.semestre = :semestre "),
    @NamedQuery(name = "Seccion.getByVerionPlan",
            query = "SELECT DISTINCT s.codigo FROM Seccion s WHERE s.coordinacion.asignatura.versionplan.id = :idVersion"),
    @NamedQuery(name = "Seccion.getEspejosById",
            query = "SELECT s.id FROM Seccion s WHERE s.codigo = :codigo AND s.coordinacion.asignatura.alias_asignatura = :alias AND s.coordinacion.anio = :anio AND s.coordinacion.semestre = :semestre")
})
public class Seccion implements Serializable {
    @OneToMany(mappedBy = "seccion")
    private List<Horario> horarios;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    
    @ManyToOne
    private Coordinacion coordinacion;

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public Coordinacion getCoordinacion() {
        return coordinacion;
    }

    public void setCoordinacion(Coordinacion coordinacion) {
        this.coordinacion = coordinacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        if (!(object instanceof Seccion)) {
            return false;
        }
        Seccion other = (Seccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.seccion[ id=" + id + " ]";
    }
    
}
