package de.sprintwerk.capacitor.android.health.connect

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.impl.converters.datatype.RECORDS_TYPE_NAME_MAP
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.health.connect.client.records.Record
import com.getcapacitor.JSObject
import java.time.Instant
import kotlin.reflect.KClass
import org.json.JSONArray
import org.json.JSONObject
import com.getcapacitor.JSArray

// Data class to hold valid permissions and any invalid record strings.
data class PermissionSetResult(
    val validPermissions: Set<String>,
    val invalidRecords: Set<String>
)

class AndroidHealthConnect {

    /**
     * Builds a set of permission strings from two JSON arrays ("read" and "write").
     * Instead of throwing an exception for an unexpected record, it collects the record in an invalidRecords set.
     */
    fun buildPermissionSet(
        readPermissions: org.json.JSONArray?,
        writePermissions: org.json.JSONArray?
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
     * Builds a JSObject result from a map of permission statuses and invalid records.
     */
    fun buildPermissionResult(result: Map<String, Boolean>, invalidRecords: Set<String>): JSObject {
        val ret = JSObject()
        ret.put("granted", result.values.all { it })
        ret.put("permissions", result)
        ret.put("invalidRecords", invalidRecords.toList())
        return ret
    }

    /**
     * Retrieves the set of permissions that have been granted by the user.
     */
    suspend fun getGrantedPermissions(context: Context): JSObject {
        val client = HealthConnectClient.getOrCreate(context)
        // Assuming your API returns a set of granted permission strings.
        val granted: Set<String> = client.permissionController.getGrantedPermissions()
        
        // Create lists for read and write permission record names.
        val readList = mutableListOf<String>()
        val writeList = mutableListOf<String>()

        val readArray = JSArray()
        val writeArray = JSArray()
        
        for (perm in granted) {
            // Use reversePermission to convert the raw permission string.
            val mapping = reversePermission(perm)
            if (mapping != null) {
                val op = mapping.getString("operation") ?: continue
                val record = mapping.getString("record") ?: continue
                if (op.equals("read", ignoreCase = true)) {
                    // readList.add(record)
                    readArray.put(record)
                } else if (op.equals("write", ignoreCase = true)) {
                    // writeList.add(record)
                    writeArray.put(record)
                }
            }
        }
        
        val result = JSObject()
        result.put("read", readArray)
        result.put("write", writeArray)
        return result
    }

    /**
    * Given a permission string (e.g. "android.permission.health.READ_EXERCISE"),
    * this function reverses the mapping using RECORDS_TYPE_NAME_MAP and HealthPermission.
    * It returns a JSObject with:
    *  - "operation": either "read" or "write"
    *  - "record": the record name (for example, "ActivitySession")
    * Returns null if no match is found.
    */
    fun reversePermission(permission: String): JSObject? {
        // Iterate through each entry in RECORDS_TYPE_NAME_MAP.
        for ((recordName, recordType) in RECORDS_TYPE_NAME_MAP) {
            val expectedRead = HealthPermission.getReadPermission(recordType = recordType)
            val expectedWrite = HealthPermission.getWritePermission(recordType = recordType)
            if (expectedRead == permission) {
                val result = JSObject()
                result.put("operation", "read")
                result.put("record", recordName)
                return result
            } else if (expectedWrite == permission) {
                val result = JSObject()
                result.put("operation", "write")
                result.put("record", recordName)
                return result
            }
        }
        return null
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

    /**
     * Reads records of the given type between start and end times.
     *
     * @param context the Android context.
     * @param type a string key that should exist in RECORDS_TYPE_NAME_MAP.
     * @param start an ISO-8601 string representing the start time (e.g. "2023-01-01T00:00:00Z").
     * @param end an ISO-8601 string representing the end time.
     * @return a JSObject containing the list of records under the "records" key.
     */
    suspend fun readRecords(context: Context, type: String, start: String, end: String, pageSize: Int?, pageToken: String? = null): JSObject {
        val startInstant = Instant.parse(start)
        val endInstant = Instant.parse(end)
        // Look up the record class from the map. We expect RECORDS_TYPE_NAME_MAP to map 'type' to a Class<out Record>.
        val recordType = RECORDS_TYPE_NAME_MAP[type] ?: throw IllegalArgumentException("Unexpected RecordType: $type")

        val client = HealthConnectClient.getOrCreate(context)
        // Build a read records request.
        val request = ReadRecordsRequest(
            recordType = recordType,
            timeRangeFilter = TimeRangeFilter.between(startInstant, endInstant),
            pageSize = pageSize ?: 1000,
            pageToken = pageToken
        )
        // Execute the query.
        val response = client.readRecords(request)
        
        val recordsArray = com.getcapacitor.JSArray()
        // Use the separate converter file to convert each record.
        response.records.forEach { record ->
            recordsArray.put(convertRecordToJson(record))
        }
        val result = JSObject()
        result.put("records", recordsArray)
        result.put("nextPageToken", response.pageToken)
        return result
    }
}
