package de.sprintwerk.capacitor.android.health.connect

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import com.getcapacitor.JSObject
import com.getcapacitor.PluginCall
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.PluginMethod
import com.getcapacitor.Plugin
import androidx.health.connect.client.PermissionController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.health.connect.client.HealthConnectClient

@CapacitorPlugin(name = "AndroidHealthConnect")
class AndroidHealthConnectPlugin : Plugin() {

    private val implementation = AndroidHealthConnect()
    private var permissionCall: PluginCall? = null
    private var requestedPermissions: Set<String> = emptySet()
    private var invalidRecords: Set<String> = emptySet()
    private lateinit var permissionLauncher: ActivityResultLauncher<Set<String>>

    override fun load() {
        super.load()
        // Ensure the activity is a ComponentActivity so we can register for activity results.
        val componentActivity = activity as? ComponentActivity
        if (componentActivity == null) {
            Log.e("AndroidHealthConnect", "Activity is not a ComponentActivity. Permission launcher cannot be registered.")
            return
        }
        permissionLauncher = componentActivity.registerForActivityResult(
            PermissionController.createRequestPermissionResultContract()
        ) { granted: Set<String> ->
            val resultMap = requestedPermissions.associateWith { it in granted }

            Log.d("AndroidHealthConnect", "Permissions granted: $resultMap")

            getGrantedPermissions(permissionCall!!)

            permissionCall = null
            requestedPermissions = emptySet()
            invalidRecords = emptySet()
        }
    }

    @PluginMethod
    fun checkAvailability(call: PluginCall) {
        try {
            val availability = implementation.checkAvailability(context)
            call.resolve(availability)
        } catch (e: RuntimeException) {
            call.reject(e.message)
        }
    }

    /**
     * Requests permissions by accepting two arrays: "read" and "write".
     * Returns a result that includes both the permission statuses and any invalid record types.
     */
    @PluginMethod
    override fun requestPermissions(call: PluginCall) {
        val readPermissionsArray = call.getArray("read")
        val writePermissionsArray = call.getArray("write")
        val result = implementation.buildPermissionSet(readPermissionsArray, writePermissionsArray)
        // Store both valid permissions and invalid records.
        requestedPermissions = result.validPermissions
        invalidRecords = result.invalidRecords

        // If there are any invalid records, reject the call.
        if (invalidRecords.isNotEmpty()) {
            call.reject("Invalid records specified: ${invalidRecords.joinToString()}")
            return
        }

        if (requestedPermissions.isEmpty()) {
            call.reject("No valid permissions specified. Invalid records: ${invalidRecords.joinToString()}")
            return
        }
        
        permissionCall = call
        permissionLauncher.launch(requestedPermissions)
    }

    @PluginMethod
    fun revokePermissions(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = HealthConnectClient.getOrCreate(context)
                client.permissionController.revokeAllPermissions()
                withContext(Dispatchers.Main) {
                    call.resolve()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    call.reject(e.message)
                }
            }
        }
    }

    /**
     * Retrieves the granted permissions and returns them to the JavaScript side.
     */
    @PluginMethod
    fun getGrantedPermissions(call: PluginCall) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = implementation.getGrantedPermissions(context)
                withContext(Dispatchers.Main) {
                    call.resolve(result)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    call.reject(e.message)
                }
            }
        }
    }

    /**
     * Reads records of the given type between start and end times.
     * Expects parameters: "type" (String), "start" (ISO 8601 String), and "end" (ISO 8601 String).
     */
    @PluginMethod
    fun readRecords(call: PluginCall) {
        val type = call.getString("type")
        val start = call.getString("start")
        val end = call.getString("end")
        val pageToken = call.getString("pageToken")
        val pageSize = call.getInt("pageSize")
        if (type == null || start == null || end == null) {
            call.reject("Missing parameters: 'type', 'start', and 'end' are required")
            return
        }
        // Launch a coroutine to run the suspend function.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = implementation.readRecords(context, type, start, end, pageSize, pageToken)
                withContext(Dispatchers.Main) {
                    call.resolve(result)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    call.reject(e.message)
                }
            }
        }
    }
}
