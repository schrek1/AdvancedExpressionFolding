package com.intellij.advancedExpressionFolding

import com.intellij.advancedExpressionFolding.inlay.VirtualLines
import com.intellij.ide.plugins.DynamicPluginListener
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.openapi.application.runReadAction
import com.intellij.openapi.editor.EditorFactory

/**
 * Lets the plugin be updated without an IDE restart.
 *
 * Before an unload it takes its listeners, inlays and PSI caches off every open editor: editors and PSI outlive the
 * plugin (Code Vision keeps even the PSI the unload replaces), so anything of ours left on them keeps the class loader
 * alive and the IDE asks for a restart. After a load it installs the virtual lines on the editors that were already
 * open, which never see `editorCreated`.
 */
class PluginUpdateListener : DynamicPluginListener {

    override fun pluginLoaded(pluginDescriptor: IdeaPluginDescriptor) {
        if (isThisPlugin(pluginDescriptor)) {
            EditorFactory.getInstance().allEditors.forEach(VirtualLines::install)
        }
    }

    override fun beforePluginUnload(pluginDescriptor: IdeaPluginDescriptor, isUpdate: Boolean) {
        if (!isThisPlugin(pluginDescriptor)) {
            return
        }
        EditorFactory.getInstance().allEditors.forEach { editor ->
            VirtualLines.uninstall(editor)
            runReadAction { FoldingService.get().clearAllKeys(editor) }
        }
    }

    private fun isThisPlugin(pluginDescriptor: IdeaPluginDescriptor) =
        pluginDescriptor.pluginClassLoader === javaClass.classLoader
}
