# About
This project demonstrates (in a simplified form) the principles of anti-fraud systems in the financial sector. For this project, we will work on a system with an expanded role model, a set of REST endpoints responsible for interacting with users, and an internal transaction validation logic based on a set of heuristic rules.

# Endpoints
## Transaction
```POST /api/antifraud/transaction``` \
Request body:
```
{
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss"
}
````
Response:
200
```
{
  "result": <String>,
  "info": <String>
}
```
Change the rules for reviewing a transaction. A transaction containing a card number is PROHIBITED if:
There are transactions from more than 2 regions of the world other than the region of the transaction that is being verified in the last hour in the transaction history;
There are transactions from more than 2 unique IP addresses other than the IP of the transaction that is being verified in the last hour in the transaction history.
A transaction containing a card number is sent for MANUAL_PROCESSING if:
There are transactions from 2 regions of the world other than the region of the transaction that is being verified in the last hour in the transaction history;
There are transactions from 2 unique IP addresses other than the IP of the transaction that is being verified in the last hour in the transaction history.
If the validation process was successful, the endpoint should respond with the status HTTP OK (200) and return the following JSON:
If the result is ALLOWED, the info field must be set to none.

In the case of the PROHIBITED or MANUAL_PROCESSING result, the info field must contain the reason for rejecting the transaction. The reason must be separated by , and sorted alphabetically. For example, amount, card-number, ip, ip-correlation, region-correlation.

If a request contains wrong data, the region and date must be validated as described above, but the endpoint should respond with the status HTTP Bad Request (400).

```PUT /api/antifraud/transaction``` adds feedback for a transaction.\
Request body:
```
{
   "transactionId": <Long>,
   "feedback": "<String>"
}
```
Response:
```
{
  "transactionId": <Long>,
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss",
  "result": "<String>",
  "feedback": "<String>"
}
```
If the feedback for a specified transaction is already in the database, respond with the HTTP Conflict status (409).\
If the feedback has the wrong format (other than ALLOWED, MANUAL_PROCESSING, PROHIBITED), respond with the HTTP Bad Request status (400).\
If the feedback throws an Exception following the table, respond with the HTTP Unprocessable Entity status (422).\
If the transaction is not found in history, respond with the HTTP Not Found status (404).\

```GET /api/antifraud/history``` shows the transaction history.\
Response:
```
[
    {
      "transactionId": <Long>,
      "amount": <Long>,
      "ip": "<String value, not empty>",
      "number": "<String value, not empty>",
      "region": "<String value, not empty>",
      "date": "yyyy-MM-ddTHH:mm:ss",
      "result": "<String>",
      "feedback": "<String>"
    },
     ...
    {
      "transactionId": <Long>,
      "amount": <Long>,
      "ip": "<String value, not empty>",
      "number": "<String value, not empty>",
      "region": "<String value, not empty>",
      "date": "yyyy-MM-ddTHH:mm:ss",
      "result": "<String>",
      "feedback": "<String>"
    }
]
```

```GET /api/antifraud/history/{(String) number}``` ransaction history for a specified card number.

## Blacklist controller
```POST /api/antifraud/suspicious-ip``` saves suspicious IP addresses to the database\
Request body:
```
{
   "ip": "<String value, not empty>"
}
```
Responses:
```200```
```
{
   "id": "<Long value, not empty>",
   "ip": "<String value, not empty>"
}
```
If an IP is already in the database, respond with the ```HTTP Conflict status (409)```.\
If an IP address has the wrong format, respond with the ```HTTP Bad Request status (400)```.

```GET /api/antifraud/suspicious-ip``` shows IP addresses in the database.\
Response:
```
[
    {
        "id": 1,
        "ip": "192.168.1.1"
    },
     ...
    {
        "id": 100,
        "ip": "192.168.1.254"
    }
]
```

```DELETE /api/antifraud/suspicious-ip/{(String) ip}``` deletes IP addresses from the database.\
Response:
```
{
   "status": "IP <ip address> successfully removed!"
}
```
If an IP is not found in the database, respond with the HTTP Not Found status (404).
If an IP address has the wrong format (not following the Description section rules), respond with the HTTP Bad Request status (400)

```POST /api/antifraud/stolencard``` saves stolen card data in the database.\
Request body:
```
{
   "number": "<String value, not empty>"
}
```
Response:
200
```
{
   "id": "<Long value, not empty>",
   "number": "<String value, not empty>"
}
```
If the card number is already in the database, respond with the HTTP Conflict status (409).
If a card number has the wrong format, respond with the HTTP Bad Request status (400).

```GET /api/antifraud/stolencard``` shows card numbers stored in the database.\
Response:
```
[
    {
        "id": 1,
        "number": "4000008449433403"
    },
     ...
    {
        "id": 100,
        "number": "4000009455296122"
    }
]
```

```DELETE /api/antifraud/stolencard/{(String) number}``` deletes a card number from the database.
Response:
200
```
{
   "status": "Card <number> successfully removed!"
}
```
If a card number is not found in the database, respond with the HTTP Not Found status (404).
If a card number follows the wrong format (look at the Description section), respond with HTTP Bad Request status (400).

## User controller
```POST /api/auth/user``` registration.\
Request body:
```
{
   "id": <Long value, not empty>,
   "name": "<String value, not empty>",
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
```
Response:
If a user has been successfully added, the endpoint must respond with the HTTP CREATED status (201) and the following body:
```
{
   "id": <Long value, not empty>,
   "name": "<String value, not empty>",
   "username": "<String value, not empty>"
}
If an attempt to register an existing user was a failure, the endpoint must respond with the HTTP CONFLICT status (409);
If a request contains wrong data, the endpoint must respond with the BAD REQUEST status (400);
```

```GET /api/auth/list```\
Response:
The endpoint must respond with the HTTP OK status (200) and the body with an array of objects representing the users sorted by ID in ascending order. Return an empty JSON array if there's no information:
```
[
    {
        "id": <user1 id>,
        "name": "<user1 name>",
        "username": "<user1 username>",
        "role": "<user1 role>"
    },
     ...
    {
        "id": <userN id>,
        "name": "<userN name>",
        "username": "<userN username>",
        "role": "<userN role>"
    }
]
```

```DELETE /api/auth/user/{(String) username}``` delete user by username.\
Response:
```
{
   "username": "<username>",
   "status": "Deleted successfully!"
}
```
If a user is not found, respond with the HTTP Not Found status (404);
Change the POST /api/antifraud/transaction endpoint; it must be available only to all authorized users.

```PUT /api/auth/role``` changes user roles.\
Request body:
```
{
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
```
Response:
200
```
{
   "id": <Long value, not empty>,
   "name": "<String value, not empty>",
   "username": "<String value, not empty>",
   "role": "<String value, not empty>"
}
````
If a user is not found, respond with the HTTP Not Found status (404);
If a role is not SUPPORT or MERCHANT, respond with HTTP Bad Request status (400);
If you want to assign a role that has been already provided to a user, respond with the HTTP Conflict status (409);

```PUT /api/auth/access``` locks/unlocks users/
Request body:
```
{
   "username": "<String value, not empty>",
   "operation": "<[LOCK, UNLOCK]>"  // determines whether the user will be activated or deactivated
}
```
Response:
200
```
{
    "status": "User <username> <[locked, unlocked]>!"
}
```
For safety reasons, ADMINISTRATOR cannot be blocked. In this case, respond with the HTTP Bad Request status (400);
If a user is not found, the endpoint must respond with HTTP Not Found status (404).

