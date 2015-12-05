package io.github.qeesung.utils;

import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by qeesung on 2015/12/6.
 */
public class ColorPairsListenerManager {
    private static ColorPairsListenerManager INSTANCE ;

    private Set<Project> registedEditor = new HashSet<>();
    private ColorPairsFileEditorManagerListener fileEditorListener = new ColorPairsFileEditorManagerListener();
    private ColorPairsCaretListener caretListener = new ColorPairsCaretListener();

    public static synchronized ColorPairsListenerManager getInstance()
    {
        if(INSTANCE == null)
            INSTANCE = new ColorPairsListenerManager();
        return INSTANCE;
    }

    public void registAllProjectEditor(Project ... projects)
    {
        for (Project project : projects)
        {
            if(!registedEditor.contains(project))
            {
                FileEditorManager.getInstance(project).addFileEditorManagerListener(fileEditorListener);
                registedEditor.add(project);
            }
        }
    }

    public void updateRegistedProjectEditor(Project ... projects)
    {
        registedEditor.clear();
        for (Project project : projects)
        {
            registedEditor.add(project);
        }
    }

    private class ColorPairsFileEditorManagerListener implements FileEditorManagerListener {

        @Override
        public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
            fileEditorManager.getSelectedTextEditor().getCaretModel().addCaretListener(caretListener);
        }

        @Override
        public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {

        }

        @Override
        public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {
        }
    }

    private static class ColorPairsCaretListener implements CaretListener
    {

        @Override
        public void caretPositionChanged(CaretEvent caretEvent) {
            System.out.println("Current postion is "+caretEvent.getNewPosition().toString());
        }

        @Override
        public void caretAdded(CaretEvent caretEvent) {

        }

        @Override
        public void caretRemoved(CaretEvent caretEvent) {

        }
    }

}
