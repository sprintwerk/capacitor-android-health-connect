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

window.testGetWeight = () => {
    AndroidHealthConnect.readRecords({ start: '2025-02-25T00:01:00Z', end: '2025-02-25T22:01:00Z', type: 'Weight' }).then((result) => {
        console.log('Weight', result);
    }).catch((error) => {
        console.error('Error', error);
    });
}
