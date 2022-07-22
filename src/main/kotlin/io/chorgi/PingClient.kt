package io.chorgi

import io.micronaut.http.client.HttpClient
import jakarta.inject.Singleton

@Singleton
class PingClient(val discoveryService: DiscoveryService, private val httpClient: HttpClient) {
    fun fetchPing(uri: String): String? {
        return httpClient.toBlocking().retrieve(uri)
    }
}

