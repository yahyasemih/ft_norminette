plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.11.0"
}

group = "ma.leet"
version = "1.0"

repositories {
    mavenCentral()
}

intellij {
    version.set("2022.2.1")
    type.set("CL") // Target IDE Platform

    plugins.set(listOf(
        "com.intellij.clion",
        "com.intellij.cidr.base",
        "com.intellij.cidr.lang",
        "com.intellij.cidr.debugger"))
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    patchPluginXml {
        sinceBuild.set("222")
        untilBuild.set("223.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
