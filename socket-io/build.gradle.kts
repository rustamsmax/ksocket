/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.kotlinAndroidExtensions)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mavenPublish)
    id("org.jetbrains.kotlin.plugin.serialization") version (Deps.kotlinVersion)
}

group = "com.rsteam"
version = Deps.mokoSocketIoVersion



dependencies {
//    commonMainImplementation(Deps.Libs.MultiPlatform.serialization)
    commonMainImplementation(Deps.Libs.MultiPlatform.serializationJson)

    androidMainImplementation(Deps.Libs.Android.appCompat)
   /* androidMainImplementation(Deps.Libs.Android.socketIo) {
        exclude(group = "org.json", module = "json")
    }*/
}

kotlin {

    jvm("desktop")

    sourceSets {

        val jvmCommonMain by creating {
            dependsOn(getByName("commonMain"))
            dependencies {
                implementation(Deps.Libs.Android.socketIo) {
                    exclude(group = "org.json", module = "json")
                }
            }
        }

        val androidMain by getting {
            dependsOn(getByName("jvmCommonMain"))
        }
        val desktopMain by getting {
            dependsOn(getByName("jvmCommonMain"))
        }
    }
}

publishing {
    repositories.maven("https://api.bintray.com/maven/rs-team/rs-socket/ksocket/;publish=1") {
        name = "bintray"

        credentials {
            username = System.getProperty("BINTRAY_USER")
            password = System.getProperty("BINTRAY_KEY")
        }
    }
}

cocoaPods {
    podsProject = file("../sample/ios-app/Pods/Pods.xcodeproj")

    pod("mokoSocketIo")
}
