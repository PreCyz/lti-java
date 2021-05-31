package nn.lti.poc.message;

import nn.lti.poc.KnownLtiVersion;
import nn.lti.poc.launch.LaunchHelpers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentItemSelectionMessageTest {

    @Test
    public void shouldBuildFromLaunchContext() {
        LtiLaunchContext context = LtiLaunchContext.builder()
          .consumer(ToolConsumer.builder().build())
          .context(Context.builder().build())
          .presentation(LaunchPresentation.builder().build())
          .version(KnownLtiVersion.One)
          .messageType(KnownMessageType.ContentItemSelection)
          .build();

        ParamExtractor extractor = new ParamExtractor(Arrays.asList(
          LaunchHelpers.e("content_items", "{}"),
          LaunchHelpers.e("custom_userid", "paul"),
          LaunchHelpers.e("ext_thing", "test")
        ));

        LtiErrorable<LtiMessage> message = ContentItemSelectionMessage.fromLaunchContext(context, extractor);

        assertThat(message.getErrors()).isEmpty();
    }
}
