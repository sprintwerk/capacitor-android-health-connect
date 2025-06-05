import { WebPlugin } from '@capacitor/core';
import type { AndroidHealthConnectPlugin, PermissionsResponse, RecordType } from './definitions';
export declare class AndroidHealthConnectWeb extends WebPlugin implements AndroidHealthConnectPlugin {
    checkAvailability(): Promise<{
        availability: 'Available' | 'NotSupported' | 'NotInstalled';
    }>;
    requestPermissions(options: {
        read: RecordType[];
        write: RecordType[];
    }): Promise<PermissionsResponse>;
    getGrantedPermissions(): Promise<PermissionsResponse>;
    revokePermissions(): Promise<void>;
    readRecords(options: {
        start: string;
        end: string;
        type: RecordType;
    }): Promise<any>;
}
