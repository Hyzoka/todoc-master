package com.clean.up.repositorie;


import android.arch.lifecycle.LiveData;

import com.clean.up.dao.ProjectDao;
import com.clean.up.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    // --- GET ---

    public LiveData<List<Project>> getProjects() {
        return mProjectDao.getProjects();
    }
}
