# chat_client

A chat client made to communicate with a school server and others connected to it.

## Main features

* enables connecting with select server
* receive and send global or private messages

## Side features

* user nickname is adjustable even when logged into the server
* message recipient can be selected from a list of active users by clicking
* if you are very bored it is possible to enable parrot and prime number counting robots

## Known issues

The chat client uses the deprecated "jackson.databind.util.ISO8601DateFormat" for date managment, 
however the format is also used by connected server, therefore it was not changed.