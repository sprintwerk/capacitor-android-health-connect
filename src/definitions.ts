export interface AndroidHealthConnectPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  checkAvailability(): Promise<{ availability: HealthConnectAvailability }>;

  requestPermissions(options: { read: string[]; write: string[] }): Promise<void>;

  readRecords(options: { startTime: string; endTime: string; record: string }): Promise<any>;
}

export type HealthConnectAvailability = 'Available' | 'NotSupported' | 'NotInstalled';
