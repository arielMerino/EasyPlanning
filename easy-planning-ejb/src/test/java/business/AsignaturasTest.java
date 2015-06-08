/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Asignatura;
import entities.Carrera;
import entities.PlanEstudio;
import entities.Profesor;
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
public class AsignaturasTest {
    
    private static Asignaturas mockAsignaturas;
    private static Asignatura asignatura1;
    private static Asignatura asignatura2;
    private static Asignatura asignatura3;
    private static Asignatura asignatura4;
    private static Asignatura asignatura5;
    private static Asignatura asignatura6;
    private static Asignatura asignatura7;
    private static Asignatura asignatura8;
    private static Asignatura asignatura9;
    private static Asignatura asignatura10;
    private static PlanEstudio plan;
    private static VersionPlan versionPlan1;
    private static VersionPlan versionPlan2;
    private static Carrera carrera;
    private static Profesor profesor1;
    private static Profesor profesor2;
    
    public AsignaturasTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mockAsignaturas = mock(Asignaturas.class);
        
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
        
        carrera = new Carrera();
        carrera.setId(Long.parseLong("1"));
        carrera.setNombre("INGENIERÍA CIVIL INFORMÁTICA");
        
        plan = new PlanEstudio();
        plan.setCodigo(1863);
        plan.setId(Long.parseLong("3"));
        plan.setJornada(0);
        plan.setCarrera(carrera);
        
        versionPlan1 = new VersionPlan();
        versionPlan1.setAnio(2001);
        versionPlan1.setId(Long.parseLong("3"));
        versionPlan1.setPlanEstudio(plan);
        versionPlan1.setVersion(3);   
        
        versionPlan2 = new VersionPlan();
        versionPlan2.setAnio(2001);
        versionPlan2.setId(Long.parseLong("2"));
        versionPlan2.setPlanEstudio(plan);
        versionPlan2.setVersion(2);   
        
        asignatura1 = new Asignatura();
        asignatura1.setCodigo("10101");
        asignatura1.setCoordinaciones(null);
        asignatura1.setEjercicios(2);
        asignatura1.setId(Long.parseLong("2"));
        asignatura1.setLaboratorio(0);
        asignatura1.setNivel(1);
        asignatura1.setNombre("CÁLCULO I PARA INGENIERÍA");
        asignatura1.setTeoria(6);
        asignatura1.setVersionplan(versionPlan1);
        
        asignatura2 = new Asignatura();
        asignatura2.setCodigo("10102");
        asignatura2.setCoordinaciones(null);
        asignatura2.setEjercicios(2);
        asignatura2.setId(Long.parseLong("3"));
        asignatura2.setLaboratorio(0);
        asignatura2.setNivel(1);
        asignatura2.setNombre("ÁLGEBRA I PARA INGENIERÍA");
        asignatura2.setTeoria(6);
        asignatura2.setVersionplan(versionPlan1);
        
        asignatura3 = new Asignatura();
        asignatura3.setCodigo("10103");
        asignatura3.setCoordinaciones(null);
        asignatura3.setEjercicios(2);
        asignatura3.setId(Long.parseLong("4"));
        asignatura3.setLaboratorio(1);
        asignatura3.setNivel(1);
        asignatura3.setNombre("FISICA I PARA INGENIERÍA");
        asignatura3.setTeoria(4);
        asignatura3.setVersionplan(versionPlan1);
        
        asignatura4 = new Asignatura();
        asignatura4.setCodigo("10104");
        asignatura4.setCoordinaciones(null);
        asignatura4.setEjercicios(0);
        asignatura4.setId(Long.parseLong("5"));
        asignatura4.setLaboratorio(2);
        asignatura4.setNivel(1);
        asignatura4.setNombre("TALLER DE DESARROLLO PERSONAL E INTEGRAL");
        asignatura4.setTeoria(2);
        asignatura4.setVersionplan(versionPlan1);
        
        asignatura5 = new Asignatura();
        asignatura5.setCodigo("10125");
        asignatura5.setCoordinaciones(null);
        asignatura5.setEjercicios(0);
        asignatura5.setId(Long.parseLong("6"));
        asignatura5.setLaboratorio(2);
        asignatura5.setNivel(1);
        asignatura5.setNombre("INTRODUCCION A LA INGENIERIA");
        asignatura5.setTeoria(0);
        asignatura5.setVersionplan(versionPlan1);
        
        asignatura6 = new Asignatura();
        asignatura6.setCodigo("10126");
        asignatura6.setCoordinaciones(null);
        asignatura6.setEjercicios(0);
        asignatura6.setId(Long.parseLong("7"));
        asignatura6.setLaboratorio(2);
        asignatura6.setNivel(1);
        asignatura6.setNombre("METODOS DE ESTUDIO");
        asignatura6.setTeoria(0);
        asignatura6.setVersionplan(versionPlan1);
        
