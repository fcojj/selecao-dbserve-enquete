package com.github.enquete.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.enquete.model.Vote;
import com.github.enquete.model.VoteKey;

public interface Votes extends JpaRepository<Vote, VoteKey> {
}
