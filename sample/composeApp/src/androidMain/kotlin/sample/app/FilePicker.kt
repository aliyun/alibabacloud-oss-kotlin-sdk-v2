package sample.app

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
actual fun FilePicker(
    onFileSelected: (PickedFile) -> Unit,
) {
    val context = LocalContext.current
    var fileName by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                fileName = it.lastPathSegment ?: "file"
                val appContext = context.applicationContext
                onFileSelected(
                    PickedFile(fileName ?: "file") {
                        appContext.readBytes(it)
                    }
                )
            }
        }
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { launcher.launch("*/*") }, content = {
            Text("Select File")
        })
        Text(
            fileName ?: "",
            maxLines = 2
        )
    }
}

private fun Context.readBytes(uri: Uri): ByteArray =
    contentResolver.openInputStream(uri)?.use { it.readBytes() }
        ?: throw IllegalStateException("Cannot open $uri")
