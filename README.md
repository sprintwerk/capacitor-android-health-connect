# @sprintwerk/capacitor-android-health-connect

Capacitor plugin to interact with Android Health Connect.

## ❗️Alpha - under active development❗️

Please keep in mind that this plugin is under active development and by far not everything Android Health Connect has to offer has been implemented yet. Currently only Steps, Weight and ActivitySession records can be read. More records and features to come soon!

## Install

```bash
npm install @sprintwerk/capacitor-android-health-connect@alpha
npx cap sync
```

Add the following to your app's `AndroidManifest.xml`:

```xml
<queries>
   <package android:name="com.google.android.apps.healthdata" />
</queries>
```

Also add this to your `AndroidManifest.xml`:

```xml
<application>
...
    <activity>
    ...

        <!-- Permission handling for Android 13 and before -->
        <intent-filter>
            <action android:name="androidx.health.ACTION_SHOW_PERMISSIONS_RATIONALE" />
        </intent-filter>

        <!-- Permission handling for Android 14 and later -->
        <intent-filter>
            <action android:name="android.intent.action.VIEW_PERMISSION_USAGE"/>
            <category android:name="android.intent.category.HEALTH_PERMISSIONS"/>
        </intent-filter>

    ...
    </activity>
</application>
```

### Permissions

You also need to add permissions for the records you want to read and/or write to the AndroidManifest.xml. A complete list of available records and the corresponding permissions can be found [here](https://developer.android.com/health-and-fitness/guides/health-connect/plan/data-types#permissions).

```xml
<!-- Example permissions -->
<uses-permission android:name="android.permission.health.READ_STEPS"/>
<uses-permission android:name="android.permission.health.WRITE_STEPS"/>
<uses-permission android:name="android.permission.health.READ_EXERCISE"/>
<uses-permission android:name="android.permission.health.WRITE_EXERCISE"/>
```

## API

<docgen-index>

* [`checkAvailability()`](#checkavailability)
* [`requestPermissions(...)`](#requestpermissions)
* [`getGrantedPermissions()`](#getgrantedpermissions)
* [`revokePermissions()`](#revokepermissions)
* [`readRecords(...)`](#readrecords)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkAvailability()

```typescript
checkAvailability() => Promise<{ availability: HealthConnectAvailability; }>
```

**Returns:** <code>Promise&lt;{ availability: <a href="#healthconnectavailability">HealthConnectAvailability</a>; }&gt;</code>

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options: { read: RecordType[]; write: RecordType[]; }) => Promise<PermissionsResponse>
```

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code>{ read: RecordType[]; write: RecordType[]; }</code> |

**Returns:** <code>Promise&lt;<a href="#permissionsresponse">PermissionsResponse</a>&gt;</code>

--------------------


### getGrantedPermissions()

```typescript
getGrantedPermissions() => Promise<PermissionsResponse>
```

**Returns:** <code>Promise&lt;<a href="#permissionsresponse">PermissionsResponse</a>&gt;</code>

--------------------


### revokePermissions()

```typescript
revokePermissions() => Promise<void>
```

--------------------


### readRecords(...)

```typescript
readRecords(options: { start: string; end: string; type: RecordType; pageSize?: number; pageToken?: string; }) => Promise<ReadRecordsResponse>
```

| Param         | Type                                                                                                                            |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ start: string; end: string; type: <a href="#recordtype">RecordType</a>; pageSize?: number; pageToken?: string; }</code> |

**Returns:** <code>Promise&lt;<a href="#readrecordsresponse">ReadRecordsResponse</a>&gt;</code>

--------------------


### Interfaces


#### PermissionsResponse

| Prop        | Type                      |
| ----------- | ------------------------- |
| **`read`**  | <code>RecordType[]</code> |
| **`write`** | <code>RecordType[]</code> |


#### ReadRecordsResponse

| Prop                | Type                |
| ------------------- | ------------------- |
| **`records`**       | <code>any[]</code>  |
| **`nextPageToken`** | <code>string</code> |


### Type Aliases


#### HealthConnectAvailability

<code>'Available' | 'NotSupported' | 'NotInstalled'</code>


#### RecordType

<code>'Steps' | 'Weight' | 'ActivitySession' | 'SleepSession' | 'RestingHeartRate'</code>

</docgen-api>

## Available records in Android Health Connect TODO: add this to the docs.

ActiveCaloriesBurned -> class androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
ActivitySession -> class androidx.health.connect.client.records.ExerciseSessionRecord
BasalBodyTemperature -> class androidx.health.connect.client.records.BasalBodyTemperatureRecord
BasalMetabolicRate -> class androidx.health.connect.client.records.BasalMetabolicRateRecord
BloodGlucose -> class androidx.health.connect.client.records.BloodGlucoseRecord
BloodPressure -> class androidx.health.connect.client.records.BloodPressureRecord
BodyFat -> class androidx.health.connect.client.records.BodyFatRecord
BodyTemperature -> class androidx.health.connect.client.records.BodyTemperatureRecord
BodyWaterMass -> class androidx.health.connect.client.records.BodyWaterMassRecord
BoneMass -> class androidx.health.connect.client.records.BoneMassRecord
CervicalMucus -> class androidx.health.connect.client.records.CervicalMucusRecord
CyclingPedalingCadenceSeries -> class androidx.health.connect.client.records.CyclingPedalingCadenceRecord
Distance -> class androidx.health.connect.client.records.DistanceRecord
ElevationGained -> class androidx.health.connect.client.records.ElevationGainedRecord
FloorsClimbed -> class androidx.health.connect.client.records.FloorsClimbedRecord
HeartRateSeries -> class androidx.health.connect.client.records.HeartRateRecord
HeartRateVariabilityRmssd -> class androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord
Height -> class androidx.health.connect.client.records.HeightRecord
Hydration -> class androidx.health.connect.client.records.HydrationRecord
LeanBodyMass -> class androidx.health.connect.client.records.LeanBodyMassRecord
Menstruation -> class androidx.health.connect.client.records.MenstruationFlowRecord
MenstruationPeriod -> class androidx.health.connect.client.records.MenstruationPeriodRecord
Nutrition -> class androidx.health.connect.client.records.NutritionRecord
OvulationTest -> class androidx.health.connect.client.records.OvulationTestRecord
OxygenSaturation -> class androidx.health.connect.client.records.OxygenSaturationRecord
PowerSeries -> class androidx.health.connect.client.records.PowerRecord
RespiratoryRate -> class androidx.health.connect.client.records.RespiratoryRateRecord
RestingHeartRate -> class androidx.health.connect.client.records.RestingHeartRateRecord
SexualActivity -> class androidx.health.connect.client.records.SexualActivityRecord
SkinTemperature -> class androidx.health.connect.client.records.SkinTemperatureRecord
SleepSession -> class androidx.health.connect.client.records.SleepSessionRecord
SpeedSeries -> class androidx.health.connect.client.records.SpeedRecord
IntermenstrualBleeding -> class androidx.health.connect.client.records.IntermenstrualBleedingRecord
Steps -> class androidx.health.connect.client.records.StepsRecord
StepsCadenceSeries -> class androidx.health.connect.client.records.StepsCadenceRecord
TotalCaloriesBurned -> class androidx.health.connect.client.records.TotalCaloriesBurnedRecord
Vo2Max -> class androidx.health.connect.client.records.Vo2MaxRecord
WheelchairPushes -> class androidx.health.connect.client.records.WheelchairPushesRecord
Weight -> class androidx.health.connect.client.records.WeightRecord
