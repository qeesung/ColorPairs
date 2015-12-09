package io.github.qeesung.utils;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qeesung on 2015/12/8.
 * this class used to find the backward and forward pair position relative editor caret
 */
public class PairFindHelper {
    private static Map<FileType , PairFinder> pairFinderMap = new HashMap<>();

    // register all pair finder
    static
    {
        pairFinderMap.put(JavaFileType.INSTANCE , new JavaFilePairFinder());
        pairFinderMap.put(XmlFileType.INSTANCE , new XmlFilePairFinder());
        // add some other file type and file finder here
    }

    public static TextRange getBackwardPairRange(CaretEvent caretEvent) {
        if (caretEvent == null)
            return null;

        FileType fileType = getFileType(caretEvent);

        if(!isSupportedFileType(fileType))
            return null;
        return pairFinderMap.get(fileType).getBackwardPairRange(caretEvent);
    }

    public static TextRange getForwardPairRange(CaretEvent caretEvent) {
        if (caretEvent == null)
            return null;

        FileType fileType = getFileType(caretEvent);

        if(!isSupportedFileType(fileType))
            return null;
        return pairFinderMap.get(fileType).getForwardPairRange(caretEvent);
    }

    // check if the file type is supported
    private static boolean isSupportedFileType(FileType fileType)
    {
        return pairFinderMap.containsKey(fileType);
    }

    // get the file type by caretEvent
    private static FileType getFileType(CaretEvent caretEvent)
    {
        Editor myEditor = caretEvent.getEditor();
        Project myProject = myEditor.getProject();
        Document document = myEditor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(myProject).getPsiFile(document);
        FileType fileType = psiFile.getFileType();
        return fileType;
    }
}
