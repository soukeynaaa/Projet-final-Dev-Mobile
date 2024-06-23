package com.example.todoapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoapp.ui.theme.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppHome(navController: NavHostController, tasks: SnapshotStateList<Task>) {
    var showMenu by remember { mutableStateOf(false) }
    var selectedFilters by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo App") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF333333)
                ),
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Filled.List, contentDescription = "Filter", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        val filterOptions = listOf("Todo", "In Progress", "Done", "Bug")
                        filterOptions.forEach { filter ->
                            var isChecked by remember { mutableStateOf(selectedFilters.contains(filter)) }
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Checkbox(
                                            checked = isChecked,
                                            onCheckedChange = {
                                                isChecked = it
                                                if (isChecked) {
                                                    selectedFilters = selectedFilters + filter
                                                } else {
                                                    selectedFilters = selectedFilters - filter
                                                }
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = filter)
                                    }
                                },
                                onClick = { }
                            )
                        }

                        DropdownMenuItem(
                            text = {
                                Button(
                                    onClick = { showMenu = false },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Apply", color = Color.White)
                                }
                            },
                            onClick = {}
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_task") },
                containerColor = Color(0xFF333333)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task", tint = Color.White)
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        TaskList(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            tasks = tasks,
            filters = selectedFilters
        )
    }
}

@Composable
fun TaskList(modifier: Modifier = Modifier, navController: NavHostController, tasks: MutableList<Task>, filters: Set<String>) {
    val filteredTasks = if (filters.isEmpty()) tasks else tasks.filter { filters.contains(it.status) }

    LazyColumn(modifier = modifier) {
        items(filteredTasks) { task ->
            TaskItem(task, navController)
        }
    }
}

@Composable
fun TaskItem(task: Task, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("update_task/${task.id}") },
        border = BorderStroke(1.dp, Color(task.color)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(task.color), CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(task.name, fontSize = 18.sp)
        }
    }
}
