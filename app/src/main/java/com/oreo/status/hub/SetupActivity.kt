package com.oreo.status.hub

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.net.Uri
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.content.Intent
import android.util.Log
import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat


class SetupActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_PERMISSIONS = 1234
        private val handler = Handler(Looper.getMainLooper())

        private val PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
    private lateinit var context: Context

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let {
                Log.d("HEY: ", it.toString())
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                proceed()
            }
        }
    }
    //private val REQUEST_CODE_SAF = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)
        supportActionBar?.hide()

        context = applicationContext

        val button = findViewById<Button>(R.id.button2)

        // check permissions
        button.setOnClickListener {
            //launchSAFIntent()
            if (!arePermissionsDenied()) {
                proceed()
            } else {
                requestPermissionsIfNeeded()
            }

        }

    }

    private fun requestPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionsDenied()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermissionQ()
            } else {
                requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestPermissionQ() {
        val sm = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        val intent = sm.primaryStorageVolume.createOpenDocumentTreeIntent()
        val startDir = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses"

        var uri: Uri? = intent.getParcelableExtra("android.provider.extra.INITIAL_URI")
        uri?.let {
            var scheme = it.toString().replace("/root/", "/document/") + "%3A$startDir"
            uri = Uri.parse(scheme)

            Log.d("URI", uri.toString())
            intent.putExtra("android.provider.extra.INITIAL_URI", uri)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or
                    Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION

            activityResultLauncher.launch(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS && grantResults.isNotEmpty()) {
            if (arePermissionsDenied()) {
                (getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()
                recreate()
            } else {
                proceed()
            }
        }
    }

    private fun arePermissionsDenied(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return contentResolver.persistedUriPermissions.isEmpty()
        }

        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(applicationContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    private fun proceed() {
        // Action to take if permissions are granted
        showToast("Permissions Granted")
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }


    private fun saveInitialFolder(folderUri: Uri) {
        // Save the initial folder URI to SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("savedUri", folderUri.toString())
        editor.apply()
    }
    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}