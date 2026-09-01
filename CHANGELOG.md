# ChangeLog - Alibaba Cloud OSS SDK for Kotlin v2

## 版本号：0.4.0 日期：2026-09-01
### 变更内容
- Feature：Add agentic bucket
- Fix：Report the real SDK version in the user agent

## 版本号：0.3.0 日期：2026-07-24
### 变更内容
- Feature：Support JS, WasmJs, iOS and macOS multiplatform targets
- Feature：Add bucket logging api
- Feature：Add bucket inventory api
- Feature：Add bucket style api
- Update：Add hashCrc64ecma field to Part and parse it from ListParts response
- Update：Add DateUtils for RFC 822 formatter
- Fix：Fix parsing XML error when the value is empty
- Fix：Normalize taggingCount header key

## 版本号：0.2.0 日期：2026-06-29
### 变更内容
- Feature：Add bucket referer api
- Feature：Add bucket encryption api
- Feature：Add bucket request payment api
- Feature：Add bucket archive direct read api
- Feature：Add bucket https config api
- Feature：Add bucket resource group api
- Feature：Add proxy configuration
- Update：Refactor FeatureFlagsType

## 版本号：0.1.0-dev 日期：2025-12-30
### 变更内容
- Feature：Add credentials provider
- Feature：Add retryer
- Feature：Add signer v1 and signer v4
- Feature：Add http client
- Feature：Add bucket basic api
- Feature：Add bucket acl api
- Feature：Add bucket versioning api
- Feature：Add bucket cors api
- Feature：Add object basic api
- Feature：Add object acl api
- Feature：Add object multipart api
- Feature：Add object symlink api
- Feature：Add object tagging api
- Feature：Add region api
- Feature：Add service api
- Feature：Add presigner
- Feature：Add paginator
- Feature：Add putObjectFromFile/getObjectToFile api
