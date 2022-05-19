import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    private const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val support = "androidx.legacy:legacy-support-v4:${Versions.support}"
    private const val annotationLibrary = "androidx.annotation:annotation:${Versions.annotations}"

    val standardLibs = arrayListOf<String>().apply {
        add(stdLib)
        add(coreKtx)
        add(appCompat)
        add(material)
        add(constraintLayout)
        add(support)
        add(annotationLibrary)
    }

    private const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycle}"
    private const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.lifeCycle}"
    private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
    private const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
    private const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycle}"
    private const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifeCycle}"

    val lifecycleLibs = arrayListOf<String>().apply {
        add(lifecycleJava8)
        add(lifecycleRuntime)
        add(viewModel)
        add(liveData)
        add(savedState)
    }

    // Fragment and activity
    private const val fragmentRuntime = "androidx.fragment:fragment:${Versions.fragment}"
    private const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    private const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"

    val activityFragmentKtxLibs = arrayListOf<String>().apply {
        add(fragmentRuntime)
        add(fragmentKtx)
        add(activityKtx)
    }

    // UI Dependencies
    private const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    private const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    private const val glideRuntime = "com.github.bumptech.glide:glide:${Versions.glide}"
    private const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    val uILibs = arrayListOf<String>().apply {
        add(cardView)
        add(recyclerView)
        add(glideRuntime)
    }

    // Navigation
    private const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    private const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

    val navigationLibs = arrayListOf<String>().apply {
        add(navigationUIKtx)
        add(navigationFragmentKtx)
    }

    // Retrofit
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val okHttpLoging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLogging}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"

    val retrofitLibs = arrayListOf<String>().apply {
        add(retrofit)
        add(retrofitGson)
        add(okHttpLoging)
        add(gson)
    }

    // Persistence
    private const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"
    private const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    private const val roomArchCompiler = "android.arch.persistence.room:compiler${Versions.room}"

    val roomLibs = arrayListOf<String>().apply {
        add(dataStore)
        add(roomRuntime)
        add(roomKtx)
    }

    // Dependency injection
    private const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    private const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    val diLibs = arrayListOf<String>().apply {
        add(hiltAndroid)
    }

    // Coroutines
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    val coroutinesLibs = arrayListOf<String>().apply {
        add(coroutinesCore)
        add(coroutines)
    }

    val compilerLibs = arrayListOf<String>().apply {

        add(roomCompiler)
        add(hiltCompiler)
        add(lifecycleCompiler)
        add(glideCompiler)
    }
}

fun DependencyHandler.annotationProcessor(list: List<String>) {
    list.forEach { dependency ->
        add("annotationProcessor", dependency)
    }
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}