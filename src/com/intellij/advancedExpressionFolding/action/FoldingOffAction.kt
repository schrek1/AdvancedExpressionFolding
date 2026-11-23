package com.intellij.advancedExpressionFolding.action

import com.intellij.advancedExpressionFolding.FoldingService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAware

class FoldingOffAction : AnAction(), DumbAware {

    override fun actionPerformed(e: AnActionEvent) {
        e.getData(CommonDataKeys.EDITOR)?.let {
            FoldingService.get().fold(it, false)
        }
    }

}
