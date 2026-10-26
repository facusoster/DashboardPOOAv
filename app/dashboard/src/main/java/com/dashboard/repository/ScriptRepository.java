package com.dashboard.repository;

import com.dashboard.model.Script;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptRepository
        extends JpaRepository<Script, Long> {

}