package elements;

import visitors.Visitor;

public interface Element {
    void accept(Visitor visitor);
}
