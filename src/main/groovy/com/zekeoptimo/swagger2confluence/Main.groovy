package com.zekeoptimo.swagger2confluence

import com.zekeoptimo.swagger2confluence.markup.impl.TextFileMarkup
import com.zekeoptimo.swagger2confluence.markup.impl.ConfluenceApiMarkup
import com.zekeoptimo.swagger2confluence.parser.SwaggerParserEngine

class Main {
    static void main(String... args) {
        def markupInterface, template, source, confluenceUser, confluencePassword, conflueceApiUrl, pageId

        def cli = new CliBuilder(usage: 'swagger-confluence [-h] [-mt] [-mc] [-t template] [-f swagger file | -u swagger url] [-o output file] [-cuser confluence user] [-cpwd confluence password] [-curl confluence api url] [-cpage confluence page id]')

        cli.with {
            h longOpt: 'help', 'Show usage information'
            mt longOpt: 'markup-text', 'Use text file markup'
            mc longOpt: 'markup-confluence-api', 'Use confluence API markup (default)'
            t longOpt: 'template', args: 1, argName: 'template', 'Template file'
            f longOpt: 'file', args: 1, argName: 'file', 'Swagger file in JSON format'
            u longOpt: 'url', args: 1, argName: 'url', 'Swagger url in JSON format'
            o longOpt: 'output-file', args: 1, argName: 'file', 'Output file'
            cuser longOpt: 'confluence-user', args: 1, argName: 'user', 'Confluence API user'
            cpwd longOpt: 'confluence-password', args: 1, argName: 'password', 'Confluence API password'
            curl longOpt: 'confluence-url', args: 1, argName: 'url', 'Confluence API url'
            cpage longOpt: 'confluence-page-id', args: 1, argName: 'id', 'Confluence Page ID'
        }

        def options = cli.parse(args)

        if (options.h) {
            cli.usage()
            return
        }

        if (options.mt)
            markupInterface = new TextFileMarkup()
        else
            markupInterface = new ConfluenceApiMarkup()

        if (options.t)
            template = new File(options.t)

        if (options.f)
            source = new File(options.f)

        if (options.u)
            source = new URL(options.u)

        if (options.cuser)
            confluenceUser = options.cuser

        if (options.cpwd)
            confluencePassword = options.cpwd

        if (options.curl)
            conflueceApiUrl = options.curl

        if (options.cpage)
            pageId = options.cpage

        if (!template || !source) {
            cli.usage()
            return
        }

        println "Loading engine ..."
        def engine = new SwaggerParserEngine(source)

        println "Parsing swagger ..."
        def result = engine.parse(markupInterface, template)

        if (confluenceUser && confluencePassword && conflueceApiUrl && pageId) {
            println "Uploading to confluence ..."
            result = engine.publishToConfluence(confluenceUser, confluencePassword, conflueceApiUrl, pageId, result)
            println " "
            println "Confluence returned the following result:"
            println " "
            println result
        } else {
            if (options.o) {
                println 'Writing to file ' + options.o
                def output = new File(options.o)
                output.write(result)
            } else
                println result
        }
    }
}