        asignatura7 = new Asignatura();
        asignatura7.setCodigo("10107");
        asignatura7.setCoordinaciones(null);
        asignatura7.setEjercicios(2);
        asignatura7.setId(Long.parseLong("8"));
        asignatura7.setLaboratorio(0);
        asignatura7.setNivel(2);
        asignatura7.setNombre("CALCULO II PARA INGENIERÍA");
        asignatura7.setTeoria(6);
        asignatura7.setVersionplan(versionPlan1);
        
        asignatura8 = new Asignatura();
        asignatura8.setCodigo("10108");
        asignatura8.setCoordinaciones(null);
        asignatura8.setEjercicios(2);
        asignatura8.setId(Long.parseLong("9"));
        asignatura8.setLaboratorio(0);
        asignatura8.setNivel(2);
        asignatura8.setNombre("ALGEBRA II PARA INGENIERÍA");
        asignatura8.setTeoria(4);
        asignatura8.setVersionplan(versionPlan1);
        
        asignatura9 = new Asignatura();
        asignatura9.setCodigo("10109");
        asignatura9.setCoordinaciones(null);
        asignatura9.setEjercicios(2);
        asignatura9.setId(Long.parseLong("10"));
        asignatura9.setLaboratorio(1);
        asignatura9.setNivel(2);
        asignatura9.setNombre("FÍSICA II PARA INGENIERÍA");
        asignatura9.setTeoria(4);
        asignatura9.setVersionplan(versionPlan1);
        
        asignatura10 = new Asignatura();
        asignatura10.setCodigo("10110");
        asignatura10.setCoordinaciones(null);
        asignatura10.setEjercicios(0);
        asignatura10.setId(Long.parseLong("11"));
        asignatura10.setLaboratorio(2);
        asignatura10.setNivel(2);
        asignatura10.setNombre("FUNDAMENTOS DE COMPUTACIÓN Y PROGRAMACIÓN");
        asignatura10.setTeoria(4);
        asignatura10.setVersionplan(versionPlan1);
        asignatura10.setProfesores(null);
        
        asignatura7.setPrerequisitos(Arrays.asList(asignatura1));
        
        asignatura8.setPrerequisitos(Arrays.asList(asignatura2));
        asignatura10.setPrerequisitos(Arrays.asList(asignatura2));
        
        asignatura9.setPrerequisitos(Arrays.asList(asignatura3));
        
        asignatura1.setProfesores(Arrays.asList(profesor1));
        asignatura2.setProfesores(Arrays.asList(profesor1));
        asignatura3.setProfesores(Arrays.asList(profesor1));
        asignatura4.setProfesores(Arrays.asList(profesor1));
        
        asignatura5.setProfesores(Arrays.asList(profesor2));
        asignatura6.setProfesores(Arrays.asList(profesor2));
        asignatura7.setProfesores(Arrays.asList(profesor2));
        asignatura8.setProfesores(Arrays.asList(profesor2));
        
        asignatura9.setProfesores(Arrays.asList(profesor1,profesor2));
        
        when(mockAsignaturas.findByCodigoAsgAndIdVersion("10101", Long.parseLong("3"))).thenReturn(asignatura1);
        when(mockAsignaturas.findByCodigoAsgAndIdVersion("10101", Long.parseLong("4"))).thenReturn(null);
        when(mockAsignaturas.findByCodigoAsgAndIdVersion("10134", Long.parseLong("3"))).thenReturn(null);
        when(mockAsignaturas.getAllProfesorAsignatura()).thenReturn(Arrays.asList(asignatura1,asignatura2,asignatura3,asignatura4,asignatura5,asignatura6,asignatura7,asignatura8,asignatura8));
        when(mockAsignaturas.findByNivelAndPlan(1, versionPlan1.getId())).thenReturn(Arrays.asList(asignatura1,asignatura2,asignatura3,asignatura4,asignatura5,asignatura6));
        when(mockAsignaturas.findByNivelAndPlan(2, versionPlan1.getId())).thenReturn(Arrays.asList(asignatura7,asignatura8,asignatura9,asignatura10));
        when(mockAsignaturas.findByNivelAndPlan(3, versionPlan1.getId())).thenReturn(new ArrayList<Asignatura>());
        when(mockAsignaturas.findByNivelAndPlan(1, Long.parseLong("4"))).thenReturn(new ArrayList<Asignatura>());
        when(mockAsignaturas.findNivelesByPlan(versionPlan1.getId())).thenReturn(Arrays.asList(1,2));
        when(mockAsignaturas.findNivelesByPlan(versionPlan2.getId())).thenReturn(new ArrayList<Integer>());
        
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetAllProfesorAsignatura(){
        System.out.println("getAllProfesoresAsignatura");
        int size = 9;
        
        assertNotNull(mockAsignaturas.getAllProfesorAsignatura());
        assertEquals(size, mockAsignaturas.getAllProfesorAsignatura().size());
    }

