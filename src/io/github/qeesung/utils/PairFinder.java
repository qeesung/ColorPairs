package io.github.qeesung.utils;

import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.xmlb.annotations.Text;

/**
 * Created by qeesung on 2015/12/8.
 * this is a interface that declare the methods that
 * how to find backward-pair and forward-pair relative editor caret
 */
public interface PairFinder {
    TextRange getBackwardPairRange(CaretEvent caretEvent);
    TextRange getForwardPairRange(CaretEvent caretEvent);
}
