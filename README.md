Для запуска нужно собрать приложение через docker-compose: <b>docker-compose up -d</b>. Затем внутри контейнера с ansible запустить playbook config.yml: <b>ansible-playbook -i hosts.hosts config.yml</b>.  

Nginx будет запущен на 8000 порте, а основное приложение (оно взято из предыдущего задания) - на 3000 порте.