package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.ResumeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeDao resumeDao;

	@Override
	public List<Resume> queryResumeList() throws Exception {
		return resumeDao.findAll();
	}

	@Override
	public Resume save(Resume resume) throws Exception {
		return resumeDao.save(resume);
	}

	@Override
	public void delete(String id) throws Exception {
		resumeDao.deleteById(Long.valueOf(id));
	}

	@Override
	public Resume findById(String id) {
		Optional<Resume> resume = resumeDao.findById(Long.valueOf(id));
		return resume.get();
	}
}
