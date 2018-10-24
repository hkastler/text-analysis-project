/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkstlr.text.opennlp.control;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import opennlp.tools.doccat.DocumentCategorizer;

/**
 *
 * @author henry.kastler
 */
public class MockDocumentCategorizer implements DocumentCategorizer {

    @Override
    public double[] categorize(String[] text, Map<String, Object> extraInformation) {
        return null;
    }

    @Override
    public double[] categorize(String[] text) {
        return null;
    }

    @Override
    public String getBestCategory(double[] outcome) {
        return null;
    }

    @Override
    public int getIndex(String category) {
        return 1;
    }

    @Override
    public String getCategory(int index) {
        return null;
    }

    @Override
    public int getNumberOfCategories() {
        return 1;
    }

    @Override
    public String getAllResults(double[] results) {
        return null;
    }

    @Override
    public Map<String, Double> scoreMap(String[] text) {
        return null;
    }

    @Override
    public SortedMap<Double, Set<String>> sortedScoreMap(String[] text) {
        return null;
    }
    
}
