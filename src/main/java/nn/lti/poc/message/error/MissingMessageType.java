package nn.lti.poc.message.error;

/**
 * The launch didn't include an lti_message_type parameter
 */
public class MissingMessageType implements LtiError {
    @Override
    public String getMessage() {
        return "The launch didn't include an lti_message_type parameter";
    }
}
