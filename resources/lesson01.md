# Занятие 1 онлайн проекта <a href="https://github.com/JavaWebinar/topjava05">Topjava</a>

- **Напоминаю, что cвой код пишете только в ветках HWxx. Код в ветке MASTER трогать нельзя!
Модификация кода только через патчи в материалах урока (Apply Patch), иначе придется мержить код.
Все патчи объязательны!**

- **Делать Apply Patch лучше по одному непосредственно перед видео на эту тему - тогда при просмотре вы сразу сможете отслеживать изменения кода проекта.**

- **Код проекта постоянно модифицируется, поэтому в видео вы можете увидеть старую версию кода. Это иногда даже лучше, т.к. способствует лучшему пониманию кода и вы видете, каким образом проект развивался**

- **Не надо подробно читать все ссылки занятия, иначе времени не останется на ДЗ, что гораздо важнее. Пусть ссылки будут Вашим справочником, к которым вы в любое время можете обратиться.**

- <a href="https://github.com/JavaOPs/topjava/wiki/Git">Подсказка по работе с патчами</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfm5hSHEtbmxmN2kxb0NocVRwWl9KanowWXVCVXRZTlhaM09wQUswZkRidTA">Материалы урока</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFOGU0a3ZUbFo3Skk">HW0 (Optional): реализация getFilteredMealsWithExceeded через Stream API</a>
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFbVV3R3RBVGVfYjA">1_ HW0.patch</a>**
- <a href="http://prologistic.com.ua/polnoe-rukovodstvo-po-java-8-stream.html">Перевод "Java 8 Stream Tutorial"</a>
- <a href="https://docs.google.com/presentation/d/1fR1N_UsQDhOarLKo5nrgMU1r5-M8v-IbKhpS3sQTKnY">Основное в Java 8</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdTJIQUExajZWWkE">Работа с git в IDEA. HW0: реализация через цикл.</a>
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFckh5RDlxZEw2Tzg">2_ HW0_ cycles.patch</a>**

> Изменения в проекте: `map.getOrDefault` земенил на `map.merge`

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFRmo0YkVVaDJPTVE">Обзор используемых в проекте технологий. Интеграция ПО.</a>
-  <a href="http://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-for-2014/">Обзор популярности инструментов и технологий Java за 2014 г.</a>
-  <a href="http://www.youtube.com/watch?v=rJZHerwi8R0">Видео "Приложение Spring Pet Clinic"</a>
-  Приложение <a href="https://github.com/spring-projects/spring-petclinic">Spring Pet Clinic</a>.
-  Demo <a href="http://petclinic.cloudapp.net/">Spring Pet Clinic</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSlZMTXBJRXJpakU">Maven.</a>
- <a href="http://maven.apache.org/">Home Page</a>
- Среда сборки проектов <a href="http://www.apache-maven.ru/" target="_blank">Maven</a>.
- <a href="http://search.maven.org/#browse">The Central Repository</a>
- <a href="http://habrahabr.ru/post/111408/">Maven archetype</a>. Создание проекта на основе maven-archetype-webapp. Сборка проекта.
- Настройка пропертей Maven: кодировка, java version, зависимости, maven-compiler-plugin
- <a href="http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html">Build Lifecycle</a>.
- <a href="http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html">Dependency Mechanism</a>
- <a href="http://www.ibm.com/developerworks/ru/library/j-5things13/">Зависимости, профили</a>
- <a href="http://maven.apache.org/guides/mini/guide-multiple-modules.html">The Reactor</a>. Snapshots
- <a href="http://habrahabr.ru/blogs/java/106717/" target="_blank">Недостатки Maven</a>. Другие инструменты сборки.
- Ресурсы:
  - <a href="http://books.sonatype.com/mvnref-book/reference/index.html">Maven: The Complete Reference</a>
  - <a href="http://habrahabr.ru/post/77333/">Автоматизация сборки проекта</a>
  - <a href="http://www.sonatype.org/nexus/">Repository management Nexus</a>
  - <a href="http://blog.bintray.com/2014/02/11/bintray-as-pain-free-gateway-to-maven-central/">Bintray: gateway to Maven Central</a>
  - <a href="http://study-and-dev.com/blog/build_management_maven_1/">Разработка ПО вместе с maven</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFT3pWNkMzWVVybnc&authuser=0">WAR. Веб-контейнер Tomcat. Сервлеты.</a>
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdy1mMmo4dnoteVk">3_ Switch_ to_ war_ packaging.patch</a>**
> Сервлет добавляется в следующем патче, те в web.xml он будет подчеркиваться красным.

- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFRWY4VkVFVjl2d2c">4_ Add_ servlet_ jsp_ html.patch</a>**
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFWi1MazZ5b1N4VHc">5_ Forward_ to_ redirect.patch</a>**

- Перевод проекта на Web.
- <a href="http://tomcat.apache.org/">Tomcat Home Page</a>
- <a href="http://java-course.ru/student/book1/servlet/">Сервлеты.</a>
- Настройка и деплой в Tomcat. Tomcat manager. Remote debug.
- Запуск Tomcat через tomcat7-maven-plugin. Конфигурирование плагина.
- Запуск Tomcat из IDEA. Динамическое обновление без передеплоя.
- Redirect, Forward, Application context, Servlet context
    - Томкат менеджер: http://localhost:8080/manager
    - Наше приложение: http://localhost:8080/topjava</a>
    - Наш сервлет:     http://localhost:8080/topjava/users

- Ресурсы:
  - <a href="https://www.youtube.com/watch?v=tN8K1y-HSws&list=PLkKunJj_bZefB1_hhS68092rbF4HFtKjW&index=14">Yakov Fain: Intro to Java EE. Glassfish. Servlets (по русски)</a>
  - <a href="https://www.youtube.com/watch?v=Vumy_fbo-Qs&list=PLkKunJj_bZefB1_hhS68092rbF4HFtKjW&index=15">Yakov Fain: HTTP Sessions, Cookies, WAR deployments, JSP (по русски)</a>
  - <a href="https://www.youtube.com/playlist?list=PLoij6udfBncjHaO5s7Ln4w4BLj5FVc49P">Golovach Courses: Junior.February2014.Servlets</a>
  - <a href="http://www.techinfo.net.ru/docs/web/javawebdev.html">Технологии Java для разработки веб-приложений</a>
  - <a href="http://blog.trifork.com/2014/07/14/how-to-remotely-debug-application-running-on-tomcat-from-within-intellij-idea">Remotely debug on tomcat from IDEA</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFeGJCdDJHbWNyUzg&authuser=0">Логирование.</a>
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFbG1SclBqOFJXb2M">6_ Add_ logging.patch</a>**

- <a href="http://habrahabr.ru/post/113145/">Java Logging: история кошмара</a>
- <a href="http://skipy.ru/useful/logging.html">Ведение лога приложения</a>
- <a href="http://www.slf4j.org/legacy.html">Добавление зависимостей логирования</a> в проект.
- <a href="http://logging.apache.org/log4j/2.x/index.html">Log4j</a>, <a href="http://logback.qos.ch/">Logback</a>
- Конфигурирование логирования. Создание обертки логирования. Настройка Live Template.
- Интеграция IDEA с Tomcat, деплой.
- Тестирование логирования в сервлете. Переменная окружения TOPJAVA_ROOT.
- Управление логированием по JMX.
- Контекст приложения. Деплой в Tomcat без IDE. Remote debug.

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSVRES241MlB2bkE">Уровни и зависимости логгирования. JMX</a>

## Домашнее задание (делаем ветку HW01 от последнего патча)

    По аналогии с Users добавить MealServlet и mealList.jsp.
    Задеплоить приложение (war) в Tomcat c applicationContext=topjava
    Попробовать разные деплои в Tomcat, remote и local debug

    Сделать отображения списка еды в jsp, цвет записи в таблице зависит от параметра isExceeded (красный/зеленый).
    Вариант реализации:
    -  из сервлета обращаетесь к реализации хранения еды в памяти;
    -  преобразуете результат в List<UserMealWithExceeded>;
    -  кладете список в запрос (request.setAttribute);
    -  делаете forward на jsp для отрисовки таблицы (при redirect аттрибуты теряются).
       В jsp для цикла можно использовать JSTL tag forEach.

Optional

    Сделать В ПАМЯТИ (HashMap) реализацию CRUD (create/read/update/delete) для списка еды.
    AJAX/JavaScript использовать не надо, делаем через <form method="post"> и doPost() в сервлете.

