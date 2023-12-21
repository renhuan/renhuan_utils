package com.renhuan.utils.eventBus

/**
 * created by renhuan
 * time : 2022/6/13 11:38
 * describe :
 */
import org.greenrobot.eventbus.EventBus

object REventBus {

    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    fun unregister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    fun sendEvent(event: Any) {
        EventBus.getDefault().post(event)
    }

    fun sendStickyEvent(event: Any) {
        EventBus.getDefault().postSticky(event)
    }

    fun isRegistered(subscriber: Any): Boolean {
        return EventBus.getDefault().isRegistered(subscriber)
    }

}