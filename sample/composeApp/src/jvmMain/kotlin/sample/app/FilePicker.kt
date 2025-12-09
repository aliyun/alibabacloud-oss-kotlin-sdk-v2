package sample.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.io.files.Path

@Composable
actual fun FilePicker(
    onFileSelected: (Path) -> Unit
) {
    var path by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "File path: ",
            modifier = Modifier.width(120.dp)
        )
        BasicTextField(
            value = path,
            onValueChange = {
                path = it
                onFileSelected(Path(it))
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}