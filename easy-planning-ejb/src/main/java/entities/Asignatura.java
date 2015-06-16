package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    /*@NamedQuery(name="Asignatura.findByCodigoAndPlan", 
            query="SELECT a FROM Asignatura a WHERE a.codigo = :codigo AND a.versionplan = :plan"),*/
    @NamedQuery(name="Asignatura.getAllProfesorAsignatura",
            query="SELECT a, p FROM Asignatura a JOIN a.profesores p"),
    @NamedQuery (name="Asignatura.findByVersionPlan",
            query="SELECT a FROM Asignatura a WHERE a.versionplan = :plan"),
    /*
    @NamedQuery (name="Asignatura.findByNivelAndCarreraAndPlan",
            query="SELECT a FROM Asignatura a WHERE a.nivel = :nivel AND a.carrera.nombre = :carrera AND a.versionplan = :plan"),*/
    @NamedQuery(name = "Asignatura.findByNivelAndPlan",
            query="SELECT a FROM Asignatura a WHERE a.nivel = :nivel AND a.versionplan.id = :idVersion"),
    @NamedQuery (name="Asignatura.findByCodigoAsgAndIdVersion",
            query="SELECT a FROM Asignatura a WHERE a.codigo = :codigo AND a.versionplan.id = :idVersion"),
    @NamedQuery(name="Asignatura.findByAsignaturaAsignada",
            query="SELECT DISTINCT a FROM Horario h, Seccion s, Coordinacion c, Asignatura a WHERE h.profesor.rutProfesor = :rutProfesor AND h.seccion.id = s.id AND s.coordinacion.id = c.id AND c.asignatura.id = a.id AND c.anio = :anio AND c.semestre = :semestre"),
    @NamedQuery(name = "Asignatura.findNivelesByPlan",
            query = "SELECT DISTINCT a.nivel FROM Asignatura a WHERE a.versionplan.id = :versionPlan")
})
public class Asignatura implements Serializable {
    @OneToMany(mappedBy = "asignatura")
    private List<Coordinacion> coordinaciones;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    private String nombre;
    private int nivel;
    private int teoria;
    private int ejercicios;
    private int laboratorio;
    @Column(name = "alias")
    private String alias_asignatura;
    
    @ManyToOne
    private VersionPlan versionplan;
    
    @OneToMany
    private List<Asignatura> prerequisitos;
    
    @ManyToMany
    private List<Profesor> profesores;
    
    
    public Asignatura(){        
    }
    
    public String getAlias() {
        return alias_asignatura;
    }

    public void setAlias(String alias) {
        this.alias_asignatura = alias;
    }

    public VersionPlan getVersionplan() {
        return versionplan;
    }

    public void setVersionplan(VersionPlan versionplan) {
        this.versionplan = versionplan;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }
    
    public int getTeoria() {
        return teoria;
    }

    public void setTeoria(int teoria) {
        this.teoria = teoria;
    }

    public int getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(int ejercicios) {
        this.ejercicios = ejercicios;
    }

    public int getLaboratorio() {
        return laboratorio;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void setLaboratorio(int laboratorio) {
        this.laboratorio = laboratorio;
    }

    public List<Coordinacion> getCoordinaciones() {
        return coordinaciones;
    }

    public void setCoordinaciones(List<Coordinacion> coordinaciones) {
        this.coordinaciones = coordinaciones;
    }
    public List<Asignatura> getPrerequisitos() {
        return prerequisitos;
    }

    public void setPrerequisitos(List<Asignatura> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "entities.Asignatura[ id=" + id + ", codigo= "+codigo+", nombre= "+nombre+", nivel= "+nivel+",T= "+teoria+", E= "+ejercicios+", L= "+laboratorio+"  ]";
    }

}
