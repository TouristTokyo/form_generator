# Автогенератор форм
Это простой автогенератор интерфейса форм.
<br>
Стек технологий: 
1) Sping Framework (backend)
2) Vue.js (frontend)

За основу разработки клиента (frontend) был взят проект [FormSchema Native](https://github.com/formschema/native)
## Как работает?
Пользователь вводит JSON в textarea и отправляет на сервер, где создаётся JsonSchema.
<br>
Далее, по данной схеме создаётся интерфейс формы.
## Какие типы Json поддерживаются?
Данный проект поддерживает слудющие типы Json:
1) Простой Json ( "key": "value" )
2) Array ( "key": [ "key": "value", "key": "value", "key": "value"  ] )
3) Object ( "key": { "key": "value", "key": "value", "key": "value" } )

Также допускается комбинация типов.
<br>
Например: "key": { "key": "value", "key": [ "key": "value", "key": "value"  ] } )
## Какие типы данных поддерживаются?
1) string
2) integer/number
3) array
4) object
5) enum
6) boolean
