package nn.lti.poc.message;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface ExtensionMessage extends LtiMessage {

    static Builder builder() {
        return new Builder();
    }

    Builder toBuilder();

    /**
     * Builder of instances.
     */
    class Builder extends ExtensionMessage_Builder { }

    static LtiErrorable<LtiMessage> fromLaunchContext(LtiLaunchContext launchContext, ParamExtractor e) {
        return LtiErrorable.success(
          ConfigureLaunchRequestMessage
            .builder()
            .type(launchContext.messageType())
            .version(launchContext.version())
            .build()
        );
    }
}
