package com.tcfroulette.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tcfroulette.app.data.database.AppDatabase
import com.tcfroulette.app.data.repository.TaskRepository
import com.tcfroulette.app.ui.screens.HomeScreen
import com.tcfroulette.app.ui.theme.TCFRouletteTheme
import com.tcfroulette.app.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val repository = TaskRepository(database)

        setContent {
            TCFRouletteTheme {
                val viewModel: HomeViewModel = viewModel(
                    factory = HomeViewModel.Factory(repository)
                )
                HomeScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
