package io.github.qeesung.utils;

import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.util.TextRange;

/**
 * Created by qeesung on 2015/12/8.
 */
public class XmlFilePairFinder implements PairFinder {
    @Override
    public TextRange getBackwardPairRange(CaretEvent caretEvent) {
        return null;
    }

    @Override
    public TextRange getForwardPairRange(CaretEvent caretEvent) {
        return null;
    }
}