    @Test
    public void testFindByCodigoAsgAndIdVersion(){
        System.out.println("findByCodigoAsgAndIdVersion");
        String codigo1 = "10101";
        String codigo2 = "10134";
        long plan1 = Long.parseLong("3");
        long plan2 = Long.parseLong("4");
        
        assertNotNull(mockAsignaturas.findByCodigoAsgAndIdVersion(codigo1, plan1));
        assertNull(mockAsignaturas.findByCodigoAsgAndIdVersion(codigo1, plan2));
        assertNull(mockAsignaturas.findByCodigoAsgAndIdVersion(codigo2, plan1));
        assertEquals(mockAsignaturas.findByCodigoAsgAndIdVersion(codigo1, plan1).getNombre(), "CÁLCULO I PARA INGENIERÍA");
        
    }   
    
    @Test
    public void testFindByNivelAndPlan(){
        System.out.println("findByNivelAndPlan");
        int nivel1 = 1;
        int nivel2 = 2;
        int nivel3 = 3;
        long versionplan1 = Long.parseLong("3");
        long versionplan2 = Long.parseLong("4");
        
        assertNotNull(mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1));
        assertNotNull(mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1));
        assertNotNull(mockAsignaturas.findByNivelAndPlan(nivel3, versionplan1));
        assertNotNull(mockAsignaturas.findByNivelAndPlan(nivel1, versionplan2));
        assertEquals(6, mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).size());
        assertEquals("CÁLCULO I PARA INGENIERÍA", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(0).getNombre());
        assertEquals("10101", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(0).getCodigo());
        assertEquals("ÁLGEBRA I PARA INGENIERÍA", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(1).getNombre());
        assertEquals("10102", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(1).getCodigo());
        assertEquals("FISICA I PARA INGENIERÍA", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(2).getNombre());
        assertEquals("10103", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(2).getCodigo());
        assertEquals("TALLER DE DESARROLLO PERSONAL E INTEGRAL", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(3).getNombre());
        assertEquals("10104", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(3).getCodigo());
        assertEquals("INTRODUCCION A LA INGENIERIA", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(4).getNombre());
        assertEquals("10125", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(4).getCodigo());
        assertEquals("METODOS DE ESTUDIO", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(5).getNombre());
        assertEquals("10126", mockAsignaturas.findByNivelAndPlan(nivel1, versionplan1).get(5).getCodigo());
        assertEquals(4, mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).size());
        assertEquals("CALCULO II PARA INGENIERÍA", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(0).getNombre());
        assertEquals("10107", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(0).getCodigo());
        assertEquals("ALGEBRA II PARA INGENIERÍA", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(1).getNombre());
        assertEquals("10108", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(1).getCodigo());
        assertEquals("FÍSICA II PARA INGENIERÍA", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(2).getNombre());
        assertEquals("10109", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(2).getCodigo());
        assertEquals("FUNDAMENTOS DE COMPUTACIÓN Y PROGRAMACIÓN", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(3).getNombre());
        assertEquals("10110", mockAsignaturas.findByNivelAndPlan(nivel2, versionplan1).get(3).getCodigo());
        assertEquals(0, mockAsignaturas.findByNivelAndPlan(nivel3, versionplan1).size());
        assertEquals(0, mockAsignaturas.findByNivelAndPlan(nivel1, versionplan2).size());
        
    }
    
    @Test
    public void testFindNivelesByPlan(){
        System.out.println("findNivelesByPlan");
        long version1 = Long.parseLong("3");
        long version2 = Long.parseLong("2");
        
        assertNotNull(mockAsignaturas.findNivelesByPlan(version1));
        assertNotNull(mockAsignaturas.findNivelesByPlan(version2));
        assertEquals(2, mockAsignaturas.findNivelesByPlan(version1).size());
        assertEquals("1", mockAsignaturas.findNivelesByPlan(version1).get(0).toString());
        assertEquals("2", mockAsignaturas.findNivelesByPlan(version1).get(1).toString());
        assertEquals(0, mockAsignaturas.findNivelesByPlan(version2).size());
        
    }
    
    /*@Test
    public void testFindByAsignaturaAsignada(){
        System.out.println("findByAsignaturaAsignada");
        
    }*/
}
