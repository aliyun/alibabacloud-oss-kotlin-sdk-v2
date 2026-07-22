package sample.app

import androidx.compose.runtime.Composable

/**
 * A file chosen by the user. [bytes] reads its content lazily so each platform can
 * back it by a filesystem path (JVM/Android) or a browser Blob (JS).
 */
class PickedFile(
    val name: String,
    private val reader: suspend () -> ByteArray
) {
    suspend fun bytes(): ByteArray = reader()
}

@Composable
expect fun FilePicker(onFileSelected: (PickedFile) -> Unit)
