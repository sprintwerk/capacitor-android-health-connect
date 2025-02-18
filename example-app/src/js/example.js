import { AndroidHealthConnect } from '@sprintwerk/capacitor-android-health-connect';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    AndroidHealthConnect.echo({ value: inputValue })
}
