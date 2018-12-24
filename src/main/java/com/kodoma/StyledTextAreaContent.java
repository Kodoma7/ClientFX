package com.kodoma;

import org.eclipse.fx.ui.controls.styledtext.StyledTextContent;
import org.eclipse.fx.ui.controls.styledtext.TextChangedEvent;
import org.eclipse.fx.ui.controls.styledtext.TextChangingEvent;

import java.util.Iterator;
import java.util.Vector;

/**
 * StyledTextAreaContent class.
 * Created on 24.12.2018.
 * @author Kodoma.
 */
public class StyledTextAreaContent implements StyledTextContent {

    private static final String LineDelimiter = System.getProperty("line.separator");

    Vector<TextChangeListener> textListeners = new Vector();

    char[] textStore = new char[0];

    int gapStart = -1;

    int gapEnd = -1;

    int gapLine = -1;

    int highWatermark = 300;

    int lowWatermark = 50;

    int[][] lines = new int[50][2];

    int lineCount = 0;

    int expandExp = 1;

    int replaceExpandExp = 1;

    StyledTextAreaContent() {
        this.setText("");
    }

    void addLineIndex(int start, int length) {
        int size = this.lines.length;
        if (this.lineCount == size) {
            int[][] newLines = new int[size + StyledTextAreaContent.Compatibility.pow2(this.expandExp)][2];
            System.arraycopy(this.lines, 0, newLines, 0, size);
            this.lines = newLines;
            ++this.expandExp;
        }

        int[] range = new int[]{start, length};
        this.lines[this.lineCount] = range;
        ++this.lineCount;
    }

    int[][] addLineIndex(int start, int length, int[][] linesArray, int count) {
        int size = linesArray.length;
        int[][] newLines = linesArray;
        if (count == size) {
            newLines = new int[size + StyledTextAreaContent.Compatibility.pow2(this.replaceExpandExp)][2];
            ++this.replaceExpandExp;
            System.arraycopy(linesArray, 0, newLines, 0, size);
        }

        int[] range = new int[]{start, length};
        newLines[count] = range;
        return newLines;
    }

