package com.lagou.edu.service;


import java.util.List;

import com.lagou.edu.pojo.Resume;

public interface ResumeService {
    List<Resume> queryResumeList() throws Exception;
    Resume save(Resume resume) throws Exception;
    void delete(String id) throws Exception;
    Resume findById(String id);
}
