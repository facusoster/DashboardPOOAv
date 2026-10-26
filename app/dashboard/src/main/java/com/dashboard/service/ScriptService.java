package com.dashboard.service;

import com.dashboard.repository.ScriptRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptRepository repository;

}