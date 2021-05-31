package nn.lti.poc.message;

import nn.lti.poc.JsonUtils;
import nn.lti.poc.LtiConstants;
import nn.lti.poc.contentitem.ContentItemSelection;
import nn.lti.poc.message.error.LtiError;
import nn.lti.poc.message.error.MalformedContentItems;
import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface ContentItemSelectionMessage extends LtiMessage {

    ContentItemSelection contentItems();

    static Builder builder() {
        return new Builder();
    }

    Builder toBuilder();
    /** Builder of instances. */
    class Builder extends ContentItemSelectionMessage_Builder {
        public Builder() {
            type(KnownMessageType.ContentItemSelection);
        }
    }

    static LtiErrorable<LtiMessage> fromLaunchContext(LtiLaunchContext launchContext, ParamExtractor e) {
        return LtiErrorable.from(e.getFirst(LtiConstants.CONTENT_ITEMS), LtiError.MISSING_CONTENT_ITEMS)
          .flatMap(
              contentItemStr -> {
                  try {
                      return LtiErrorable.success(JsonUtils.getMapper().readValue(contentItemStr, ContentItemSelection.class));
                  } catch(Exception exc) {
                      return LtiErrorable.error(new MalformedContentItems(contentItemStr));
                  }
              }
          ).map(contentItems -> (
            ContentItemSelectionMessage
              .builder()
              .version(launchContext.version())
              .contentItems(contentItems)
              .build()
          ));
    }

}
