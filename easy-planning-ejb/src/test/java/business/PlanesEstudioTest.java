/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Carrera;
import entities.PlanEstudio;
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
public class PlanesEstudioTest {
    
    private static PlanesEstudio mockPlanesEstudio;
    
    private static Carrera carrera;
    
    private static PlanEstudio planEstudio1;
    private static PlanEstudio planEstudio2;
    
    public PlanesEstudioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockPlanesEstudio = mock(PlanesEstudio.class);
        
        carrera = new Carrera();
        carrera.setId(Long.parseLong("1"));
        carrera.setNombre("INGENIERÍA CIVIL INFORMÁTICA");
        
        planEstudio1 = new PlanEstudio();
        planEstudio1.setCodigo(1363);
        planEstudio1.setId(Long.parseLong("1"));
        planEstudio1.setJornada(0);
        planEstudio1.setCarrera(carrera);
        
        planEstudio2 = new PlanEstudio();
        planEstudio2.setCodigo(1973);
        planEstudio2.setId(Long.parseLong("2"));
        planEstudio2.setJornada(1);
        planEstudio2.setCarrera(carrera);
        
        when(mockPlanesEstudio.findPlanByIdCarrera(carrera.getId())).thenReturn(Arrays.asList(planEstudio1,planEstudio2));
        when(mockPlanesEstudio.findPlanByIdCarrera(Long.parseLong("2"))).thenReturn(new ArrayList<PlanEstudio>());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findPlanByIdCarrera method, of class PlanesEstudio.
     */
    @Test
    public void testFindPlanByIdCarrera() throws Exception {
        System.out.println("findPlanByIdCarrera");
        long idCarrera1 = Long.parseLong("1");
        long idCarrera2 = Long.parseLong("2");
        
        assertNotNull(mockPlanesEstudio.findPlanByIdCarrera(idCarrera1));
        assertNotNull(mockPlanesEstudio.findPlanByIdCarrera(idCarrera2));
        assertEquals(2, mockPlanesEstudio.findPlanByIdCarrera(idCarrera1).size());
        assertEquals(1363, mockPlanesEstudio.findPlanByIdCarrera(idCarrera1).get(0).getCodigo());
        assertEquals(1973, mockPlanesEstudio.findPlanByIdCarrera(idCarrera1).get(1).getCodigo());
        assertEquals(0, mockPlanesEstudio.findPlanByIdCarrera(idCarrera2).size());
    }
    
}
