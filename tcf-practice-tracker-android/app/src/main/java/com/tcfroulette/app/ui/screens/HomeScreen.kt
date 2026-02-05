package com.tcfroulette.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tcfroulette.app.data.database.TaskEntity
import com.tcfroulette.app.ui.theme.*
import com.tcfroulette.app.viewmodel.HomeUiState
import com.tcfroulette.app.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(PurplePrimary, PurpleSecondary)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp)),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "TCF Roulette",
                    style = MaterialTheme.typography.displayMedium,
                    color = TextSecondary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = "Practice random TCF French exam prompts",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Roll button
                Button(
                    onClick = { viewModel.rollNewPrompt() },
                    enabled = uiState !is HomeUiState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(PurplePrimary, PurpleSecondary)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Roll the Dice",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Content area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(BackgroundPrompt)
                        .border(2.dp, BorderColor, RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    when (val state = uiState) {
                        is HomeUiState.Initial -> {
                            Text(
                                text = "Press the button to get a random TCF practice prompt!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextMuted,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        is HomeUiState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = PurplePrimary,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                        is HomeUiState.Success -> {
                            TaskContent(task = state.task)
                        }
                        is HomeUiState.Error -> {
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyMedium,
                                color = ErrorRed,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskContent(task: TaskEntity) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Header row with task ID and format
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Left side - Task ID and Format
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Badge(
                    text = "Task #${task.id}",
                    gradientColors = listOf(TaskIdBlue, TaskIdBlueDark)
                )
                if (task.formatCategory != null) {
                    Badge(
                        text = task.displayFormat,
                        gradientColors = listOf(FormatPink, FormatPinkDark)
                    )
                } else {
                    MutedBadge(text = task.displayFormat)
                }
            }

            // Right side - Category and Task Type
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Badge(
                    text = task.category,
                    gradientColors = listOf(CategoryGreen, CategoryGreenDark)
                )
                Badge(
                    text = task.taskType,
                    gradientColors = listOf(TaskTypeOrange, TaskTypeOrangeDark)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scenario section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFFF9FAFB))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Scenario: ",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF6B7280),
                    fontWeight = FontWeight.Medium
                )
                if (task.scenarioCategory != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(ScenarioCyan, ScenarioCyanDark)
                                ),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = task.displayScenario,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE5E7EB),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = task.displayScenario,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF9CA3AF),
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Divider
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 2.dp,
            color = BorderColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Prompt section
        Text(
            text = "Your TCF Prompt:",
            style = MaterialTheme.typography.titleMedium,
            color = TextSecondary,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Prompt text box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PromptBgStart, PromptBgEnd)
                    )
                )
                .border(1.dp, PromptBorder, RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Text(
                text = task.prompt,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                lineHeight = 28.sp,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Composable
private fun Badge(
    text: String,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(colors = gradientColors),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
    }
}

@Composable
private fun MutedBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MutedBadgeBg,
                shape = RoundedCornerShape(15.dp)
            )
            .border(2.dp, MutedBadgeBorder, RoundedCornerShape(15.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MutedBadgeText,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
    }
}
