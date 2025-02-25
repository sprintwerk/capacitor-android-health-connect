package de.sprintwerk.capacitor.android.health.connect

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.impl.converters.datatype.RECORDS_TYPE_NAME_MAP
import org.json.JSONArray
import com.getcapacitor.JSObject

// Data class to hold valid permissions and any invalid record strings.
data class PermissionSetResult(
    val validPermissions: Set<String>,
    val invalidRecords: Set<String>
)

class AndroidHealthConnect {

    fun echo(value: String?): String? {
        Log.i("AndroidHealthConnect", value ?: "null")
        return value
    }

    /**
     * Builds a set of permission strings from two JSON arrays ("read" and "write").
     * Instead of throwing an exception for an unexpected record,
     * it collects the record in an invalidRecords set.
     */
    fun buildPermissionSet(
        readPermissions: JSONArray?,
        writePermissions: JSONArray?
    ): PermissionSetResult {
        val validPermissions = mutableSetOf<String>()
        val invalidRecords = mutableSetOf<String>()

        readPermissions?.let { array ->
            for (i in 0 until array.length()) {
                val record = array.optString(i)
                if (record.isNotEmpty()) {
                    val recordType = RECORDS_TYPE_NAME_MAP[record]
                    if (recordType != null) {
                        validPermissions.add(HealthPermission.getReadPermission(recordType = recordType))
                    } else {
                        invalidRecords.add(record)
                        Log.w("AndroidHealthConnect", "Read permission for record '$record' is not recognized")
                    }
                }
            }
        }

        writePermissions?.let { array ->
            for (i in 0 until array.length()) {
                val record = array.optString(i)
                if (record.isNotEmpty()) {
                    val recordType = RECORDS_TYPE_NAME_MAP[record]
                    if (recordType != null) {
                        validPermissions.add(HealthPermission.getWritePermission(recordType = recordType))
                    } else {
                        invalidRecords.add(record)
                        Log.w("AndroidHealthConnect", "Write permission for record '$record' is not recognized")
                    }
                }
            }
        }

        return PermissionSetResult(validPermissions, invalidRecords)
    }

    /**
     * Builds a JSObject result from a map of permission statuses.
     */
    fun buildPermissionResult(result: Map<String, Boolean>, invalidRecords: Set<String>): JSObject {
        val ret = JSObject()
        ret.put("granted", result.values.all { it })
        ret.put("permissions", result)
        ret.put("invalidRecords", invalidRecords.toList())
        return ret
    }

    /**
     * Checks Health Connect SDK availability and returns a JSObject with the result.
     */
    fun checkAvailability(context: Context): JSObject {
        val status = HealthConnectClient.getSdkStatus(context)
        val availability = when (status) {
            HealthConnectClient.SDK_AVAILABLE -> "Available"
            HealthConnectClient.SDK_UNAVAILABLE -> "NotSupported"
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> "NotInstalled"
            else -> throw RuntimeException("Invalid sdk status: $status")
        }
        return JSObject().apply { put("availability", availability) }
    }
}
