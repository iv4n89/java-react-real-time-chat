package com.chat.message_service.domain.valueobject;

public abstract class ValueObject<T> {
    protected final T value;

    public ValueObject(T value) {
        this.value = value;
        this.validate();
    }

    public T getValue() {
        return this.value;
    }

    protected abstract void validate();
    protected abstract String getValidationErrorMessage();
}
