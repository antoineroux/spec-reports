package org.spockframework.runtime.extension

import groovy.text.GStringTemplateEngine
import groovy.text.SimpleTemplateEngine
import org.spockframework.runtime.model.BlockKind
import org.spockframework.runtime.model.SpecInfo;
/**
 * This class is a simplified controller that renders all features for a given
 * specification.
 * @author antoine
 *
 */
class SpecInfoController {

    SpecInfo specInfo
    
    /**
     * Associates labels with better looking equivalents.
     */
    private static labels = [
        (BlockKind.SETUP): "Given",
        (BlockKind.WHEN): "When",
        (BlockKind.THEN): "Then",
        (BlockKind.WHERE): "Where",
        (BlockKind.EXPECT): "Expect",
        (BlockKind.CLEANUP): ""
    ]
    
    private static specSuffixes = [
        "Spec", "Specs",
        "Specification", "Specifications",
        "Test", "Tests"
    ]
    
    
    /**
     * Renders the corresponding page.
     * 
     * @return the full HTML page as a String
     */
    Writable render() {
        def templateIs = this.class.getResourceAsStream("SpecInfoView.template")
        def tmpl = new InputStreamReader(templateIs)
        
        def binding = [specInfo: specInfo,
                       labels: labels,
                       specName: formatSpecName(specInfo.name)]
        def engine = new GStringTemplateEngine()
        return engine.createTemplate(tmpl).make(binding)
    }
    
    
    /**
     * Formats a class name of a specification to something "human-readable".
     * For instance: "XMLParserSpec" -> "XML Parser".
     * 
     * @param specName  Name of the class.
     * @return  the human readable name.
     */
    private String formatSpecName(String specName) {
        for (String suffix: specSuffixes) {
            if (specName.endsWith(suffix)) {
                def truncatedName = specName[0..(specName.size() - suffix.size()) - 1]
                return splitCamelCase(truncatedName)
            }
        }
    }
    
    private String splitCamelCase(String s) {
        return s.replaceAll(
            String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"
      ),
      " "
   );
}
    
}
