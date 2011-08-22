package org.spockframework.runtime.extension.specdoc

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
class SpecInfoController extends BaseController {

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
    
    
}
