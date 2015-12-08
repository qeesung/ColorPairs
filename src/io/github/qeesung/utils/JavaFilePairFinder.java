package io.github.qeesung.utils;

import com.intellij.openapi.editor.event.CaretEvent;
import com.intellij.openapi.util.TextRange;
import io.github.qeesung.data.PairColorProperty;

/**
 * Created by qeesung on 2015/12/8.
 */
public class JavaFilePairFinder implements PairFinder {
    @Override
    public TextRange getBackwardPairRange(CaretEvent caretEvent) {
        return null;
    }

    @Override
    public TextRange getForwardPairRange(CaretEvent caretEvent) {
        return null;
    }
}
