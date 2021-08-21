package com.example.extensionstest.extensionstestlib

// additional info for an extension
data class ExtensionMetadata(
    val name: String
)

// Interface definition for an extension
interface Extension {
    // required metadata for the extension
    val metadata: ExtensionMetadata

    // provides a list of all sources of this extension
    val sources: List<Source>
}