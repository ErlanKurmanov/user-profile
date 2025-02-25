package TLabs.user_profile.controller;

import TLabs.user_profile.exceptions.ResourceNotFoundException;
import TLabs.user_profile.model.UserProfile;
import TLabs.user_profile.response.ApiResponse;
import TLabs.user_profile.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {
    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    //Создать профиль для теста остальных функций
    @PostMapping
    public ResponseEntity<ApiResponse> createProfile(@RequestBody UserProfile profile) {
        try{
            service.createProfile(profile);
            return ResponseEntity.ok(new ApiResponse("Add profile success!", profile));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


    //Обновить анкету профиля
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProfile(@PathVariable Long id, @RequestBody UserProfile profile) {
        try{
            UserProfile user = service.updateProfile(id, profile);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //Получить анкету
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProfile(id));
    }


    //Загрузить фото профиля
    @PostMapping("/{id}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (file.getSize() > 100 * 1024) {
            return ResponseEntity.badRequest().body("File size exceeds 100 KB");
        }

        try {
            UserProfile profile = service.getProfile(id);
            profile.setAvatar(file.getBytes());
            service.updateProfile(id, profile);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading avatar");
        }
    }


}
