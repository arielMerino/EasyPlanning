/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Coordinacion;
import entities.Horario;
import entities.Profesor;
import entities.Seccion;
import entities.VersionPlan;
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
public class HorariosTest {
    
    private static Horarios mockHorarios;
    
    private static Profesor profesor1;
    private static Profesor profesor2;
    private static Profesor profesor3;
    private static Profesor profesor4;
    
    private static Horario horario1;
    private static Horario horario2;
    private static Horario horario3;
    private static Horario horario4;
    private static Horario horario5;
    
    private static Asignatura asignatura;
    
    private static Coordinacion coordinacion;
    
    private static Seccion seccion1;
    private static Seccion seccion2;
    
    private static VersionPlan versionPlan;
    
    public HorariosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockHorarios = mock(Horarios.class);
        
        profesor1 = new Profesor();
        profesor1.setApellido("Pino");
        profesor1.setMail("yerko.pino@usach.cl");
        profesor1.setNombre("Yerko");
        profesor1.setRutProfesor("18.338.861-4");
        
        profesor2 = new Profesor();
        profesor2.setApellido("Pérez");
        profesor2.setMail("juan.perez@usach.cl");
        profesor2.setNombre("Juan");
        profesor2.setRutProfesor("7.413.382-7");
        
        profesor3 = new Profesor();
        profesor3.setApellido("Inostroza");
        profesor3.setMail("mario.inostroza@usach.cl");
        profesor3.setNombre("Mario");
        profesor3.setRutProfesor("13.043.816-4");
        
        profesor4 = new Profesor();
        profesor4.setApellido("Berríos");
        profesor4.setMail("luis.berrios@usach.cl");
        profesor4.setNombre("Luis");
        profesor4.setRutProfesor("14.162.240-4");
        
        versionPlan = new VersionPlan();
        versionPlan.setAnio(2015);
        versionPlan.setId(Long.parseLong("1"));
        versionPlan.setVersion(1);
        
        asignatura = new Asignatura();
        asignatura.setCodigo("10101");
        asignatura.setEjercicios(2);
        asignatura.setId(Long.parseLong("2"));
        asignatura.setLaboratorio(0);
        asignatura.setNivel(1);
        asignatura.setNombre("CÁLCULO I PARA INGENIERÍA");
        asignatura.setTeoria(6);
        asignatura.setVersionplan(versionPlan);
        
        coordinacion = new Coordinacion();
        coordinacion.setAnio(2015);
        coordinacion.setCantAlumnosEstimado(50);
        coordinacion.setCantAlumnosReal(46);
        coordinacion.setAsignatura(asignatura);
        coordinacion.setId(Long.parseLong("1"));
        coordinacion.setSemestre(1);
        
        seccion1 = new Seccion();
        seccion1.setCodigo("10101-A1");
        seccion1.setCoordinacion(coordinacion);
        seccion1.setId(Long.parseLong("1"));
        
        seccion2 = new Seccion();
        seccion2.setCodigo("10101-B1");
        seccion2.setCoordinacion(coordinacion);
        seccion2.setId(Long.parseLong("2"));
        
        horario1 = new Horario();
        horario1.setBloque("L2");
        horario1.setId(Long.parseLong("1"));
        horario1.setProfesor(profesor1);
        horario1.setSeccion(seccion1);
        
        horario2 = new Horario();
        horario2.setBloque("M3");
        horario2.setId(Long.parseLong("2"));
        horario2.setProfesor(profesor1);
        horario2.setSeccion(null);
        
        horario3 = new Horario();
        horario3.setBloque("J5");
        horario3.setId(Long.parseLong("3"));
        horario3.setProfesor(profesor2);
        horario3.setSeccion(null);
        
        horario4 = new Horario();
        horario4.setBloque("V1");
        horario4.setId(Long.parseLong("4"));
        horario4.setProfesor(profesor2);
        horario4.setSeccion(null);
        
        horario5 = new Horario();
        horario5.setBloque("W3");
        horario5.setId(Long.parseLong("5"));
        horario5.setProfesor(profesor3);
        horario5.setSeccion(seccion2);
        
