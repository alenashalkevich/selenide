#language:ru
#encoding:UTF-8

@test
Функционал: Параметризированный запуск тестов

  Структура сценария: Проверка логина с именем <logIn> и паролем <password>
    Допустим открыта страница "https://www.saucedemo.com/"
    Когда введен логин "<logIn>" и пароль "<password>"
    Тогда на странице присутствует надпись "PRODUCTS"

    Примеры:
      | logIn         | password     |
      | standard_user | secret_sauce |
      | standard_user | secret_sauce |
      | standard_user | secret_sauce |

