package nn.lti.poc.message;

import java.util.Optional;

public interface ConsumerBasedLaunch extends LtiMessage {

    User user();
    Optional<ToolConsumer> toolConsumer();
    Optional<Context> context();
    Optional<LaunchPresentation> launchPresentation();

}
