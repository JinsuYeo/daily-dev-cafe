package com.jsyeo.dailydevcafe;


import com.jsyeo.dailydevcafe.domain.Post;
import com.jsyeo.dailydevcafe.domain.member.Member;
import com.jsyeo.dailydevcafe.dto.request.PublishPostRequestDto;
import com.jsyeo.dailydevcafe.dto.request.SignUpRequestDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            SignUpRequestDto memberDto = new SignUpRequestDto();
            memberDto.setName("memberA");
            memberDto.setEmail("test@test.com");
            memberDto.setNickname("testNickname");
            memberDto.setPassword("Pa$sw0rd");
            memberDto.setAgreedPersonal(true);

            Member member = new Member(memberDto);
            em.persist(member);

            PublishPostRequestDto postDto = new PublishPostRequestDto();
            postDto.setTitle("Test Title");
            postDto.setContent("Publish Post Test");
            postDto.setCategory("Test Category");

            Post post = new Post(postDto, member);
            em.persist(post);
        }
    }
}