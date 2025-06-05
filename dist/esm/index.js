import { registerPlugin } from '@capacitor/core';
const AndroidHealthConnect = registerPlugin('AndroidHealthConnect', {
    web: () => import('./web').then((m) => new m.AndroidHealthConnectWeb()),
});
export * from './definitions';
export { AndroidHealthConnect };
//# sourceMappingURL=index.js.map