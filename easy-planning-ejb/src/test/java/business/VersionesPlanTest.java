/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Carrera;
import entities.PlanEstudio;
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
public class VersionesPlanTest {
    
    private static VersionesPlan mockVersionesPlan;
    
    private static Carrera carrera;
    
    private static PlanEstudio planEstudio;
    
    private static VersionPlan versionPlan1;
    private static VersionPlan versionPlan2;
    
    public VersionesPlanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        mockVersionesPlan = mock(VersionesPlan.class);
        
        carrera = new Carrera();
        carrera.setId(Long.parseLong("1"));
        carrera.setNombre("INGENIERÍA CIVIL INFORMÁTICA");
        
        planEstudio = new PlanEstudio();
        planEstudio.setCodigo(1363);
        planEstudio.setId(Long.parseLong("1"));
        planEstudio.setJornada(0);
        planEstudio.setCarrera(carrera);
        
        versionPlan1 = new VersionPlan();
        versionPlan1.setAnio(2015);
        versionPlan1.setId(Long.parseLong("1"));
        versionPlan1.setPlanEstudio(planEstudio);
        versionPlan1.setVersion(1);
        versionPlan1.setPlanificado(true);
        
        versionPlan2 = new VersionPlan();
        versionPlan2.setAnio(2015);
        versionPlan2.setId(Long.parseLong("2"));
        versionPlan2.setPlanEstudio(planEstudio);
        versionPlan2.setVersion(2);
        versionPlan2.setPlanificado(true);
        
        when(mockVersionesPlan.findByIdPlan(planEstudio.getId())).thenReturn(Arrays.asList(versionPlan1,versionPlan2));
        when(mockVersionesPlan.findByIdPlan(Long.parseLong("2"))).thenReturn(new ArrayList<VersionPlan>());
        when(mockVersionesPlan.findByPlanificado(true)).thenReturn(Arrays.asList(versionPlan1));
        when(mockVersionesPlan.findByPlanificado(false)).thenReturn(new ArrayList<VersionPlan>());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByIdPlan method, of class VersionesPlan.
     */
    @Test
    public void testFindByIdPlan() throws Exception {
        System.out.println("findByIdPlan");
        long idPlan1 = Long.parseLong("1");
        long idPlan2 = Long.parseLong("2");
        
        assertNotNull(mockVersionesPlan.findByIdPlan(idPlan1));
        assertNotNull(mockVersionesPlan.findByIdPlan(idPlan2));
        assertEquals(2, mockVersionesPlan.findByIdPlan(idPlan1).size());
        assertEquals(1, mockVersionesPlan.findByIdPlan(idPlan1).get(0).getVersion());
        assertEquals(2015, mockVersionesPlan.findByIdPlan(idPlan1).get(0).getAnio());
        assertEquals(2, mockVersionesPlan.findByIdPlan(idPlan1).get(1).getVersion());
        assertEquals(2015, mockVersionesPlan.findByIdPlan(idPlan1).get(1).getAnio());
        assertEquals(0, mockVersionesPlan.findByIdPlan(idPlan2).size());
    }
    
    @Test
    public void testTrueFindByPlanificado(){
        System.out.println("testTrueFindByPlanificado");
        
        assertEquals(1, mockVersionesPlan.findByPlanificado(true).size());
        
    }
    
    @Test
    public void testFalseFindByPlanificado(){
        System.out.println("testFalseFindByPlanificado");
        
        assertEquals(0, mockVersionesPlan.findByPlanificado(false).size());
    }
}
