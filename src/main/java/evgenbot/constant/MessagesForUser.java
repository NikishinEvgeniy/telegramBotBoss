package evgenbot.constant;

public enum MessagesForUser {
    SUCCESS("\n Информация обновлена \n"),
    SALARY_ERROR("\n Ошибка, введите корректную зарплату \n"),
    ID_ERROR("\n Ошибка, введите корректный ID \n"),
    START("\n Привет, рад видеть тебя, Начальник! \n"),
    UPDATE("\n Выберите, что хотите изменить \n" +
                    "1. Имя \n" +
                    "2. Фамилию \n" +
                    "3. Зарплату \n" +
                    "4. Место работы \n"),
    CHOOSE("\n Что вы хотите сделать с сотрудником \n" +
                    "1. Сократить \n" +
                    "2. Изменить контактные данные \n"),
    ADD("\n Добавление сотрудника \n"),
    HELP("\n Доступные комманды:\n" +
            "/show - показывает всех твоих сотрудников \n" +
            "/help - информация о доступных командах \n" +
            "/add - позволяет добавить нового сотрудника в коллектив\n" +
            "/change - позволяет изменить данные о сотруднике\n"),
    ERROR("\n Нет такого варианта ответа \n"),
    ID("\n Введите ID сотрудника \n"),
    DELETE("\n Сотрудник успешно сокращен \n"),
    ASK_NAME("\n Введите имя сотрудника \n"),
    ASK_SURNAME("\n Введите фамилию сотрудника \n"),
    ASK_SALARY("\n Введите зарплату сотрудника \n"),
    ASK_DEPARTMENT("\n Введите место работы сотрудника \n");

    private String description;

    MessagesForUser(String description){
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
