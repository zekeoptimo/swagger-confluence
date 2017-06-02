package com.zekeoptimo.swagger2confluence.markup.impl

import com.zekeoptimo.swagger2confluence.markup.MarkupInterface
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Confluence API interface
 */
class TextFileMarkup implements MarkupInterface {

    private static final Logger logger = LoggerFactory.getLogger(TextFileMarkup.class)

    TextFileMarkup() {
        // Register escape
        Object.metaClass.escape = { -> escape(delegate) }
        Object.metaClass.parseType = { -> parseType(delegate) }
    }

    String line(String text) {
        return text + "\n"
    }

    String preProcessing(String text) {
        return text
    }

    String postProcessing(String text) {
        return text
    }

    static String escape(String text) {
        return text
    }

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
