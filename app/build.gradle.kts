java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.hamcrest:hamcrest-library:1.3") // ✅ Add this for `instanceOf()` support
    testImplementation("org.mockito:mockito-core:4.11.0")
}

application {
    mainClass.set("a5.SpellChecker")
}

tasks.named<JavaExec>("run") {
    doFirst {
        // Manually parse arguments from either appArgs or args and strip surrounding quotes
        val rawArgs = project.findProperty("args")?.toString()
            ?.removeSurrounding("\"")
            ?: ""
        args = rawArgs.split("\\s+".toRegex()).filter { it.isNotBlank() }
    }
}

tasks.named<Test>("test") {
    useJUnit()
}

val collection: FileCollection = layout.files(
    "src/data/*.txt"
)

tasks.register("list") {
    val projectDirectory = layout.projectDirectory
    doLast {
        var srcDir: File? = null

        val collection = projectDirectory.files({
            srcDir?.listFiles()
        })

        srcDir = projectDirectory.file("src").asFile
        println("Contents of ${srcDir.name}")
        collection.map { it.relativeTo(projectDirectory.asFile) }.sorted().forEach { println(it) }

        srcDir = projectDirectory.file("src/data").asFile
        println("Contents of ${srcDir.name}")
        collection.map { it.relativeTo(projectDirectory.asFile) }.sorted().forEach { println(it) }
    }
}
