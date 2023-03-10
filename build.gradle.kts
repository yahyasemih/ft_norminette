plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.13.2"
}

group = "ma.leet"
version = "1.3"

repositories {
    mavenCentral()
}

intellij {
    version.set("2022.3.2")
    type.set("CL")

    plugins.set(listOf(
        "com.intellij.clion",
        "com.intellij.cidr.base",
        "com.intellij.cidr.lang"))
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
