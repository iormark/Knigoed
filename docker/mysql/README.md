Установка
=========
Дамп содержит первые 5000 записей.
Выполните скрипт из нужной директории:
```shell
dump.sh
sh dump.sh
```
Далее в Dockerfile указываем созданный архив:
```shell
ADD knigoed1.tar.gz /docker-entrypoint-initdb.d
```
Cобираем образ и запускаем:
```shell
docker-compose stop
docker-compose rm -v springknigoed_mysql_1
docker-compose build
docker-compose start
```

Миграции
========
```shell
mvn flyway:migrate
```