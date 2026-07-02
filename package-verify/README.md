# Kotlin Package Verification

Verifies the published `com.aliyun:kotlin-oss-v2` Maven package works correctly on JVM.

## Coverage

- Service: DescribeRegions, ListBuckets
- Bucket: Put, GetInfo, GetStat, GetLocation, Delete, GetAcl
- Object: Put, Head, GetMeta, Get, Copy, Delete
- Object Acl: Put, Get
- Object Tagging: Put, Get, Delete
- Object Symlink: Put, Get
- AppendObject / SealAppendObject
- Multipart: Initiate, UploadPart, UploadPartCopy, ListParts, ListMultipartUploads, Complete, Abort
- Presigner: pre-signed URL generation and access
- Paginator: ListObjects, ListObjectsV2, ListBuckets
- Extensions: IsBucketExist, IsObjectExist
- DeleteMultipleObjects

## Environment Variables

| Variable | Description |
|----------|-------------|
| `OSS_ACCESS_KEY_ID` | AccessKey ID (required) |
| `OSS_ACCESS_KEY_SECRET` | AccessKey Secret (required) |
| `OSS_SESSION_TOKEN` | STS Token (optional) |
| `OSS_REGION` | OSS region, default `cn-hangzhou` |
| `OSS_TEST_BUCKET` | Test bucket name prefix (optional) |

## Usage

```bash
# Run command-line verification
./gradlew :package-verify:run
