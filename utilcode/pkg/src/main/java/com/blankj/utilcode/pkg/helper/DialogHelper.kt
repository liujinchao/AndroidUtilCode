package com.blankj.utilcode.pkg.helper

import android.graphics.Bitmap
import android.support.v7.app.AlertDialog
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.pkg.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest
import com.blankj.utilcode.util.ToastUtils

/**
 * ```
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/10
 * desc  : helper about dialog
 * ```
 */
object DialogHelper {

    fun showRationaleDialog(shouldRequest: ShouldRequest) {
        val topActivity = ActivityUtils.getTopActivity()
        if (!ActivityUtils.isActivityAlive(topActivity)) return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> shouldRequest.again(true) }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> shouldRequest.again(false) }
                .setCancelable(false)
                .create()
                .show()
    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.getTopActivity()
        if (!ActivityUtils.isActivityAlive(topActivity)) return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> PermissionUtils.launchAppDetailsSettings() }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> }
                .setCancelable(false)
                .create()
                .show()
    }

    fun showKeyboardDialog() {
        val topActivity = ActivityUtils.getTopActivity()
        if (!ActivityUtils.isActivityAlive(topActivity)) return
        val dialogView = LayoutInflater.from(topActivity).inflate(R.layout.dialog_keyboard, null)
        val dialog = AlertDialog.Builder(topActivity).setView(dialogView).create()
        dialog.setCanceledOnTouchOutside(false)
        val keyboardDialogEt = dialogView.findViewById<EditText>(R.id.keyboardDialogEt)
        val listener = View.OnClickListener { v ->
            when (v.id) {
                R.id.keyboardDialogHideSoftInputBtn -> KeyboardUtils.hideSoftInput(keyboardDialogEt)
                R.id.keyboardDialogShowSoftInputBtn -> KeyboardUtils.showSoftInput(keyboardDialogEt)
                R.id.keyboardDialogToggleSoftInputBtn -> KeyboardUtils.toggleSoftInput()
                R.id.keyboardDialogCloseBtn -> {
                    KeyboardUtils.hideSoftInput(keyboardDialogEt)
                    dialog.dismiss()
                }
            }
        }
        dialogView.findViewById<View>(R.id.keyboardDialogHideSoftInputBtn).setOnClickListener(listener)
        dialogView.findViewById<View>(R.id.keyboardDialogShowSoftInputBtn).setOnClickListener(listener)
        dialogView.findViewById<View>(R.id.keyboardDialogToggleSoftInputBtn).setOnClickListener(listener)
        dialogView.findViewById<View>(R.id.keyboardDialogCloseBtn).setOnClickListener(listener)
        dialog.show()

//        val attributes = dialog.window.attributes
//        attributes.y = SizeUtils.dp2px(300f)
//        attributes.height = ScreenUtils.getScreenHeight() / 2
//        dialog.window.attributes = attributes
//        dialog.window.decorView.setPadding(0, 0, 0, 0)

        KeyboardUtils.fixAndroidBug5497(dialog.window)
    }

    fun showFragmentDialog(info: CharSequence) {
        val topActivity = ActivityUtils.getTopActivity()
        if (!ActivityUtils.isActivityAlive(topActivity)) return
        val dialogView = LayoutInflater.from(topActivity).inflate(R.layout.dialog_fragment, null)
        val aboutTv = dialogView.findViewById<TextView>(R.id.fragmentDialogAboutTv)
        aboutTv.movementMethod = ScrollingMovementMethod.getInstance()
        aboutTv.text = info
        val dialog = AlertDialog.Builder(topActivity).setView(dialogView).create()
        dialog.show()
    }

    fun showScreenshotDialog(screenshot: Bitmap) {
        val topActivity = ActivityUtils.getTopActivity()
        if (!ActivityUtils.isActivityAlive(topActivity)) return
        val dialogView = LayoutInflater.from(topActivity).inflate(R.layout.dialog_screen, null)
        val screenshotIv = dialogView.findViewById<ImageView>(R.id.screenDialogScreenshotIv)
        screenshotIv.setImageBitmap(screenshot)
        val dialog = AlertDialog.Builder(topActivity).setView(dialogView).create()
        dialog.show()
    }

    fun showToastDialog() {
        val topActivity = ActivityUtils.getTopActivity()
        if (!ActivityUtils.isActivityAlive(topActivity)) return
        val dialogView = LayoutInflater.from(topActivity).inflate(R.layout.dialog_toast, null)
        dialogView.findViewById<Button>(R.id.toastDialogShowShortToastBtn)
                .setOnClickListener { ToastUtils.showShort("Short") }
        val dialog = AlertDialog.Builder(topActivity).setView(dialogView).create()
//        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}
