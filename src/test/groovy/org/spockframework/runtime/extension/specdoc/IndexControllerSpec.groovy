package org.spockframework.runtime.extension.specdoc


import org.spockframework.runtime.model.SpecInfo
import spock.lang.Specification;

class IndexControllerSpec extends Specification {

    def indexController = new IndexController()
    
    def "renders the index page"() {
        when: "rendering index page"
        def resultAsString = indexController.renderIndex().toString()
        
        then: "the page contains some basic HTML"
        resultAsString =~ "<body>"
        
    }
    
    def "renders the menu containing the list of all spec names"() {
        given: "a list of specs"
        def specs = [
            new SpecInfo(name: "UserSpec", filename: "UserSpec.groovy"),
            new SpecInfo(name: "ConnectionFactorySpec", filename: "ConnectionFactorySpec.groovy"),
            new SpecInfo(name: "LoginControllerSpecification", filename: "LoginControllerSpecification")
        ]

        
        when: "the menu is rendered"
        indexController.specInfos = specs
        def resultAsString = indexController.renderMenu().toString()
        
        then: "it contains the names of all specs"
        resultAsString =~ "User"
        resultAsString =~ "Connection Factory"
        resultAsString =~ "Login Controller"
        
    }
}
