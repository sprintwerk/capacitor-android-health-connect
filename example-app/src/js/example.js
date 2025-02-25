import { AndroidHealthConnect } from '@sprintwerk/capacitor-android-health-connect';

window.requestPermissions = () => {
    AndroidHealthConnect.requestPermissions({ read: ['Steps', 'Exercise', 'ActivitySession', 'Weight'], write: ['Steps'] }).then((result) => {
        console.log('Permissions', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}

window.checkAvailability = () => {
    AndroidHealthConnect.checkAvailability().then((result) => {
        console.log('Availability', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}

window.testRevokePermissions = () => {
    AndroidHealthConnect.revokePermissions().then((result) => {
        console.log('Revoke Permissions', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}

window.testGetWeight = () => {
    AndroidHealthConnect.readRecords({ start: '2025-02-25T00:01:00Z', end: '2025-02-25T22:01:00Z', type: 'Weight' }).then((result) => {
        console.log('Weight', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}

window.testGetActivities = () => {
    AndroidHealthConnect.readRecords({ start: '2025-02-25T00:01:00Z', end: '2025-02-25T22:01:00Z', type: 'ActivitySession' }).then((result) => {
        console.log('Activities', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}

window.testGetSteps = (pageToken) => {
    AndroidHealthConnect.readRecords({ start: '2025-02-25T00:01:00Z', end: '2025-02-25T22:01:00Z', type: 'Steps', pageToken: pageToken, pageSize: 3 }).then((result) => {
        console.log('Steps', result);

        if (result.nextPageToken) {
            testGetSteps(result.nextPageToken);
        }
    }).catch((error) => {
        console.error('Error', error);
    });
}
