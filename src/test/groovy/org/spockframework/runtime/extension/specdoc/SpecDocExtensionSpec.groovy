package org.spockframework.runtime.extension.specdoc

import org.spockframework.runtime.extension.specdoc.SpecDocExtension;

import spock.lang.Specification;

class SpecDocExtensionSpec extends Specification {

    
    def "should not trigger if spec.output.dir is not set"() {
        when: "spec.output.dir is not set"
        def specDocExtension = new SpecDocExtension()
        specDocExtension.outputDir = null
        specDocExtension.visitSpec(null)
        
        then: "visitSpec is not triggered"
        notThrown(Exception)
    }
}
