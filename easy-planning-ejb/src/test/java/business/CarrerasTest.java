/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import entities.Carrera;
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
public class CarrerasTest {
    
    private static Carreras mockCarreras;
    private static Carrera carrera;
    
    public CarrerasTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mockCarreras = mock(Carreras.class);
        
        carrera = new Carrera();
        carrera.setId(Long.parseLong("1"));
        carrera.setNombre("INGENIERÍA CIVIL INFORMÁTICA");
        
        when(mockCarreras.findByNombre(carrera.getNombre())).thenReturn(carrera);
        when(mockCarreras.findByNombre("INGENIERÍA INFORMÁTICA")).thenReturn(null);

    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByNombre method, of class Carreras.
     */
    @Test
    public void testFindByNombre() throws Exception {
        System.out.println("findByNombre");
        String nombre1 = "INGENIERÍA CIVIL INFORMÁTICA";
        String nombre2 = "INGENIERÍA INFORMÁTICA";
        Carrera expResult = new Carrera();
        expResult.setId(Long.parseLong("1"));
        expResult.setNombre("INGENIERÍA CIVIL INFORMÁTICA");
        
        assertNotNull(mockCarreras.findByNombre(nombre1));
        assertNull(mockCarreras.findByNombre(nombre2));
        assertEquals(expResult, mockCarreras.findByNombre(nombre1));
    }
    
}
