package test.app.todocompose.data.models

import androidx.compose.ui.graphics.Color
import test.app.todocompose.ui.theme.HighPriorityColor
import test.app.todocompose.ui.theme.LowPriorityColor
import test.app.todocompose.ui.theme.MediumPriorityColor
import test.app.todocompose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}