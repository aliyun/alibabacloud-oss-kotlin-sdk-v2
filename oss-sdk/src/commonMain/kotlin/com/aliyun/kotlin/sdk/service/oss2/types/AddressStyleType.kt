package com.aliyun.kotlin.sdk.service.oss2.types

public enum class AddressStyleType {
    VirtualHosted,
    Path,
    CName,

    /**
     * Agentic-only, the physical bucket name is replaced by the short alias host label.
     * The plain client falls back to [VirtualHosted].
     */
    VirtualHostedAlias
}
