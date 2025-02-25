package TLabs.user_profile.service;

import TLabs.user_profile.model.UserProfile;

import java.util.Map;

public interface IUserProfileService {
    UserProfile getProfile(Long id);
    UserProfile createProfile(UserProfile profile);
    UserProfile updateProfile(Long id, UserProfile updatedProfile);
    UserProfile updateAdditionalFields(Long id, Map<String, Object> fields);
    UserProfile deleteAdditionalField(Long id, String fieldName);
}
