package TLabs.user_profile.repository;

import TLabs.user_profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository <UserProfile, Long> {
}
