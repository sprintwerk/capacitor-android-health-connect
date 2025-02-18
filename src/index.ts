import { registerPlugin } from '@capacitor/core';

import type { AndroidHealthConnectPlugin } from './definitions';

const AndroidHealthConnect = registerPlugin<AndroidHealthConnectPlugin>('AndroidHealthConnect', {
  web: () => import('./web').then((m) => new m.AndroidHealthConnectWeb()),
});

export * from './definitions';
export { AndroidHealthConnect };
