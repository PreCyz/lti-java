package nn.lti.poc.message;

import nn.lti.poc.LtiConstants;
import org.inferred.freebuilder.FreeBuilder;

import java.util.Optional;

@FreeBuilder
public interface LaunchPresentation {

    Optional<String> locale();
    Optional<String> description();
    Optional<Target> documentTarget();

    static Builder builder() {
        return new Builder();
    }

    Builder toBuilder();

    class Builder extends LaunchPresentation_Builder {
        public Builder() {
            locale(Optional.empty());
            description(Optional.empty());
            documentTarget(Optional.empty());
        }
    }

    static LaunchPresentation fromParams(ParamExtractor e) {
        return builder()
          .locale(e.getFirst(LtiConstants.LAUNCH_PRESENTATION_LOCALE))
          .description(e.getFirst(LtiConstants.TOOL_CONSUMER_INSTANCE_DESCRIPTION))
          .documentTarget(e.getFirst(LtiConstants.LAUNCH_PRESENTATION_DOCUMENT_TARGET).flatMap(LaunchPresentation::getTarget))
          .build();
    }

    static Optional<Target> getTarget(String targetStr) {
        try {
            return Optional.of(Target.valueOf(targetStr.toLowerCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
