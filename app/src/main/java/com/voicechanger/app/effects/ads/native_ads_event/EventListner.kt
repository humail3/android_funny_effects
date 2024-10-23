package com.voicechanger.app.effects.ads.native_ads_event

interface EventListner {
    fun eventNotify(eventType: Int, eventObject: Any?): Int
}
