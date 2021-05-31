package nn.lti.poc.launch.error;

/**
 * The request did not include a base string.
 */
public class MissingBaseString implements OauthVerificationError {

    @Override
    public String getMessage() {
        return "The request did not include a base string";
    }
}
