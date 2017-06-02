package com.zekeoptimo.swagger2confluence.model

import com.zekeoptimo.swagger2confluence.parser.SwaggerParser

/**
 * Parse line definition
 *
 */
class ParseLine {
    private final Object line
    private final Boolean parse
    private final Closure data
    private final Closure sort
    private final Closure filter

    /**
     * Construct a detail line with optional parsing
     *
     * @param line String
     * @param parse Parse results?
     */
    ParseLine(String line, Boolean parse) {
        this.line = line
        this.parse = parse
        this.data = null
        this.sort = null
        this.filter = null
    }

    /**
     * Construct a detail line from a closure
     *
     * @param line Closure line
     */
    ParseLine(Closure line) {
        this.line = line
        this.parse = false
        this.data = null
        this.sort = null
        this.filter = null
    }

    /**
     * Construct a sub parser line
     *
     * @param parser Parser
     * @param data Data
     * @param sort Sort
     * @param filter Filter
     */
    ParseLine(SwaggerParser parser, Closure data, Closure sort, Closure filter) {
        this.line = parser
        this.parse = null
        this.data = data
        this.sort = sort
        this.filter = filter
    }

    Object getLine() {
        return line
    }

    Boolean getParse() {
        return parse
    }

    Closure getData() {
        return data
    }

    Closure getSort() {
        return sort
    }

    Closure getFilter() {
        return filter
    }
}