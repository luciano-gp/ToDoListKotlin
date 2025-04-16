package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist.dto.TodoItem
import com.example.todolist.ui.components.AddTaskInput
import com.example.todolist.ui.components.EditTaskModal
import com.example.todolist.ui.components.TodoItemView

@Composable
fun TodoListScreen(navController: NavController) {
    var todoList by remember { mutableStateOf(listOf<TodoItem>()) }

    var editingItem by remember { mutableStateOf<TodoItem?>(null) }
    var editedText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            BottomAppBar{
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("todolist") { inclusive = true }
                            }
                        }
                    ) {
                        Text("Logout")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            AddTaskInput { newTask ->
                todoList = listOf(TodoItem(id = todoList.size, text = newTask)) + todoList
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(todoList) { item ->
                    TodoItemView(
                        todo = item,
                        onCheckedChange = { isChecked ->
                            todoList = todoList.map {
                                if (it.id == item.id) it.copy(isDone = isChecked) else it
                            }.sortedWith(
                                compareBy<TodoItem> { it.isDone }
                                    .thenByDescending { it.id }
                            )
                        },
                        onDelete = {
                            todoList = todoList.filter { it.id != item.id }
                        },
                        onEdit = {
                            editingItem = item
                            editedText = item.text
                        }
                    )
                }
            }
        }

        editingItem?.let { item ->
            EditTaskModal(
                currentText = item.text,
                onDismiss = { editingItem = null },
                onConfirm = { updatedText ->
                    todoList = todoList.map {
                        if (it.id == item.id) it.copy(text = updatedText) else it
                    }
                    editingItem = null
                }
            )
        }
    }
}