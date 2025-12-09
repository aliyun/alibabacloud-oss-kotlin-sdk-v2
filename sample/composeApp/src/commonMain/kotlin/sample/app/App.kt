package sample.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliyun.kotlin.sdk.service.oss2.ClientConfiguration
import com.aliyun.kotlin.sdk.service.oss2.OSSClient
import com.aliyun.kotlin.sdk.service.oss2.credentials.StaticCredentialsProvider
import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgent
import com.aliyun.kotlin.sdk.service.oss2.logging.LogAgentLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.io.files.Path
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun App() {
    val coroutineScope = rememberCoroutineScope()
    var service by remember { mutableStateOf<Service?>(null) }
    var job by remember { mutableStateOf<Job?>(null) }
    val state = rememberScrollState()

    var accessKeyId by remember { mutableStateOf("") }
    var accessKeySecret by remember { mutableStateOf("") }
    var securityToken by remember { mutableStateOf<String?>(null) }
    var region by remember { mutableStateOf("cn-hangzhou") }
    var endpoint by remember { mutableStateOf<String?>(null) }
    var connectTimeout by remember { mutableStateOf<Duration?>(null) }
    var readWriteTimeout by remember { mutableStateOf<Duration?>(null) }
    var useCName by remember { mutableStateOf<Boolean?>(null) }

    var bucketName by remember { mutableStateOf("") }
    var objectKey by remember { mutableStateOf("") }
    var path by remember { mutableStateOf<Path?>(null) }
    var progress by remember { mutableFloatStateOf(0F) }

    var presignResult by remember { mutableStateOf<Map<String, Any>?>(null) }

    var openDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf<String?>(null) }
    var dialogImage by remember { mutableStateOf<ByteArray?>(null) }

    var objects by remember { mutableStateOf<List<String>>(listOf()) }

    fun dialogMessage(message: String) {
        dialogMessage = message
        openDialog = true
        dialogImage = null
    }

    fun dialogMessage(bytes: ByteArray) {
        dialogMessage = null
        openDialog = true
        dialogImage = bytes
    }

    fun dialogException(e: Exception) {
        dialogMessage(e.message ?: e.toString())
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White).verticalScroll(state),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Client",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Region: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = region,
                        onValueChange = { region = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Endpoint: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = endpoint ?: "",
                        onValueChange = { endpoint = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "AccessKeyId: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = accessKeyId,
                        onValueChange = { accessKeyId = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "AccessKeySecret: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = accessKeySecret,
                        onValueChange = { accessKeySecret = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "SecurityToken: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = securityToken ?: "",
                        onValueChange = { securityToken = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "ConnectTimeout: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = connectTimeout?.toString() ?: "",
                        onValueChange = { connectTimeout = it.toLong().toDuration(DurationUnit.SECONDS) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "ReadWriteTimeout: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = readWriteTimeout?.toString() ?: "",
                        onValueChange = { readWriteTimeout = it.toLong().toDuration(DurationUnit.SECONDS) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "useCName: ",
                        modifier = Modifier.width(120.dp)
                    )
                    Switch(
                        checked = useCName ?: false,
                        onCheckedChange = { useCName = it }
                    )
                }
                Button(
                    onClick = {
                        try {
                            service = Service(
                                OSSClient.create(ClientConfiguration.loadDefault().apply {
                                    this.region = region
                                    this.endpoint = endpoint
                                    credentialsProvider = StaticCredentialsProvider(
                                        accessKeyId = accessKeyId,
                                        accessKeySecret = accessKeySecret,
                                        securityToken = securityToken
                                    )
                                    logger = LogAgent.default(LogAgentLevel.DEBUG)
                                    this.connectTimeout = connectTimeout
                                    this.readWriteTimeout = readWriteTimeout
                                    this.useCName = useCName
                                })
                            )
                        } catch (e: Exception) {
                            dialogException(e)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        Text(
                            text = "Set Client (${service?.let { "Client has been set" } ?: "Client is null"})",
                            fontSize = 13.sp
                        )
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = "Bucket",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Bucket name: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = bucketName,
                        onValueChange = { bucketName = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Spacer(
                    Modifier.fillMaxWidth().height(11.dp).padding(vertical = 5.dp)
                        .background(Color.LightGray)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                (service
                                    ?: throw RuntimeException("Client not initialized.")).putBucket(
                                    bucketName
                                )
                                dialogMessage("Bucket has been created")
                            } catch (e: Exception) {
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("PutBucket")
                    })
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    (service
                                        ?: throw RuntimeException("Client not initialized.")).deleteBucket(
                                        bucketName
                                    )
                                    dialogMessage("Bucket has been deleted")
                                } catch (e: Exception) {
                                    dialogException(e)
                                }
                            }
                        },
                        content = {
                            Text("DeleteBucket")
                        }
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    (service
                                        ?: throw RuntimeException("Client not initialized.")).listObjects(
                                        bucketName
                                    )
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    dialogException(e)
                                }
                            }
                        },
                        content = {
                            Text("ListObjects")
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Object",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Object key: ",
                        modifier = Modifier.width(120.dp)
                    )
                    BasicTextField(
                        value = objectKey,
                        onValueChange = { objectKey = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilePicker { filePath ->
                        path = filePath
                    }
                }
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = Color.Blue,
                    backgroundColor = Color.LightGray
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(onClick = {
                        job = coroutineScope.launch(Dispatchers.IO) {
                            try {
                                path?.let {
                                    (service
                                        ?: throw RuntimeException("Client not initialized.")).putObject(
                                        bucketName,
                                        objectKey,
                                        it
                                    ) { p ->
                                        progress = p
                                    }
                                } ?: throw RuntimeException("File path is null")
                                dialogMessage("Upload successful")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("PutObject")
                    })
                    Button(onClick = {
                        job = coroutineScope.launch(Dispatchers.IO) {
                            try {
                                val content = (service
                                    ?: throw RuntimeException("Client not initialized.")).getObject(
                                    bucketName,
                                    objectKey
                                )
                                when (content) {
                                    is Data.Image -> dialogMessage(content.bytes!!)
                                    is Data.Text -> dialogMessage(content.bytes!!)
                                    else -> dialogMessage("GetObject success.")
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("GetObject")
                    })
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                val headers = (service
                                    ?: throw RuntimeException("Client not initialized.")).headObject(
                                    bucketName,
                                    objectKey
                                )
                                dialogMessage(headers.map { "${it.key}: ${it.value}" }
                                    .joinToString("\n"))
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("HeadObject")
                    })
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                (service
                                    ?: throw RuntimeException("Client not initialized.")).deleteObject(
                                    bucketName,
                                    objectKey
                                )
                                dialogMessage("Object has been deleted")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("DeleteObject")
                    })
                    Button(onClick = {
                        job?.cancel()
                    }, content = {
                        Text("Cancel")
                    })
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(5.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Presign",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                presignResult = (service
                                    ?: throw RuntimeException("Client not initialized.")).presign(
                                    bucketName,
                                    objectKey,
                                    PresignType.PUT
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("PutObject")
                    })
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                presignResult = (service
                                    ?: throw RuntimeException("Client not initialized.")).presign(
                                    bucketName,
                                    objectKey,
                                    PresignType.GET
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("GetObject")
                    })
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                presignResult = (service
                                    ?: throw RuntimeException("Client not initialized.")).presign(
                                    bucketName,
                                    objectKey,
                                    PresignType.HEAD
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                                dialogException(e)
                            }
                        }
                    }, content = {
                        Text("HeadObject")
                    })
                }
                presignResult?.let {
                    SelectionContainer {
                        Text(
                            it.map { item ->
                                when (val value = item.value) {
                                    is String -> "${item.key}: ${item.value}"
                                    is Map<*, *> -> "${item.key}: \n" + value.map { header ->
                                        "    ${header.key}: ${header.value}"
                                    }.joinToString("\n")

                                    else -> ""
                                }
                            }.joinToString("\n")
                        )
                    }
                }
            }
        }
    }
    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            text = {
                dialogImage?.let {
                    Image(
                        bitmap = it.decodeToImageBitmap(),
                        null
                    )
                }
                dialogMessage?.let {
                    Text(it)
                }
            },
            confirmButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text("确定")
                }
            }
        )
    }
}