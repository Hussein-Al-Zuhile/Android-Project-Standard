package com.hussein.androidprojectstandard.data.datasource.remote.mqtt

import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect
import com.hivemq.client.mqtt.mqtt3.Mqtt3Client
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import com.hussein.androidprojectstandard.BuildConfig
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import com.hussein.androidprojectstandard.data.base.BaseMQTTClient
import com.hussein.androidprojectstandard.data.base.MQTTEvent

class ExampleMQTTClient : BaseMQTTClient() {

    override val client = Mqtt3Client.builder()
        .identifier(UUID.randomUUID().toString())
        .serverHost(BuildConfig.EXAMPLE_MQTT_IP)
        .serverPort(BuildConfig.EXAMPLE_MQTT_PORT)
        .automaticReconnect(
            MqttClientAutoReconnect.builder()
                .initialDelay(3, TimeUnit.SECONDS)
                .maxDelay(1, TimeUnit.MINUTES)
                .build()
        )
        .addConnectedListener {
            println("ExampleMQTTClient connected ${BuildConfig.EXAMPLE_MQTT_IP}")
        }
        .addDisconnectedListener {
            println("ExampleMQTTClient disconnected ${it.cause}")
        }
        .buildAsync()

}


sealed class ExampleMQTTEvent(override val topic: String) : MQTTEvent {

}
