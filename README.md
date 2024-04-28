# T1 Homework 4

Реализована система аутентификации и авторизации с использованием Spring Security и JWT.

Модель данных:
- Сущность *User* с полями: id, firstName, lastName, username, password, role (тип данных *Role*);
  - Сущность *Role*: USER, ADMIN.
- Сущность *Token* с полями: id, token, loggedOut, user (тип данных *User*).

Через методы контроллера *AuthenticationController* осуществляется создание нового пользователя и генерация JWT токена при успешной аутентификации.\
Через методы контроллера *DemoController* осуществляется доступ к защищенным ресурсам на основе ролей.

Добавлена краткая документация к API с использованием OpenAPI 3 и Swagger UI:
![picture](https://github.com/dgrishin95/t1_homework4/blob/master/src/main/resources/static/picture.PNG?raw=true)
