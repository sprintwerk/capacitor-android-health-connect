package de.sprintwerk.capacitor.android.health.connect

import com.getcapacitor.JSObject
import com.getcapacitor.JSArray
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.ExerciseSegment
import androidx.health.connect.client.records.ExerciseLap
import androidx.health.connect.client.records.ExerciseRoute
import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.records.metadata.Device

/**
 * Converts a Health Connect record to a JSON-friendly object.
 * Currently, it provides custom conversion for WeightRecord;
 * for other types, it returns the record's toString() value.
 */
fun convertRecordToJson(record: Record): Any {
    return when (record) {
        is ExerciseSessionRecord -> {
            val obj = JSObject()
            obj.put("startTime", record.startTime.toString())
            obj.put("startZoneOffset", record.startZoneOffset?.toString() ?: "")
            obj.put("endTime", record.endTime.toString())
            obj.put("endZoneOffset", record.endZoneOffset?.toString() ?: "")
            obj.put("exerciseType", convertExerciseType(record.exerciseType))
            obj.put("exerciseTypeId", record.exerciseType)
            obj.put("title", record.title ?: "")
            obj.put("notes", record.notes ?: "")
            obj.put("metadata", convertMetadataToJson(record.metadata))
            // Convert segments into a JSArray.
            val segmentsArray = JSArray()
            record.segments.forEach { segment ->
                segmentsArray.put(convertExerciseSegmentToJson(segment))
            }
            obj.put("segments", segmentsArray)
            // Convert laps into a JSArray.
            val lapsArray = JSArray()
            record.laps.forEach { lap ->
                lapsArray.put(convertExerciseLapToJson(lap))
            }
            obj.put("laps", lapsArray)
            obj.put("plannedExerciseSessionId", record.plannedExerciseSessionId ?: "")
            obj
        }
        is StepsRecord -> {
            val obj = JSObject()
            obj.put("startTime", record.startTime.toString())
            obj.put("startZoneOffset", record.startZoneOffset?.toString() ?: "")
            obj.put("endTime", record.endTime.toString())
            obj.put("endZoneOffset", record.endZoneOffset?.toString() ?: "")
            obj.put("count", record.count)
            obj.put("metadata", convertMetadataToJson(record.metadata))
            obj
        }
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
    json.put("recordingMethod", convertRecordingMethodToString(metadata.recordingMethod))
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
 * Converts an ExerciseSegment to a JSON-friendly JSObject.
 *
 * ExerciseSegment(
 *     startTime: Instant,
 *     endTime: Instant,
 *     segmentType: Int,
 *     repetitions: Int
 * )
 */
fun convertExerciseSegmentToJson(segment: ExerciseSegment): JSObject {
    val json = JSObject()
    json.put("startTime", segment.startTime.toString())
    json.put("endTime", segment.endTime.toString())
    json.put("segmentType", convertExerciseSegmentType(segment.segmentType))
    json.put("repetitions", segment.repetitions)
    return json
}

/**
 * Converts an ExerciseLap to a JSON-friendly JSObject.
 *
 * ExerciseLap(
 *     startTime: Instant,
 *     endTime: Instant,
 *     length: Length?
 * )
 */
fun convertExerciseLapToJson(lap: ExerciseLap): JSObject {
    val json = JSObject()
    json.put("startTime", lap.startTime.toString())
    json.put("endTime", lap.endTime.toString())
    json.put("length", lap.length?.inMeters ?: null)
    json.put("unit", "m")
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

/**
 * Converts an exercise type integer to a descriptive string.
 */
fun convertExerciseType(type: Int): String {
    return when (type) {
        0  -> "EXERCISE_TYPE_OTHER_WORKOUT"
        2  -> "EXERCISE_TYPE_BADMINTON"
        4  -> "EXERCISE_TYPE_BASEBALL"
        5  -> "EXERCISE_TYPE_BASKETBALL"
        8  -> "EXERCISE_TYPE_BIKING"
        9  -> "EXERCISE_TYPE_BIKING_STATIONARY"
        10 -> "EXERCISE_TYPE_BOOT_CAMP"
        11 -> "EXERCISE_TYPE_BOXING"
        13 -> "EXERCISE_TYPE_CALISTHENICS"
        14 -> "EXERCISE_TYPE_CRICKET"
        16 -> "EXERCISE_TYPE_DANCING"
        25 -> "EXERCISE_TYPE_ELLIPTICAL"
        26 -> "EXERCISE_TYPE_EXERCISE_CLASS"
        27 -> "EXERCISE_TYPE_FENCING"
        28 -> "EXERCISE_TYPE_FOOTBALL_AMERICAN"
        29 -> "EXERCISE_TYPE_FOOTBALL_AUSTRALIAN"
        31 -> "EXERCISE_TYPE_FRISBEE_DISC"
        32 -> "EXERCISE_TYPE_GOLF"
        33 -> "EXERCISE_TYPE_GUIDED_BREATHING"
        34 -> "EXERCISE_TYPE_GYMNASTICS"
        35 -> "EXERCISE_TYPE_HANDBALL"
        36 -> "EXERCISE_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING"
        37 -> "EXERCISE_TYPE_HIKING"
        38 -> "EXERCISE_TYPE_ICE_HOCKEY"
        39 -> "EXERCISE_TYPE_ICE_SKATING"
        44 -> "EXERCISE_TYPE_MARTIAL_ARTS"
        46 -> "EXERCISE_TYPE_PADDLING"
        47 -> "EXERCISE_TYPE_PARAGLIDING"
        48 -> "EXERCISE_TYPE_PILATES"
        50 -> "EXERCISE_TYPE_RACQUETBALL"
        51 -> "EXERCISE_TYPE_ROCK_CLIMBING"
        52 -> "EXERCISE_TYPE_ROLLER_HOCKEY"
        53 -> "EXERCISE_TYPE_ROWING"
        54 -> "EXERCISE_TYPE_ROWING_MACHINE"
        55 -> "EXERCISE_TYPE_RUGBY"
        56 -> "EXERCISE_TYPE_RUNNING"
        57 -> "EXERCISE_TYPE_RUNNING_TREADMILL"
        58 -> "EXERCISE_TYPE_SAILING"
        59 -> "EXERCISE_TYPE_SCUBA_DIVING"
        60 -> "EXERCISE_TYPE_SKATING"
        61 -> "EXERCISE_TYPE_SKIING"
        62 -> "EXERCISE_TYPE_SNOWBOARDING"
        63 -> "EXERCISE_TYPE_SNOWSHOEING"
        64 -> "EXERCISE_TYPE_SOCCER"
        65 -> "EXERCISE_TYPE_SOFTBALL"
        66 -> "EXERCISE_TYPE_SQUASH"
        68 -> "EXERCISE_TYPE_STAIR_CLIMBING"
        69 -> "EXERCISE_TYPE_STAIR_CLIMBING_MACHINE"
        70 -> "EXERCISE_TYPE_STRENGTH_TRAINING"
        71 -> "EXERCISE_TYPE_STRETCHING"
        72 -> "EXERCISE_TYPE_SURFING"
        73 -> "EXERCISE_TYPE_SWIMMING_OPEN_WATER"
        74 -> "EXERCISE_TYPE_SWIMMING_POOL"
        75 -> "EXERCISE_TYPE_TABLE_TENNIS"
        76 -> "EXERCISE_TYPE_TENNIS"
        78 -> "EXERCISE_TYPE_VOLLEYBALL"
        79 -> "EXERCISE_TYPE_WALKING"
        80 -> "EXERCISE_TYPE_WATER_POLO"
        81 -> "EXERCISE_TYPE_WEIGHTLIFTING"
        82 -> "EXERCISE_TYPE_WHEELCHAIR"
        83 -> "EXERCISE_TYPE_YOGA"
        else -> "UNKNOWN_EXERCISE_TYPE"
    }
}

/**
 * Converts an exercise segment type integer to a descriptive string.
 */
fun convertExerciseSegmentType(type: Int): String {
    return when (type) {
        1  -> "EXERCISE_SEGMENT_TYPE_ARM_CURL"
        2  -> "EXERCISE_SEGMENT_TYPE_BACK_EXTENSION"
        3  -> "EXERCISE_SEGMENT_TYPE_BALL_SLAM"
        4  -> "EXERCISE_SEGMENT_TYPE_BARBELL_SHOULDER_PRESS"
        5  -> "EXERCISE_SEGMENT_TYPE_BENCH_PRESS"
        6  -> "EXERCISE_SEGMENT_TYPE_BENCH_SIT_UP"
        7  -> "EXERCISE_SEGMENT_TYPE_BIKING"
        8  -> "EXERCISE_SEGMENT_TYPE_BIKING_STATIONARY"
        9  -> "EXERCISE_SEGMENT_TYPE_BURPEE"
        10 -> "EXERCISE_SEGMENT_TYPE_CRUNCH"
        11 -> "EXERCISE_SEGMENT_TYPE_DEADLIFT"
        12 -> "EXERCISE_SEGMENT_TYPE_DOUBLE_ARM_TRICEPS_EXTENSION"
        13 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_CURL_LEFT_ARM"
        14 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_CURL_RIGHT_ARM"
        15 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_FRONT_RAISE"
        16 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_LATERAL_RAISE"
        17 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_ROW"
        18 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_LEFT_ARM"
        19 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_RIGHT_ARM"
        20 -> "EXERCISE_SEGMENT_TYPE_DUMBBELL_TRICEPS_EXTENSION_TWO_ARM"
        21 -> "EXERCISE_SEGMENT_TYPE_ELLIPTICAL"
        22 -> "EXERCISE_SEGMENT_TYPE_FORWARD_TWIST"
        23 -> "EXERCISE_SEGMENT_TYPE_FRONT_RAISE"
        24 -> "EXERCISE_SEGMENT_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING"
        25 -> "EXERCISE_SEGMENT_TYPE_HIP_THRUST"
        26 -> "EXERCISE_SEGMENT_TYPE_HULA_HOOP"
        27 -> "EXERCISE_SEGMENT_TYPE_JUMPING_JACK"
        28 -> "EXERCISE_SEGMENT_TYPE_JUMP_ROPE"
        29 -> "EXERCISE_SEGMENT_TYPE_KETTLEBELL_SWING"
        30 -> "EXERCISE_SEGMENT_TYPE_LATERAL_RAISE"
        31 -> "EXERCISE_SEGMENT_TYPE_LAT_PULL_DOWN"
        32 -> "EXERCISE_SEGMENT_TYPE_LEG_CURL"
        33 -> "EXERCISE_SEGMENT_TYPE_LEG_EXTENSION"
        34 -> "EXERCISE_SEGMENT_TYPE_LEG_PRESS"
        35 -> "EXERCISE_SEGMENT_TYPE_LEG_RAISE"
        36 -> "EXERCISE_SEGMENT_TYPE_LUNGE"
        37 -> "EXERCISE_SEGMENT_TYPE_MOUNTAIN_CLIMBER"
        38 -> "EXERCISE_SEGMENT_TYPE_OTHER_WORKOUT"
        39 -> "EXERCISE_SEGMENT_TYPE_PAUSE"
        40 -> "EXERCISE_SEGMENT_TYPE_PILATES"
        41 -> "EXERCISE_SEGMENT_TYPE_PLANK"
        42 -> "EXERCISE_SEGMENT_TYPE_PULL_UP"
        43 -> "EXERCISE_SEGMENT_TYPE_PUNCH"
        44 -> "EXERCISE_SEGMENT_TYPE_REST"
        45 -> "EXERCISE_SEGMENT_TYPE_ROWING_MACHINE"
        46 -> "EXERCISE_SEGMENT_TYPE_RUNNING"
        47 -> "EXERCISE_SEGMENT_TYPE_RUNNING_TREADMILL"
        48 -> "EXERCISE_SEGMENT_TYPE_SHOULDER_PRESS"
        49 -> "EXERCISE_SEGMENT_TYPE_SINGLE_ARM_TRICEPS_EXTENSION"
        50 -> "EXERCISE_SEGMENT_TYPE_SIT_UP"
        51 -> "EXERCISE_SEGMENT_TYPE_SQUAT"
        52 -> "EXERCISE_SEGMENT_TYPE_STAIR_CLIMBING"
        53 -> "EXERCISE_SEGMENT_TYPE_STAIR_CLIMBING_MACHINE"
        54 -> "EXERCISE_SEGMENT_TYPE_STRETCHING"
        55 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_BACKSTROKE"
        56 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_BREASTSTROKE"
        57 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_BUTTERFLY"
        58 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_FREESTYLE"
        59 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_MIXED"
        60 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_OPEN_WATER"
        61 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_OTHER"
        62 -> "EXERCISE_SEGMENT_TYPE_SWIMMING_POOL"
        0  -> "EXERCISE_SEGMENT_TYPE_UNKNOWN"
        63 -> "EXERCISE_SEGMENT_TYPE_UPPER_TWIST"
        64 -> "EXERCISE_SEGMENT_TYPE_WALKING"
        65 -> "EXERCISE_SEGMENT_TYPE_WEIGHTLIFTING"
        66 -> "EXERCISE_SEGMENT_TYPE_WHEELCHAIR"
        67 -> "EXERCISE_SEGMENT_TYPE_YOGA"
        else -> "UNKNOWN_SEGMENT_TYPE"
    }
}

fun convertRecordingMethodToString(method: Int): String {
    return when (method) {
        0 -> "RECORDING_METHOD_UNKNOWN"
        1 -> "RECORDING_METHOD_ACTIVELY_RECORDED"
        2 -> "RECORDING_METHOD_AUTOMATICALLY_RECORDED"
        3 -> "RECORDING_METHOD_MANUAL_ENTRY"
        else -> "RECORDING_METHOD_UNKNOWN"
    }
}

