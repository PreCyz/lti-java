package nn.lti.poc.message;

import nn.lti.poc.LtiVersion;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface LtiLaunchContext {

    ToolConsumer consumer();
    Context context();
    LaunchPresentation presentation();
    LtiVersion version();
    MessageType messageType();

    static Builder builder() {
        return new Builder();
    }

    Builder toBuilder();
    /** Builder of instances. */
    class Builder extends LtiLaunchContext_Builder { }

}
