@Grapes([
    @Grab(group='net.sf.json-lib', module='json-lib', version='2.3', classifier='jdk15'),
    @Grab('org.slf4j:slf4j-api:1.7.25'),
    @Grab('org.slf4j:slf4j-simple:1.7.25'),
    @Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7'),
    @GrabConfig(systemClassLoader=true)
])

import com.zekeoptimo.swagger2confluence.parser.SwaggerParserEngine
import com.zekeoptimo.swagger2confluence.markup.impl.ConfluenceApiMarkup
import com.zekeoptimo.swagger2confluence.markup.impl.TextFileMarkup


/**
 * Example groovy script to parse the pet store swagger from http://petstore.swagger.io/v2/swagger.json
 *
 * To run this script:
 *
 * groovy -cp ..\build\libs\swagger-confluence-1.0-SNAPSHOT.jar Example.groovy
 *
 * If running within IntelliJ you will probably need to remove the @Grapes section.
 *
 */

//
// Example using a URL and producing Confluence output using our basic template
//
def markupInterface = new ConfluenceApiMarkup()
def template = new File("../templates/basic-template.html")
def swaggerSpecs = new URL("http://petstore.swagger.io/v2/swagger.json")

def a = new SwaggerParserEngine(swaggerSpecs)
def result = a.parse(markupInterface, template)

println result

//
// Example of adding additional closures, producing a text file
// This example closure get all PATHS, excluding any operations
// that aren't "get".
//
markupInterface = new TextFileMarkup()
def T_PATHS_GET_ONLY = {
    def paths = [:]

    it.values.paths.each { p ->
        p.value?.each { o ->
            if (o.key == "get") {
                if (!paths.containsKey(p.key))
                    paths.put(p.key, [:])
                paths[p.key].put(o.key, o.value)
            }
        }
    }

    return paths
}

a.addClosures(["T_PATHS_GET_ONLY" : T_PATHS_GET_ONLY])

template = '''
%Traverse(c:T_PATHS_GET_ONLY, key)%
${key}
%Traverse(values, key)%
${key.toUpperCase()} ${summary}
%EndTraverse%
%EndTraverse%
'''

result = a.parse(markupInterface, template)

println result

// This code would allow you to publish to Confluence
//result = a.publishToConfluence("user", "password", "https://confluence-server/wiki/rest/api", "12345", processed)
//println result
