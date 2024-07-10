# Генератор Чеков

## Функции
- Парсинг аргументов командной строки для получения продуктов, количества, информации о скидочной карте и балансе.
- Применение скидок на основе продуктов и скидочных карт.
- Проверка достаточности баланса для покрытия общей суммы с учетом скидок.
- Генерация подробного чека в формате CSV, включающего дату, время, продукты, скидки и итоговые суммы.

## Файлы и классы
### Основные классы
- **CheckRunner.java**
  - Точка входа в приложение. Парсит аргументы командной строки и инициирует генерацию чека.

- **CsvWriter.java**
  - Обрабатывает запись чека в CSV файл.

- **ParameterParser.java**
  - Парсит аргументы командной строки для создания объекта `SalesReceipt`. Также обрабатывает запись ошибок.

- **DiscountChecker.java**
  - Проверяет скидки для продуктов и скидочных карт.

- **Product.java**
  - Представляет продукт с такими свойствами, как id, описание, цена, количество на складе и статус оптовой продажи.

- **ProductFactory.java**
  - Создает объекты `Product` из CSV файла.

- **SalesReceipt.java**
  - Представляет чек, содержащий баланс, карту, продукты и итоговую сумму. Содержит класс Builder для упрощенного создания экземпляров.

### Ресурсы
- **discountCards.csv**
  - CSV файлс информацией о скидочных картах.

- **products.csv**
  - CSV файл с информацией о продуктах.

## Как запустить
### Аргументы командной строки
Программа ожидает аргументы командной строки в следующем формате:
```
java --enable-preview --source 22 -cp src src/main/java/ru/clevertec/check/CheckRunner.java <список продуктов> discountCard=<номер карты> balanceDebitCard=<баланс>
```
