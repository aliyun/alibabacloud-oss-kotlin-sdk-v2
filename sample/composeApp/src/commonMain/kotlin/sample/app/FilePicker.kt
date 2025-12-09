package sample.app

import androidx.compose.runtime.Composable
import kotlinx.io.files.Path

@Composable
expect fun FilePicker(onFileSelected: (Path) -> Unit)