package selectorSpecificity.validators;

public interface Execute {
    static boolean validate(int length) {
        return (length > 0) && (length < 20);
    }
}
