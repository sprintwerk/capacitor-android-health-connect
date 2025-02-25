# @sprintwerk/capacitor-android-health-connect

Capacitor plugin to interact with Android Health Connect

## Install

```bash
npm install @sprintwerk/capacitor-android-health-connect
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------

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
