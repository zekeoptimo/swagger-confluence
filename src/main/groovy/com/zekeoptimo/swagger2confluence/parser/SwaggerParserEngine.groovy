package com.zekeoptimo.swagger2confluence.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.zekeoptimo.swagger2confluence.closures.DefaultClosures
import com.zekeoptimo.swagger2confluence.markup.MarkupInterface
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import com.zekeoptimo.swagger2confluence.model.swagger.Swagger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * Swagger Parse Engine
 *
 * @author zekeo
 */
class SwaggerParserEngine {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerParserEngine.class)

    private final Object data

    /**
     * Load from a Swagger object
     *
     * @param data Swagger data
     */
    SwaggerParserEngine(Swagger data) {
        this.data = data
    }

    /**
     * Load from a string
     *
     * @param jsonString String
     * @throws IOException Exception
     */
    SwaggerParserEngine(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
        this.data = mapper.readValue(jsonString, Swagger.class)
    }

    /**
     * Load from a file
     *
     * @param jsonFile File
     * @throws IOException Exception
     */
    SwaggerParserEngine(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
        //this.data = mapper.readValue(jsonFile, Swagger.class)
        this.data = mapper.readValue(jsonFile, Object.class)
    }

    /**
     * Load from a URL
     *
     * @param jsonUrl URL
     * @throws IOException Exception
     */
    SwaggerParserEngine(URL jsonUrl) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
        this.data = mapper.readValue(jsonUrl, Swagger.class)
    }

    /**
     * Parse the loaded schema using the supplied parser spec
     *
     * @param swaggerParser Swagger Parser spec
     * @return Results
     */
    String parse(MarkupInterface markupInterface, File template) {
        def parser = new SwaggerParser(markupInterface)
        def current = parser
        def traverseLevel = 0

        def startTime = LocalDateTime.now()

        template.each { line ->
            if(line) {
                def done = false

                // Check for traverse
                def traverse = (line =~ /^\s*%\s*Traverse\s*\(\s*((\w+)|(c:\w+))\s*(,\s*\w+\s*)*\s*\)\s*%\s*$/)
                if (traverse) {
                    String path = traverse[0][1]
                    String sort = traverse[0][4] ? traverse[0][4].find(/\w+/) : null
                    String filter = traverse[0][5] ? traverse[0][5].find(/\w+/) : null

                    // Determine / derive closures to use
                    Closure dataClosure
                    if (path[0..1] == "c:") {
                        def c = DefaultClosures.getField(path.substring(2))?.get(null)
                        if (!(c && c instanceof Closure))
                            throw new Exception("Closure " + path.substring(2) + " not found!")
                        dataClosure = c
                    } else {
                        dataClosure = {
                            def props = (it instanceof Map) ? it : it.properties
                            def result = (path == "values") ? props.values : props.values[path]

                            if (result && !(result instanceof Map || result instanceof List))
                                return [result]

                            if (!result) {
                                logger.info("Empty path: " + path)
                                return []
                            }

                            return result
                        }
                    }

                    current = current.traverse(dataClosure,
                            // Sort
                            sort ? { ((it instanceof Map) ? it : it.properties)[sort] } : null,
                            // Filter
                            filter ? { ((it instanceof Map) ? it : it.properties)[filter] } : null
                    )

                    traverseLevel++
                    done = true
                }

                // Check for end of traverse
                if (!done && (line =~ /^\s*%\s*EndTraverse\s*%\s*$/)) {
                    if (traverseLevel == 0)
                        throw new Exception("EndTraverse without matching Traverse")

                    current = current.return()

                    traverseLevel--
                    done = true
                }

                if (!done) {
                    def parse = false

                    // Find values that we need to parse out normally ie ${field} or ${field.sub}
                    line = line.replaceAll(/(\\*\$\{)([A-Za-z0-9_.\[\]"'()?]+)\}/) { all, first, second ->
                        if (first[0] == '\\')
                            return all
                        parse = true

                        def firstWord = second.find(/\w+/)
                        if (firstWord != "values" && firstWord != "me" && firstWord != "parent" && firstWord != "key")
                            second = "values?." + second
                        return first + second + " != null ? (" + second + ".toString()?.escape() ?: '') : ''}"
                    }

                    // Find values that we need to parse, but don't add extra processing ie $*{field} or $*{field == 'hamburger' ? 'cool' : 'bummer'}
                    line = line.replaceAll(/(\\*\$)\*(\{.+\})/) { all, first, second ->
                        if (first[0] == '\\')
                            return all
                        parse = true
                        return first + second
                    }

                    if (parse || line =~ /\$\*\{.+\}/)
                        current.line(line, true)
                    else
                        current.line(line, false)
                }
            }
        }

        logger.info("Loading definition took " + (ChronoUnit.MILLIS.between(startTime, LocalDateTime.now())/1000) + " seconds")
        startTime = LocalDateTime.now()

        String output = parser.parse((data instanceof Map ? data : data?.properties) ?: [:])
        output = parser.getMarkupInterface().postProcessing(output)

        logger.info("Parsing took " + (ChronoUnit.MILLIS.between(startTime, LocalDateTime.now())/1000)  + " seconds")
        return output
    }

    /**
     * Get swagger data object
     *
     * @return Swagger data
     */
    Swagger getData() {
        return data
    }

    /**
     * Publish a document to confluence
     *
     * @param user User
     * @param password Password
     * @param baseUrl Base URL to confluence API
     * @param pageId Page ID
     * @param newBody New Body to publish to Confluence
     * @return HTTP result from Confluence
     */
    def publishToConfluence(String user, String password, String baseUrl, String pageId, String newBody) {
        if (baseUrl && baseUrl[baseUrl.length()-1] != '/')
            baseUrl = baseUrl + "/"

        def startTime = LocalDateTime.now()
        def client = new RESTClient()
        client.setHeaders([Authorization: "Basic " + "${user}:${password}".getBytes().encodeBase64().toString()])

        def response

        try {
            response = client.get([uri: "${baseUrl}content/${pageId}"])
        } catch (HttpResponseException e) {
            if (e?.response?.status == 404) {
                logger.error("Page not found")
                return null
            } else {
                logger.error("Exception encountered :", e)
                throw e
            }
        }

        if (response && response?.getStatus() == 200 && response?.getData()) {
            def data = response.getData()
            def newVersion = (((int)data?.version?.number) ?: 0) + 1

            def body = [
                    id: pageId,
                    version: [number: newVersion],
                    type: data?.type ?: "page",
                    title: data?.title ?: "New Page",
                    body: [storage: [value: newBody, representation: "storage"]]
            ]

            try {
                response = client.put([uri: "${baseUrl}content/${pageId}", requestContentType: ContentType.JSON, body: body])
                logger.info("Uploading to Confluence took " + (ChronoUnit.MILLIS.between(startTime, LocalDateTime.now())/1000)  + " seconds")
                return response.getData()
            } catch (HttpResponseException e) {
                logger.error("Exception message: " + e?.response?.getData()?.message ?: e.getMessage())
                throw e
            }
        }
    }
}