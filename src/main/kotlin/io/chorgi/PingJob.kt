package io.chorgi

import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*

@Singleton
class PingJob(private val discoverer: DiscoveryService, private val pingClient: PingClient) {

    @Scheduled(fixedDelay = "60s", initialDelay = "10s")
    fun executeEveryMinute() {
        val addresses = discoverer.podAddresses().filterNot { it == InetAddress.getLocalHost().hostAddress }
        addresses.forEach {
            LOG.info(pingClient.fetchPing("http://${it}:8080/ping"), SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()))
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(PingJob::class.java)
    }
}