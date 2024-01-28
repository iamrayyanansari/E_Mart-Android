buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()

//        maven {
//            url = uri( "https://artifactory.paytm.in/libs-release-local")
//        }
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.5")
        classpath("com.android.tools.build:gradle:8.1.4")


        // Add other dependencies as needed
    }
}