    public void addTextChangeListener(TextChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException();
        } else {
            this.textListeners.addElement(listener);
        }
    }

    void adjustGap(int position, int sizeHint, int line) {
        int size;
        if (position == this.gapStart) {
            size = this.gapEnd - this.gapStart - sizeHint;
            if (this.lowWatermark <= size && size <= this.highWatermark) {
                return;
            }
        } else if (position + sizeHint == this.gapStart && sizeHint < 0) {
            size = this.gapEnd - this.gapStart - sizeHint;
            if (this.lowWatermark <= size && size <= this.highWatermark) {
                return;
            }
        }

        this.moveAndResizeGap(position, sizeHint, line);
    }

    void indexLines() {
        int start = 0;
        this.lineCount = 0;
        int textLength = this.textStore.length;

        int i;
        for (i = start; i < textLength; ++i) {
            char ch = this.textStore[i];
            if (ch == '\r') {
                if (i + 1 < textLength) {
                    ch = this.textStore[i + 1];
                    if (ch == '\n') {
                        ++i;
                    }
                }

                this.addLineIndex(start, i - start + 1);
                start = i + 1;
            } else if (ch == '\n') {
                this.addLineIndex(start, i - start + 1);
                start = i + 1;
            }
        }

        this.addLineIndex(start, i - start);
    }

    static boolean isDelimiter(char ch) {
        if (ch == '\r') {
            return true;
        } else {
            return ch == '\n';
        }
    }

    protected boolean isValidReplace(int start, int replaceLength, String newText) {
        char before;
        char after;
        if (replaceLength == 0) {
            if (start == 0) {
                return true;
            }

            if (start == this.getCharCount()) {
                return true;
            }

            before = this.getTextRange(start - 1, 1).charAt(0);
            if (before == '\r') {
                after = this.getTextRange(start, 1).charAt(0);
                if (after == '\n') {
                    return false;
                }
            }
        } else {
            before = this.getTextRange(start, 1).charAt(0);
            if (before == '\n' && start != 0) {
                after = this.getTextRange(start - 1, 1).charAt(0);
                if (after == '\r') {
                    return false;
                }
            }

            after = this.getTextRange(start + replaceLength - 1, 1).charAt(0);
            if (after == '\r' && start + replaceLength != this.getCharCount()) {
                after = this.getTextRange(start + replaceLength, 1).charAt(0);
                if (after == '\n') {
                    return false;
                }
            }
        }

        return true;
    }

    int[][] indexLines(int offset, int length, int numLines) {
        int[][] indexedLines = new int[numLines][2];
        int start = 0;
        int lineCount = 0;
        this.replaceExpandExp = 1;

        int i;
        for (i = start; i < length; ++i) {
            int location = i + offset;
            if (location < this.gapStart || location >= this.gapEnd) {
                char ch = this.textStore[location];
                if (ch == '\r') {
                    if (location + 1 < this.textStore.length) {
                        ch = this.textStore[location + 1];
                        if (ch == '\n') {
                            ++i;
                        }
                    }

                    indexedLines = this.addLineIndex(start, i - start + 1, indexedLines, lineCount);
                    ++lineCount;
                    start = i + 1;
                } else if (ch == '\n') {
                    indexedLines = this.addLineIndex(start, i - start + 1, indexedLines, lineCount);
                    ++lineCount;
                    start = i + 1;
                }
            }
        }

        int[][] newLines = new int[lineCount + 1][2];
        System.arraycopy(indexedLines, 0, newLines, 0, lineCount);
        int[] range = new int[]{start, i - start};
        newLines[lineCount] = range;
        return newLines;
    }

    void insert(int position, String text) {
        if (text.length() != 0) {
            int startLine = this.getLineAtOffset(position);
            int change = text.length();
            boolean endInsert = position == this.getCharCount();
            this.adjustGap(position, change, startLine);
            int startLineOffset = this.getOffsetAtLine(startLine);
            int startLineLength = this.getPhysicalLine(startLine).length();
            if (change > 0) {
                this.gapStart += change;

                for (int i = 0; i < text.length(); ++i) {
                    this.textStore[position + i] = text.charAt(i);
                }
            }

            int[][] newLines = this.indexLines(startLineOffset, startLineLength, 10);
            int numNewLines = newLines.length - 1;
            if (newLines[numNewLines][1] == 0) {
                if (endInsert) {
                    ++numNewLines;
                } else {
                    --numNewLines;
                }
            }

            this.expandLinesBy(numNewLines);

            int i;
            for (i = this.lineCount - 1; i > startLine; --i) {
                this.lines[i + numNewLines] = this.lines[i];
            }

            for (i = 0; i < numNewLines; ++i) {
                newLines[i][0] += startLineOffset;
                this.lines[startLine + i] = newLines[i];
            }

            if (numNewLines < newLines.length) {
                newLines[numNewLines][0] += startLineOffset;
                this.lines[startLine + numNewLines] = newLines[numNewLines];
            }

            this.lineCount += numNewLines;
            this.gapLine = this.getLineAtPhysicalOffset(this.gapStart);
        }
    }

    void moveAndResizeGap(int position, int size, int newGapLine) {
        char[] content = null;
        int oldSize = this.gapEnd - this.gapStart;
        int newSize;
        if (size > 0) {
            newSize = this.highWatermark + size;
        } else {
            newSize = this.lowWatermark - size;
        }

        if (this.gapExists()) {
            this.lines[this.gapLine][1] -= oldSize;

            for (int i = this.gapLine + 1; i < this.lineCount; ++i) {
                this.lines[i][0] -= oldSize;
            }
        }

        if (newSize < 0) {
            if (oldSize > 0) {
                content = new char[this.textStore.length - oldSize];
                System.arraycopy(this.textStore, 0, content, 0, this.gapStart);
                System.arraycopy(this.textStore, this.gapEnd, content, this.gapStart, content.length - this.gapStart);
                this.textStore = content;
            }

            this.gapStart = this.gapEnd = position;
        } else {
            content = new char[this.textStore.length + (newSize - oldSize)];
            int newGapEnd = position + newSize;
            int gapLength;
            if (oldSize == 0) {
                System.arraycopy(this.textStore, 0, content, 0, position);
                System.arraycopy(this.textStore, position, content, newGapEnd, content.length - newGapEnd);
            } else if (position < this.gapStart) {
                gapLength = this.gapStart - position;
                System.arraycopy(this.textStore, 0, content, 0, position);
                System.arraycopy(this.textStore, position, content, newGapEnd, gapLength);
                System.arraycopy(this.textStore, this.gapEnd, content, newGapEnd + gapLength, this.textStore.length - this.gapEnd);
            } else {
                gapLength = position - this.gapStart;
                System.arraycopy(this.textStore, 0, content, 0, this.gapStart);
                System.arraycopy(this.textStore, this.gapEnd, content, this.gapStart, gapLength);
                System.arraycopy(this.textStore, this.gapEnd + gapLength, content, newGapEnd, content.length - newGapEnd);
            }

            this.textStore = content;
            this.gapStart = position;
            this.gapEnd = newGapEnd;
            if (this.gapExists()) {
                this.gapLine = newGapLine;
                gapLength = this.gapEnd - this.gapStart;
                this.lines[this.gapLine][1] += gapLength;

                for (int i = this.gapLine + 1; i < this.lineCount; ++i) {
                    this.lines[i][0] += gapLength;
                }
            }

        }
    }

    int lineCount(int startOffset, int length) {
        if (length == 0) {
            return 0;
        } else {
            int lineCount = 0;
            int count = 0;
            int i = startOffset;
            if (startOffset >= this.gapStart) {
                i = startOffset + (this.gapEnd - this.gapStart);
            }

            for (; count < length; ++i) {
                if (i < this.gapStart || i >= this.gapEnd) {
                    char ch = this.textStore[i];
                    if (ch == '\r') {
                        if (i + 1 < this.textStore.length) {
                            ch = this.textStore[i + 1];
                            if (ch == '\n') {
                                ++i;
                                ++count;
                            }
                        }

                        ++lineCount;
                    } else if (ch == '\n') {
                        ++lineCount;
                    }

                    ++count;
                }
            }

            return lineCount;
        }
    }

    static int lineCount(String text) {
        int lineCount = 0;
        int length = text.length();

        for (int i = 0; i < length; ++i) {
            char ch = text.charAt(i);
            if (ch == '\r') {
                if (i + 1 < length && text.charAt(i + 1) == '\n') {
                    ++i;
                }

                ++lineCount;
            } else if (ch == '\n') {
                ++lineCount;
            }
        }

        return lineCount;
    }

    public int getCharCount() {
        int length = this.gapEnd - this.gapStart;
        return this.textStore.length - length;
    }

    public String getLine(int index) {
        if (index < this.lineCount && index >= 0) {
            int start = this.lines[index][0];
            int length = this.lines[index][1];
            int end = start + length - 1;
            if (this.gapExists() && end >= this.gapStart && start < this.gapEnd) {
                StringBuffer buf = new StringBuffer();
                int gapLength = this.gapEnd - this.gapStart;
                buf.append(this.textStore, start, this.gapStart - start);
                buf.append(this.textStore, this.gapEnd, length - gapLength - (this.gapStart - start));

                for (length = buf.length(); length - 1 >= 0 && isDelimiter(buf.charAt(length - 1)); --length) {
                }

                return buf.toString().substring(0, length);
            } else {
                while (length - 1 >= 0 && isDelimiter(this.textStore[start + length - 1])) {
                    --length;
                }

                return new String(this.textStore, start, length);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String getLineDelimiter() {
        return LineDelimiter;
    }

    String getFullLine(int index) {
        int start = this.lines[index][0];
        int length = this.lines[index][1];
        int end = start + length - 1;
        if (this.gapExists() && end >= this.gapStart && start < this.gapEnd) {
            StringBuffer buffer = new StringBuffer();
            int gapLength = this.gapEnd - this.gapStart;
            buffer.append(this.textStore, start, this.gapStart - start);
            buffer.append(this.textStore, this.gapEnd, length - gapLength - (this.gapStart - start));
            return buffer.toString();
        } else {
            return new String(this.textStore, start, length);
        }
    }

    String getPhysicalLine(int index) {
        int start = this.lines[index][0];
        int length = this.lines[index][1];
        return this.getPhysicalText(start, length);
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public int getLineAtOffset(int charPosition) {
        if (charPosition <= this.getCharCount() && charPosition >= 0) {
            int position;
            if (charPosition < this.gapStart) {
                position = charPosition;
            } else {
                position = charPosition + (this.gapEnd - this.gapStart);
            }

            int high;
            if (this.lineCount > 0) {
                high = this.lineCount - 1;
                if (position == this.lines[high][0] + this.lines[high][1]) {
                    return high;
                }
            }

            high = this.lineCount;
            int low = -1;
            int index = this.lineCount;

            while (high - low > 1) {
                index = (high + low) / 2;
                int lineStart = this.lines[index][0];
                int lineEnd = lineStart + this.lines[index][1] - 1;
                if (position <= lineStart) {
                    high = index;
                } else {
                    if (position <= lineEnd) {
                        high = index;
                        break;
                    }

                    low = index;
                }
            }

            return high;
        } else {
            throw new IllegalArgumentException();
        }
    }

    int getLineAtPhysicalOffset(int position) {
        int high = this.lineCount;
        int low = -1;
        int index = this.lineCount;

        while (high - low > 1) {
            index = (high + low) / 2;
            int lineStart = this.lines[index][0];
            int lineEnd = lineStart + this.lines[index][1] - 1;
            if (position <= lineStart) {
                high = index;
            } else {
                if (position <= lineEnd) {
                    high = index;
                    break;
                }

                low = index;
            }
        }

        return high;
    }

    public int getOffsetAtLine(int lineIndex) {
        if (lineIndex == 0) {
            return 0;
        } else if (lineIndex < this.lineCount && lineIndex >= 0) {
            int start = this.lines[lineIndex][0];
            return start > this.gapEnd ? start - (this.gapEnd - this.gapStart) : start;
        } else {
            throw new IllegalArgumentException();
        }
    }

    void expandLinesBy(int numLines) {
        int size = this.lines.length;
        if (size - this.lineCount < numLines) {
            int[][] newLines = new int[size + Math.max(10, numLines)][2];
            System.arraycopy(this.lines, 0, newLines, 0, size);
            this.lines = newLines;
        }
    }

    boolean gapExists() {
        return this.gapStart != this.gapEnd;
    }

    String getPhysicalText(int start, int length) {
        return new String(this.textStore, start, length);
    }

    public String getTextRange(int start, int length) {
        if (this.textStore == null) {
            return "";
        } else if (length == 0) {
            return "";
        } else {
            int end = start + length;
            if (this.gapExists() && end >= this.gapStart) {
                if (this.gapStart < start) {
                    int gapLength = this.gapEnd - this.gapStart;
                    return new String(this.textStore, start + gapLength, length);
                } else {
                    StringBuffer buf = new StringBuffer();
                    buf.append(this.textStore, start, this.gapStart - start);
                    buf.append(this.textStore, this.gapEnd, end - this.gapStart);
                    return buf.toString();
                }
            } /*else if (this.textStore.length == 0) {
                return StringUtils.EMPTY;
            } */else {
                return new String(this.textStore, start, length);
            }
        }
    }

    public void removeTextChangeListener(TextChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException();
        } else {
            this.textListeners.remove(listener);
        }
    }

    public void replaceTextRange(int start, int replaceLength, String newText) {
        // TODO edit
        if (start + replaceLength == 0) {
            return;
        }
        if (!this.isValidReplace(start, replaceLength, newText)) {
            throw new IllegalArgumentException();
        } else {
            TextChangingEvent event = TextChangingEvent.textChanging(this, start, replaceLength, this.lineCount(start, replaceLength), newText, newText.length(), lineCount(newText));
            Iterator var6 = this.textListeners.iterator();

            while (var6.hasNext()) {
                TextChangeListener l = (TextChangeListener)var6.next();
                l.textChanging(event);
            }

            this.delete(start, replaceLength, event.replaceLineCount + 1);
            this.insert(start, newText);
            TextChangedEvent textChanged = TextChangedEvent.textChanged(this);
            Iterator var7 = this.textListeners.iterator();

            while (var7.hasNext()) {
                TextChangeListener l = (TextChangeListener)var7.next();
                l.textChanged(textChanged);
            }

            System.err.println(this.getTextRange(0, this.getCharCount()));
        }
    }

    public void setText(String text) {
        this.textStore = text.toCharArray();
        this.gapStart = -1;
        this.gapEnd = -1;
        this.expandExp = 1;
        this.indexLines();
        TextChangedEvent textSet = TextChangedEvent.textSet(this);
        Iterator var4 = this.textListeners.iterator();

        while (var4.hasNext()) {
            TextChangeListener l = (TextChangeListener)var4.next();
            l.textSet(textSet);
        }

    }

    void delete(int position, int length, int numLines) {
        if (length != 0) {
            int startLine = this.getLineAtOffset(position);
            int startLineOffset = this.getOffsetAtLine(startLine);
            int endLine = this.getLineAtOffset(position + length);
            String endText = "";
            boolean splittingDelimiter = false;
            if (position + length < this.getCharCount()) {
                endText = this.getTextRange(position + length - 1, 2);
                if (endText.charAt(0) == '\r' && endText.charAt(1) == '\n') {
                    splittingDelimiter = true;
                }
            }

            this.adjustGap(position + length, -length, startLine);
            int[][] oldLines = this.indexLines(position, length + (this.gapEnd - this.gapStart), numLines);
            if (position + length == this.gapStart) {
                this.gapStart -= length;
            } else {
                this.gapEnd += length;
            }

            int j = position;

            for (boolean eol = false; j < this.textStore.length && !eol; ++j) {
                if (j < this.gapStart || j >= this.gapEnd) {
                    char ch = this.textStore[j];
                    if (isDelimiter(ch)) {
                        if (j + 1 < this.textStore.length && ch == '\r' && this.textStore[j + 1] == '\n') {
                            ++j;
                        }

                        eol = true;
                    }
                }
            }

            this.lines[startLine][1] = position - startLineOffset + (j - position);
            int numOldLines = oldLines.length - 1;
            if (splittingDelimiter) {
                --numOldLines;
            }

            for (int i = endLine + 1; i < this.lineCount; ++i) {
                this.lines[i - numOldLines] = this.lines[i];
            }

            this.lineCount -= numOldLines;
            this.gapLine = this.getLineAtPhysicalOffset(this.gapStart);
        }
    }

    static class Compatibility {

        Compatibility() {
        }

        public static int pow2(int n) {
            if (n >= 1 && n <= 30) {
                return 2 << n - 1;
            } else if (n != 0) {
                throw new IllegalArgumentException();
            } else {
                return 1;
            }
        }
    }

    static class SWT {

        public static final char CR = '\r';

        public static final char LF = '\n';

        SWT() {
        }
    }
}
