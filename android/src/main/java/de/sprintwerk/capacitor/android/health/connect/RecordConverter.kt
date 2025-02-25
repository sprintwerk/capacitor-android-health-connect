package de.sprintwerk.capacitor.android.health.connect

import com.getcapacitor.JSObject
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.records.metadata.Device

/**
 * Converts a Health Connect record to a JSON-friendly object.
 * Currently, it provides custom conversion for WeightRecord;
 * for other types, it returns the record's toString() value.
 */
fun convertRecordToJson(record: Record): Any {
    return when (record) {
        is WeightRecord -> {
            val obj = JSObject()
            obj.put("time", record.time.toString())
            obj.put("zoneOffset", record.zoneOffset?.toString() ?: "")
            obj.put("value", record.weight.inKilograms)
            obj.put("unit", "kg")
            obj.put("metadata", convertMetadataToJson(record.metadata))
            obj
        }
        // Add additional custom conversions for other record types here if needed.
        else -> record.toString()
    }
}

/**
 * Converts Metadata to a JSON-friendly JSObject.
 *
 * Metadata consists of:
 * - id: String
 * - dataOrigin: DataOrigin (converted via toString())
 * - lastModifiedTime: Instant (converted via toString())
 * - clientRecordId: String?
 * - clientRecordVersion: Long
 * - device: Device? (converted with convertDeviceToJson)
 * - recordingMethod: Int
 */
fun convertMetadataToJson(metadata: Metadata): JSObject {
    val json = JSObject()
    json.put("id", metadata.id)
    json.put("dataOrigin", metadata.dataOrigin.packageName)
    json.put("lastModifiedTime", metadata.lastModifiedTime.toString())
    json.put("clientRecordId", metadata.clientRecordId)
    json.put("clientRecordVersion", metadata.clientRecordVersion)
    json.put("recordingMethod", metadata.recordingMethod)
    json.put("device", metadata.device?.let { convertDeviceToJson(it) } ?: null)
    return json
}

/**
 * Converts a Device object to a JSON-friendly JSObject.
 *
 * Device(manufacturer: String?, model: String?, type: Int)
 *
 * The type is mapped to a descriptive string based on:
 *  - TYPE_CHEST_STRAP = 7
 *  - TYPE_FITNESS_BAND = 6
 *  - TYPE_HEAD_MOUNTED = 5
 *  - TYPE_PHONE = 2
 *  - TYPE_RING = 4
 *  - TYPE_SCALE = 3
 *  - TYPE_SMART_DISPLAY = 8
 *  - TYPE_UNKNOWN = 0
 *  - TYPE_WATCH = 1
 */
fun convertDeviceToJson(device: Device): JSObject {
    val json = JSObject()
    json.put("manufacturer", device.manufacturer)
    json.put("model", device.model)
    json.put("type", convertDeviceTypeToString(device.type))
    return json
}

/**
 * Helper function to map a device type Int to a descriptive String.
 */
fun convertDeviceTypeToString(type: Int): String {
    return when (type) {
        7 -> "TYPE_CHEST_STRAP"
        6 -> "TYPE_FITNESS_BAND"
        5 -> "TYPE_HEAD_MOUNTED"
        2 -> "TYPE_PHONE"
        4 -> "TYPE_RING"
        3 -> "TYPE_SCALE"
        8 -> "TYPE_SMART_DISPLAY"
        0 -> "TYPE_UNKNOWN"
        1 -> "TYPE_WATCH"
        else -> "TYPE_UNKNOWN"
    }
}