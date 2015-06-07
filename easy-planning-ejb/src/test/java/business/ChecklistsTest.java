/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Checklist;
import entities.Encuesta;
import entities.Profesor;
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
public class ChecklistsTest {
    
    private static Checklists mockChecklists;
    
    private static Profesor profesor1;
    private static Profesor profesor2;
    
    private static Encuesta encuesta;
    
    private static Asignatura asignatura1;
    private static Asignatura asignatura2;    
    
    private static Checklist checklist1;
    private static Checklist checklist2;
    
    public ChecklistsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockChecklists = mock(Checklists.class);
        
        profesor1 = new Profesor();
        profesor1.setApellido("Pino");
        profesor1.setMail("yerko.pino@usach.cl");
        profesor1.setNombre("Yerko");
        profesor1.setRutProfesor("18.338.861-4");
        
        profesor2 = new Profesor();
        profesor2.setApellido("Pérez");
        profesor2.setMail("yerko.pino@usach.cl");
        profesor2.setNombre("Juan");
        profesor2.setRutProfesor("7.413.382-7");
        
        encuesta = new Encuesta();
        encuesta.setAnio(2015);
        encuesta.setComentario("Este es el comentario de una encuesta.");
        encuesta.setId(Long.parseLong("1"));
        encuesta.setProfesor(profesor1);
        encuesta.setSemestre(1);
        
        asignatura1 = new Asignatura();
        asignatura1.setCodigo("10101");
        asignatura1.setCoordinaciones(null);
        asignatura1.setEjercicios(2);
        asignatura1.setId(Long.parseLong("1"));
        asignatura1.setLaboratorio(0);
        asignatura1.setNivel(1);
        asignatura1.setNombre("CÁLCULO I PARA INGENIERÍA");
        asignatura1.setTeoria(6);
        asignatura1.setVersionplan(null);
        
        asignatura2 = new Asignatura();
        asignatura2.setCodigo("10102");
        asignatura2.setCoordinaciones(null);
        asignatura2.setEjercicios(2);
        asignatura2.setId(Long.parseLong("2"));
        asignatura2.setLaboratorio(0);
        asignatura2.setNivel(1);
        asignatura2.setNombre("ÁLGEBRA I PARA INGENIERÍA");
        asignatura2.setTeoria(6);
        asignatura2.setVersionplan(null);
        
        checklist1 = new Checklist();
        checklist1.setAceptado(true);
        checklist1.setAsignatura(asignatura1);
        checklist1.setEncuesta(encuesta);
        checklist1.setId(Long.parseLong("1"));
        
        checklist2 = new Checklist();
        checklist2.setAceptado(true);
        checklist2.setAsignatura(asignatura2);
        checklist2.setEncuesta(encuesta);
        checklist2.setId(Long.parseLong("2"));
        
        when(mockChecklists.findProfesorByAsgAnioSemestre(checklist1.getAsignatura().getId(), encuesta.getAnio(), encuesta.getSemestre())).thenReturn(Arrays.asList(profesor1.getRutProfesor()));
        when(mockChecklists.findProfesorByAsgAnioSemestre(Long.parseLong("3"), 2015, 1)).thenReturn(new ArrayList<String>());
        when(mockChecklists.findProfesorByAsgAnioSemestre(checklist1.getAsignatura().getId(), 2016, 1)).thenReturn(new ArrayList<String>());
        when(mockChecklists.findProfesorByAsgAnioSemestre(checklist1.getAsignatura().getId(), 2015, 2)).thenReturn(new ArrayList<String>());
        when(mockChecklists.findAsignaturasByEncuestaId(encuesta.getId())).thenReturn(Arrays.asList(checklist1.getAsignatura(),checklist2.getAsignatura()));
        when(mockChecklists.findAsignaturasByEncuestaId(Long.parseLong("2"))).thenReturn(new ArrayList<Asignatura>());
        when(mockChecklists.findChecklistByIdEncuesta(encuesta.getId())).thenReturn(Arrays.asList(checklist1,checklist2));
        when(mockChecklists.findChecklistByIdEncuesta(Long.parseLong("2"))).thenReturn(null);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findProfesorByAsgAnioSemestre method, of class Checklists.
     */
    @Test
    public void testFindProfesorByAsgAnioSemestre() throws Exception {
        System.out.println("findProfesorByAsgAnioSemestre");
        long asg = Long.parseLong("1");
        int anio = 2015;
        int semestre = 1;
        
        assertNotNull(mockChecklists.findProfesorByAsgAnioSemestre(asg, anio, semestre));
        assertNotNull(mockChecklists.findProfesorByAsgAnioSemestre(Long.parseLong("3"), anio, semestre));
        assertNotNull(mockChecklists.findProfesorByAsgAnioSemestre(asg, anio+1, semestre));
        assertNotNull(mockChecklists.findProfesorByAsgAnioSemestre(asg, anio, semestre+1));
        assertEquals(1, mockChecklists.findProfesorByAsgAnioSemestre(asg, anio, semestre).size());
        assertEquals("18.338.861-4", mockChecklists.findProfesorByAsgAnioSemestre(asg, anio, semestre).get(0));
        assertEquals(0, mockChecklists.findProfesorByAsgAnioSemestre(Long.parseLong("3"), anio, semestre).size());
        assertEquals(0, mockChecklists.findProfesorByAsgAnioSemestre(asg, anio+1, semestre).size());
        assertEquals(0, mockChecklists.findProfesorByAsgAnioSemestre(asg, anio, semestre+1).size());
        
    }

    /**
     * Test of findAsignaturasByEncuestaId method, of class Checklists.
     */
    @Test
    public void testFindAsignaturasByEncuestaId() throws Exception {
        System.out.println("findAsignaturasByEncuestaId");
        long id_encuesta1 = Long.parseLong("1");
        long id_encuesta2 = Long.parseLong("2");
        
        assertNotNull(mockChecklists.findAsignaturasByEncuestaId(id_encuesta1));
        assertNotNull(mockChecklists.findAsignaturasByEncuestaId(id_encuesta2));
        assertEquals(2, mockChecklists.findAsignaturasByEncuestaId(id_encuesta1).size());
        assertEquals("CÁLCULO I PARA INGENIERÍA", mockChecklists.findAsignaturasByEncuestaId(id_encuesta1).get(0).getNombre());
        assertEquals("ÁLGEBRA I PARA INGENIERÍA", mockChecklists.findAsignaturasByEncuestaId(id_encuesta1).get(1).getNombre());
        assertEquals(0, mockChecklists.findAsignaturasByEncuestaId(id_encuesta2).size());
        
    }

    /**
     * Test of findChecklistByIdEncuesta method, of class Checklists.
     */
    @Test
    public void testFindChecklistByIdEncuesta() throws Exception {
        System.out.println("findChecklistByIdEncuesta");
        long idEncuesta1 = Long.parseLong("1");
        long idEncuesta2 = Long.parseLong("2");
        
        assertNotNull(mockChecklists.findChecklistByIdEncuesta(idEncuesta1));
        assertNull(mockChecklists.findChecklistByIdEncuesta(idEncuesta2));
        assertEquals(2, mockChecklists.findChecklistByIdEncuesta(idEncuesta1).size());
        
    }
    
}
