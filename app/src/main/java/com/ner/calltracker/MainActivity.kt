package com.ner.calltracker

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.*


@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showCallPermissionsWithPermissionCheck()
        btn_autostart.setOnClickListener {
            autostartPermissionCheck()
        }

    }

    private fun autostartPermissionCheck() {
        try {
            val intent = Intent()
            val manufacturer = Build.MANUFACTURER
            when {
                "xiaomi".equals(manufacturer, ignoreCase = true) -> {
                    intent.component = ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                }
                "oppo".equals(manufacturer, ignoreCase = true) -> {
                    intent.component = ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                    )
                }
                "vivo".equals(manufacturer, ignoreCase = true) -> {
                    intent.component = ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                    )
                }
                "Letv".equals(manufacturer, ignoreCase = true) -> {
                    intent.component = ComponentName(
                        "com.letv.android.letvsafe",
                        "com.letv.android.letvsafe.AutobootManageActivity"
                    )
                }
                "Honor".equals(manufacturer, ignoreCase = true) -> {
                    intent.component = ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.process.ProtectActivity"
                    )
                }
            }
            val list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (list.size > 0) {
                startActivity(intent)
            }
        } catch (e: Exception) {
            Log.e("exc", e.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_PHONE_STATE
    )
    fun showCallPermissions() {
    }

    @OnPermissionDenied(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_PHONE_STATE
    )
    fun onCallPermissionDenied() {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        finish()
    }

    @OnShowRationale(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_PHONE_STATE
    )
    fun showRationaleForPermissions(request: PermissionRequest) {
        showRationaleDialog("Permission Needed for this app to work.", request)
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onPermissionNeverAskAgain() {

    }

    private fun showRationaleDialog(messageResId: String, request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton("Allow") { _, _ -> request.proceed() }
            .setNegativeButton("Deny") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(messageResId)
            .show()
    }
}