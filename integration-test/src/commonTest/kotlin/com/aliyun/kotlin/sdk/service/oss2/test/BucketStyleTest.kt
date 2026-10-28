package com.aliyun.kotlin.sdk.service.oss2.test

import com.aliyun.kotlin.sdk.service.oss2.exceptions.ServiceException
import com.aliyun.kotlin.sdk.service.oss2.extension.api.deleteStyle
import com.aliyun.kotlin.sdk.service.oss2.extension.api.getStyle
import com.aliyun.kotlin.sdk.service.oss2.extension.api.listStyle
import com.aliyun.kotlin.sdk.service.oss2.extension.api.putStyle
import com.aliyun.kotlin.sdk.service.oss2.extension.models.DeleteStyleRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.GetStyleRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.ListStyleRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.PutStyleRequest
import com.aliyun.kotlin.sdk.service.oss2.extension.models.Style
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BucketStyleTest: TestBase() {

    @Test
    fun testPutAndGetStyleSuccess() = bucketTest { bucketName ->
        val putResult = defaultClient.putStyle(PutStyleRequest {
            bucket = bucketName
            styleName = "imageStyle"
            style = Style {
                content = "image/resize,p_50"
            }
        })
        assertEquals(200, putResult.statusCode)

        waitForCacheExpiration(1)

        val getResult = defaultClient.getStyle(GetStyleRequest {
            bucket = bucketName
            styleName = "imageStyle"
        })
        assertEquals("image/resize,p_50", getResult.style?.content)
        assertEquals("imageStyle", getResult.style?.name)
        assertNotNull(getResult.style?.createTime)
        assertNotNull(getResult.style?.category)
        assertNotNull(getResult.style?.lastModifyTime)
    }

    @Test
    fun testPutStyleWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.putStyle(PutStyleRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putStyle(PutStyleRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.styleName is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.putStyle(PutStyleRequest {
                bucket = bucketName
                styleName = "imageStyle"
            })
        }
        assertEquals(exception.message, "request.style is required")

        exception = assertFails {
            invalidClient.putStyle(PutStyleRequest {
                bucket = bucketName
                styleName = "imageStyle"
                style = Style{}
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testGetStyleWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.getStyle(GetStyleRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.getStyle(GetStyleRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.styleName is required")

        exception = assertFails {
            invalidClient.getStyle(GetStyleRequest {
                bucket = bucketName
                styleName = "imageStyle"
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testListStyleSuccess() = bucketTest { bucketName ->
        defaultClient.putStyle(PutStyleRequest {
            bucket = bucketName
            styleName = "imageStyle1"
            style = Style {
                content = "image/resize,p_50"
            }
        })
        defaultClient.putStyle(PutStyleRequest {
            bucket = bucketName
            styleName = "imageStyle2"
            style = Style {
                content = "image/resize,p_50"
            }
        })

        waitForCacheExpiration(3)

        val result = defaultClient.listStyle(ListStyleRequest {
            bucket = bucketName
        })
        assertEquals(2, result.styleList?.styles?.size)
        assertEquals("imageStyle1", result.styleList?.styles?.get(0)?.name)
        assertEquals("image/resize,p_50", result.styleList?.styles?.get(0)?.content)
        assertNotNull(result.styleList?.styles?.get(0)?.category)
        assertNotNull(result.styleList?.styles?.get(0)?.createTime)
        assertNotNull(result.styleList?.styles?.get(0)?.lastModifyTime)
        assertEquals("imageStyle2", result.styleList?.styles?.get(1)?.name)
        assertEquals("image/resize,p_50", result.styleList?.styles?.get(1)?.content)
        assertNotNull(result.styleList?.styles?.get(1)?.category)
        assertNotNull(result.styleList?.styles?.get(1)?.createTime)
        assertNotNull(result.styleList?.styles?.get(1)?.lastModifyTime)
    }

    @Test
    fun testListStyleWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.listStyle(ListStyleRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFails {
            invalidClient.listStyle(ListStyleRequest {
                bucket = bucketName
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }

    @Test
    fun testDeleteStyleSuccess() = bucketTest { bucketName ->
        defaultClient.putStyle(PutStyleRequest {
            bucket = bucketName
            styleName = "imageStyle"
            style = Style {
                content = "image/resize,p_50"
            }
        })

        val result = defaultClient.deleteStyle(DeleteStyleRequest {
            bucket = bucketName
            styleName = "imageStyle"
        })
        assertEquals(204, result.statusCode)
    }

    @Test
    fun testDeleteStyleWithException() = bucketTest { bucketName ->
        var exception: Throwable = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteStyle(DeleteStyleRequest {})
        }
        assertEquals(exception.message, "request.bucket is required")

        exception = assertFailsWith<IllegalArgumentException> {
            defaultClient.deleteStyle(DeleteStyleRequest {
                bucket = bucketName
            })
        }
        assertEquals(exception.message, "request.styleName is required")

        exception = assertFails {
            invalidClient.deleteStyle(DeleteStyleRequest {
                bucket = bucketName
                styleName = "imageStyle"
            })
        }
        assertTrue { exception.cause is ServiceException }
        assertEquals((exception.cause as ServiceException).statusCode, 403)
        assertEquals((exception.cause as ServiceException).errorCode, "InvalidAccessKeyId")
    }
}
