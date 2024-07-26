package evgenbot.constant;

public enum MessagesForUser {
    START("\n Привет, рад видеть тебя, Начальник! \n"),
    UPDATE(""),
    DELETE(""),
    ADD(""),
    SHOW(""),
    HELP("\n Доступные комманды:\n" +
            "/show - показывает всех твоих сотрудников \n" +
            "/add - позволяет добавить нового сотрудника в коллектив \n" +
            "/delete - позволяет сократить сотрудника\n" +
            "/update - позволяет изменить данные о сотруднике\n"),
    ERROR("\n Нет такого варианта ответа \n");
    private String description;

    MessagesForUser(String description){
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
