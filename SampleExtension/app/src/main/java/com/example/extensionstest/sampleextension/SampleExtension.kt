package com.example.extensionstest.sampleextension

import com.example.extensionstest.extensionstestlib.*


class SampleExtension: Extension {
    override val metadata = ExtensionMetadata(
        name="Sample Extension"
    )

    override val sources = listOf(SampleSource())
}






