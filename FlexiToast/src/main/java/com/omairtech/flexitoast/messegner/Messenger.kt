package com.omairtech.flexitoast.messegner

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StringRes
import com.omairtech.flexitoast.toast.FlexiToast
import com.omairtech.flexitoast.toast.FlexiToastStyle
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class Messenger {
    private val channel = Channel<Message>(3, BufferOverflow.DROP_OLDEST)
    val actions = channel.receiveAsFlow()

    fun send(text: Text, type: MessageType, style: FlexiToastStyle) {
        channel.trySend(Message(text, type, style))
    }

    fun send(text: Text, style: FlexiToastStyle) {
        send(text, MessageType.CUSTOM_TOAST, style)
    }

    fun sendSuccess(text: Text, style: FlexiToastStyle = FlexiToastStyle.SUCCESS) {
        send(text, style)
    }

    fun sendSuccess(text: String, style: FlexiToastStyle = FlexiToastStyle.SUCCESS) {
        send(text.asText(), style)
    }

    fun sendSuccess(@StringRes id: Int, style: FlexiToastStyle = FlexiToastStyle.SUCCESS) {
        send(id.asText(), style)
    }

    fun sendError(text: Text, style: FlexiToastStyle = FlexiToastStyle.FAILURE) {
        send(text, style)
    }

    fun sendError(text: String, style: FlexiToastStyle = FlexiToastStyle.FAILURE) {
        send(text.asText(), style)
    }

    fun sendError(@StringRes id: Int, style: FlexiToastStyle = FlexiToastStyle.FAILURE) {
        send(id.asText(), style)
    }
}

fun handleMessenger(context: Context, message: Message) {
    when (message.type) {
        MessageType.ALERT -> {
            AlertDialog.Builder(context)
                .setMessage(message.text.value(context))
                .setPositiveButton(android.R.string.ok) { d: DialogInterface, _: Int -> d.dismiss() }
                .show()
        }

        MessageType.TOAST -> {
            Toast.makeText(context, message.text.value(context), Toast.LENGTH_SHORT).show()
        }

        MessageType.CUSTOM_TOAST -> {
            FlexiToast(context, message.text.value(context), message.style).show()
        }
    }
}