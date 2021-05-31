package nn.lti.poc.launch.error;

/**
 * No Oauth consumer key could be found in the request.
 */
public class MissingOauthKey implements OauthVerificationError {

    public MissingOauthKey() {}

    @Override
    public String getMessage() {
        return "There was no oauth_consumer_key in the request.";
    }
}
