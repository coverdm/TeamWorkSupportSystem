package com.matuszak.projects.conversation;

import com.matuszak.projects.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversationPersistence {

    private final ConversationRepository conversationRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ConversationPersistence(ConversationRepository conversationRepository,
                                   ProjectRepository projectRepository) {
        this.conversationRepository = conversationRepository;
        this.projectRepository = projectRepository;
    }

//    public List<Conversation> getConversations(String projectUUID){
//        projectRepository.getProjectByUuid(projectUUID);
//
//    }
}
