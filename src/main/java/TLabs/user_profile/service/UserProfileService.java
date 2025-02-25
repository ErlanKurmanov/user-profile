package TLabs.user_profile.service;


import TLabs.user_profile.model.UserProfile;
import TLabs.user_profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserProfileService implements IUserProfileService{

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserProfile getProfile(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public UserProfile createProfile(UserProfile profile) {
        return repository.save(profile);
    }


    //Редактирование анкеты профиля
    @Override
    public UserProfile updateProfile(Long id, UserProfile updatedProfile) {
        return repository.findById(id)
                .map(profile -> {
                    profile.setNickname(updatedProfile.getNickname());
                    profile.setAvatar(updatedProfile.getAvatar());
                    profile.setExpertiseAreas(updatedProfile.getExpertiseAreas());
                    profile.setGender(updatedProfile.getGender());
                    profile.setBirthDate(updatedProfile.getBirthDate());
                    profile.setBirthTime(updatedProfile.getBirthTime());
                    profile.setBirthPlace(updatedProfile.getBirthPlace());
                    profile.setCurrentResidence(updatedProfile.getCurrentResidence());
                    profile.setAdditionalFields(updatedProfile.getAdditionalFields());
                    return repository.save(profile);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }


    //Добавить дополнительное поле с учетки админа
    @Override
    public UserProfile updateAdditionalFields(Long id, Map<String, Object> fields) {
        return repository.findById(id)
                .map(profile -> {
                    if (profile.getAdditionalFields() == null) {
                        profile.setAdditionalFields(new HashMap<>());
                    }
                    profile.getAdditionalFields().putAll(fields);
                    return repository.save(profile);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found"));

    }

    //Удалить дополнительное поле с учетки админа
    @Override
    public UserProfile deleteAdditionalField(Long id, String fieldName) {
        return repository.findById(id)
                .map(profile -> {
                    profile.getAdditionalFields().remove(fieldName);
                    return repository.save(profile);
                })
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }



}
