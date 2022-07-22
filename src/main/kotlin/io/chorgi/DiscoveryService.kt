package io.chorgi

import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.CoreV1Api
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory


@Singleton
class DiscoveryService(private val coreV1Api: CoreV1Api) {
    @Throws(ApiException::class)
    fun podAddresses(namespace: String = "pp"): List<String> {
        val endpointsList = coreV1Api.listNamespacedEndpoints("pp", null, null, null, null, null, null, null, null, null, null)
        val subset = endpointsList.items.filter{ it?.metadata?.name == "ping-service" }.flatMap { it.subsets ?: emptyList() }
        val addresses = subset.flatMap { it.addresses ?: emptyList() }
        return addresses.map { it.ip }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(DiscoveryService::class.java)
    }
}