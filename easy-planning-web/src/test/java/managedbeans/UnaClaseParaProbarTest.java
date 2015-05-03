/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alonso
 */
public class UnaClaseParaProbarTest {
    
    public UnaClaseParaProbarTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of suma method, of class UnaClaseParaProbar.
     */
    @Test
    public void testSuma() {
        System.out.println("suma");
        int numeroUno = 2;
        int numeroDos = 2;
        UnaClaseParaProbar instance = new UnaClaseParaProbar();
        int expResult = 4;
        int result = instance.suma(numeroUno, numeroDos);
        // Se comparan los resultados
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
