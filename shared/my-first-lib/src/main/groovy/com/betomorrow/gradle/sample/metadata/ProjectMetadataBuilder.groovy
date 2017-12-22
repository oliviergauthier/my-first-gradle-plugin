package com.betomorrow.gradle.sample.metadata

import org.ajoberstar.grgit.Grgit

import java.time.Instant

class ProjectMetadataBuilder {

    private String name
    private String version
    private int logSize
    private String projectDirectory


    ProjectMetadataBuilder withName(String name) {
        this.name = name
        return this
    }

    ProjectMetadataBuilder withVersion(String version) {
        this.version = version
        return this
    }

    ProjectMetadataBuilder withProjectDir(String projectDirectory) {
        this.projectDirectory = projectDirectory
        return this
    }

    ProjectMetadataBuilder withLogSize(int logSize) {
        this.logSize = logSize
        return this
    }


    ProjectMetadata build() {
        ProjectMetadata metadata = new ProjectMetadata()
        metadata.name = name
        metadata.version = version
        metadata.buildDate = Instant.now()

        try {
            def grgit = Grgit.open(dir: projectDirectory)
            metadata.revision = grgit.log().first().getAbbreviatedId()
            metadata.changelog = grgit.log(maxCommits: logSize).collect { it.fullMessage }
        } catch (Exception e) {
            print("Can't load repository " + e.getMessage())
        }

        return metadata

    }

}
