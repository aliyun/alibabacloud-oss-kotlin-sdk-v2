package com.aliyun.kotlin.sdk.service.oss2.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class HttpUtilsTest {
    @Test
    fun urlValuesEncodeCorrectly() {
        val nonEncodedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~"
        val encodedCharactersInput = "\t\n\r !\"#$%&'()*+,/:;<=>?@[\\]^`{|}"
        val encodedCharactersOutput =
            "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D"

        assertEquals("", HttpUtils.urlEncode(""))
        assertEquals(nonEncodedCharacters, HttpUtils.urlEncode(nonEncodedCharacters))
        assertEquals(encodedCharactersOutput, HttpUtils.urlEncode(encodedCharactersInput))
    }

    @Test
    fun urlValuesDecodeCorrectly() {
        val nonEncodedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~"
        val encodedCharactersInput = "\t\n\r !\"#$%&'()*+,/:;<=>?@[\\]^`{|}"
        val encodedCharactersOutput =
            "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D"

        assertEquals("", HttpUtils.urlDecode(""))
        assertEquals(nonEncodedCharacters, HttpUtils.urlDecode(nonEncodedCharacters))
        assertEquals(encodedCharactersInput, HttpUtils.urlDecode(encodedCharactersOutput))
    }

    @Test
    fun pathValuesEncodeCorrectly() {
        val nonEncodedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~/"
        val encodedCharactersInput = "\t\n\r !\"#$%&'()*+,:;<=>?@[\\]^`{|}"
        val encodedCharactersOutput =
            "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D"

        assertEquals("", HttpUtils.urlEncodePath(""))
        assertEquals(nonEncodedCharacters, HttpUtils.urlEncodePath(nonEncodedCharacters))
        assertEquals(encodedCharactersOutput, HttpUtils.urlEncodePath(encodedCharactersInput))
    }

    @Test
    fun pathValuesDecodeCorrectly() {
        val nonEncodedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~/"
        val encodedCharactersInput = "\t\n\r !\"#$%&'()*+,:;<=>?@[\\]^`{|}"
        val encodedCharactersOutput =
            "%09%0A%0D%20%21%22%23%24%25%26%27%28%29%2A%2B%2C%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D"

        assertEquals("", HttpUtils.urlDecode(""))
        assertEquals(nonEncodedCharacters, HttpUtils.urlDecode(nonEncodedCharacters))
        assertEquals(encodedCharactersInput, HttpUtils.urlDecode(encodedCharactersOutput))
    }
}
