package com.zekeoptimo.swagger2confluence.parser

import com.zekeoptimo.swagger2confluence.markup.MarkupInterface
import com.zekeoptimo.swagger2confluence.model.ParseLine

import groovy.text.GStringTemplateEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Swagger Parser
 */
class SwaggerParser {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerParser.class)
    private static final engine = new GStringTemplateEngine()
    private final SwaggerParser parent
    private final MarkupInterface markupInterface
    private List<ParseLine> specs = []

    SwaggerParser(MarkupInterface markupInterface) {
        this.parent = null
        this.markupInterface = markupInterface
    }

    SwaggerParser(SwaggerParser parent, MarkupInterface markupInterface) {
        this.parent = parent
        this.markupInterface = markupInterface
    }

    /**
     * Add detail line in GString format
     *
     * @param text Detail line in groovy string format
     * @return this
     */
    SwaggerParser line(String text, boolean parse) {
        specs << new ParseLine(text, parse)
        return this
    }

    /**
     * Add detail line in Closure format
     *
     * @param closure Detail line in closure format
     * @return this
     */
    SwaggerParser line(Closure closure) {
        specs << new ParseLine(closure)
        return this
    }

    /**
     * Conditionally parse
     *
     * @param condition Condition to parse
     * @return SwaggerParser
     */
    SwaggerParser "if"(Closure condition) {
        traverse({ [it] }, null, condition)
    }

    /**
     * Parse a record
     *
     * @param bindings Bindings
     * @return output
     */
    String parse(Map bindings) {
        String output = ""
        if (specs) {

            // The root object won't have "values", so add it here
            if (!bindings.containsKey("values"))
                bindings.put("values", bindings)

            specs.each {
                if (it) {
                    def item = it.line
                    def parse = it.parse
                    def data = it.data
                    def sort = it.sort
                    def filter = it.filter

                    if (data && item instanceof SwaggerParser) {
                        def result = parseSub(bindings, (SwaggerParser) item, data, sort, filter)
                        if (result) output += result
                    } else if (item instanceof String) {
                        if (parse) {
                            try {
                                def template = engine.createTemplate(item)
                                def result = template.make(bindings)
                                if (result != null) output += markupInterface.line(result.toString())
                            } catch (MissingPropertyException e) {
                                logger.error("Missing property: " + e.getProperty() + ", Unable to parse line: " + item)
                            } catch (Exception e) {
                                logger.error("Exception: " + e.getMessage() + ", Unable to parse line: " + item)
                            }
                        } else {
                            if (item) output += markupInterface.line(item)
                        }
                    } else if (item instanceof Closure) {
                        def result = item.call(bindings)
                        if (result != null) output += markupInterface.line(result.toString())
                    }
                }
            }
        }

        return output
    }

    /**
     * Traverse a dataset
     *
     * @param data Data to traverse
     * @return SwaggerParser
     */
    SwaggerParser traverse(Closure data, Closure sort = null, Closure filter = null) {
        def parser = new SwaggerParser(this, this.markupInterface)
        specs << new ParseLine(parser, data, sort, filter)
        return parser
    }

    /**
     * Parse sub record
     *
     * @param bindings Bindings
     * @param subParser Sub Parser
     * @param data Data closure
     * @param sort Sort
     * @param filter Filter
     * @return output
     */
    private String parseSub(Map bindings, SwaggerParser subParser, Closure data, Closure sort, Closure filter) {
        def output = ""
        if (data) {
            def records = data.call(bindings)
            if (records) {
                if (records instanceof Map) {
                    def source = (Map) records
                    if (filter) source = source.findAll(filter)
                    if (sort) source = source.sort(sort)

                    source.each {
                        def b = ["parent": bindings, "me": it, "key": it.key, "values": (it.value instanceof Map ? it.value : it.value.properties)]
                        def result = subParser.parse(b)
                        if (result) output += result
                    }
                } else if (records instanceof List) {
                    def source = (List) records
                    if (filter) source = source.findAll(filter)
                    if (sort) source = source.sort(sort)

                    def listKey = 0
                    source.each {
                        def b = ["parent": bindings, "me": it, "key": listKey, "values": (it instanceof Map ? it : it.properties)]
                        def result = subParser.parse(b)
                        if (result) output += result
                        listKey++
                    }
                } else {
                    def listKey = 0
                    records.each {
                        def b = ["parent": bindings, "me": it, "key": listKey, "values": (it instanceof Map ? it : it.properties)]
                        def result = subParser.parse(b)
                        if (result) output += result
                        listKey++
                    }
                }
            }
        }

        return output
    }

    /**
     * Return to parent record
     *
     * @return parent record
     */
    SwaggerParser "return"() {
        return parent
    }

    MarkupInterface getMarkupInterface() {
        return markupInterface
    }
}