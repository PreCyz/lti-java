package nn.lti.poc;

import nn.lti.poc.message.LtiErrorable;
import nn.lti.poc.message.ParamExtractor;

import static nn.lti.poc.message.error.LtiError.MISSING_LTI_VERSION;

public interface LtiVersion {

    String getValue();

    static LtiErrorable<LtiVersion> fromParams(ParamExtractor e) {
        return e.getFirst(LtiConstants.LTI_VERSION, MISSING_LTI_VERSION).map(LtiVersion::fromString);
    }

    static LtiVersion fromString(String version) {
        for (KnownLtiVersion type : KnownLtiVersion.values()) {
            if (type.getValue().equalsIgnoreCase(version)) {
                return type;
            }
        }
        return new UnknownLtiVersion(version);
    }

}
