import org.gradle.api.artifacts.dsl.DependencyHandler

object AndroidTestDependencies {
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    private const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    private const val testCoreKtx =  "androidx.test:core-ktx:${Versions.testCore}"
    private const val junit4Ktx = "androidx.test.ext:junit-ktx:${Versions.junitKtx}"
    private const val testCore = "androidx.test:core:${Versions.testCore}"
    private const val roomTest = "androidx.room:room-testing:${Versions.room}"

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(coreTesting)
        add(coroutinesTest)
        add(testCore)
        add(testCoreKtx)
        add(junit4Ktx)
        add(roomTest)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}