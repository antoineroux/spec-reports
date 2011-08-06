package org.spockframework.runtime.extension

import org.spockframework.runtime.extension.IGlobalExtension;
import org.spockframework.runtime.model.SpecInfo


class SpecDocExtension implements IGlobalExtension {

    def outputDir = System.getProperty("spec.output.dir")
    
    def controller = new SpecInfoController()
    
    void visitSpec(SpecInfo specInfo) {
        if (outputDir && new File(outputDir).isDirectory()) {
            controller.specInfo = specInfo
            Writable output = controller.render()
            def fileName = specInfo.filename[0..specInfo.filename.indexOf(".")-1]
            def fos = new FileOutputStream(outputDir + "/" + fileName + ".html")
            def osw = new OutputStreamWriter(fos)            
            output.writeTo(osw)
            osw.close()
            fos.close()
            
            def css = new File(outputDir + "/layout.css")
            if (!css.exists()) {
                def layout = this.class.getResourceAsStream("/css/layout.css")
                css << layout.bytes
            }
        }
    }

}