        when(mockHorarios.findByBloqueAndProfesor(horario1.getBloque(), profesor1.getRutProfesor())).thenReturn(horario1);
        when(mockHorarios.findByBloqueAndProfesor(horario3.getBloque(), profesor1.getRutProfesor())).thenReturn(null);
        when(mockHorarios.findByBloqueAndProfesor(horario1.getBloque(), profesor2.getRutProfesor())).thenReturn(null);
        when(mockHorarios.findByBloqueAndProfesor("J1", profesor1.getRutProfesor())).thenReturn(null);
        when(mockHorarios.findByBloqueAndProfesor(horario1.getBloque(), "5.166.831-6")).thenReturn(null);
        when(mockHorarios.findDisponibleByBloqueAndProfesor(horario2.getBloque(), profesor1.getRutProfesor())).thenReturn(horario2);
        when(mockHorarios.findDisponibleByBloqueAndProfesor(horario1.getBloque(), profesor1.getRutProfesor())).thenReturn(null);
        when(mockHorarios.findDisponiblesByProfesorId(profesor1.getRutProfesor())).thenReturn(Arrays.asList(horario2));
        when(mockHorarios.findDisponiblesByProfesorId(profesor2.getRutProfesor())).thenReturn(Arrays.asList(horario3,horario4));
        when(mockHorarios.findDisponiblesByProfesorId(profesor3.getRutProfesor())).thenReturn(new ArrayList<Horario>());
        when(mockHorarios.findByHorariosNoDisponibles()).thenReturn(Arrays.asList(horario1,horario5));
        when(mockHorarios.findBySeleccionados(profesor1.getRutProfesor())).thenReturn(Arrays.asList(horario1,horario2));
        when(mockHorarios.findBySeleccionados(profesor2.getRutProfesor())).thenReturn(Arrays.asList(horario3,horario4));
        when(mockHorarios.findBySeleccionados(profesor3.getRutProfesor())).thenReturn(Arrays.asList(horario5));
        when(mockHorarios.findBySeleccionados(profesor4.getRutProfesor())).thenReturn(null);
        when(mockHorarios.findAsignadosByProfesorId(profesor1.getRutProfesor())).thenReturn(Arrays.asList(horario1));
        when(mockHorarios.findAsignadosByProfesorId(profesor2.getRutProfesor())).thenReturn(null);
        when(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(horario1.getBloque(), versionPlan.getId(), asignatura.getNivel(), coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(horario1);
        when(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre("L1", versionPlan.getId(), asignatura.getNivel(), coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(null);
        when(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(horario1.getBloque(), Long.parseLong("2"), asignatura.getNivel(), coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(null);
        when(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(horario1.getBloque(), versionPlan.getId(), 2, coordinacion.getAnio(), coordinacion.getSemestre())).thenReturn(null);
        when(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(horario1.getBloque(), versionPlan.getId(), asignatura.getNivel(), 2016, coordinacion.getSemestre())).thenReturn(null);
        when(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(horario1.getBloque(), versionPlan.getId(), asignatura.getNivel(), coordinacion.getAnio(), 2)).thenReturn(null);
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByBloqueAndProfesor method, of class Horarios.
     */
    @Test
    public void testFindByBloqueAndProfesor() throws Exception {
        System.out.println("findByBloqueAndProfesor");
        String bloque1 = "L2";
        String bloque2 = "J5";
        String bloque3 = "J1";
        String rutProfesor1 = "18.338.861-4";
        String rutProfesor2 = "7.413.382-7";
        String rutProfesor3 = "5.166.831-6";
        
        assertNotNull(mockHorarios.findByBloqueAndProfesor(bloque1, rutProfesor1));
        assertNull(mockHorarios.findByBloqueAndProfesor(bloque2, rutProfesor1));
        assertNull(mockHorarios.findByBloqueAndProfesor(bloque1, rutProfesor2));
        assertNull(mockHorarios.findByBloqueAndProfesor(bloque3, rutProfesor1));
        assertNull(mockHorarios.findByBloqueAndProfesor(bloque1, rutProfesor3));
        assertEquals("1", mockHorarios.findByBloqueAndProfesor(bloque1, rutProfesor1).getId().toString());
    }

    /**
     * Test of findDisponibleByBloqueAndProfesor method, of class Horarios.
     */
    @Test
    public void testFindDisponibleByBloqueAndProfesor() throws Exception {
        System.out.println("findDisponibleByBloqueAndProfesor");
        String bloque = "M3";
        String rutProfesor = "18.338.861-4";
        
        assertNotNull(mockHorarios.findDisponibleByBloqueAndProfesor(bloque, rutProfesor));
        assertNull(mockHorarios.findDisponibleByBloqueAndProfesor("L2", rutProfesor));
        assertEquals("2", mockHorarios.findDisponibleByBloqueAndProfesor(bloque, rutProfesor).getId().toString());
    }

    /**
     * Test of findDisponiblesByProfesorId method, of class Horarios.
     */
    @Test
    public void testFindDisponiblesByProfesorId() throws Exception {
        System.out.println("findDisponiblesByProfesorId");
        String rutProfesor1 = "18.338.861-4";
        String rutProfesor2 = "7.413.382-7";
        String rutProfesor3 = "13.043.816-4";
        
        assertNotNull(mockHorarios.findDisponiblesByProfesorId(rutProfesor1));
        assertNotNull(mockHorarios.findDisponiblesByProfesorId(rutProfesor2));
        assertNotNull(mockHorarios.findDisponiblesByProfesorId(rutProfesor3));
        assertEquals(1, mockHorarios.findDisponiblesByProfesorId(rutProfesor1).size());
        assertEquals("2", mockHorarios.findDisponiblesByProfesorId(rutProfesor1).get(0).getId().toString());
        assertEquals(2, mockHorarios.findDisponiblesByProfesorId(rutProfesor2).size());
        assertEquals("3", mockHorarios.findDisponiblesByProfesorId(rutProfesor2).get(0).getId().toString());
        assertEquals("4", mockHorarios.findDisponiblesByProfesorId(rutProfesor2).get(1).getId().toString());
        assertEquals(0, mockHorarios.findDisponiblesByProfesorId(rutProfesor3).size());
    }

    /**
     * Test of findByHorariosNoDisponibles method, of class Horarios.
     */
    @Test
    public void testFindByHorariosNoDisponibles() throws Exception {
        System.out.println("findByHorariosNoDisponibles");
        
        assertNotNull(mockHorarios.findByHorariosNoDisponibles());
        assertEquals(2, mockHorarios.findByHorariosNoDisponibles().size());
        assertEquals("1", mockHorarios.findByHorariosNoDisponibles().get(0).getId().toString());
        assertEquals("5", mockHorarios.findByHorariosNoDisponibles().get(1).getId().toString());
    }

    /**
     * Test of findBySeleccionados method, of class Horarios.
     */
    @Test
    public void testFindBySeleccionados() throws Exception {
        System.out.println("findBySeleccionados");
        String rutProfesor1 = "18.338.861-4";
        String rutProfesor2 = "7.413.382-7";
        String rutProfesor3 = "13.043.816-4";
        String rutProfesor4 = "14.162.240-4";
        
        assertNotNull(mockHorarios.findBySeleccionados(rutProfesor1));
        assertNotNull(mockHorarios.findBySeleccionados(rutProfesor2));
        assertNotNull(mockHorarios.findBySeleccionados(rutProfesor3));
        assertNull(mockHorarios.findBySeleccionados(rutProfesor4));
        assertEquals(2, mockHorarios.findBySeleccionados(rutProfesor1).size());
        assertEquals("1",mockHorarios.findBySeleccionados(rutProfesor1).get(0).getId().toString());
        assertEquals("2",mockHorarios.findBySeleccionados(rutProfesor1).get(1).getId().toString());
        assertEquals(2, mockHorarios.findBySeleccionados(rutProfesor2).size());
        assertEquals("3",mockHorarios.findBySeleccionados(rutProfesor2).get(0).getId().toString());
        assertEquals("4",mockHorarios.findBySeleccionados(rutProfesor2).get(1).getId().toString());
        assertEquals(1, mockHorarios.findBySeleccionados(rutProfesor3).size());
        assertEquals("5",mockHorarios.findBySeleccionados(rutProfesor3).get(0).getId().toString());
    }
    
    @Test
    public void testFindAsignadosByProfesorId(){
        System.out.println("findAsignadosByProfesorId");
        String rut1 = "18.338.861-4";
        String rut2 = "7.413.382-7";
        
        assertNotNull(mockHorarios.findAsignadosByProfesorId(rut1));
        assertNull(mockHorarios.findAsignadosByProfesorId(rut2));
        assertEquals(1, mockHorarios.findAsignadosByProfesorId(rut1).size());
        assertEquals("L2", mockHorarios.findAsignadosByProfesorId(rut1).get(0).getBloque());
        
    }
    
    @Test
    public void testFindBybloqueCarreraPlanNivelAnioYSemestre(){
        System.out.println("findBybloqueCarreraPlanNivelAnioYSemestre");
        String bloque1 = "L2";
        String bloque2 = "L1";
        long version1 = Long.parseLong("1");
        long version2 = Long.parseLong("2");
        int nivel1 = 1;
        int nivel2 = 2;
        int anio1 = 2015;
        int anio2 = 2016;
        int semestre1 = 1;
        int semestre2 = 2;
        
        assertNotNull(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque1, version1, nivel1, anio1, semestre1));
        assertNull(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque2, version1, nivel1, anio1, semestre1));
        assertNull(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque1, version2, nivel1, anio1, semestre1));
        assertNull(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque1, version1, nivel2, anio1, semestre1));
        assertNull(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque1, version1, nivel1, anio2, semestre1));
        assertNull(mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque1, version1, nivel1, anio1, semestre2));
        assertEquals("1", mockHorarios.findBybloqueCarreraPlanNivelAnioYSemestre(bloque1, version1, nivel1, anio1, semestre1).getId().toString());
        
    }
    
}
