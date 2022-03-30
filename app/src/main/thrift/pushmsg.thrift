namespace java ru.stackit.atm

struct PushMsget {
    1: Contextual context,
    2: string timestamp,
    3: bool isShowabl
}

union Contextual {
    1: PlainText text,
    2: TaskUuid task
}

struct PlainText {
    1: string text
}

struct TaskUuid {
    1: string uuidString
}