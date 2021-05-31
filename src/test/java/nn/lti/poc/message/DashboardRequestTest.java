package nn.lti.poc.message;

import nn.lti.poc.KnownLtiVersion;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static nn.lti.poc.launch.LaunchHelpers.e;
import static org.assertj.core.api.Assertions.assertThat;

public class DashboardRequestTest {

    @Test
    public void shouldBuildFromLaunchContext() {
        LtiLaunchContext context = LtiLaunchContext.builder()
          .consumer(ToolConsumer.builder().build())
          .context(Context.builder().build())
          .presentation(LaunchPresentation.builder().build())
          .version(KnownLtiVersion.One)
          .messageType(KnownMessageType.DashboardRequest)
          .build();

        ParamExtractor extractor = new ParamExtractor(Arrays.asList(
          e("user_id", "1234"),
          e("custom_userid", "paul"),
          e("ext_thing", "test")
        ));

        LtiErrorable<LtiMessage> message = DashboardRequestMessage.fromLaunchContext(context, extractor);

        assertThat(message.getErrors()).isEmpty();
    }
}
