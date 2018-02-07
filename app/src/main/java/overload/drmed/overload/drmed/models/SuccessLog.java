package overload.drmed.overload.drmed.models;

/**
 * Created by Dalbo on 21/04/2017.
 */

public abstract class SuccessLog {

    boolean success;
    String message;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
