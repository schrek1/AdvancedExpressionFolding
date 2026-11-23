package com.intellij.advancedExpressionFolding.folding.base.folding

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.folding.util.TestDynamicDataProvider
import com.intellij.advancedExpressionFolding.processor.methodcall.dynamic.IDynamicDataProvider
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.openapi.roots.libraries.LibraryUtil
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess
import com.intellij.testFramework.PsiTestUtil
import com.intellij.testFramework.runInEdtAndWait
import com.intellij.util.PathUtil
import java.io.File
import kotlin.reflect.KMutableProperty0

internal fun foldingState(): State = State()()

private const val LOMBOK_LIBRARY = "lombok"

interface FoldingTestSection {
    val testCase: FoldingFeatureTestCase
}

abstract class FoldingFeatureTestCase : BaseTest(), FoldingTestSection {
    final override val testCase: FoldingFeatureTestCase
        get() = this

    fun runFoldingTest(
        vararg turnOnProperties: KMutableProperty0<Boolean>,
        dynamic: IDynamicDataProvider = TestDynamicDataProvider(),
    ) {
        doFoldingTest(*turnOnProperties, dynamic = dynamic)
    }

    /** Adds library classes the test file resolves against; the light test project has only the JDK. */
    fun addLibraryStubs(vararg classSources: String) = runInEdtAndWait {
        classSources.forEach { fixture.addClass(it) }
    }

    /**
     * Runs `action` with the real lombok jar attached to the light test module, so the bundled Lombok plugin
     * generates its PSI. It only switches on for a library holding `lombok.Getter`; source stubs are not enough.
     */
    fun withLombokLibrary(action: () -> Unit) {
        val jar = File(PathUtil.getJarPathForClass(Class.forName("lombok.Getter")))
        VfsRootAccess.allowRootAccess(fixture.testRootDisposable, jar.parent)
        runInEdtAndWait { PsiTestUtil.addLibrary(fixture.module, LOMBOK_LIBRARY, jar.parent, jar.name) }
        try {
            action()
        } finally {
            runInEdtAndWait {
                LibraryUtil.findLibrary(fixture.module, LOMBOK_LIBRARY)?.let { PsiTestUtil.removeLibrary(fixture.module, it) }
            }
        }
    }

    fun runReadOnlyFoldingTest(
        vararg turnOnProperties: KMutableProperty0<Boolean>,
        dynamic: IDynamicDataProvider = TestDynamicDataProvider(),
    ) {
        doReadOnlyFoldingTest(*turnOnProperties, dynamic = dynamic)
    }
}
