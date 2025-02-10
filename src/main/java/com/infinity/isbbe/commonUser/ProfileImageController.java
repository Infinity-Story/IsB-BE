package com.infinity.isbbe.commonUser;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.config.JwtTokenProvider;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileImageController {

    @Value("${spring.upload.dir}")
    private String uploadDir;

    @Autowired
    private MemberRepository memberRepository;  // Member 테이블에 접근할 Repository

    @Autowired
    private AdminRepository adminRepository;  // Admin 테이블에 접근할 Repository

    @PostMapping("/uploadImage")
    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        try {
            // 파일 이름 생성
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);

            // 이미지 파일 저장
            Files.createDirectories(path.getParent()); // 디렉토리 생성
            file.transferTo(path);

            // JWT 토큰에서 로그인한 사용자의 정보를 가져옵니다.
            String username = authentication.getName();
            Object principal = authentication.getPrincipal();

            // 로그인한 사용자가 Member인지 Admin인지 확인
            if (principal instanceof Member) {
                Member member = (Member) principal;
                member.setProfileImage(fileName);  // profile_image 컬럼에 경로 저장
                memberRepository.save(member);  // 데이터베이스에 저장
            } else if (principal instanceof Admin) {
                Admin admin = (Admin) principal;
                admin.setProfileImage(fileName);  // profile_image 컬럼에 경로 저장
                adminRepository.save(admin);  // 데이터베이스에 저장
            }

            return ResponseEntity.ok("Image uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws FileNotFoundException {
        try {
            // 이미지 파일 경로를 구성
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")  // 이미지 타입 맞추기 (필요시 수정)
                        .body(resource);
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
