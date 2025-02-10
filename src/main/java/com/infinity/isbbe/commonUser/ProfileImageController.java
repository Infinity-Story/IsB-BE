package com.infinity.isbbe.commonUser;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
            // 파일 이름 생성 및 URL-safe 인코딩
            String originalFileName = file.getOriginalFilename();
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8.toString());  // URL 인코딩
            String fileName = System.currentTimeMillis() + "_" + encodedFileName;  // 파일 이름에 타임스탬프 추가
            Path path = Paths.get(uploadDir, fileName);

            // 이미지 파일 저장
            Files.createDirectories(path.getParent()); // 디렉토리 생성
            file.transferTo(path);

            // JWT 토큰에서 로그인한 사용자의 정보를 가져옵니다.
            String username = authentication.getName();
            System.out.println("Authenticated username: " + username);  // 디버깅 로그

            // Member 또는 Admin 조회
            Optional<Member> memberOptional = memberRepository.findByMemberId(username);  // Optional로 조회
            Optional<Admin> adminOptional = adminRepository.findByAdminId(username);    // Optional로 조회

            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();  // Optional에서 값 추출
                System.out.println("Authenticated member: " + member.getMemberId());  // 디버깅 로그
                member.setProfileImage(fileName);  // profile_image 컬럼에 파일 이름 저장
                memberRepository.save(member);  // Member 객체에 저장된 이미지 정보를 데이터베이스에 업데이트
            } else if (adminOptional.isPresent()) {
                Admin admin = adminOptional.get();  // Optional에서 값 추출
                System.out.println("Authenticated admin: " + admin.getAdminCode());  // 디버깅 로그
                admin.setProfileImage(fileName);  // profile_image 컬럼에 파일 이름 저장
                adminRepository.save(admin);  // Admin 객체에 저장된 이미지 정보를 데이터베이스에 업데이트
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok("Image uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            // URL-safe 인코딩된 파일 이름을 UTF-8로 디코딩
            String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8); // 예외 처리 필요 없음

            // 이미지 파일 경로를 구성
            Path filePath = Paths.get(uploadDir).resolve(decodedFileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)  // 이미지 타입 맞추기 (필요시 수정)
                        .body(resource);
            } else {
                throw new FileNotFoundException("File not found: " + decodedFileName);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
