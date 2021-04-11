import java.util.Stack;

public class Place {

    private String id;

    private Stack<Token> tokens;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Stack<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Stack<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return id;
    }
}
