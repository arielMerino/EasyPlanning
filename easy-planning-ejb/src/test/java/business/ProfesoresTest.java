/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Coordinacion;
import entities.Encuesta;
import entities.Horario;
import entities.Profesor;
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
public class ProfesoresTest {
    
    private static Profesores mockProfesores;
    
    private static Profesor profesor1;
    
    private static Encuesta encuesta;
    
    private static Asignatura asignatura;
    
    private static Coordinacion coordinacion;
    
    private static Seccion seccion;
    
    private static Horario horario1;
    private static Horario horario2;
    
    public ProfesoresTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockProfesores = mock(Profesores.class);
        
        profesor1 = new Profesor();
        profesor1.setApellido("Pino");
        profesor1.setMail("yerko.pino@usach.cl");
        profesor1.setNombre("Yerko");
        profesor1.setRutProfesor("18.338.861-4");
        
        encuesta = new Encuesta();
        encuesta.setAnio(2015);
        encuesta.setComentario("Comentario de la enceusta");
        encuesta.setId(Long.parseLong("1"));
        encuesta.setProfesor(profesor1);
        encuesta.setSemestre(1);
        
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
        coordinacion.setAsignatura(asignatura);
        coordinacion.setId(Long.parseLong("1"));
        coordinacion.setSemestre(1);
        
        seccion = new Seccion();
        seccion.setCodigo("10101-A1");
        seccion.setCoordinacion(coordinacion);
        seccion.setId(Long.parseLong("1"));
        
        horario1 = new Horario();
        horario1.setBloque("L1");
        horario1.setId(Long.parseLong("1"));
        horario1.setProfesor(profesor1);
        horario1.setSeccion(seccion);
        horario1.setTipo("Teoría");
        
        horario2 = new Horario();
        horario2.setBloque("W1");
        horario2.setId(Long.parseLong("1"));
        horario2.setProfesor(profesor1);
        horario2.setSeccion(null);
        horario2.setTipo("Teoría");
        
        when(mockProfesores.findByRut(profesor1.getRutProfesor())).thenReturn(profesor1);
        when(mockProfesores.findByRut("7.413.382-7")).thenReturn(null);
        when(mockProfesores.getEncuestaBySemestreAndAnio(profesor1.getRutProfesor(), 1, 2015)).thenReturn(encuesta);
        when(mockProfesores.getEncuestaBySemestreAndAnio("7.413.382-7", 1, 2015)).thenReturn(null);
        when(mockProfesores.getEncuestaBySemestreAndAnio(profesor1.getRutProfesor(), 2, 2015)).thenReturn(null);
        when(mockProfesores.getEncuestaBySemestreAndAnio(profesor1.getRutProfesor(), 1, 2016)).thenReturn(null);
        when(mockProfesores.getProfesorByHorarioAsignado(coordinacion.getAsignatura().getId(), coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(profesor1);
        when(mockProfesores.getProfesorByHorarioAsignado(Long.parseLong("3"), coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(null);
        when(mockProfesores.getProfesorByHorarioAsignado(coordinacion.getAsignatura().getId(), 2016, coordinacion.getSemestre())).thenReturn(null);
        when(mockProfesores.getProfesorByHorarioAsignado(coordinacion.getAsignatura().getId(), coordinacion.getAnio(), 2)).thenReturn(null);
        when(mockProfesores.findDisponiblesByBloque(horario1.getBloque())).thenReturn(new ArrayList<Profesor>());
        when(mockProfesores.findDisponiblesByBloque(horario2.getBloque())).thenReturn(Arrays.asList(profesor1));
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByRut method, of class Profesores.
     */
    @Test
    public void testFindByRut() throws Exception {
        System.out.println("findByRut");
        String rut1 = "18.338.861-4";
        String rut2 = "7.413.382-7";
        
        assertNotNull(mockProfesores.findByRut(rut1));
        assertNull(mockProfesores.findByRut(rut2));
        assertEquals("Yerko", mockProfesores.findByRut(rut1).getNombre());
        assertEquals("Pino", mockProfesores.findByRut(rut1).getApellido());
        assertEquals("yerko.pino@usach.cl", mockProfesores.findByRut(rut1).getMail());
        
    }

    /**
     * Test of getEncuestaBySemestreAndAnio method, of class Profesores.
     */
    @Test
    public void testGetEncuestaBySemestreAndAnio() throws Exception {
        System.out.println("getEncuestaBySemestreAndAnio");
        String rutProfesor1 = "18.338.861-4";
        String rutProfesor2 = "7.413.382-7";
        int semestre1 = 1;
        int semestre2 = 2;
        int anio1 = 2015;
        int anio2 = 2016;
        
        assertNotNull(mockProfesores.getEncuestaBySemestreAndAnio(rutProfesor1, semestre1, anio1));
        assertNull(mockProfesores.getEncuestaBySemestreAndAnio(rutProfesor2, semestre1, anio1));
        assertNull(mockProfesores.getEncuestaBySemestreAndAnio(rutProfesor1, semestre2, anio1));
        assertNull(mockProfesores.getEncuestaBySemestreAndAnio(rutProfesor1, semestre1, anio2));
        assertEquals("1", mockProfesores.getEncuestaBySemestreAndAnio(rutProfesor1, semestre1, anio1).getId().toString());
    }

    @Test
    public void testFindDisponiblesByBloque() throws Exception {
        System.out.println("findDisponiblesByBloque");
        String bloque1 = "L1";
        String bloque2 = "W1";
        
        assertNotNull(mockProfesores.findDisponiblesByBloque(bloque1));
        assertNotNull(mockProfesores.findDisponiblesByBloque(bloque2));
        assertEquals(0, mockProfesores.findDisponiblesByBloque(bloque1).size());
        assertEquals(1, mockProfesores.findDisponiblesByBloque(bloque2).size());
        assertEquals("18.338.861-4", mockProfesores.findDisponiblesByBloque(bloque2).get(0).getRutProfesor());
    }

    /**
     * Test of getProfesorByHorarioAsignado method, of class Profesores.
     */
    @Test
    public void testGetProfesorByHorarioAsignado() throws Exception {
        System.out.println("getProfesorByHorarioAsignado");
        Long id_asignatura1 = Long.parseLong("2");
        Long id_asignatura2 = Long.parseLong("3");
        int anio1 = 2015;
        int anio2 = 2016;
        int semestre1 = 1;
        int semestre2 = 2;
        
        assertNotNull(mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio1, semestre1));
        assertNull(mockProfesores.getProfesorByHorarioAsignado(id_asignatura2, anio1, semestre1));
        assertNull(mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio2, semestre1));
        assertNull(mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio1, semestre2));
        assertEquals("18.338.861-4", mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio1, semestre1).getRutProfesor());
        assertEquals("Yerko", mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio1, semestre1).getNombre());
        assertEquals("Pino", mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio1, semestre1).getApellido());
        assertEquals("yerko.pino@usach.cl", mockProfesores.getProfesorByHorarioAsignado(id_asignatura1, anio1, semestre1).getMail());
    }
    
}
