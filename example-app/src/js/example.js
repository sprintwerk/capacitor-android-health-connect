import { AndroidHealthConnect } from '@sprintwerk/capacitor-android-health-connect';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    AndroidHealthConnect.echo({ value: inputValue })
    try {
        
        console.log('FOO');
        AndroidHealthConnect.requestPermissions({ read: ['Steps', 'Exercise', 'ActivitySession'], write: ['Steps'] });
        console.log('BAR');
    } catch (error) {
        console.error('Error', error);
        
    }
}
