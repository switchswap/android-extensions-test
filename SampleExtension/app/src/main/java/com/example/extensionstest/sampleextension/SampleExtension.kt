package com.example.extensionstest.sampleextension

import com.example.extensionstest.extensionstestlib.IExtension

class SampleExtension: IExtension {
    override fun test1(): String = "Hello world!"
    override fun test2(): Int = 17
    override fun test3(): Boolean = true
}