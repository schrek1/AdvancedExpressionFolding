package data;

@SuppressWarnings("ALL")
public class ConditionalAsIfElseSetterTestData {

    // J1 - a long ternary as the argument of a setter, on one line
    void copyProductName(Order order, Fixed fixedFields, Template template, Request request) {
        order.productName = fixedFields.productName ? template.productName : request.productNameOr("n/a");
    }

    // J2 - the same ternary wrapped over several lines
    void copyNotifyUrl(Order order, Fixed fixedFields, Template template, Request request) {
        order.notifyUrl = fixedFields.notifyUrl
                ? template.notifyUrl
                : request.notifyUrlOr("n/a");
    }

    // J3 - the ternary on a line of its own, after the opening parenthesis
    void copyCallbackUrl(Order order, Fixed fixedFields, Template template, Request request) {
        order.callbackUrl = if (fixedFields.callbackUrl) template.callbackUrl else request.callbackUrlOr("n/a");
    }

    static class Order {
        void setProductName(String productName) {
        }

        void setNotifyUrl(String notifyUrl) {
        }

        void setCallbackUrl(String callbackUrl) {
        }
    }

    static class Fixed {
        boolean isProductName() {
            return true;
        }

        boolean isNotifyUrl() {
            return true;
        }

        boolean isCallbackUrl() {
            return true;
        }
    }

    static class Template {
        String getProductName() {
            return "template";
        }

        String getNotifyUrl() {
            return "template";
        }

        String getCallbackUrl() {
            return "template";
        }
    }

    static class Request {
        String productNameOr(String fallback) {
            return fallback;
        }

        String notifyUrlOr(String fallback) {
            return fallback;
        }

        String callbackUrlOr(String fallback) {
            return fallback;
        }
    }
}
