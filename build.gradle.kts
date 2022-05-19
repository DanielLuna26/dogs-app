// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.gradle)
        classpath(BuildPlugins.gradlePlugin)
        classpath(BuildPlugins.safeArgs)
        classpath(BuildPlugins.hiltDagger)

        // NOTE: Do not place your application dependencies here; they belong
    }
}

task<Delete> ("clean") {
    delete(rootProject.buildDir)
}