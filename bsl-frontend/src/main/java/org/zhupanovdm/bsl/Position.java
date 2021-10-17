package org.zhupanovdm.bsl;

import com.sonar.sslr.api.Token;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nonnull;

@Data
@EqualsAndHashCode
public class Position implements Comparable<Position> {
    private final int line;
    private final int column;

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    @Override
    public int compareTo(@Nonnull Position position) {
        int lineCmp = Integer.compare(line, position.line);
        if (lineCmp == 0) {
            return Integer.compare(column, position.column);
        }
        return lineCmp;
    }

    public static Position fromToken(Token token) {
        return new Position(token.getLine(), token.getColumn());
    }
}
