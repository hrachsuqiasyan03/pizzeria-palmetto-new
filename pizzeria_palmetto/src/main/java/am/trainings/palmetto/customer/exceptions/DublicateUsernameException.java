package am.trainings.palmetto.customer.exceptions;

import am.trainings.palmetto.common.BaseException;

public class DublicateUsernameException  extends BaseException {
    public DublicateUsernameException(String message) {
        super (message);
    }
}
