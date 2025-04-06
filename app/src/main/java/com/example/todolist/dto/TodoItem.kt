package com.example.todolist.dto

data class TodoItem(
    val id: Int,
    var text: String,
    var isDone: Boolean = false
)