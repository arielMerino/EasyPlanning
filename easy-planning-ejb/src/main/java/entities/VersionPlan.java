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
 * @author ariel-linux
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "VersionPlan.findByIdPlan",
        query = "SELECT vp FROM VersionPlan vp WHERE vp.planEstudio.id = :idPlan"),
    @NamedQuery(
        name = "VersionPlan.findByPlanificado",
        query = "SELECT vp FROM VersionPlan vp WHERE vp.planificado = :planificado")
})
public class VersionPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int anio;
    private int version;
    private boolean planificado;
    private int resolucion;
    private int anio_resolucion;
    
    @ManyToOne
    private PlanEstudio planEstudio;

    public int getResolucion() {
        return resolucion;
    }

    public void setResolucion(int resolucion) {
        this.resolucion = resolucion;
    }

    public int getAnio_resolucion() {
        return anio_resolucion;
    }

    public void setAnio_resolucion(int anio_resolucion) {
        this.anio_resolucion = anio_resolucion;
    }

    public boolean isPlanificado() {
        return planificado;
    }

    public void setPlanificado(boolean planificado) {
        this.planificado = planificado;
    }

    public PlanEstudio getPlanEstudio() {
        return planEstudio;
    }

    public void setPlanEstudio(PlanEstudio planEstudio) {
        this.planEstudio = planEstudio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
        if (!(object instanceof VersionPlan)) {
            return false;
        }
        VersionPlan other = (VersionPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.versionplan[ id=" + id + " ]";
    }
    
}
