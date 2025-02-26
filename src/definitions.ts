export interface AndroidHealthConnectPlugin {
  checkAvailability(): Promise<{ availability: HealthConnectAvailability }>;

  requestPermissions(options: { read: RecordType[]; write: RecordType[] }): Promise<PermissionsResponse>;

  getGrantedPermissions(): Promise<PermissionsResponse>;

  revokePermissions(): Promise<void>;

  readRecords(options: {
    start: string;
    end: string;
    type: RecordType;
    pageSize?: number;
    pageToken?: string;
  }): Promise<ReadRecordsResponse>;
}

export type HealthConnectAvailability = 'Available' | 'NotSupported' | 'NotInstalled';

export type RecordType = 'Steps' | 'Weight' | 'ActivitySession';

export interface ReadRecordsResponse {
  records: any[];
  nextPageToken?: string;
}

export interface PermissionsResponse {
  read: RecordType[];
  write: RecordType[];
}
