package com.intellij.advancedExpressionFolding.processor.core

import com.intellij.advancedExpressionFolding.folding.BaseTest
import com.intellij.advancedExpressionFolding.processor.language.kotlin.MethodDefaultParameterExt
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiMethodCallExpression
import com.intellij.psi.impl.FakePsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.DumbModeTestUtils
import com.intellij.testFramework.runInEdtAndWait
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FoldPassRobustnessTest : BaseTest() {

    private var getSetBefore = false

    @BeforeEach
    fun rememberOptions() {
        getSetBefore = state.getSetExpressionsCollapse
        state.getSetExpressionsCollapse = true
        rebuildMethodCallFactory()
    }

    @AfterEach
    fun restoreOptions() {
        state.getSetExpressionsCollapse = getSetBefore
    }

    @Test
    fun defaultParameterPass_methodsWithoutOverloads_resolvesNoCall() {
        // GIVEN - one-statement methods that call into the JDK, none of them overloaded
        val shop = configureShop()

        // WHEN / THEN - the class-level default-parameter pass runs while no index is available, and never resolves a
        // call (in dumb mode a resolve into the JDK needs the index and throws)
        assertDoesNotThrow {
            DumbModeTestUtils.runInDumbModeSynchronously(fixture.project) {
                ReadAction.run<Throwable> { MethodDefaultParameterExt.enhanceMethodsWithDefaultParams(shop) }
            }
        }
    }

    @Test
    fun collectFoldRegions_elementFailsToBuild_foldsItsSiblings() {
        // GIVEN - a getter call next to an element whose fold cannot be built
        val shop = configureShop()
        val root = ReadAction.compute<ParentOf, Throwable> {
            val getterCall = PsiTreeUtil.findChildrenOfType(shop, PsiMethodCallExpression::class.java)
                .single { it.methodExpression.referenceName == "getName" }
            ParentOf(shop.containingFile, listOf(FailingElement(), getterCall))
        }
        val descriptors = mutableListOf<FoldingDescriptor>()

        // WHEN - the fold pass walks both
        ReadAction.run<Throwable> {
            BuildExpressionExt.collectFoldRegionsRecursively(root, fixture.editor.document, mutableSetOf(), descriptors)
        }

        // THEN - the getter still folds
        val placeholders = descriptors.map { it.placeholderText }
        assertTrue("n" in placeholders, "placeholders: $placeholders")
    }

    private fun configureShop(): PsiClass {
        lateinit var shop: PsiClass
        runInEdtAndWait {
            val file = fixture.configureByText(
                "Shop.java",
                """
                import java.util.List;
                class Shop {
                    private final List<String> items = List.of();
                    private String name;
                    String getName() { return name; }
                    int count() { return items.size(); }
                    String first() { return items.getFirst(); }
                    String show(Shop shop) { return shop.getName(); }
                }
                """.trimIndent()
            ) as PsiJavaFile
            shop = file.classes.single()
        }
        return shop
    }

    private class ParentOf(private val file: PsiFile, private val kids: List<PsiElement>) : FakePsiElement() {
        override fun getParent(): PsiElement = file
        override fun getChildren(): Array<PsiElement> = kids.toTypedArray()
    }

    private class FailingElement : FakePsiElement() {
        override fun getParent(): PsiElement? = null
        override fun <T : Any?> getUserData(key: Key<T>): T? = throw NoClassDefFoundError("broken IDE class")
    }
}
