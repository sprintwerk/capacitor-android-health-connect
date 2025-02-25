export interface AndroidHealthConnectPlugin {
  checkAvailability(): Promise<{ availability: HealthConnectAvailability }>;

  requestPermissions(options: { read: RecordType[]; write: RecordType[] }): Promise<void>;

  revokePermissions(): Promise<void>;

  readRecords(options: { start: string; end: string; type: RecordType }): Promise<any>;
}

export type HealthConnectAvailability = 'Available' | 'NotSupported' | 'NotInstalled';

export type RecordType = 'Steps' | 'Weight' | 'ActivitySession';
