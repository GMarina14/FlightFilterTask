# **Flight Filter Task**

## **Краткое описание:**
Приложение позволяет исключить из коллекции перелетов не подходящие по критериям (отдельно по каждому условию):
* Вылет до текущего момента времени.
* Сегменты с датой прилёта раньше даты вылета.
* Перелеты, где общее время, проведённое на земле, превышает два часа (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним).

## **Стек технологий**
 
- _Язык и окружение_: Java 17
- _Тестирование_: JUnit,  интеграционное тестирование