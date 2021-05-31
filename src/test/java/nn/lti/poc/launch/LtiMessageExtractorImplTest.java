package nn.lti.poc.launch;

import nn.lti.poc.KnownLtiVersion;
import nn.lti.poc.UnknownLtiVersion;
import nn.lti.poc.message.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static nn.lti.poc.launch.LaunchHelpers.e;
import static nn.lti.poc.message.error.LtiError.MISSING_LTI_VERSION;
import static nn.lti.poc.message.error.LtiError.MISSING_MESSAGE_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

public class LtiMessageExtractorImplTest {

    private LtiErrorable<LtiMessage> extract(Map.Entry<String, String>... entries) {
        LtiMessageExtractorImpl extractor = new LtiMessageExtractorImpl();
        return extractor.extractLtiMessage(Arrays.asList(entries));
    }

    @Test
    public void extractsLaunches() {

        LtiErrorable<LtiMessage> launch =
          extract(
            e("user_id", "1234"),
            e("lti_version", "LTI-1p0"),
            e("lti_message_type", "basic-lti-launch-request")
          );

        assertThat(launch.getErrors()).isEmpty();

        assertThat(launch.getEntity()).isPresent();

        assertThat(BasicLtiLaunchRequestMessage.class.isAssignableFrom(launch.getEntity().get().getClass())).isTrue();

        BasicLtiLaunchRequestMessage l = (BasicLtiLaunchRequestMessage) launch.getEntity().get();

        assertThat(l.user().id()).isEqualTo("1234");
        assertThat(l.version()).isEqualTo(KnownLtiVersion.One);
        assertThat(l.type()).isEqualTo(KnownMessageType.BasicLtiLaunchRequest);
    }

    @Test
    public void extractsExtensionMessageTypes() {

        LtiErrorable<LtiMessage> launch =
          extract(
            e("user_id", "1234"),
            e("lti_version", "LTI-1p0"),
            e("lti_message_type", "my-custom-extension-message-type")
          );

        assertThat(launch).isNotNull();

        assertThat(launch.getErrors()).isEmpty();

        assertThat(launch.getEntity()).isPresent();

        assertThat(launch.getEntity().get().type()).isEqualTo(ExtensionMessageType.of("my-custom-extension-message-type"));
    }

    @Test
    public void extractsUnknownLtiVersions() {

        LtiErrorable<LtiMessage> launch =
          extract(
            e("user_id", "1234"),
            e("lti_version", "LTI-5p8"),
            e("lti_message_type", "basic-lti-launch-request")
          );

        assertThat(launch.getEntity().get().version()).isEqualTo(UnknownLtiVersion.of("LTI-5p8"));
    }

    @Test
    public void shouldCollectErrors() {

        LtiErrorable<LtiMessage> launch =
          extract(
            e("asdf", "1234"),
            e("ffgae", "asfdsf")
          );

        assertThat(launch.getErrors()).hasSize(2);
        assertThat(launch.getErrors().contains(MISSING_LTI_VERSION)).isTrue();

        assertThat(launch.getErrors().contains(MISSING_MESSAGE_TYPE)).isTrue();
    }

}
