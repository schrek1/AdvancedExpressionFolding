package com.intellij.advancedExpressionFolding.processor.methodcall

import com.intellij.advancedExpressionFolding.processor.methodcall.dynamic.ConfigurationParser
import com.intellij.advancedExpressionFolding.processor.methodcall.dynamic.DynamicMethodCall
import com.intellij.advancedExpressionFolding.processor.methodcall.dynamic.IDynamicDataProvider
import com.intellij.advancedExpressionFolding.processor.takeIfTrue
import com.intellij.advancedExpressionFolding.processor.util.Consts
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.advancedExpressionFolding.settings.state.IGlobalSettingsState

typealias MethodName = String
typealias ClassName = String

/**
 * Thread-safe singleton factory for method call folding definitions.
 *
 * **Threading Model:**
 * - Uses @Volatile fields for lock-free reads (frequent folding operations)
 * - Uses synchronized for atomic multi-field updates (rare configuration changes)
 * - This dual approach optimizes for the read-heavy/write-rare access pattern
 * - Direct @Volatile field access is faster than AtomicReference.get() for reads
 *
 * **Why both @Volatile AND synchronized:**
 * - @Volatile: Ensures immediate visibility of updates to all reader threads
 * - synchronized: Ensures atomicity when updating multiple related fields together
 * - Reader threads (folding builders) never block and always see consistent state
 * - Writer threads (configuration changes) update all fields atomically
 *
 * **Lifecycle:**
 * 1. Lazy initialization during first PsiMethodCallExpression folding
 * 2. Background reads during method call folding operations
 * 3. Rebuilt on the first read after a settings change switches a method-call fold on or off
 * 4. Runtime updates via AddDynamicMethodFoldingIntention
 *
 * **Performance characteristics:**
 * - Reads: a check of the method-call options, then an O(1) HashMap lookup; no lock while settings are unchanged
 * - Writes: O(n) reconstruction of all mappings, synchronized but infrequent
 */
object MethodCallFactory : IGlobalSettingsState by State()() {

    @Volatile
    private var dynamicProvider: IDynamicDataProvider? = ConfigurationParser

    /**
     * The mappings built for one combination of settings: `enabledOptions` holds `canExecute()` of each
     * [MethodCallManager.methodCalls] entry, then `dynamic`.
     */
    private class Mappings(
        val enabledOptions: BooleanArray,
        val methodCallMap: Map<MethodName?, List<AbstractMethodCall>>,
        val supportedClasses: Collection<ClassName>,
        val supportedMethods: Collection<MethodName>,
        val classlessMethods: Collection<MethodName>,
    )

    @Volatile
    private var mappings = Mappings(BooleanArray(0), emptyMap(), emptyList(), emptyList(), emptyList())

    val supportedClasses: Collection<ClassName> get() = currentMappings().supportedClasses
    val supportedMethods: Collection<MethodName> get() = currentMappings().supportedMethods
    val classlessMethods: Collection<MethodName> get() = currentMappings().classlessMethods

    fun refreshMethodCallMappings(dynamicProvider: IDynamicDataProvider? = null) {
        synchronized(this) {
            if (dynamicProvider != null) {
                // for tests
                this.dynamicProvider = dynamicProvider
            }
            mappings = createMappings()
        }
    }

    // A settings change (Settings Apply, settings sync, tests) only flips the options, so every read checks them.
    private fun currentMappings(): Mappings {
        mappings.takeIf { it.matchesSettings() }?.let { return it }
        synchronized(this) {
            if (!mappings.matchesSettings()) {
                mappings = createMappings()
            }
            return mappings
        }
    }

    private fun Mappings.matchesSettings(): Boolean {
        val methodCalls = MethodCallManager.methodCalls
        return enabledOptions.size == methodCalls.size + 1 &&
            enabledOptions[methodCalls.size] == dynamic &&
            methodCalls.indices.all { enabledOptions[it] == methodCalls[it].canExecute() }
    }

    private fun createMappings(): Mappings {
        val methodCalls = MethodCallManager.methodCalls
        // read before building, so a change made meanwhile is caught by the next read
        val enabledOptions = BooleanArray(methodCalls.size + 1) { index ->
            if (index < methodCalls.size) methodCalls[index].canExecute() else dynamic
        }
        val methodCallMap = createMethodCalls(methodCalls)
        return Mappings(
            enabledOptions = enabledOptions,
            methodCallMap = methodCallMap,
            supportedClasses = createSupportedClasses(methodCallMap),
            supportedMethods = createSupportedMethods(methodCallMap),
            classlessMethods = createClasslessMethods(methodCallMap),
        )
    }

    fun initialize(dynamicProvider: IDynamicDataProvider? = ConfigurationParser): MethodCallFactory {
        if (dynamicProvider != null) {
            this.dynamicProvider = dynamicProvider
        }
        refreshMethodCallMappings()
        return this
    }

    private fun createMethodCalls(methodCalls: List<AbstractMethodCall>): Map<MethodName?, List<AbstractMethodCall>> =
        (methodCalls + loadDynamicMethods().orEmpty()).filter {
            it.canExecute()
        }.flatMap { methodCall ->
            methodCall.methodNames.map { methodName ->
                methodName to methodCall
            }
        }.groupBy(
            { it.first },
            { it.second }
        ).mapValues { entry ->
            entry.value.sortedWith(compareBy {
                it.classNames.isEmpty()
            })
        }

    private fun loadDynamicMethods(): List<DynamicMethodCall>? = dynamic.takeIfTrue()?.let { dynamicProvider?.parse() }

    private fun createSupportedClasses(methodCallMap: Map<MethodName?, List<AbstractMethodCall>>): Collection<ClassName> =
        methodCallMap.values
            .asSequence()
            .flatten()
            .filter {
                it.classNames.isNotEmpty()
            }.map {
                it.classNames
            }.distinct().flatten()
            .toList()

    private fun createSupportedMethods(methodCallMap: Map<MethodName?, List<AbstractMethodCall>>): List<MethodName> =
        methodCallMap.values
            .flatten()
            .map {
                it.methodNames
            }.flatten()

    private fun createClasslessMethods(methodCallMap: Map<MethodName?, List<AbstractMethodCall>>): List<MethodName> =
        methodCallMap.values
            .flatten()
            .filter {
                it.classNames.isEmpty()
            }.map {
                it.methodNames
            }.flatten() + Consts.UNSUPPORTED_CLASSES_METHODS_EXCEPTIONS

    fun findByMethodName(methodName: MethodName?): List<AbstractMethodCall>? =
        currentMappings().methodCallMap[methodName]
}
