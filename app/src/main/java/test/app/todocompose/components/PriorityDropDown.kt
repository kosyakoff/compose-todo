package test.app.todocompose.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import test.app.todocompose.data.models.Priority
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.util.Constants

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val angle: Float by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.PRIORITY_DROP_DOWN_HEIGHT)
            .clickable { expanded = true }
            .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(Dimensions.PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.weight(8f)
        )
        IconButton(
            onClick = { expanded = true }, modifier = Modifier
                .alpha(0.6f)
                .rotate(degrees = angle)
                .weight(1.5f)
        ) {
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
        ) {
            DropdownMenuItem(text = { PriorityItem(priority = Priority.LOW) }, onClick = {
                expanded = false
                onPrioritySelected(Priority.LOW)
            })

            DropdownMenuItem(text = { PriorityItem(priority = Priority.MEDIUM) }, onClick = {
                expanded = false
                onPrioritySelected(Priority.MEDIUM)
            })

            DropdownMenuItem(text = { PriorityItem(priority = Priority.HIGH) }, onClick = {
                expanded = false
                onPrioritySelected(Priority.HIGH)
            })
        }

    }
}


@Composable
@Preview(Constants.PREVIEW_DEFAULT)
@Preview(Constants.PREVIEW_DARK_MODE, uiMode = UI_MODE_NIGHT_YES)
private fun PriorityDropDownPreview() {
    PriorityDropDown(priority = Priority.HIGH, onPrioritySelected = {})
}