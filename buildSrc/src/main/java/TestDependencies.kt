import org.gradle.api.artifacts.dsl.DependencyHandler

object TestDependencies {
    private const val junit = "junit:junit:${Versions.junit}"
    private const val testCoreKtx =  "androidx.test:core-ktx:${Versions.testCore}"
    private const val junit4Ktx = "androidx.test.ext:junit-ktx:${Versions.junitKtx}"
    private const val testCore = "androidx.test:core:${Versions.testCore}"
    private const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito}"
    private const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    private const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    private const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    private const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    private const val roboelectric = "org.robolectric:robolectric:${Versions.roboelectric}"

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(testCoreKtx)
        add(junit4Ktx)
        add(mockitoKotlin)
        add(mockitoCore)
        add(coroutinesTest)
        add(coreTesting)
        add(mockWebServer)
        add(testCore)
        add(roboelectric)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}