package nn.lti.poc;

import nn.lti.poc.launch.*;
import nn.lti.poc.message.LtiErrorable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

    static final Map<String, String> launchPramMap = new LinkedHashMap<>();

    final String consumerKey = "EUD15";
    final String consumerSecret = "4nSXFge2wXnMoBHK";
    final String launchUrl = "https://eu.rhapsode.com/integration/launch.php?plp=386&sign=bb9dc7eee662b95b97edab1260eafbca";

    @BeforeAll
    static void beforeAll() {
        launchPramMap.put(LtiConstants.USER_ID, "userId");
        launchPramMap.put(LtiConstants.LIS_PERSON_NAME_GIVEN, "Paweł");
        launchPramMap.put(LtiConstants.LIS_PERSON_NAME_FAMILY, "Gawędzki");
        launchPramMap.put(LtiConstants.LIS_PERSON_CONTACT_EMAIL_PRIMARY, "pawg@netcompany.com");
        launchPramMap.put(LtiConstants.LAUNCH_PRESENTATION_LOCALE, "pl-PL");
        launchPramMap.put(LtiConstants.RESOURCE_LINK_ID, "L12444-X");
        launchPramMap.put(LtiConstants.CONTEXT_ID, "378267AD");
        launchPramMap.put(LtiConstants.CONTEXT_TITLE, "Mathematics 1A 2019");
        launchPramMap.put(LtiConstants.ROLES, "Instructor,Learner");
        launchPramMap.put(LtiConstants.LIS_OUTCOME_SERVICE_URL, "/bin/lti/results");
        launchPramMap.put(LtiConstants.LIS_RESULT_SOURCEDID, "our_id_that_connects_with_outcome_service_url");
        launchPramMap.put(LtiConstants.LAUNCH_PRESENTATION_RETURN_URL, "Return to LMS");
        launchPramMap.put("oauth_nonce", "set_after_signing");
        launchPramMap.put("oauth_signature", "set_after_signing");
        launchPramMap.put("oauth_timestamp", "set_after_signing");
        launchPramMap.put("oauth_consumer_key", "set_after_signing");
        launchPramMap.put("oauth_signature_method", "set_after_signing");
        launchPramMap.put("oauth_callback", "about:blank");
        launchPramMap.put("oauth_version", "1.0");
        launchPramMap.put(LtiConstants.LTI_MESSAGE_TYPE, "basic-lti-launch-request");
        launchPramMap.put(LtiConstants.LTI_VERSION, "LTI-1p0");
    }

    @Test
    void given_when_thenSign() throws LtiSigningException {
        LtiSigner signer = new LtiOauth10aSigner();
        Map<String, String> parameters = new HashMap<>();

        final Map<String, String> resultMap = signer.signParameters(parameters.entrySet(), consumerKey, consumerSecret, launchUrl, "POST");

        assertThat(resultMap).isNotEmpty();
        assertThat(resultMap.get("oauth_consumer_key")).isEqualTo(consumerKey);
        assertThat(resultMap.get("oauth_signature_method")).isEqualTo("HMAC-SHA1");
        assertThat(resultMap.get("oauth_nonce")).isNotEmpty();
        assertThat(resultMap.get("oauth_signature")).isNotEmpty();
        assertThat(resultMap.get("oauth_timestamp")).isNotEmpty();
        assertThat(resultMap.get("oauth_version")).isEqualTo("1.0");

        final LtiVerifier ltiVerifier = new LtiOauth10aVerifier();
        final LtiErrorable<Collection<? extends Map.Entry<String, String>>> result = ltiVerifier.verifyParameters(parameters.entrySet(), launchUrl, "POST", consumerSecret);

        assertThat(result).isNotNull();

    }

    @Test
    void given_when_then() throws LtiSigningException {
        LtiSigner signer = new LtiOauth10aSigner();
        Map<String, String> parameters = new HashMap<>();

        final Map<String, String> resultMap = signer.signParameters(
                parameters.entrySet(), consumerKey, consumerSecret, launchUrl, "POST"
        );

        launchPramMap.put("oauth_nonce", resultMap.get("oauth_nonce"));
        launchPramMap.put("oauth_signature", resultMap.get("oauth_signature"));
        launchPramMap.put("oauth_timestamp", resultMap.get("oauth_timestamp"));
        launchPramMap.put("oauth_consumer_key", resultMap.get("oauth_consumer_key"));
        launchPramMap.put("oauth_signature_method", resultMap.get("oauth_signature_method"));
        launchPramMap.put("oauth_version", resultMap.get("oauth_version"));

        assertThat(launchPramMap).isNotEmpty();
    }

    @Test
    void given_when_thenVerify() throws LtiSigningException {
        LtiSigner signer = new LtiOauth10aSigner();
        final Map<String, String> resultMap = signer.signParameters(new HashMap<String, String>().entrySet(), consumerKey, consumerSecret, launchUrl, "POST");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("oauth_consumer_key", consumerKey);
        final LtiVerifier ltiVerifier = new LtiOauth10aVerifier();

        final LtiErrorable<Collection<? extends Map.Entry<String, String>>> result = ltiVerifier.verifyParameters(
                parameters.entrySet(),
                launchUrl,
                "POST",
                resultMap.get("oauth_signature")
        );

        assertThat(result).isNotNull();
    }
}
