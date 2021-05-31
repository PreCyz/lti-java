package nn.lti.poc.message;

import nn.lti.poc.KnownLtiVersion;
import nn.lti.poc.launch.LaunchHelpers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigureLaunchRequestTest {

    @Test
    public void shouldBuildFromLaunchContext() {
        LtiLaunchContext context = LtiLaunchContext.builder()
          .consumer(ToolConsumer.builder().build())
          .context(Context.builder().build())
          .presentation(LaunchPresentation.builder().build())
          .version(KnownLtiVersion.One)
          .messageType(KnownMessageType.ConfigureLaunchRequest)
          .build();

        ParamExtractor extractor = new ParamExtractor(Arrays.asList(
          LaunchHelpers.e("user_id", "1234"),
          LaunchHelpers.e("custom_userid", "paul"),
          LaunchHelpers.e("ext_thing", "test")
        ));

        LtiErrorable<LtiMessage> message = ConfigureLaunchRequestMessage.fromLaunchContext(context, extractor);

        assertThat(message.getErrors()).isEmpty();
    }
}
