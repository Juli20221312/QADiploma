Дипломное задание:
https://github.com/netology-code/qa-diploma


Для запуска проекта необходимо:

1. Выполнить в консоли команду: 
   git clone https://github.com/Juli20221312/QADiploma.git
2. Запустить на локальном компьютере Docker Desktop. 
3. Запустить контейнер в IntelliJ IDEA с помощью команды в консоли:  
docker-compose up --build
4. Выполнить запуск сервиса с указанием пути к базе данных с помощью одной из команд в консоли:
 - для mysql:
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar aqa-shop.jar
 - для postgresql:
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar aqa-shop.jar
5. Запустить тесты с параметрами, указав путь к базе данных в консоли:
 - для mysql:
./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
 - для postgresql
./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
6. Для формирования Allure отчетов выполнить команду в консоли:    
   ./gradlew allureServe  
7. Для переключения базы данных необходимо остановить приложение в консоли, где оно запущено с помощью команды "Ctrl+C" и запустить приложение в соответствии с п. 4.