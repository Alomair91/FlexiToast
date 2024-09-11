package com.omairtech.flexitoast.messegner

import com.omairtech.flexitoast.toast.FlexiToastStyle

data class Message(
    val text: Text,
    var type: MessageType = MessageType.CUSTOM_TOAST,
    var style: FlexiToastStyle = FlexiToastStyle.SUCCESS,
)
