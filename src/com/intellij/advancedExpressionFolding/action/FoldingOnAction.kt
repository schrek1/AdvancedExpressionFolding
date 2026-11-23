package com.intellij.advancedExpressionFolding.action

import com.intellij.advancedExpressionFolding.FoldingService
import com.intellij.advancedExpressionFolding.settings.IConfig
import com.intellij.advancedExpressionFolding.settings.State
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAware

class FoldingOnAction :
    AnAction(),
    IConfig by State()(),
    DumbAware {

    override fun actionPerformed(e: AnActionEvent) {
        if (!globalOn) {
            globalOn = true
            // folding was off, so the editors hold no plugin regions to collapse yet
            FoldingService.get().refoldOpenEditors()
        }
        e.getData(CommonDataKeys.EDITOR)?.let {
            FoldingService.get().fold(it, true)
        }
    }

}
