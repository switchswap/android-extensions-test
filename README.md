# android-extensions-test
Test repository for research into creating extensible android apps!

## ExtensionsTest
This is the base app that loads extensions.

## SampleExtension
This is a sample extension. It has no activities and won't show up in an app drawer. It does, however, contain code that the base app can use via reflection.

## ExtensionsTestLib
This class library module is a dependency in both the SampleExtension and ExtensionsTest projects. It allows the reflected class to be casted used in a cleaner fashion than with reflection syntax.