- Ресурсы:
  - <a href="http://java-course.ru/student/book1/servlet/">Интернет-приложения на JAVA</a>
  - <a href="http://stackoverflow.com/questions/246859/http-1-0-vs-1-1">HTTP 1.0 vs 1.1</a>
  - <a href="http://java-course.ru/student/book1/jsp/">JSP</a>
  - <a href="http://devcolibri.com/1250">JSTL для написания JSP страниц</a>
  - <a href="http://javatutor.net/articles/jstl-patterns-for-developing-web-application-1">JSTL: Шаблоны для разработки веб-приложений в java</a>
  - <a href="http://design-pattern.ru/patterns/mvc.html">MVC - Model View Controller</a>

---------

### Проверочные вопросы по занятию:

- Для подключения `JSTL` в `pom.xml` нужно добавить зависимость:
```
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```
С каким scope она добавляется и почему ?

- Определите по <a href="http://www.slf4j.org/legacy.html">зависимостям логирования</a> что нужно изменить в `pom.xml`, чтобы перейти с logback на log4j ?

### Ваши вопросы

>  Используются ли еще где-то в реальной разработке JSP, или это уже устаревшая технология? Заменит ли ее JSF?

JSF и JSP- разные ниши и задачи.
JSP- шаблонизатор, JSF- МVС фреймворк. Из моего опыта- с JSP сталкивался в 60% проектов. Его прямая замена: http://www.thymeleaf.org/, но пока встречется достаточно редко. По разным ссылкам вам JSP наругают и предложат кучу чего-то другого. Однако он не умирает, потому что просто и дешево. Кроме того включается в любой веб-контейнер (в Tomcat его реализация jasper)
JSF- sun-овский еще фреймворк, с которым я ни разу не сталкивался и особого желания нет. Вот он как раз, по моему мнению, активно замещается хотябы angular.
См. https://javatalks.ru/topics/38037

> А зачем мы использовали logback? Почему SLF4J нас не устроило? Почему реализация лоирования не log4j?

В `slf4j-api` это API. Там есть только пустая реализация `org.slf4j.helpers.NOPLogger` (можно посмотреть в исходниках).
Logback для новых проектов стал стандарт. spring-petclinic и spring-boot используют его. http://logback.qos.ch/reasonsToSwitch.html

### Решение проблем

- Если вы не попадаете на страничку/брекпойнт в сервлете:
  - внимательно проверьте url и applicationContext (Application Context должен быть тот же, что и url приложения): https://github.com/JavaOPs/topjava/wiki/IDEA
  - деплоить лучше как war exploded: просто нет упаковки в war и при нажатой кнопке `Update Resources on Frame Deactivation` можно обновляться css, html, jsp без передеплоя. При изменении web.xml, добавлении методов, классов необходим redeploy.
  - посомтрите в task manager: возможно у вас запущено несколько JVM и они мешают дург другу.

- Проблемы с кодировкой в POST. Возможное решение:
```
protected void doPost(HttpServletRequest request, ...) {
    request.setCharacterEncoding("UTF-8");
```

- Пробелема с maven зависимостью: иногда помогает удаление закаченного артифакта из локального maven репозитория (посмотреть его расположение можно в Maven Settings) и Reimport (последняя и первая кнопка в Maven Projects).

- Для работы с JSTL кроме добавления зависимости в `pom.xml` не забываем добавлять в шапку JSP:
```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
...
```

----------------------------

### Подсказки по HW01 (лучше сначала сделать самостоятельно, а потом проверить на соответствие)

- Реализация через HashMap подразумевает доступ по ключу! Поиск по ней означает, что вы используете неверную структуру данных.
В качестве ключа лучше добавить `id` в UserMeal/ UserMealWithExceed
- Хранение в HashMap - одна из наших реализаций (такжен будет jdbc, jpa и data-jpa). Т.е. с ней лучше работать через интерфейс, который НЕ ДОЛЖЕН НИЧЕГО знать о деталях реализации (Map, DB или что-то еще). Пока нет Spring, можно его инстанциировать в сервлете в методе `init(ServletConfig config)` (слои приложения будем разбирать на 2м занятии).
- Хранить нужно UserMeal и конвертировать ее в UserMealWithExceed когда отдаем список на отображение в JSP. Иначе при редактировании любой записи или изменении юзером своей нормы caloriesPerDay нужно будет пересчитывать все записи юзера.
- Стили `color` можно применять ко всей строке таблицы `tr`, а не каждой ячейке.