import { WebPlugin } from '@capacitor/core';

import type { AndroidHealthConnectPlugin } from './definitions';

export class AndroidHealthConnectWeb extends WebPlugin implements AndroidHealthConnectPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
