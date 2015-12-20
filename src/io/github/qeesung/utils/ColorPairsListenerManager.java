package io.github.qeesung.utils;

import com.intellij.codeInsight.highlighting.HighlightManager;
import com.intellij.codeInsight.highlighting.HighlightUsagesHandler;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.event.CaretAdapter;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.editor.event.CaretListener;
import com.intellij.openapi.editor.event.EditorMouseListener;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.FocusManagerImpl;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.ui.ColorUtil;
import com.intellij.util.Alarm;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private class ColorPairsFileEditorManagerListener extends FileEditorManagerAdapter {

        @Override
        public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {
            fileEditorManager.getSelectedTextEditor().getCaretModel().addCaretListener(caretListener);
        }
    }

    private static class ColorPairsCaretListener extends CaretAdapter
    {

        private static int number = 0;
        private final Alarm myAlarm = new Alarm();
        @Override
        public void caretPositionChanged(CaretEvent caretEvent) {
            Editor myEditor = caretEvent.getEditor();
            Project myProject = myEditor.getProject();
//            System.out.println("Current postion is "+caretEvent.getNewPosition().toString());
//
//            Document document = myEditor.getDocument();
//            PsiFile psiFile = PsiDocumentManager.getInstance(myProject).getPsiFile(document);
//            PsiElement psiElement = psiFile.findElementAt(caretEvent.getCaret().getOffset());
//            if(psiElement != null)
//                System.out.println(psiElement.toString());

            HighlightManager highlightManager = HighlightManager.getInstance(myProject);
            EditorColorsScheme scheme = myEditor.getColorsScheme();
            final TextAttributes attributes = new TextAttributes();
            Color defaultColor = EditorColorsManager.getInstance().getGlobalScheme().getDefaultBackground();
            attributes.setEffectType(EffectType.ROUNDED_BOX);
//            attributes.setBackgroundColor(new Color(255,0,0));
            if(number%2 == 0) {
                attributes.setEffectColor(new Color(0, 255, 0));
            }
            else
                attributes.setEffectColor(defaultColor);


            JavaFilePairFinder javaFilePairFinder = new JavaFilePairFinder();
            TextRange textRange = javaFilePairFinder.getForwardPairRange(caretEvent);
            TextRange textRange1 = javaFilePairFinder.getBackwardPairRange(caretEvent);
            if(textRange == null || textRange1 == null)
                return;
            String conent = myEditor.getDocument().getText();
            System.out.println(conent.substring(textRange.getStartOffset() , textRange.getEndOffset()));
            System.out.println(conent.substring(textRange1.getStartOffset() , textRange1.getEndOffset()));
            List<TextRange> list = new ArrayList<>();
            list.add(textRange);
            list.add(textRange1);
            HighlightUsagesHandler.highlightRanges(highlightManager,myEditor , attributes,false, list);
            number++;

        }

//        @Override
//        public void caretPositionChanged(CaretEvent e) {
//            myAlarm.cancelAllRequests();
//            Editor editor = e.getEditor();
//            System.out.println("Current postion is "+e.getNewPosition().toString());
//            final SelectionModel selectionModel = editor.getSelectionModel();
//            // Don't update braces in case of the active selection.
//            if (selectionModel.hasSelection()) {
//                return;
//            }
//
//            final Document document = editor.getDocument();
//            int line = e.getNewPosition().line;
//            if (line < 0 || line >= document.getLineCount()) {
//                return;
//            }
//            BraceHighlighter.updateBraces(editor, myAlarm);
//      }
    }

}
