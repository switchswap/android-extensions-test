package com.example.extensionstest

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import com.example.extensionstest.extensionstestlib.IExtension
import dalvik.system.PathClassLoader

object ExtensionLoader {
    /**
     * Returns a list of all the currently installed extensions
     */
    @SuppressLint("QueryPermissionsNeeded")
    fun findExtensions(context: Context): List<ExtensionItem> {
        val packageManager = context.packageManager
        val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_CONFIGURATIONS)
        val extPackages = installedPackages.filter { isPackageAnExtension(it) }

        return extPackages.map {
            loadExtension(context, it)
        }
    }

    private fun loadExtension(context: Context, packageInfo: PackageInfo): ExtensionItem {
        // Get relevant info from package
        val packageManager = context.packageManager
        val appInfo = packageInfo.applicationInfo
        val extensionName = packageManager.getApplicationLabel(appInfo).toString().substringAfter("SampleExtension: ")
        val versionName = packageInfo.versionName
        val versionCode = packageInfo.versionCode

        // Path class loader to limit app to loading only installed apps
        val classLoader = PathClassLoader(appInfo.sourceDir, null, context.classLoader)
        val loadedClass = Class.forName("${packageInfo.packageName}.SampleExtension", false, classLoader).newInstance()
        if (loadedClass is IExtension) {
            // We can now use the reflected class from the extension!
//            Log.d("ASDF", loadedClass.test1())
            return ExtensionItem.Loaded(extensionName, loadedClass)
        }
        return ExtensionItem.Invalid(extensionName)
    }

    private fun isPackageAnExtension(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.reqFeatures.orEmpty().any { it.name == "extensionstest.extension" }
    }
}