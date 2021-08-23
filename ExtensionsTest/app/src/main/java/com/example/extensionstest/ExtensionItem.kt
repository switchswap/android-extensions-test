package com.example.extensionstest

import com.example.extensionstest.extensionstestlib.IExtension

sealed class ExtensionItem {
    data class Loaded(
        val name: String,
        val extension: IExtension
    ): ExtensionItem()

    data class Invalid(
        val name: String
    ): ExtensionItem()
}
