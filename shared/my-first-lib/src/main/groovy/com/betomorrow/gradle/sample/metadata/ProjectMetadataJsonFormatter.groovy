package com.betomorrow.gradle.sample.metadata

import groovy.json.JsonBuilder

class ProjectMetadataJsonFormatter {

    String format(ProjectMetadata metadata) {
        def builder = new JsonBuilder()

        def root  = builder.metadata {}
        root.metadata.name = metadata.name
        root.metadata.version = metadata.version
        root.metadata.buildDate = metadata.buildDate.toString()
        root.metadata.revision = metadata.revision
        root.changelog = metadata.changelog

        return builder.toPrettyString()
    }

}
