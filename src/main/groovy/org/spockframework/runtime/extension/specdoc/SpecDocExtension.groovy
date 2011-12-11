package org.spockframework.runtime.extension.specdoc

import org.spockframework.runtime.extension.IGlobalExtension;
import org.spockframework.runtime.model.SpecInfo


class SpecDocExtension implements IGlobalExtension {

    def outputDir = System.getProperty("spec.output.dir")
    
    def specInfoController = new SpecInfoController()
    
    def indexController = new IndexController()
    
    def counter = 0
    
    static shutdownHookInstalled = false
    
    static specInfos = []
    
    void visitSpec(SpecInfo specInfo) {
        if (outputDir) {
            def target = new File(outputDir)
            if (!target.exists()) target.mkdirs()
            
            if (target.isDirectory()) {
                if (!shutdownHookInstalled) {
                    System.addShutdownHook buildIndex
                    shutdownHookInstalled = true
                }
                
                specInfos << specInfo
                
                specInfoController.specInfo = specInfo
                Writable output = specInfoController.render()
                def fileName = new SpecFormatter().fileNameFor(specInfo.filename)
                writeFile(outputDir + "/" + fileName + ".html", output)
                
                def css = new File(outputDir + "/layout.css")
                if (!css.exists()) {
                    def layout = this.class.getResourceAsStream("/css/layout.css")
                    css << layout.bytes
                }
            }
        }
    }
    
    def buildIndex = {
        def indexController = new IndexController(specInfos)
        def menu = indexController.renderMenu()
        def index = indexController.renderIndex()
        
        writeFile(outputDir + "/menu.html", menu)
        writeFile(outputDir + "/index.html", index)
    }
    
    private writeFile(String path, Writable output) {
        def fos = new FileOutputStream(path)
        def osw = new OutputStreamWriter(fos)
        output.writeTo(osw)
        osw.close()
        fos.close()
    }

}
