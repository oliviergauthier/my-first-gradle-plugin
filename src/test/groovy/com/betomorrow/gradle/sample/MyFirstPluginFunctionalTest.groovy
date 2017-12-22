package com.betomorrow.gradle.sample

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class MyFirstPluginFunctionalTest extends Specification {

    // See https://docs.gradle.org/4.0/userguide/test_kit.html

    @Rule final TemporaryFolder testProjectDir = new TemporaryFolder()
    File buildFile



    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "info tasks generate metadata file"() {
        given:
        buildFile << """
            plugins {
                id 'com.betomorrow.my-first-gradle-plugin'
            }

            version = "1.0-SNAPSHOT"
            
            info {
                filename = "package.json"
                logSize = 1
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withDebug(true)
                .withArguments('info')
                .withPluginClasspath()
                .build()

        then:
            result.task(":info").outcome == TaskOutcome.SUCCESS
    }

    def "hello world task prints hello world"() {
        given:
        buildFile << """
            task helloWorld {
                doLast {
                    println 'Hello world!'
                }
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir.root)
                .withArguments('helloWorld')
                .build()

        then:
        result.output.contains('Hello world!')
        result.task(":helloWorld").outcome == TaskOutcome.SUCCESS
    }



}
