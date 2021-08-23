package com.example.extensionstest

import android.content.Context

class ExtensionManager(private val context: Context) {
    var extensions = emptyList<ExtensionItem>()
    var loadedExtensions = emptyList<ExtensionItem.Loaded>()

    fun initExtensions() {
        extensions = ExtensionLoader.findExtensions(context)
        loadedExtensions = extensions.filterIsInstance<ExtensionItem.Loaded>()
    }

    init {
        initExtensions()
    }
}