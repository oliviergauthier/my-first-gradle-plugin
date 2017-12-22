package com.betomorrow.gradle.sample.tasks

import com.betomorrow.gradle.sample.metadata.ProjectMetadata
import com.betomorrow.gradle.sample.metadata.ProjectMetadataBuilder
import com.betomorrow.gradle.sample.metadata.ProjectMetadataJsonFormatter
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskAction

class InfoTask extends DefaultTask {

    Property<String> filePath = project.objects.property(String)
    Property<Integer> logSize = project.objects.property(Integer)

    void setFilePath(String value) {
        filePath.set(value)
    }

    void setLogSize(int value) {
        logSize.set(value)
    }

    @TaskAction
    void generateFile() {

        ProjectMetadata metadata = new ProjectMetadataBuilder()
                                            .withName(project.name)
                                            .withVersion(project.version.toString())
                                            .withLogSize(logSize.get())
                                            .withProjectDir(project.rootDir.absolutePath)
                                            .build()

        String content = new ProjectMetadataJsonFormatter().format(metadata)

        project.file(filePath.get()).withWriter {
            it.write(content)
        }
    }

}
