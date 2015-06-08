/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Coordinacion;
import entities.Seccion;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SeccionesTest {
    
    private static Secciones mockSecciones;
    
    private static Asignatura asignatura1;
    private static Asignatura asignatura2;
    
    private static Coordinacion coordinacion1;
    private static Coordinacion coordinacion2;
    
    private static Seccion seccion1;
    private static Seccion seccion2;
    private static Seccion seccion3;
    
    public SeccionesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockSecciones = mock(Secciones.class);
        
        asignatura1 = new Asignatura();
        asignatura1.setCodigo("10101");
        asignatura1.setEjercicios(2);
        asignatura1.setId(Long.parseLong("2"));
        asignatura1.setLaboratorio(0);
        asignatura1.setNivel(1);
        asignatura1.setNombre("CÁLCULO I PARA INGENIERÍA");
        asignatura1.setTeoria(6);
        
        asignatura2 = new Asignatura();
        asignatura2.setCodigo("10102");
        asignatura2.setCoordinaciones(null);
        asignatura2.setEjercicios(2);
        asignatura2.setId(Long.parseLong("3"));
        asignatura2.setLaboratorio(0);
        asignatura2.setNivel(1);
        asignatura2.setNombre("ÁLGEBRA I PARA INGENIERÍA");
        asignatura2.setTeoria(6);
        
        coordinacion1 = new Coordinacion();
        coordinacion1.setAnio(2015);
        coordinacion1.setCantAlumnosEstimado(50);
        coordinacion1.setCantAlumnosReal(46);
        coordinacion1.setAsignatura(asignatura1);
        coordinacion1.setId(Long.parseLong("1"));
        coordinacion1.setSemestre(1);
        
        coordinacion2 = new Coordinacion();
        coordinacion2.setAnio(2015);
        coordinacion2.setCantAlumnosEstimado(50);
        coordinacion2.setCantAlumnosReal(49);
        coordinacion2.setAsignatura(asignatura2);
        coordinacion2.setId(Long.parseLong("2"));
        coordinacion2.setSemestre(1);
        
        seccion1 = new Seccion();
        seccion1.setCodigo("10101-A1");
        seccion1.setCoordinacion(coordinacion1);
        seccion1.setId(Long.parseLong("1"));
        
        seccion2 = new Seccion();
        seccion2.setCodigo("10101-B1");
        seccion2.setCoordinacion(coordinacion1);
        seccion2.setId(Long.parseLong("2"));
        
        seccion3 = new Seccion();
        seccion3.setCodigo("10102-A1");
        seccion3.setCoordinacion(coordinacion2);
        seccion3.setId(Long.parseLong("3"));
        
        when(mockSecciones.findByAsignaturaAnioYSemestre(asignatura1.getId(), coordinacion1.getAnio(), coordinacion1.getSemestre())).thenReturn(Arrays.asList(seccion1,seccion2));
        when(mockSecciones.findByAsignaturaAnioYSemestre(asignatura2.getId(), coordinacion2.getAnio(), coordinacion2.getSemestre())).thenReturn(Arrays.asList(seccion3));
        when(mockSecciones.findByAsignaturaAnioYSemestre(Long.parseLong("4"), 2015, 1)).thenReturn(new ArrayList<Seccion>());
        when(mockSecciones.findByAsignaturaAnioYSemestre(Long.parseLong("2"), 2016, 1)).thenReturn(new ArrayList<Seccion>());
        when(mockSecciones.findByAsignaturaAnioYSemestre(Long.parseLong("3"), 2015, 2)).thenReturn(new ArrayList<Seccion>());
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByAsignaturaAnioYSemestre method, of class Secciones.
     */
    @Test
    public void testFindByAsignaturaAnioYSemestre() throws Exception {
        System.out.println("findByAsignaturaAnioYSemestre");
        long asg1 = Long.parseLong("2");
        long asg2 = Long.parseLong("3");
        long asg3 = Long.parseLong("4");
        int anio1 = 2015;
        int anio2 = 2016;
        int semestre1 = 1;
        int semestre2 = 2;
        
        assertNotNull(mockSecciones.findByAsignaturaAnioYSemestre(asg1, anio1, semestre1));
        assertNotNull(mockSecciones.findByAsignaturaAnioYSemestre(asg2, anio1, semestre1));
        assertNotNull(mockSecciones.findByAsignaturaAnioYSemestre(asg3, anio1, semestre1));
        assertNotNull(mockSecciones.findByAsignaturaAnioYSemestre(asg1, anio2, semestre1));
        assertNotNull(mockSecciones.findByAsignaturaAnioYSemestre(asg2, anio1, semestre2));
        assertEquals(2, mockSecciones.findByAsignaturaAnioYSemestre(asg1, anio1, semestre1).size());
        assertEquals("10101-A1", mockSecciones.findByAsignaturaAnioYSemestre(asg1, anio1, semestre1).get(0).getCodigo());
        assertEquals("10101-B1", mockSecciones.findByAsignaturaAnioYSemestre(asg1, anio1, semestre1).get(1).getCodigo());
        assertEquals(1, mockSecciones.findByAsignaturaAnioYSemestre(asg2, anio1, semestre1).size());
        assertEquals("10102-A1", mockSecciones.findByAsignaturaAnioYSemestre(asg2, anio1, semestre1).get(0).getCodigo());
        assertEquals(0, mockSecciones.findByAsignaturaAnioYSemestre(asg3, anio1, semestre1).size());
        assertEquals(0, mockSecciones.findByAsignaturaAnioYSemestre(asg1, anio2, semestre1).size());
        assertEquals(0, mockSecciones.findByAsignaturaAnioYSemestre(asg2, anio1, semestre2).size());
    }
    
}
