package io.chorgi

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*

@Controller("/ping")
class PingServer {

    @Get
    @Produces(MediaType.TEXT_PLAIN)
    fun index(): String {
        LOG.info("ping", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()))
        return "pong${System.lineSeparator()}"
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(PingServer::class.java)
    }
}