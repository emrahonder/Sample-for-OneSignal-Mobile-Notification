# Sample for OneSignal Notification Send
This sample shows how to send mobile notification with using OneSignal system.
OneSignal can send mobile notification very easily and it provides a restful webservice. Just player ID is required and this id is generated once an mobile application is setup and sent to OneSignal. This player ID can be got from OneSignal GUI.

To send request, JSON is needed. To generate JSON, I have used this lib (https://github.com/stleary/JSON-java), I have packaged this lib and added into project.
Sample Code:

```
		sendNotification("PLAYERID","TITLE","CONTENT");
```
