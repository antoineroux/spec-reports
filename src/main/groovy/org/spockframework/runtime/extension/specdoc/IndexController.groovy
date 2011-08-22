package org.spockframework.runtime.extension.specdoc

import groovy.text.GStringTemplateEngine
import org.spockframework.runtime.model.SpecInfo

class IndexController extends BaseController {

    List<SpecInfo> specInfos
    
    def engine = new GStringTemplateEngine()
    
    IndexController(specInfos) {
        this.specInfos = specInfos
    }
    
    /**
     * Renders the index.html page.
     * 
     * @return the index page
     */
    Writable renderIndex() {        
        renderView "IndexView.template", [:]
    }
    
    /**
     * Renders the menu with the list of all specifications available.
     * 
     * @return the menu page.
     */
    Writable renderMenu() {
        def specInfoFormatter = new SpecFormatter()
        def specs = specInfos.collect { 
              [name: formatSpecName(it.name),
               url: specInfoFormatter.fileNameFor(it.filename) + ".html"]
        }
        renderView "MenuView.template", [specInfos: specs]
    }
    
}
