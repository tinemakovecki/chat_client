# chat_client

A chat client made to communicate with a school server and others connected to it.

## Main features

* enables connecting with select server
* receive and send global or private messages

## Side features

* possible to enable parrot and prime number counting robots if you are very bored
* user nickname is adjustable also when logged into the server
* message recipient can be selected by clicking from a list of active users

## Known issues

The chat client uses the deprecated "jackson.databind.util.ISO8601DateFormat" for date managment, 
however the format is also used by connected server, therfore it was not changed.