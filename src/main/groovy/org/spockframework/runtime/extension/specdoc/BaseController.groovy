package org.spockframework.runtime.extension.specdoc

import groovy.lang.Writable;

import java.util.Map;

abstract class BaseController {

    private static specSuffixes = [
        "Spec", "Specs",
        "Specification", "Specifications",
        "Test", "Tests"
    ]
    
    /**
     * Formats a class name of a specification to something "human-readable".
     * For instance: "XMLParserSpec" -> "XML Parser".
     *
     * @param specName  Name of the class.
     * @return  the human readable name.
     */
    protected String formatSpecName(String specName) {
        for (String suffix: specSuffixes) {
            if (specName.endsWith(suffix)) {
                def truncatedName = specName[0..(specName.size() - suffix.size()) - 1]
                return splitCamelCase(truncatedName)
            }
        }
    }
    
    protected String splitCamelCase(String s) {
        return s.replaceAll(
            String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"
            ),
            " "
        );
    }
    
    /**
     * Renders a specific view.
     * 
     * @param viewName The view to render.
     * @param binding  The binding to be applied.
     * @return  The view.
     */
    protected Writable renderView(String viewName, Map binding) {
        def templateIs = this.class.getResourceAsStream(viewName)
        def tmpl = new InputStreamReader(templateIs)
        
        return engine.createTemplate(tmpl).make(binding)
    }
}
