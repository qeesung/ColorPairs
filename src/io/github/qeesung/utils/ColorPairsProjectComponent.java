package io.github.qeesung.utils;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;

/**
 * Created by qeesung on 2015/12/5.
 */
public class ColorPairsProjectComponent implements ProjectComponent {

    @Override
    public void projectOpened() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        // register editor action for all project editors
        ColorPairsListenerManager.getInstance().registAllProjectEditor(projects);
    }

    @Override
    public void projectClosed() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        // refresh the registed project editors
        ColorPairsListenerManager.getInstance().updateRegistedProjectEditor(projects);
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "ColorPairs";
    }
}





