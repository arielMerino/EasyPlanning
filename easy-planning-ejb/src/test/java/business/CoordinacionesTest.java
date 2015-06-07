/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Coordinacion;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author yerko
 */
public class CoordinacionesTest {
    
    private static Coordinaciones mockCoordinaciones;
    
    private static Asignatura asignatura;
    
    private static Coordinacion coordinacion;
    
    public CoordinacionesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockCoordinaciones = mock(Coordinaciones.class);
        
        asignatura = new Asignatura();
        asignatura.setCodigo("10101");
        asignatura.setEjercicios(2);
        asignatura.setId(Long.parseLong("2"));
        asignatura.setLaboratorio(0);
        asignatura.setNivel(1);
        asignatura.setNombre("CÁLCULO I PARA INGENIERÍA");
        asignatura.setTeoria(6);
        
        coordinacion = new Coordinacion();
        coordinacion.setAnio(2015);
        coordinacion.setCantAlumnosEstimado(50);
        coordinacion.setCantAlumnosReal(46);
        coordinacion.setId(Long.parseLong("1"));
        coordinacion.setSemestre(1);
        coordinacion.setAsignatura(asignatura);
        
        when(mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignatura, coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(coordinacion);
        when(mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignatura, 2016, 1)).thenReturn(null);
        when(mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignatura, 2015, 2)).thenReturn(null);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByAsignaturaAndAnioAndSemestre method, of class Coordinaciones.
     */
    @Test
    public void testFindByAsignaturaAndAnioAndSemestre() throws Exception {
        System.out.println("findByAsignaturaAndAnioAndSemestre");
        Asignatura asignatura = null;
        int anio1 = 2015;
        int anio2 = 2016;
        int semestre1 = 1;
        int semestre2 = 2;
        
        Asignatura asignaturaAux = new Asignatura();
        asignaturaAux.setCodigo("10101");
        asignaturaAux.setEjercicios(2);
        asignaturaAux.setId(Long.parseLong("2"));
        asignaturaAux.setLaboratorio(0);
        asignaturaAux.setNivel(1);
        asignaturaAux.setNombre("CÁLCULO I PARA INGENIERÍA");
        asignaturaAux.setTeoria(6);
        
        assertNotNull(mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignaturaAux, anio1, semestre1));
        assertNull(mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignatura, anio2, semestre1));
        assertNull(mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignatura, anio1, semestre2));
        assertEquals(50, mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignaturaAux, anio1, semestre1).getCantAlumnosEstimado());
        assertEquals(46, mockCoordinaciones.findByAsignaturaAndAnioAndSemestre(asignaturaAux, anio1, semestre1).getCantAlumnosReal());
    }
    
}
