# 📦 Client Orders Processing

## 📌 Описание проекта

Приложение обрабатывает файлы с заказами клиентов:

* читает данные из файлов
* парсит строки в объекты
* сортирует заказы по дате
* применяет скидку (уменьшается для каждого следующего клиента)
* записывает результат в новый файл

---

## ⚙️ Логика работы

1. 📥 Чтение файлов
2. 🔍 Парсинг строк → `ClientOrder`
3. 📊 Сортировка по `orderDate`
4. 💰 Расчёт цены с учётом скидки
5. 📤 Запись результата в новый файл

---

## 💸 Правила расчёта скидки

* первый клиент → **50% скидка**
* каждый следующий → **–5%**
* скидка не может быть меньше 0%

---

## 📁 Формат входных данных

Пример строки:

```
2024-01-01T10:00:00;CompanyName;100
```

Где:

* `orderDate` — дата заказа (`LocalDateTime`)
* `companyName` — название компании
* `ordersWeight` — вес заказа

---

## 📁 Выходной файл

Файл создаётся автоматически с суффиксом:

```
_calcul_result
```

Пример:

```
discount_day.txt → discount_day_calcul_result.txt
```

---

## ⚙️ Конфигурация

Файл:

```
src/main/resources/application.properties
```

Пример:

```
base.cost=100
start.discount=50
discount.step=5
files.paths=discount_day.txt,discount_day_without_ext
```

---

## 📂 Структура проекта

```
src/main/java/org/example
├── App.java
├── AppConfig.java
├── data
│   └── ClientOrder.java
└── services
    ├── DiscountCounter.java
    ├── Reader.java
    ├── Writer.java
    ├── parsers
    │   └── ClientParser.java
    └── utilites
```

---

## 🚀 Как запустить

### 1. Поместить входные файлы

Файлы должны находиться в корне проекта или в указанной директории:

```
discount_day.txt
discount_day_without_ext
```

> ⚠️ Файлы не добавляются в Git (игнорируются)

---

### 2. Запуск через Maven

```
./mvnw clean install
./mvnw exec:java -Dexec.mainClass="org.example.App"
```

---

## 🧪 Особенности реализации

* используется `Stream API`
* конфигурация через `application.properties`
* разделение на слои:

    * Reader
    * Parser
    * Service (DiscountCounter)
    * Writer

---

## ⚠️ Обработка ошибок

* некорректные строки пропускаются
* ошибки парсинга логируются

---

## 📌 Возможные улучшения

* использование `BigDecimal` для денежных расчётов
* логирование через `SLF4J`
* поддержка CSV/JSON форматов
* параллельная обработка файлов

---

## 👨‍💻 Автор
Максим Федоров

Проект выполнен в учебных целях для практики:

* Java Core
* Stream API
* работа с файлами
* архитектура приложения
