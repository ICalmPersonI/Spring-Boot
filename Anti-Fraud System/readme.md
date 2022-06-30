###Transaction
```POST /api/antifraud/transaction``` @RequestBody Transaction transaction
```PUT /api/antifraud/transaction``` @RequestBody TransactionFeedback feedback
```GET /api/antifraud/history```
```GET /api/antifraud/history/{(String) number}```
###Blacklist
```POST /api/antifraud/suspicious-ip``` @RequestBody IP ip
```GET /api/antifraud/suspicious-ip```
```DELETE /api/antifraud/suspicious-ip/{(String) ip} ```
```POST /api/antifraud/stolencard``` @RequestBody Card card
```GET /api/antifraud/stolencard```
```DELETE /api/antifraud/stolencard/{(String) number}```
###User
```POST /api/auth/user``` @RequestBody User user
```GET /api/auth/list```
```DELETE /api/auth/user/{(String) username}```
```PUT /api/auth/role``` @RequestBody UserRoleRequest request
```PUT /api/auth/access``` @RequestBody UserAccessRequest request
