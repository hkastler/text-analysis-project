/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author henry.kastler
 */
public class JAXRSConfigurationTest {
    
    JAXRSConfiguration cut;
    public JAXRSConfigurationTest() {
    }
    
    

    @Test
    public void testJAXRSConfiguration() {
        cut = new JAXRSConfiguration();
        Assertions.assertNotNull(cut);
    }
    
}
