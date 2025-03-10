package com.example.todolist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun AddTaskInput(
    onTaskAdded: (String) -> Unit
) {
    var newTaskText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        TextField(
            value = newTaskText,
            onValueChange = { newTaskText = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 0.dp),
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 0.dp),
            label = { Text("Nova tarefa") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (newTaskText.isNotBlank()) {
                        onTaskAdded(newTaskText)
                        newTaskText = ""
                        keyboardController?.hide()
                    }
                }
            )
        )

        Button(
            onClick = {
                if (newTaskText.isNotBlank()) {
                    onTaskAdded(newTaskText)
                    newTaskText = ""
                }
            },
            modifier = Modifier
                .height(56.dp),
            shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
        ) {
            Text("Adicionar")
        }
    }
}