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
import androidx.compose.ui.unit.dp
import kotlinx.browser.document
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.khronos.webgl.get
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File
import org.w3c.files.FileReader
import org.w3c.files.get

@Composable
actual fun FilePicker(
    onFileSelected: (PickedFile) -> Unit
) {
    var fileName by remember { mutableStateOf<String?>(null) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            pickFile { name, bytes ->
                fileName = name
                onFileSelected(PickedFile(name) { bytes })
            }
        }, content = {
            Text("Select File")
        })
        Text(fileName ?: "", maxLines = 2)
    }
}

private fun pickFile(onRead: (name: String, bytes: ByteArray) -> Unit) {
    val input = document.createElement("input") as HTMLInputElement
    input.type = "file"
    input.onchange = {
        val file: File? = input.files?.get(0)
        if (file != null) {
            val reader = FileReader()
            reader.onload = {
                val buffer = reader.result as ArrayBuffer
                onRead(file.name, buffer.toByteArray())
                Unit
            }
            reader.readAsArrayBuffer(file)
        }
        Unit
    }
    input.click()
}

private fun ArrayBuffer.toByteArray(): ByteArray {
    val view = Int8Array(this)
    return ByteArray(view.length) { view[it] }
}
