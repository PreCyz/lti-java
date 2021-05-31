package nn.lti.poc.message;

import nn.lti.poc.LtiVersion;

import java.util.Map;

public interface LtiMessage {

    LtiVersion version();
    MessageType type();
    Map<String, String> customParameters();
    Map<String, String> extensionParameters();

}
