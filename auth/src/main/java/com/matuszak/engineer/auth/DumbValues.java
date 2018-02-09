package com.matuszak.engineer.auth;

import com.matuszak.engineer.auth.model.dto.RegisterModel;
import com.matuszak.engineer.auth.repository.SubjectRepository;
import com.matuszak.engineer.auth.service.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DumbValues implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthFacade authFacade;
    private final SubjectRepository subjectRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        String password = "Mamcie12";

        subjectRepository.deleteAll();

        authFacade.register(new RegisterModel("dawid_matuszak@outlook.com", password, password));
        authFacade.register(new RegisterModel("dawid_sikorski@gmail.com", password, password));
        authFacade.register(new RegisterModel("marcin_tatus@onet.com", password, password));
        authFacade.register(new RegisterModel("mateusz_stanek@live.com", password, password));
        authFacade.register(new RegisterModel("michal_golabek@interia.pl", password, password));
    }
}
