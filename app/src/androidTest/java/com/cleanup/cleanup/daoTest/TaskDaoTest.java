package com.cleanup.cleanup.daoTest;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.cleanup.cleanup.LiveDataTestUtil;
import com.cleanup.cleanup.dao.TodocDatabase;
import com.cleanup.cleanup.model.Project;
import com.cleanup.cleanup.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    private static long PROJECT_ID = 1L;
    private static long PROJECT_ID_2 = 2L;
    private Project[] PROJECTS = Project.getAllProjects();
    private static Task NEW_TASK_ONE = new Task(PROJECT_ID,"Sweep the floor", new Date().getTime());
    private static Task NEW_TASK_TWO = new Task(PROJECT_ID_2,"Clean the bathroom", new Date().getTime());
    private static Task NEW_TASK_THREE = new Task(PROJECT_ID,"Wipe the bay window", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDatabase() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDatabase() {
        this.database.close();
    }

    @Test
    public void insertAndGetAllProjects() throws InterruptedException {
        for(Project project : PROJECTS) {
            this.database.projectDao().insertProjects(project);
        }
        List<Project> projects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());

        assertEquals(3, projects.size());
        assertTrue(projects.get(0).getName().equals(PROJECTS[0].getName()) && projects.get(0).getId() == PROJECTS[0].getId());
        assertTrue(projects.get(1).getName().equals(PROJECTS[1].getName()) && projects.get(1).getId() == PROJECTS[1].getId());
        assertTrue(projects.get(2).getName().equals(PROJECTS[2].getName()) && projects.get(2).getId() == PROJECTS[2].getId());
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        for(Project project : PROJECTS) {
            this.database.projectDao().insertProjects(project);
        }

        this.database.taskDao().insertTask(NEW_TASK_ONE);
        this.database.taskDao().insertTask(NEW_TASK_TWO);
        this.database.taskDao().insertTask(NEW_TASK_THREE);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertEquals(3, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        for(Project project : PROJECTS) {
            this.database.projectDao().insertProjects(project);
        }

        this.database.taskDao().insertTask(NEW_TASK_ONE);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks()).get(0);
        this.database.taskDao().deleteTask(taskAdded);

        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getAllTasks());
        assertTrue(tasks.isEmpty());
    }
}
