# swagger-confluence

Tool for exporting Swagger to Confluence.

Current options:

- Use a template to convert Swagger into the desired format
- Programmatically write the conversion specifications
- Upload results to Confluence API
- Basic XHTML template for Confluence API provided

## Building

``gradle clean build``

## Usage

### Running from the Command line

The grade build produces two jar files, a regular jar file and a "fat jar" that includes all dependencies.
 
The "fat jar" can be run like so:

``java -jar build/libs/swagger-confluence-1.0-SNAPSHOT-all.jar``

### Running from a Groovy script

Running from a groovy script gives you some advantages including the ability to include additional closures into your 
code (which gives you more parsing options).

See scripts\Example.groovy for an example