package data;

public class GuardReturnWhenTestData {

    enum Code { NOT_FOUND, CONFLICT, RATE_LIMITED }

    interface Status {
        boolean matches(Code code);
    }

    record Response(Status status, String body) {
    }

    record Result(int httpStatus, String body) {
        static Result ok(Response response) {
            return new Result(200, response.body());
        }
    }

    Result handle(Response response) {
        Status status = response.status();
        when { status.matches(Code.NOT_FOUND) -> return notFound(response)
        status.matches(Code.CONFLICT) -> return conflict(response)
        status.matches(Code.RATE_LIMITED) -> return retryLater(response)
        else -> return Result.ok(response) }
    }

    String classify(int size) {
        when { size == 0 -> return "empty"
        size < 10 -> return "small"
        else -> return "large" }
    }

    void dispatch(String command) {
        when { command.isEmpty() -> return
        command.startsWith("#") -> return
        else -> return }
    }

    String onlyTheTail(String name, int size) {
        if (name == null) {
            name = "unknown";
            return name;
        }
        when { size == 0 -> return name
        size == 1 -> return name + "!"
        else -> return name + size }
    }

    String singleGuard(String name) {
        if (name == null) {
            return "unknown";
        }
        return name;
    }

    String commentInBranch(int size) {
        if (size == 0) {
            // nothing to show
            return "empty";
        }
        if (size < 10) {
            return "small";
        }
        return "large";
    }

    String commentBetween(int size) {
        if (size == 0) {
            return "empty";
        }
        // the common case
        if (size < 10) {
            return "small";
        }
        return "large";
    }

    String elseBranch(int size) {
        if (size == 0) {
            return "empty";
        } else if (size < 10) {
            return "small";
        }
        return "large";
    }

    String noFinalReturn(int size) {
        if (size == 0) {
            return "empty";
        }
        if (size < 10) {
            return "small";
        }
        String label = "large";
        return label;
    }

    private Result notFound(Response response) {
        return new Result(404, response.body());
    }

    private Result conflict(Response response) {
        return new Result(409, response.body());
    }

    private Result retryLater(Response response) {
        return new Result(429, response.body());
    }
}
