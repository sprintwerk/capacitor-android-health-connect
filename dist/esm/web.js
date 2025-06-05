import { WebPlugin } from '@capacitor/core';
export class AndroidHealthConnectWeb extends WebPlugin {
    async checkAvailability() {
        console.warn('HealthConnect is not available on the web');
        return { availability: 'NotSupported' };
    }
    async requestPermissions(options) {
        console.warn('HealthConnect is not available on the web', options);
        return { read: [], write: [] };
    }
    async getGrantedPermissions() {
        console.warn('HealthConnect is not available on the web');
        return { read: [], write: [] };
    }
    async revokePermissions() {
        console.warn('HealthConnect is not available on the web');
    }
    async readRecords(options) {
        console.warn('HealthConnect is not available on the web', options);
    }
}
//# sourceMappingURL=web.js.map