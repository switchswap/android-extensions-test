package com.example.extensionstest

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import com.example.extensionstest.extensionstestlib.Extension
import com.example.extensionstest.extensionstestlib.Source
import dalvik.system.PathClassLoader
import kotlinx.coroutines.*

object ExtensionLoader {
    /**
     * Returns a list of all the currently installed extensions
     */
    fun loadSources(context: Context): List<Source> {
        val packageManager = context.packageManager
        val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_CONFIGURATIONS)
        val extPackages = installedPackages.filter { isPackageAnExtension(it) }

        val extensions: List<Extension> = extPackages.mapNotNull { pkg -> loadExtension(context, pkg) }

        return extensions.flatMap { it -> it.sources }
    }

    fun loadExtension(context: Context, packageInfo: PackageInfo): Extension? {
        // Get relevant info from package
        val packageManager = context.packageManager
        val appInfo = packageInfo.applicationInfo

        // Path class loader to limit app to loading only installed apps
        val classLoader = PathClassLoader(appInfo.sourceDir, null, context.classLoader)
        val loadedClass = Class.forName("${packageInfo.packageName}.SampleExtension", false, classLoader).newInstance()
        if (loadedClass is Extension) {
           return loadedClass
        }

        return null;
    }

    private fun isPackageAnExtension(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.reqFeatures.orEmpty().any { it.name == "extensionstest.extension" }
    }

//    fun testFindAnimeInExtension(extension: Extension) {
//        val anime = Anime(
//            provider = "",
//            provider_anime_id = 0,
//            titleNative = "",
//            titleEnglish = "",
//            titleRomaji = "Kobayashi-san Chi no Maid Dragon",
//            coverUrl = "",
//            bannerUrl = "",
//            description = "",
//            status = AnimeStatus.FINISHED,
//            episodeCount = 13,
//            rating = 7.75f,
//            tags = emptyList<String>(),
//        )
//
//        GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT) {
//            extension.sources.firstOrNull()?.findAnimeEntryInSource(anime)
//        }
//    }
}