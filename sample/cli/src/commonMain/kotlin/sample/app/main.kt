package sample.app

import kotlinx.cli.ArgParser
import kotlinx.cli.ExperimentalCli

@OptIn(ExperimentalCli::class)
fun main(args: Array<String>) {
    val parser = ArgParser("oss")
    parser.subcommands(
        ListBuckets(),
        DescribeRegions(),
        IsBucketExist(),
        PutBucket(),
        DeleteBucket(),
        GetBucketInfo(),
        GetBucketLocation(),
        GetBucketStat(),
        PutBucketAcl(),
        GetBucketAcl(),
        PutBucketVersioning(),
        GetBucketVersioning(),
        PutObject(),
        GetObject(),
        GetObjectToFile(),
        IsObjectExist(),
        HeadObject(),
        AppendObject(),
        CopyObject(),
        DeleteObject(),
        DeleteMultipleObjects(),
        GetObjectMeta(),
        RestoreObject(),
        InitiateMultipartUpload(),
        UploadPart(),
        ListParts(),
        UploadPartCopy(),
        ListMultipartUploads(),
        CompleteMultipartUpload(),
        AbortMultipartUpload(),
        ListObjectsV2(),
        ListObjects(),
        PutObjectAcl(),
        GetObjectAcl(),
        PutSymlink(),
        GetSymlink(),
        PutObjectTagging(),
        GetObjectTagging(),
        DeleteObjectTagging(),
        Presign()
    )
    parser.parse(args)
}
