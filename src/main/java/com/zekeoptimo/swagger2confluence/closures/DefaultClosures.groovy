package com.zekeoptimo.swagger2confluence.closures


/**
 * Default set of closures for use in templates
 */
class DefaultClosures {

    /**
     * Closure that finds paths and operations for a specific tag. This allows you to traverse the paths/operations
     * and have them grouped by their tag.
     */
    public final static Closure T_TAG_PATHS = {
        String tag = it.values.name
        def paths = [:]

        it.parent.paths.each { p ->
            p.value?.each { o ->
                if (o.value?.tags?.contains(tag)) {
                    if (!paths.containsKey(p.key))
                        paths.put(p.key, [:])
                    paths[p.key].put(o.key, o.value)
                }
            }
        }

        return paths
    }
}