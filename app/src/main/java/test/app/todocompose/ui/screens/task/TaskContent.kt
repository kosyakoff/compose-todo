package test.app.todocompose.ui.screens.task

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import test.app.todocompose.R
import test.app.todocompose.components.PriorityDropDown
import test.app.todocompose.data.models.Priority
import test.app.todocompose.ui.theme.Dimensions
import test.app.todocompose.util.Constants

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimensions.LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title, onValueChange = onTitleChange,
            label = { Text(text = stringResource(R.string.task_content_title_title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true
        )
        Divider(
            modifier = Modifier.height(Dimensions.MEDIUM_PADDING),
            color = Color.Transparent
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        Divider(
            modifier = Modifier.height(Dimensions.MEDIUM_PADDING),
            color = Color.Transparent
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(text = stringResource(R.string.task_content_description_title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
@Preview(Constants.PREVIEW_DEFAULT, showBackground = true)
@Preview(Constants.PREVIEW_DARK_MODE, uiMode = UI_MODE_NIGHT_YES, showBackground = true)
private fun TaskContentPreview() {
    TaskContent(
        title = LoremIpsum(words = Constants.PREVIEW_LOREM_LARGE).values.joinToString(),
        onTitleChange = {},
        description = LoremIpsum(words = Constants.PREVIEW_LOREM_LARGE).values.joinToString(),
        onPrioritySelected = {},
        onDescriptionChange = {},
        priority = Priority.HIGH
    )
}