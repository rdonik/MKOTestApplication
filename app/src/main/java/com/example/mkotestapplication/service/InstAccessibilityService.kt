package com.example.mkotestapplication.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.mkotestapplication.BuildConfig
import com.example.mkotestapplication.R


class InstAccessibilityService: AccessibilityService() {

    private val editTextNodes = ArrayList<AccessibilityNodeInfo>()
    private val alertDialogNodes = ArrayList<AccessibilityNodeInfo>()
    private var serviceInterface: ServiceInterface? = null

    fun setListener(serviceInterface: ServiceInterface) {
        this.serviceInterface = serviceInterface
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {

            Log.d(TAG, "onAccessibilityEvent: $event")

            if (it.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {

                editTextNodes.clear()

                findChildViews(it.source, TYPE_EDIT_TEXT)

                editTextNodes.forEach {
                    if (!it.isPassword) {
                        Log.d(TAG, "onAccessibilityPARENT: ${editTextNodes.size} ${it.text}")
                        serviceInterface?.onSuccessLogin(it.text.toString())
                        return@forEach
                    }
                }
            }

//            if (it.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//
//                alertDialogNodes.clear()
//
//                findChildViews(it.source, TYPE_ALERT_DIALOG)
//
//                if (alertDialogNodes.size > 0) {
//                    serviceInterface?.onSuccessLogin("")
//                }
//            }
        }

    }

    private fun findChildViews(parentView: AccessibilityNodeInfo?, viewType: String) {
        if (parentView?.className == null) {
            return
        }

        val childCount = parentView.childCount

        if (childCount == 0 && parentView.className.toString().contentEquals(viewType)) {
            when(viewType) {
                TYPE_EDIT_TEXT -> editTextNodes.add(parentView)
                TYPE_ALERT_DIALOG -> alertDialogNodes.add(parentView)
            }
        } else {
            for (i in 0 until childCount) {
                findChildViews(parentView.getChild(i), viewType)
            }
        }
    }

    override fun onInterrupt() {
        Log.e(TAG, "onInterrupt: something went wrong")
    }

    override fun onServiceConnected() {
        sSharedInstance = this

        val info = AccessibilityServiceInfo()
        info.apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK

            packageNames = arrayOf(getString(R.string.instagram_app_id), BuildConfig.APPLICATION_ID)

            feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN

            notificationTimeout = 100
        }

        this.serviceInfo = info
        Log.d(TAG, "onServiceConnected: onServiceConnected")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        sSharedInstance = null
        return super.onUnbind(intent)
    }


    companion object {
        private const val TAG = "InstAccessibilityService"
        private const val TYPE_EDIT_TEXT = "android.widget.EditText"
        private const val TYPE_ALERT_DIALOG = "android.app.AlertDialog"
        private var sSharedInstance: InstAccessibilityService? = null

        fun getSharedInstance(): InstAccessibilityService? {
            return sSharedInstance
        }
    }
}