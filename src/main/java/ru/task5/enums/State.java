package ru.task5.enums;

public enum State {
    Закрыт(0), Открыт(1), Зарезервирован(2), Удалён(3);

    int status;
    State(int status) {
        this.status = status;
    }
}
