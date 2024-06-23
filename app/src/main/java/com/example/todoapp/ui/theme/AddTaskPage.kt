package com.example.todoapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoapp.ui.theme.Task
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskPage(navController: NavHostController, tasks: MutableList<Task>) {
    var taskName by remember { mutableStateOf(TextFieldValue("")) }
    var taskDescription by remember { mutableStateOf(TextFieldValue("")) }
    var selectedStatus by remember { mutableStateOf("Todo") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODO APP") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF333333)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.popBackStack() },
                containerColor = Color(0xFF333333)
            ) {
                Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.White)
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ajouter une tâche", fontSize = 20.sp, color = Color.Black)

                StatusDropdown(
                    selectedStatus = selectedStatus,
                    onStatusChange = { selectedStatus = it },
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                )
            }

            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                label = { Text("Nom de la tâche", color = Color.Black) },

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black
                )
            )

            OutlinedTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text("Description", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black
                        )
            )

            Button(
                onClick = {
                    tasks.add(Task(taskName.text, taskDescription.text, getStatusColor(selectedStatus), selectedStatus))
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Ajouter", color = Color.White)
            }
        }
    }
}

@Composable
fun StatusDropdown(
    selectedStatus: String,
    onStatusChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    val statusOptions = listOf(
        "Todo" to Color.Gray,
        "In Progress" to Color.Cyan,
        "Done" to Color.Green,
        "Bug" to Color.Red
    )

    Box(
        modifier = Modifier
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .clickable { onExpandedChange(true) }
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val statusColor = statusOptions.find { it.first == selectedStatus }?.second ?: Color.Gray
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(statusColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(selectedStatus, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Arrow", tint = Color.Black)
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) }
    ) {
        statusOptions.forEach { (status, color) ->
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(color, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = status)
                    }
                },
                onClick = {
                    onStatusChange(status)
                    onExpandedChange(false)
                }
            )
        }
    }
}

fun getStatusColor(status: String): Int {
    return when (status) {
        "Todo" -> android.graphics.Color.GRAY
        "In Progress" -> android.graphics.Color.CYAN
        "Done" -> android.graphics.Color.GREEN
        "Bug" -> android.graphics.Color.RED
        else -> android.graphics.Color.GRAY
    }
}
