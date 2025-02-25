import { AndroidHealthConnect } from '@sprintwerk/capacitor-android-health-connect';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    AndroidHealthConnect.echo({ value: inputValue })
    try {
        
        console.log('FOO');
        AndroidHealthConnect.requestPermissions({ read: ['Steps', 'Exercise', 'ActivitySession', 'Weight'], write: ['Steps'] });
        console.log('BAR');
    } catch (error) {
        console.error('Error', error);
        
    }
}

window.checkAvailability = () => {
    AndroidHealthConnect.checkAvailability().then((result) => {
        console.log('Availability', result);
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

window.testGetSteps = () => {
    AndroidHealthConnect.readRecords({ start: '2025-02-25T00:01:00Z', end: '2025-02-25T22:01:00Z', type: 'Steps' }).then((result) => {
        console.log('Steps', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}
