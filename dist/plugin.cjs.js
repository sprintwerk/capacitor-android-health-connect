'use strict';

var core = require('@capacitor/core');

const AndroidHealthConnect = core.registerPlugin('AndroidHealthConnect', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.AndroidHealthConnectWeb()),
});

class AndroidHealthConnectWeb extends core.WebPlugin {
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

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    AndroidHealthConnectWeb: AndroidHealthConnectWeb
});

exports.AndroidHealthConnect = AndroidHealthConnect;
//# sourceMappingURL=plugin.cjs.js.map
