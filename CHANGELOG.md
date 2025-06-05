# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0-alpha.4] - 2025-01-28

### Added
- **SleepSession Record Support**: Added support for reading sleep session data from Android Health Connect
  - Includes sleep stages (AWAKE, SLEEPING, OUT_OF_BED, LIGHT, DEEP, REM)
  - Provides detailed sleep session information including start/end times, title, notes, and metadata
  
- **RestingHeartRate Record Support**: Added support for reading resting heart rate data
  - Provides beats per minute measurements with timestamp and metadata
  
- **Enhanced Example App**: Updated the example application to demonstrate the new record types
  - Added buttons to test Sleep Sessions and Resting Heart Rate functionality
  - Updated permission requests to include the new record types

### TypeScript API Changes
- Extended `RecordType` type to include `'SleepSession'` and `'RestingHeartRate'`
- All existing APIs remain unchanged for backward compatibility

### Android Implementation
- Added `SleepSessionRecord` and `RestingHeartRateRecord` imports
- Implemented JSON conversion for both new record types
- Added `convertSleepStageType()` function to map sleep stage integers to descriptive strings
- Enhanced record converter to handle sleep stage arrays and heart rate data

## [1.0.0-alpha.3] - Previous Release
- Initial implementation with Steps, Weight, and ActivitySession support 