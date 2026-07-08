package sample.app

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
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.unit.dp
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import platform.Foundation.NSURL
import platform.UIKit.UIDocumentPickerViewController
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UniformTypeIdentifiers.UTTypeData
import platform.darwin.NSObject

@Composable
actual fun FilePicker(
    onFileSelected: (PickedFile) -> Unit
) {
    var fileName by remember { mutableStateOf<String?>(null) }
    val viewController = LocalUIViewController.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            val delegate = DocumentPickerDelegate(onFileSelected)

            val picker = UIDocumentPickerViewController(
                forOpeningContentTypes = listOf(UTTypeData),
                asCopy = true
            )
            picker.delegate = delegate
            picker.allowsMultipleSelection = true

            viewController.presentViewController(picker, animated = true, completion = null)
        }, content = {
            Text("Select File")
        })
        Text(fileName ?: "", maxLines = 2)
    }
}

class DocumentPickerDelegate(
    val onFileSelected: (PickedFile) -> Unit
) : NSObject(), UIDocumentPickerDelegateProtocol {

    override fun documentPicker(
        controller: UIDocumentPickerViewController,
        didPickDocumentsAtURLs: List<*>
    ) {
        val urls = didPickDocumentsAtURLs.filterIsInstance<NSURL>()
        urls.firstOrNull()?.let { url ->
            onFileSelected(
                PickedFile(url.path!!) {
                    SystemFileSystem.source(Path(url.path!!)).buffered().use { source ->
                        source.readByteArray()
                    }
                }
            )
        }
    }

    override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
    }
}
