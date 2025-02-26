package com.hussein.androidprojectstandard.data.base

import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck
import com.hussein.androidprojectstandard.R
import com.hussein.androidprojectstandard.domain.base.Result
import com.hussein.androidprojectstandard.utils.MessageType
import com.hussein.androidprojectstandard.utils.UIText
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext
import java.util.concurrent.CompletableFuture

abstract class BaseRepository() {

    suspend inline fun <reified T> sendRemoteRequest(request: (() -> HttpResponse)): Result<T> {
        val result = try {
            Result.success(request().body<T>())
        } catch (exception: ClientRequestException) { // for 4xx responses
            exception.printStackTrace()
            if (exception.response.status == HttpStatusCode.Unauthorized) {
                Result.Failure.Unauthorized()
            } else {
                Result.Failure.BadRequestException()
            }
        } catch (exception: ServerResponseException) {
            exception.printStackTrace()
            // TODO: Add ErrorResponse data class, based on the agreement with backend team
            val errorMessage = try {
//                UIText.Hardcoded(
//                    exception.response.body<ErrorResponse>().error.toString(),
//                    type = MessageType.Error
//                )
                  UIText.LocalizedString(
                      R.string.message_server_error,
                      type = MessageType.Error
                  )
            } catch (exception: Exception) {
                exception.printStackTrace()
                UIText.LocalizedString(
                    R.string.message_server_error,
                    type = MessageType.Error
                )
            }

            Result.Failure.ServerErrorException(errorMessage)
        } catch (exception: Exception) {
            exception.printStackTrace()

            if (exception is CancellationException) throw exception

            Result.failure()
        }
        return result
    }

    suspend fun connectToMQTT(request: () -> Mqtt3ConnAck?): Result<Unit> {
        val result = try {

            withContext(Dispatchers.IO) {

                val response = request()
                if (response?.returnCode?.isError == true) {
                    Result.Failure.MQTT.ConnectionFailed()
                } else {
                    Result.success<Unit>()
                }
            }
        } catch (exception: Exception) {
            println("Exception message: ${exception.message}")
            exception.printStackTrace()
            Result.Failure.MQTT.ConnectionFailed()
        }
        return result
    }

    suspend inline fun subscribeToTopic(request: () -> CompletableFuture<Mqtt3SubAck>): Result<Unit> {
        val result = try {
            val response = request().await()

            if (response.returnCodes.any { it.isError }) {
                Result.Failure.MQTT.SubscribeFailed()
            } else {
                Result.success<Unit>()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()

            Result.failure(
                message = if (exception.message != null) {
                    UIText.Hardcoded(exception.message!!)
                } else {
                    null
                }
            )
        }

        return result
    }

    suspend inline fun subscribeToTopics(request: () -> List<CompletableFuture<Mqtt3SubAck>>): Result<Unit> {
        val results =
            request()
                .map { subscriptionForATopic -> subscribeToTopic { subscriptionForATopic } }

        return if (results.all(Result<Unit>::isSuccess)) {
            Result.success()
        } else {
            Result.failure(results.first { it is Result.Failure }.message)
        }

    }

    suspend inline fun publishToTopic(request: () -> CompletableFuture<Mqtt3Publish>): Result<Unit> {
        val result = try {
            request().await()

            Result.success<Unit>()
        } catch (exception: Exception) {
            exception.printStackTrace()

            Result.Failure.MQTT.PublishingFailed()
        }

        return result
    }
}