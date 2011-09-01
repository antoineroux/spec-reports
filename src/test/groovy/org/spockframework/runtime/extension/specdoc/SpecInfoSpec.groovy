package org.spockframework.runtime.extension.specdoc

import spock.lang.Specification



class SpecInfoSpec extends Specification {

    def "should do something"() {
        given: "something"
        when: "something else"
        then: "youhou"
        and: "some other condition"
        1 == 1
    }
}
