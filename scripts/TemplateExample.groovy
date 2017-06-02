import com.zekeoptimo.swagger2confluence.SwaggerParseEngine
import com.zekeoptimo.swagger2confluence.markup.impl.ConfluenceApi

/**
 * Example groovy script to parse the pet store swagger json file from swagger.io
 */

def markupInterface = new ConfluenceApi()
def template = new File("../templates/basic-template.html")
def swaggerFile = new File("../json/petstoreswagger.json")

def a = new SwaggerParseEngine(swaggerFile)
def result = a.parse(markupInterface, template)

println result

//result = a.publishToConfluence("user", "password", "https://confluence-server/wiki/rest/api", "12345", processed)
//def time3 = LocalDateTime.now()
//println "Sync to Confluence took " + ChronoUnit.SECONDS.between(time2, time3) + " seconds"
//println result
