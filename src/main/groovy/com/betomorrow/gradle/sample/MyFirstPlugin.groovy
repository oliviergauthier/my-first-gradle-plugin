package com.betomorrow.gradle.sample

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.ajoberstar.grgit.Grgit

class MyFirstPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.with {
            task("info", description: "Show project information", group : "other") {
                doLast {

                    def grgit = Grgit.open(currentDir : "..")

                    println("Project Informations : ")
                    println("- Name : ${project.name}")
                    println("- Version : ${project.version}")
                    println("- Revision :${grgit.log().first().getAbbreviatedId()}")
                }
            }
        }
    }
}
