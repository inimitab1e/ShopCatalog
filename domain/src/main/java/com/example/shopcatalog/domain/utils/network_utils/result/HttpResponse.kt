package com.example.shopcatalog.domain.utils.network_utils.result

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}
