package com.hussein.androidprojectstandard.domain.base

import com.hussein.androidprojectstandard.R
import com.hussein.androidprojectstandard.utils.MessageType
import com.hussein.androidprojectstandard.utils.UIText

sealed class Result<T>() {

    abstract val message: UIText?

    companion object {
        fun <T> initial(message: UIText? = null) = Initial<T>(message)

        fun <T> loading(message: UIText? = null) = Loading.Generic<T>(message)

        fun <T> success(data: T? = null, message: UIText? = null): Success<T> = if (data != null) {
            Success.Data(data, message)
        } else {
            Success.Generic<T>(message)
        }

        fun <T> successWithData(data: T, message: UIText? = null) = Success.Data(data, message)

        fun <T> failure(message: UIText? = UIText.LocalizedString(R.string.message_generic_error)) =
            Failure.Generic<T>(message ?: UIText.LocalizedString(R.string.message_generic_error))
    }

    data class Initial<T>(override val message: UIText? = null) : Result<T>()

    sealed class Loading<T> : Result<T>() {
        data class Generic<T>(override val message: UIText? = null) : Loading<T>()

        data class Refreshing<T>(override val message: UIText? = null) : Loading<T>()
    }

    sealed class Success<T> : Result<T>() {
        data class Generic<T>(override val message: UIText? = null) : Success<T>()

        data class Data<T>(
            val data: T,
            override val message: UIText? = null,
        ) : Success<T>()
    }

    sealed class Failure<T> :
        Result<T>() {
        override val message: UIText = UIText.LocalizedString(
            R.string.message_generic_error,
            type = MessageType.Error
        )

        data class Generic<T>(
            override val message: UIText = UIText.LocalizedString(
                R.string.message_generic_error,
                type = MessageType.Error
            )
        ) :
            Failure<T>()

        data class NoInternetConnection<T>(
            override val message: UIText = UIText.LocalizedString(
                R.string.message_no_internet,
                type = MessageType.Error
            )
        ) : Failure<T>()

        data class ServerErrorException<T>(
            override val message: UIText = UIText.LocalizedString(
                R.string.message_server_error,
                type = MessageType.Error
            )
        ) : Failure<T>()

        data class BadRequestException<T>(
            override val message: UIText = UIText.LocalizedString(
                R.string.message_bad_request,
                type = MessageType.Error
            )
        ) : Failure<T>()

        data class Unauthorized<T>(
            override val message: UIText = UIText.LocalizedString(
                R.string.message_unauthorized,
                type = MessageType.Error
            )
        ) : Failure<T>()

        sealed class MQTT<T> : Failure<T>() {
            data class ConnectionFailed<T>(
                override val message: UIText = UIText.LocalizedString(
                    R.string.message_mqtt_connection_failed,
                    type = MessageType.Error
                )
            ) : MQTT<T>()

            data class DisconnectionFailed<T>(
                override val message: UIText = UIText.LocalizedString(
                    R.string.message_mqtt_disconnection_failed,
                    type = MessageType.Error
                )
            ) : MQTT<T>()

            data class SubscribeFailed<T>(
                override val message: UIText = UIText.LocalizedString(
                    R.string.message_mqtt_subscription_failed,
                    type = MessageType.Error
                )
            ) : MQTT<T>()


            data class UnsubscribeFailed<T>(
                override val message: UIText = UIText.LocalizedString(
                    R.string.message_mqtt_unsubscribe_failed,
                    type = MessageType.Error
                )
            ) : MQTT<T>()

            data class PublishingFailed<T>(
                override val message: UIText = UIText.LocalizedString(
                    R.string.message_mqtt_publishing_failed,
                    type = MessageType.Error
                )
            ) : MQTT<T>()
        }
    }

    val isLoading: Boolean
        get() = this is Loading

    val isSuccess: Boolean
        get() = this is Success

    val isSuccessWithData: Boolean
        get() = this is Success.Data

    val isFailure: Boolean
        get() = this is Failure
}