package org.example.splitwise.services;

import org.example.splitwise.models.SplitwiseGroup;
import org.example.splitwise.models.User;
import org.example.splitwise.repo.GroupRepo;
import org.example.splitwise.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private GroupRepo groupRepo;
    private UserRepo userRepo;

    public GroupService(GroupRepo groupRepo, UserRepo userRepo) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
    }

    public Optional<SplitwiseGroup> findById(Long groupId) {
        return groupRepo.findById(groupId);
    }
    public List<SplitwiseGroup> findAllGroups() {
        return groupRepo.findAll();
    }

    @Transactional
    public void save(SplitwiseGroup group) {
        groupRepo.saveAndFlush(group);
    }

    @Transactional
    public void addMember(Long memberId, Long groupId) {
        SplitwiseGroup group = this.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        User member = userRepo.findById(memberId).orElseThrow(() -> new RuntimeException("User not found"));;
        group.getMembers().add(member);
        member.getGroups().add(group);
    }
}
