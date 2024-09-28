package com.bernacki.hrapp.service;

import com.bernacki.hrapp.entity.Project;
import com.bernacki.hrapp.entity.ProjectAssignmentId;
import com.bernacki.hrapp.entity.ProjectPhase;
import com.bernacki.hrapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectAssignmentService projectAssignmentService;
    private ProjectConsultantService projectConsultantService;
    private ProjectPhaseService projectPhaseService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectAssignmentService projectAssignmentService, ProjectConsultantService projectConsultantService, ProjectPhaseService projectPhaseService) {
        this.projectRepository = projectRepository;
        this.projectAssignmentService = projectAssignmentService;
        this.projectConsultantService = projectConsultantService;
        this.projectPhaseService = projectPhaseService;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(int id) {
        Optional<Project> result = projectRepository.findById(id);
        Project project = null;
        if(result.isPresent()){
            project= result.get();
        }
        return project;
    }



    @Override
    public List<ProjectPhase> findPhasesAssignedToProject(int id) {
        return projectRepository.findPhasesAssignedToProject(id);
    }

    @Override
    public List<Project> findProjectAssignedToClient(int id) {
        return projectRepository.findProjectAssignedToClient(id);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Transactional
    @Override
    public void deleteById(int projectId) {
        Optional<Project> result = projectRepository.findById(projectId);
        Project project = null;
        if(result.isPresent()){
            project = result.get();
            if(project.getProjectAssignments() !=null) {
                List<ProjectAssignmentId> projectAssignmentIdList = projectAssignmentService.findProjectAssignmentIdsByProjectId(projectId);
                if(!projectAssignmentIdList.isEmpty()){
                    projectAssignmentService.deleteAllByIds(projectAssignmentIdList);

                }
            }
            if(project.getProjectConsultant() != null){
                projectConsultantService.deleteByProjectId(projectId);
            }
            projectPhaseService.deleteAllByProjectId(projectId);
            projectRepository.deleteProjectByProjectId(projectId);
        }
    }

    @Override
    public Page<Project> findAllPaginated(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }


    @Override
    public Page<Project> findByTitle(String title, Pageable pageable) {
        String searchPattern = "%" + title + "%";
        return projectRepository.findByTitleLikeIgnoreCase(searchPattern, pageable);
    }

    @Override
    public Page<Project> findByProjectType(String projectType, Pageable pageable) {
        return projectRepository.findByProjectType(projectType, pageable);
    }

    @Override
    public Page<Project> findByCurrentPhase(String phase, Pageable pageable) {
        return projectRepository.findByCurrentPhase(phase, pageable);
    }

    @Override
    public Page<Project> getProjectPageSearched( String searchBy,Map<String,String> searchParams, Pageable pageable){
        Page<Project> projectPage = null;
        String searchByTitle = searchParams.get("searchByTitle");
        String searchByProjectType = searchParams.get("searchByProjectType");
        String searchByCurrentPhase = searchParams.get("searchByCurrentPhase");

        if(searchBy==null || searchBy.isEmpty()){
            projectPage = findAllPaginated(pageable);
        } else if (searchBy.equals("Title")) {
            projectPage = findByTitle(searchByTitle, pageable);
        } else if (searchBy.equals("Type")) {
            projectPage = findByProjectType(searchByProjectType, pageable);
        } else if (searchBy.equals("Current Phase")) {
            projectPage = findByCurrentPhase(searchByCurrentPhase, pageable);
        }
        else {
            projectPage = findAllPaginated(pageable);
        }

        return projectPage;
    }
}
