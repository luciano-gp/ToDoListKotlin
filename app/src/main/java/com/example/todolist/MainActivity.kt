package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.ui.components.TodoItemView
import com.example.todolist.ui.components.AddTaskInput

data class TodoItem(
    val id: Int,
    var text: String,
    var isDone: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                TodoApp()
            }
        }
    }
}

@Composable
fun TodoApp() {
    var todoList by remember { mutableStateOf(listOf<TodoItem>()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing
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
                        }
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TodoAppPreview() {
    ToDoListTheme {
        TodoApp()
    }
}
