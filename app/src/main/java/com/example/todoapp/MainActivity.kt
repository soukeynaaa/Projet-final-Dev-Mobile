package com.example.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.AddTaskPage
import com.example.todoapp.ui.TodoAppHome
import com.example.todoapp.ui.UpdateTaskPage
import com.example.todoapp.ui.theme.Task
import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                val navController = rememberNavController()
                val tasks = remember { mutableStateListOf<Task>() }
                NavHost(navController, startDestination = "home") {
                    composable("home") { TodoAppHome(navController, tasks) }
                    composable("add_task") { AddTaskPage(navController, tasks) }
                    composable("update_task/{taskId}") { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId")
                        val task = tasks.find { it.id == taskId }
                        task?.let { UpdateTaskPage(navController, it, tasks) }
                    }
                }
            }
        }
    }
}
