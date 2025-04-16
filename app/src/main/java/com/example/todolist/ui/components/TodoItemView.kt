package com.example.todolist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.IconButton
import com.example.todolist.dto.TodoItem

@Composable
fun TodoItemView(
    todo: TodoItem,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { onCheckedChange(it) }
        )

        Text(
            text = todo.text,
            fontSize = 18.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            color = if (todo.isDone) Color.Gray else Color.White,
            textDecoration = if (todo.isDone) TextDecoration.LineThrough else TextDecoration.None
        )

        IconButton(onClick = onEdit) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar tarefa")
        }

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remover tarefa",
                tint = Color(0xFF882323)
            )
        }
    }
}