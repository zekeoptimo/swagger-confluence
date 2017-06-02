package com.zekeoptimo.swagger2confluence.markup.impl

import com.zekeoptimo.swagger2confluence.markup.MarkupInterface
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Confluence API interface
 */
class ConfluenceApi implements MarkupInterface {

    private static final Logger logger = LoggerFactory.getLogger(ConfluenceApi.class)

    ConfluenceApi() {
        // Register escape
        Object.metaClass.escape = { -> escape(delegate) }
        Object.metaClass.parseType = { -> parseType(delegate) }
    }

    String line(String text) {
        return text
    }

    String preProcessing(String text) {
        return text
    }

    /**
     * Post-processing - convert definitions links
     *
     * @param text
     * @return
     */
    String postProcessing(String text) {
        if (text != null)
            text = text.replaceAll(/\#\/definitions\/([A-Za-z0-9_]+)/) { _, match ->
                '<ac:link ac:anchor="' + match + '"><ac:plain-text-link-body><![CDATA[' + match + ']]></ac:plain-text-link-body></ac:link>'
            }

        return text
    }
/**
     * String escaping for Confluence API
     *
     * @param text String to escape
     * @return Escaped string
     */
    static String escape(String text) {
        if (text != null)
            return text.toString()
                    .replace('&', '&amp;')
                    .replace('>', '&gt;')
                    .replace('<', '&lt;')
                    .replace('\t', ' ')

        return null
    }

    /**
     * Default implementation of type parsing to standardize the way types are displayed. This handles many situations
     * and types of classes that might contain type information.
     *
     * @param object Any object with type information
     * @return String
     */
    static String parseType(Object object) {
        if (!object) return null

        // If the object is a Map or Map entry parse it as such, otherwise
        // get the properties of the
        if (object instanceof Map.Entry) return parseType(object.value)

        if (!(object instanceof Map)) return parseType(object?.properties)

        if (object?.schema) return parseType(object.schema)

        if (object?.enums && object.enums instanceof List)
            return "enum " + object.enums.toString()

        if (object?.type && object?.items)
            return object.type + ' [' + parseType(object.items) + ']'

        if (object?.ref) return object.ref
        if (object?.containsKey('$ref')) return object['$ref']
        if (object?.type) return object.type

        logger.info("Unable to determine type for object " + object.getClass() + ": " + object)

        return null
    }
}
