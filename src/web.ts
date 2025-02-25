import { WebPlugin } from '@capacitor/core';

import type { AndroidHealthConnectPlugin } from './definitions';

export class AndroidHealthConnectWeb extends WebPlugin implements AndroidHealthConnectPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);

    return options;
  }

  async checkAvailability(): Promise<{ availability: 'Available' | 'NotSupported' | 'NotInstalled' }> {
    console.warn('HealthConnect is not available on the web');

    return { availability: 'NotSupported' };
  }

  async requestPermissions(options: { read: string[]; write: string[] }): Promise<void> {
    console.warn('HealthConnect is not available on the web', options);
  }

  async readRecords(options: { startTime: string; endTime: string; record: string }): Promise<any> {
    console.warn('HealthConnect is not available on the web', options);
  }
}
