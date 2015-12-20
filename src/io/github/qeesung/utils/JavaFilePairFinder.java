package io.github.qeesung.utils;

import com.intellij.lang.java.JavaPsiElementExternalizer;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.tree.IElementType;
import io.github.qeesung.data.PairSymbol;

import java.util.Map;
import java.util.Stack;

/**
 * Created by qeesung on 2015/12/8.
 */
public class JavaFilePairFinder implements PairFinder {

    private static Map<String , String> pairedPairSymbol = null;
    private static Map<String , String> reversePairedPairSymbol = null;

    static
    {
        pairedPairSymbol = PairSymbol.getPairedPairMap();
        reversePairedPairSymbol = PairSymbol.getReversePairedPairMap();
    }


    @Override
    public TextRange getBackwardPairRange(CaretEvent caretEvent) {
        return startToIFindPairSymbol(caretEvent, false);
    }

    @Override
    public TextRange getForwardPairRange(CaretEvent caretEvent) {
        return startToIFindPairSymbol(caretEvent, true);
    }

    private TextRange startToIFindPairSymbol(CaretEvent caretEvent , boolean forward)
    {
        if (caretEvent == null)
            return  null;

        boolean firstTime = true;


        Editor editor = caretEvent.getEditor();
        Project project = editor.getProject();
        Document document = editor.getDocument();
        int offset = caretEvent.getCaret().getOffset();
        if(project == null || document == null || offset < 0 || offset >= document.getText().length())
            return null;

        // create a stack for analysis the char sequence
        Stack<String> stack = new Stack<>();

        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        // get the data
        String content = document.getText();

        if(!forward && offset > 0)
            offset--;

        while(offset >= 0 && offset < content.length())
        {

            PsiElement psiElement = psiFile.findElementAt(offset);
            IElementType elementType = null;
            PsiJavaToken psiJavaToken;
            if(psiElement instanceof PsiJavaToken) {
                psiJavaToken = (PsiJavaToken) psiElement;
                elementType = psiJavaToken.getTokenType();
            }
            else
            {
                offset = forward?offset+1 : offset-1;
                continue;
            }

            if(elementType.equals(ElementType.WHITE_SPACE)) // check if a white space
            {
                offset = forward?offset+1 : offset-1;
            }
            else if(offset> 1 &&
                    checkType(psiFile , forward?offset:offset+1 , ElementType.STRING_LITERAL) &&
                    checkType(psiFile , forward?offset-1:offset, ElementType.STRING_LITERAL)) // check if in a string
            {
                // search for forward " and backward "
                if (!forward)
                    offset++;
                while(offset >= 0 && offset < content.length())
                {
                    offset = forward?offset+1 : offset-1;
                    if(offset> 1 &&
                    checkType(psiFile , offset , ElementType.STRING_LITERAL) &&
                    checkType(psiFile , offset-1 , ElementType.STRING_LITERAL))
                        continue;
                    if(firstTime)
                        return forward?new TextRange(offset-1 , offset):new TextRange(offset, offset+1);
                    else
                        break;
                }
            }
            else if(offset> 1 &&
                    checkType(psiFile , forward?offset:offset+1 , ElementType.CHARACTER_LITERAL) &&
                    checkType(psiFile , forward?offset-1:offset , ElementType.CHARACTER_LITERAL)) // check if in a char
            {
                // search for forward ' and backward '
                if(!forward)
                    offset++;
                while(offset >= 0 && offset < content.length())
                {
                    offset = forward?offset+1 : offset-1;
                    if(offset> 1 &&
                    checkType(psiFile , offset , ElementType.CHARACTER_LITERAL) &&
                    checkType(psiFile , offset-1 , ElementType.CHARACTER_LITERAL))
                        continue;
                    if(firstTime )
                        return forward?new TextRange(offset-1 , offset):new TextRange(offset, offset+1);
                    else
                        break;
                }
            }
            else if(elementType.toString().startsWith("DOC_") &&
                        !elementType.equals(ElementType.DOC_COMMENT_START)) // not in a string and check if in a block comment
            {
                offset = forward?offset+1 : offset-1;
            }
            else if(offset > 1 &&
                    checkType(psiFile , offset , ElementType.END_OF_LINE_COMMENT) &&
                    checkType(psiFile , offset-1 , ElementType.END_OF_LINE_COMMENT)) // check if the caret in a line comment
            {
                offset = forward?offset+1 : offset-1;
            }
            else // not in string , not in block comment , not in line comment
            {

                Map<String , String > pairedMap = forward?pairedPairSymbol:reversePairedPairSymbol;
                Map<String , String > reversePairedMap = forward?reversePairedPairSymbol:pairedPairSymbol;

                // get the char at the offset
                if(offset >= content.length())
                    return null;
                String strChar = content.substring(offset , offset+1);

                if(pairedMap.containsKey(strChar) && reversePairedMap.containsKey(strChar))
                {
                    while(offset >= 0 && offset < content.length())
                    {
                        offset = forward?offset+1 : offset-1;
                        if(offset> 1 &&
                                ( ( checkType(psiFile , offset , ElementType.CHARACTER_LITERAL) &&
                                checkType(psiFile , offset-1 , ElementType.CHARACTER_LITERAL) ) ||
                                 ( checkType(psiFile , offset , ElementType.STRING_LITERAL) &&
                                checkType(psiFile , offset-1 , ElementType.STRING_LITERAL) )))
                            continue;
                        if(forward)
                            offset--;
                        break;
                    }
                }
                else if(pairedMap.containsKey(strChar))
                {
                    stack.push(strChar);
                }
                else if(reversePairedMap.containsKey(strChar))
                {
                    String reverseSymbol="";
                    if(stack.size() > 0)
                    reverseSymbol = pairedMap.get(stack.peek());
                    if(reverseSymbol.equals(strChar))
                        stack.pop();
                    else
                        return new TextRange(offset,offset+1);
                }
                offset = forward?offset+1 : offset-1;
            }
            firstTime = false;

        }
        return  null;
    }


    private boolean checkType(PsiFile psiFile , int offset , IElementType elementType)
    {
        PsiElement tempElement = psiFile.findElementAt(offset);
        IElementType tempType = null;
        if(tempElement instanceof PsiJavaToken)
            tempType = ((PsiJavaToken)tempElement).getTokenType();
        else
            return false;
        return tempType.equals(elementType);
    }
}
