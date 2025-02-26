package com.hussein.androidprojectstandard.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize
import androidx.compose.animation.SharedTransitionScope.PlaceHolderSize.Companion.contentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.content.ContextCompat
import com.hivemq.client.mqtt.datatypes.MqttTopic
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

import com.hussein.androidprojectstandard.data.base.MQTTEvent
import com.hussein.androidprojectstandard.presentation.main.LocalAnimatedContentScope
import com.hussein.androidprojectstandard.presentation.main.LocalSharedTransitionScope

@Composable
fun <T> ReceiveChannel<T>.ConsumeEach(onReceived: suspend (T) -> Unit) {
    LaunchedEffect(this) {
        for (stateEvent in this@ConsumeEach) {
            onReceived(stateEvent)
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
fun Modifier.optionalSharedElement(
    key: Any,
    placeHolderSize: PlaceHolderSize = contentSize,
    renderInOverlayDuringTransition: Boolean = true,
    zIndexInOverlay: Float = 0f,
): Modifier {
    return composed(
        "Optional Shared Element",
        key,
        placeHolderSize,
        renderInOverlayDuringTransition,
        zIndexInOverlay
    ) {
        LocalSharedTransitionScope.current?.let { sharedTransitionScope ->
            LocalAnimatedContentScope.current?.let { animatedContentScope ->
                with(sharedTransitionScope) {
                    sharedElement(
                        rememberSharedContentState(key),
                        animatedContentScope,
                        placeHolderSize = placeHolderSize,
                        renderInOverlayDuringTransition = renderInOverlayDuringTransition,
                        zIndexInOverlay = zIndexInOverlay
                    )
                }
            }
        } ?: Modifier
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalComposeUiApi::class)
fun Modifier.optionalSharedBounds(
    key: Any,
    placeHolderSize: PlaceHolderSize = contentSize,
    renderInOverlayDuringTransition: Boolean = true,
    zIndexInOverlay: Float = 0f,
): Modifier {
    return composed(
        "Optional Shared Element",
        key,
        placeHolderSize,
        renderInOverlayDuringTransition,
        zIndexInOverlay
    ) {
        LocalSharedTransitionScope.current?.let { sharedTransitionScope ->
            LocalAnimatedContentScope.current?.let { animatedContentScope ->
                with(sharedTransitionScope) {
                    sharedBounds(
                        rememberSharedContentState(key),
                        animatedContentScope,
                        placeHolderSize = placeHolderSize,
                        renderInOverlayDuringTransition = renderInOverlayDuringTransition,
                        zIndexInOverlay = zIndexInOverlay
                    )
                }
            }
        } ?: Modifier
    }
}

fun VectorDrawable.convertVectorDrawableToBitmap(
    width: Int? = null,
    height: Int? = null,
    @FloatRange(0.0, 360.0) rotation: Float = 0f,
): Bitmap? {
    try {
        val bitmap = Bitmap.createBitmap(
            width ?: intrinsicWidth,
            height ?: intrinsicHeight,
            Bitmap.Config.ARGB_8888,
        )


        val canvas = Canvas(bitmap)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)

        if (rotation > 0) {
            val matrix = Matrix()
            matrix.postRotate(rotation, bitmap.width / 2f, bitmap.height / 2f)
            val rotatedBitmap = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true,
            )
//            bitmap.recycle()
            return rotatedBitmap
        } else {
            return bitmap
        }
    } catch (e: OutOfMemoryError) {
        // Handle the error
        return null
    }

}

// region Date and Time
fun convertUtcToLocal(utcTimestamp: Long): String {
    val instant = Instant.ofEpochMilli(utcTimestamp)

    val localZone = ZoneId.systemDefault()
    val localDateTime = instant.atZone(localZone)

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return localDateTime.format(formatter)
}
// endregion

// region MQTT

fun MqttTopic.joinLevelsToString(): String {
    return levels.joinToString(MqttTopic.TOPIC_LEVEL_SEPARATOR.toString())
}

fun MqttTopic.isEqualsEvent(event: MQTTEvent) = joinLevelsToString() == event.topic

val JsonConverter by lazy {
    Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = true
    }
}

inline fun <reified T> Mqtt3Publish.decodeToObject() =
    JsonConverter.decodeFromString<T>(String(payloadAsBytes))


inline fun <reified T> T.encodeToJsonString(): String =
    JsonConverter.encodeToString(serializer(), this)

// endregion

// region Esri Map

// endregion