package TLabs.user_profile.controller;

import TLabs.user_profile.model.UserProfile;
import TLabs.user_profile.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/profiles")
public class AdminController {

    private final UserProfileService service;

    public AdminController(UserProfileService service) {
        this.service = service;
    }

    // Добавить или обновить поле анкеты профиля
    @PatchMapping("/{id}/fields")
    public ResponseEntity<UserProfile> addOrUpdateFields(
            @PathVariable Long id,
            @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(service.updateAdditionalFields(id, fields));
    }

    //Удалить поле с анкеты профиля
    @DeleteMapping("/{id}/fields/{fieldName}")
    public ResponseEntity<UserProfile> removeField(@PathVariable Long id, @PathVariable String fieldName) {
        return ResponseEntity.ok(service.deleteAdditionalField(id, fieldName));
    }

}
