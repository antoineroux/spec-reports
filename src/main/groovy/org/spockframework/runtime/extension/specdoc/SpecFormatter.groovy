package org.spockframework.runtime.extension.specdoc


/**
 * Formats the specification class names to produce human-readable names,
 * filenames...
 * 
 * @author Antoine Roux
 *
 */
class SpecFormatter {
    
    String fileNameFor(String specFileName) {
        specFileName[0..specFileName.indexOf(".")-1]
    }

}
