package org.spockframework.runtime.extension.specdoc

import com.sun.corba.se.spi.ior.Writeable

import org.spockframework.runtime.extension.specdoc.SpecInfoController;
import org.spockframework.runtime.model.BlockInfo;
import org.spockframework.runtime.model.BlockKind;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;

import spock.lang.Specification;

class SpecInfoControllerSpec extends Specification {

    def specInfoController = new SpecInfoController()
    
    def specInfo
    
    def setup() {
        specInfo = new SpecInfo()
        specInfo.name = "SomeNameSpec"
        def f1 = new FeatureInfo()
        f1.with {
            name = "should do something"
        }
        def given = new BlockInfo()
        given.with {
            kind = BlockKind.SETUP
            texts = ["a setup is defined"]
        }
        def when = new BlockInfo()
        when.with {
            kind = BlockKind.WHEN
            texts = ["some action is triggered"]  
        }
        def then = new BlockInfo()
        then.with {
            kind = BlockKind.THEN
            texts = ["some assertions are checked", "some more assertions are checked"]
        }
        f1.addBlock given
        f1.addBlock when
        f1.addBlock then
        
        
        def f2 = new FeatureInfo()
        f2.with {
            name = "should do something else"
        }
        specInfo.addFeature(f1)
        specInfo.addFeature(f2)
    }
    
    def "renders the list of features"() {

        when: "a SpecInfo is passed to the controller and rendering is triggered"
        specInfoController.specInfo = specInfo
        def result = specInfoController.render()
        def resultAsString = result.toString()

        then: "the resulting page should contain the list of features"
        resultAsString.contains "should do something"
        resultAsString.contains "should do something else"
    }
    
    def "renders the content of the feature as given / when / then"() {
        when: "a SpecInfo is passed to the controller and rendering is triggered"
        specInfoController.specInfo = specInfo
        def result = specInfoController.render() 
        def resultAsString = result.toString()
        
        then: "the resulting page contains the feature specifications showed as given / when / then"
        resultAsString =~ "Given"
        resultAsString =~ "a setup is defined"
        resultAsString =~ "When"
        resultAsString =~ "some action is triggered"
        resultAsString =~ "Then"
        resultAsString =~ "some assertions are checked"
        
        and: ' "And" is used if there are several texts for the same label'
        resultAsString =~ "And"
        resultAsString =~ "some more assertions are checked"
    }
}
